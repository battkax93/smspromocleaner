package sunny.smspromocleaner;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;

public class FilterMessages {

    public void deleteThreadbyContent(Context ctx, String[] filterList, String[] row) {
        Cursor c = ctx.getContentResolver().query(
                Uri.parse("content://sms/"),
                row, null, null, null);
        filterMsgbyContent(c, filterList, ctx);
        Toast.makeText(ctx, R.string.success_filter_promo, Toast.LENGTH_SHORT).show();
    }

    public void deleteThreadbyAddress(Context ctx, String[] filterList, String[] row) {
        Cursor c = ctx.getContentResolver().query(
                Uri.parse("content://sms/"),
                row, null, null, null);
        filterMsgbyAddress(c, filterList, ctx);
        Toast.makeText(ctx, R.string.success_filter_promo, Toast.LENGTH_SHORT).show();
    }

    public void filterMsgbyContent(Cursor c, String[] filterList, Context ctx) {
        ArrayList<String> x = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String address = c.getString(2);
            String body = c.getString(5).toLowerCase();
            System.out.println("==cek " + id + " , " + address);
            for (int a = 0; a < filterList.length; a++) {
                if (body.contains(filterList[a])) {
                    x.add("1");
                    System.out.println("==ketemu" + filterList[a]);
                    ctx.getContentResolver().delete(
                            Uri.parse("content://sms/" + id), null, null);
                }
            }

        }
        System.out.println("==cek " + x.size());
    }

    public void filterMsgbyAddress(Cursor c, String[] filterList, Context ctx) {
        ArrayList<String> x = new ArrayList<>();
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String address = c.getString(2).toLowerCase();
            System.out.println("==cek " + id + " , " + address);
            for (int a = 0; a < filterList.length; a++) {
                if (address.equals(filterList[a])) {
                    x.add("1");
                    System.out.println("==ketemu" + filterList[a]);
                    ctx.getContentResolver().delete(
                            Uri.parse("content://sms/" + id), null, null);
                }
            }
        }
        System.out.println("==cek " + x.size());
    }
}
