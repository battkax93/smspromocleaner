package sunny.smspromocleaner;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.util.ArrayList;
import java.util.Arrays;

import sunny.smspromocleaner.activity.MainController;

public class FilterMessages {

    public String[] countMsgbyContent(Context ctx, String[] row) {
        Cursor c = ctx.getContentResolver().query(
                Uri.parse("content://sms/"),
                row, null, null, null);
//        calculateWordMsg(c,ctx);
       return calculateContMsg(c);
    }

    public String[] countMsgbyAdd(Context ctx, String[] row) {
        Cursor c = ctx.getContentResolver().query(
                Uri.parse("content://sms/"),
                row, null, null, null);
//        calculateWordMsg(c,ctx);
       return calculateAddMsg(c);
    }

    public int countAllMsgbyAdd(Context ctx, String[] row) {
        Cursor c = ctx.getContentResolver().query(
                Uri.parse("content://sms/"),
                row, null, null, null);
       return countAllMessage(c);
    }

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

    public String[] calculateAddMsg (Cursor c) {
        ArrayList<String> x = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String address = c.getString(2);
            String body = c.getString(5).toLowerCase();
            System.out.println("==cek " + id + " , " + address);
            x.add(address);
        }
        String all = Utils.arrayListToString(x);
        System.out.println("cek " + all);
        return SortArrayUtil.mainAdd(all);
    }

    public String[] calculateContMsg (Cursor c) {
        ArrayList<String> x = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String address = c.getString(2);
            String body = c.getString(5).toLowerCase();
            System.out.println("==cek " + id + " , " + body);
            x.add(body);
        }
        String all = Utils.arrayListToString(x);
        System.out.println("cek " + all);
        return SortArrayUtil.mainCont(all);
    }

    public int countAllMessage (Cursor c){
        ArrayList<String> x = new ArrayList<>();
        while (c.moveToNext()) {
            x.add("1");
        }
        return x.size();
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


