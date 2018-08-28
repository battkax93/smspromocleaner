package sunny.smspromocleaner;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;


public class SmsReceiver extends BroadcastReceiver {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String keyAddress = "keyAddress";
    private static final String ACTION = "android.provider.Telephony.RECEIVE_SMS";
    int MSG_TPE = 0;

    @SuppressLint("CommitPrefEdits")
    @Override
    public void onReceive(Context context, Intent intent) {
        pref = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        String add = pref.getString(keyAddress, null);
        String[] add2 = add != null ? add.split(",") : new String[0];

        String MSG_TPE = intent.getAction();
        System.out.println("==cek " + MSG_TPE);
        Log.d("==cek","rec");

        if (MSG_TPE != null && MSG_TPE.equals(ACTION)) {
            Bundle bundle = intent.getExtras();
            Object messages[] = (Object[]) (bundle != null ? bundle.get("pdus") : null);
            assert messages != null;
            for (Object message : messages) {
                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) message);
                String pNumber = currentMessage.getDisplayOriginatingAddress();
                for (String add3 : add2) {
                    if (pNumber != null && pNumber.equalsIgnoreCase(add3)) {
                        Toast.makeText(context, "blocked sms", Toast.LENGTH_SHORT).show();
                        abortBroadcast();
                    }
                }
            }

        }
    }
}
