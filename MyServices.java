package sunny.smspromocleaner;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Wayan-MECS on 8/28/2018.
 */

public class
MyServices extends Service {

    public class myService extends Binder {
        public final Service service;

        public myService(Service service) {
            this.service = service;
        }

    }

    public static int NOTIFICATION_ID = 0;
    private final IBinder mBinder = new myService(this);
    BroadcastReceiver mReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }
    @Override
    public void onCreate() {
        final IntentFilter it = new IntentFilter();
        it.addAction("android.provider.Telephony.SMS_RECEIVED");
        mReceiver = new SmsReceiver();
        registerReceiver(mReceiver, it);
    }
    /*@Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showSuccess () {
        Toast.makeText(this, "broadcast received", Toast.LENGTH_SHORT).show();
    }

    BroadcastReceiver mReceiver;

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showSuccess();
        }
        public MyReceiver() {

        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("==MyServices");
        Toast.makeText(getApplicationContext(), "onCreate() has been executed", Toast.LENGTH_SHORT).show();
        final IntentFilter it = new IntentFilter();
        it.addAction("android.provider.Telephony.SMS_RECEIVED");
        mReceiver = new SmsReceiver();
        registerReceiver(mReceiver, it);
    }*/
}
