package sunny.smspromocleaner;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class FilterMessages {

    public void deleteThreadbyContent(Context ctx, String[] filterList, String[] row) {
        Cursor c = ctx.getContentResolver().query(
                Uri.parse("content://sms/"),
                row, null, null, null);
        filterMsgbyContent(c, filterList, ctx);
    }

    public void deleteThreadbyAddress(Context ctx, String[] filterList, String[] row) {
        Cursor c = ctx.getContentResolver().query(
                Uri.parse("content://sms/"),
                row, null, null, null);
        filterMsgbyAddress(c, filterList, ctx);
    }

    public void filterMsgbyContent(Cursor c, String[] filterList, Context ctx) {
        ArrayList<String> x = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String address = c.getString(2);
            String body = c.getString(5).toLowerCase();
            System.out.println("==cek " + id + " , " + address);
            for (String aFilterList : filterList) {
                if (body.contains(aFilterList.trim().toLowerCase())) {
                    x.add("1");
                    System.out.println("==ketemu" + aFilterList);
                    ctx.getContentResolver().delete(
                            Uri.parse("content://sms/" + id), null, null);
                }
            }
        }

        String countDeleted = String.valueOf(x.size());
        showDialog(countDeleted, ctx);
    }

    public void filterMsgbyAddress(Cursor c, String[] filterList, Context ctx) {
        ArrayList<String> x = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String address = c.getString(2);
            System.out.println("==cek " + id + " , " + address);
            for (String aFilterList : filterList) {
                if (address != null && address.equalsIgnoreCase(aFilterList.trim().toLowerCase())) {
                    x.add("1");
                    System.out.println("==ketemu" + aFilterList);
                    ctx.getContentResolver().delete(
                            Uri.parse("content://sms/inbox/" + id), null, null);
                }
            }
        }
        String countDeleted = String.valueOf(x.size());
        showDialog(countDeleted, ctx);
        /*Toast.makeText(ctx,"successfully cleaning " + countDeleted +
                " promo messages from the inbox",Toast.LENGTH_SHORT).show();*/
    }

    public void showDialog(String i, Context ctx) {
        MainController mc = new MainController();
        mc.showDialogCodeNumber(i, ctx);
    }
/*
    public void hdl(final int id, final Context ctx){
        android.os.Handler h = new android.os.Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                ctx.getContentResolver().delete(
                        Uri.parse("content://sms/inbox/" + id), null, null);
            }
        },2000);
    }
*/
}


