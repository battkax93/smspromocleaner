package sunny.smspromocleaner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class MainActivity extends MainController {

    SharedPreferences pref;
    SharedPreferences.Editor editor;


    ContentFragment contentFragment;
    AddressFragment addressFragment;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    String[] title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        reqPermission();
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

    @Override
    protected void onPause() {
        super.onPause();
        /*System.out.println("==onPause");
        String cont = etContent.getText().toString();
        String add = etAddress.getText().toString();
        editor.putString(keyAddress,add);
        editor.putString(keyContent,cont);
        editor.apply();*/
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
      /*  System.out.println("==onResume");
        String cont = pref.getString(keyContent,null);
        String add = pref.getString(keyAddress, null);
        etContent.setText(cont);
        etAddress.setText(add);*/
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
