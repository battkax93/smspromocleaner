package sunny.smspromocleaner;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.format.DateFormat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import sunny.smspromocleaner.activity.MainController;

public class FilterMessages {

    public String[] countMsgbyContent(Context ctx, String[] row, int allMsg) {
        Cursor c = ctx.getContentResolver().query(
                Uri.parse("content://sms/"),
                row, null, null, null);
       return calculateContMsg(c, allMsg);
    }

    public String[] countMsgbyAdd(Context ctx, String[] row, int allMsg) {
        Cursor c = ctx.getContentResolver().query(
                Uri.parse("content://sms/"),
                row, null, null, null);
//        calculateWordMsg(c,ctx);
       return calculateAddMsg(c, allMsg);
    }

    public int countAllMsgbyAdd(Context ctx, String[] row, int type) {
        Cursor c = ctx.getContentResolver().query(
                Uri.parse("content://sms/"),
                row, null, null, null);
       return countAllMessage(c, type);
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
            String date = c.getString(4);
            System.out.println("==cek " + id + " , " + address);
            for (String aFilterList : filterList) {
                if (address != null && address.equalsIgnoreCase(aFilterList.trim().toLowerCase())) {
                    x.add("1");
                    System.out.println("date= " + date);
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

    public String[] calculateAddMsg (Cursor c, int allMsg) {
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
        return SortArrayUtil.mainAdd(all, allMsg);
    }

    public String[] calculateContMsg (Cursor c, int allMsg) {
        ArrayList<String> x = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String address = c.getString(2);
            String body = c.getString(5).toLowerCase();
            System.out.println("==cek " + id + " , " + body);
            x.add(body);
            y.add("1");
        }
        System.out.println("dpt ni " + allMsg);
        String all = Utils.arrayListToString(x);
        return SortArrayUtil.mainCont(all, allMsg);
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    public int countAllMessage (Cursor c, int type){
        ArrayList<String> x = new ArrayList<>();
        ArrayList<String> y = new ArrayList<>();
        Long date, date2;
        while (c.moveToNext()) {
            x.add("1");
            date = c.getLong(4);
            date2 = System.currentTimeMillis();
            if(Objects.equals(getDate(date2), getDate(date))){
                y.add("1");
            }
        }
        if(type == 1) {
            return x.size();
        } else {
            return y.size();
        }
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


