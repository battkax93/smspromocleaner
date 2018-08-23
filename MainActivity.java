package sunny.smspromocleaner;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    Button bContent, bAddress;
    FilterMessages fm;
    String[] row;
    String[] filter = {"POTONGAN", "diskon", "liburan", "bebas",    "samsung"};
    String[] filter2 = {"xl-axiata","hiburanasik","xlprioritas","168","spektrat"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        row = getResources().getStringArray(R.array.row);
        fm = new FilterMessages();
        reqPermission();
        init();
    }

    public void init(){
        bContent = findViewById(R.id.b_filter_content);
        bAddress = findViewById(R.id.b_filter_address);

        bContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.deleteThreadbyContent(getApplicationContext(),filter,row);
            }
        });

        bAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.deleteThreadbyAddress(getApplicationContext(),filter2,row);
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
