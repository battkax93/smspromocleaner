package sunny.smspromocleaner;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends MainController {

    String keyContent = "keyContent";
    String keyAddress = "keyAddress";
    EditText etContent;
    FancyButton bContent;
    LottieAnimationView lottieAnimationView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initControl();
        reqPermission();
    }

    @SuppressLint("CommitPrefEdits")
    public void init() {

        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        etContent = findViewById(R.id.et_content);
        bContent = findViewById(R.id.btn_content);
        lottieAnimationView = findViewById(R.id.lottieAnimationView);

        lottieAnimationView.setAnimation("email.json");
        lottieAnimationView.playAnimation();
        lottieAnimationView.loop(true);

    }

    public void initControl() {
        bContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cont = etContent.getText().toString().trim().toLowerCase();
                editor.putString(keyContent, cont);
                editor.apply();
                System.out.println("==cek array " + cont);
                if (TextUtils.isEmpty(cont)) etContent.setError("cannot be empty");
                toArrayContent(getApplicationContext(), cont);
            }
        });
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
        System.out.println("==onPause");
        String cont = etContent.getText().toString();
        editor.putString(keyContent,cont);
        editor.apply();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        System.out.println("==onResume");
        String cont = pref.getString(keyContent,null);
        etContent.setText(cont);
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
