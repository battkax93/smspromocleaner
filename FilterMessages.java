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
//        Toast.makeText(ctx, R.string.success_filter_promo, Toast.LENGTH_SHORT).show();
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
            for (String aFilterList : filterList) {
                if (body.contains(aFilterList)) {
                    x.add("1");
                    System.out.println("==ketemu" + aFilterList);
                    ctx.getContentResolver().delete(
                            Uri.parse("content://sms/" + id), null, null);
                }
            }

        }
        String countDeleted = String.valueOf(x.size());
        Toast.makeText(ctx,"successfully cleaning " + countDeleted +
                " promo messages from the inbox",Toast.LENGTH_SHORT).show();
    }

    public void filterMsgbyAddress(Cursor c, String[] filterList, Context ctx) {
        ArrayList<String> x = new ArrayList<>();
        if (c != null && c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                String address = c.getString(2).toLowerCase();
                System.out.println("==cek " + id + " , " + address);
                for (String aFilterList : filterList) {
                    if (address.equals(aFilterList)) {
                        x.add("1");
                        System.out.println("==ketemu" + aFilterList);
                        ctx.getContentResolver().delete(
                                Uri.parse("content://sms/inbox/" + id), null, null);
                    }
                }

            } while (c.moveToNext());
        }
        String countDeleted = String.valueOf(x.size());
        Toast.makeText(ctx,"successfully cleaning " + countDeleted +
                " promo messages from the inbox",Toast.LENGTH_SHORT).show();
    }

}


