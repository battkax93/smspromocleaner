package sunny.smspromocleaner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

import sunny.smspromocleaner.adapter.ViewPagerAdapter;
import sunny.smspromocleaner.fragment.AddressFragment;
import sunny.smspromocleaner.fragment.ContentFragment;
import sunny.smspromocleaner.services.MyJobServices;

public class MainActivity extends MainController {

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    ContentFragment contentFragment;
    AddressFragment addressFragment;

    String keyBC = "keyBC";

    BroadcastReceiver mReceiver;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    String[] title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        reqPermission();
//        scheduler();
//        registB();
    }

    @SuppressLint("CommitPrefEdits")
    public void init() {

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        title = getResources().getStringArray(R.array.title);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        trySetupTabIcons(tabLayout);

    }

    public void trySetupTabIcons(TabLayout tl) {
        try {
            setupTabIcons(tl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        contentFragment = new ContentFragment();
        addressFragment = new AddressFragment();
        adapter.addFragment(contentFragment, "GROUP");
        adapter.addFragment(addressFragment, "SHAKE");
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);
    }

    private View prepareTabView(int pos) {
        View view = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(title[pos]);

        return view;
    }

    private void setupTabIcons(TabLayout tl) {

        for (int i = 0; i < title.length; i++) {
            tl.getTabAt(i).setCustomView(prepareTabView(i));
        }

    }

    public void reqPermission() {

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.SEND_SMS
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                System.out.println("==permissionGranted");
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {/* ... */}
        }).check();
    }

    public void scheduler() {
        Log.d("==","scheduler");

        int s = 1;

        ComponentName serviceComponent = new ComponentName(getApplicationContext(), MyJobServices.class);
        JobInfo.Builder builder = new JobInfo.Builder(s, serviceComponent);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);

        //interval

        if (Build.VERSION.SDK_INT >= 26) {
            builder.setPeriodic(15 * 60 * 1000);
        } else if (Build.VERSION.SDK_INT >= 24) {
            builder.setPeriodic(10 * 60 * 1000);
        } else {
            builder.setPeriodic(10 * 1000);
        }

        JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
        assert jobScheduler != null;
        jobScheduler.schedule(builder.build());
    }

    public void registB() {
        System.out.println("==registB");
        Log.d("==cek", "registB");
        final IntentFilter it = new IntentFilter();
        it.addAction("android.provider.Telephony.SMS_RECEIVED");
        mReceiver = new SmsReceiver();
        registerReceiver(mReceiver, it);
    }

    public void cekSms() {

        System.out.println("==cekSms");
        Log.d("cek", "==cekSms");


        Uri allMessage = Uri.parse("content://sms/");
        ContentResolver cr = getContentResolver();
        Cursor c = cr.query(allMessage, null, null, null, null);
//        while  (c.moveToNext()) {
//            String row = c.getString(1);
//            System.out.println("cek " + row);
//            Log.d("cek ", row);
       /* try {
            while (c.moveToNext()) {
                int id = c.getInt(0);
                String body = c.getString(2);
                if (body.contains("tanpa diundi")) {
                    getApplicationContext().getContentResolver().delete(
                            Uri.parse("content://sms/" + id), null, null);
                }

            }
        } catch (Exceeption e) {
            e.printStackTrace();
        }*/
        System.out.println("cek" + c.getCount());
    }

    public boolean deleteMessages(String smsId) {
        Boolean isSmsDelete = false;
        try {
            getContentResolver().delete(Uri.parse("content://sms/" + smsId), null, null);
            isSmsDelete = true;
        } catch (Exception e) {
            isSmsDelete = false;
        }
        return isSmsDelete;
    }
}
