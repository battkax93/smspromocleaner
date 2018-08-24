package sunny.smspromocleaner;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Wayan-MECS on 8/24/2018.
 */

public class MainController extends AppCompatActivity {

    FilterMessages fm;
    String[] row;

    public void showDialogCodeNumber() {
        final Dialog dialog = new Dialog(this, R.style.PauseDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_content);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        FancyButton dialogBtn_save = dialog.findViewById(R.id.btn_save);
        final EditText etContent = dialog.findViewById(R.id.et_content);

        dialogBtn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void toArrayContent(Context ctx,String cont){
        row = getResources().getStringArray(R.array.row);
        fm = new FilterMessages();
        String[] content = cont.split(",");
        fm.deleteThreadbyContent(ctx,content,row);
    }

    public void toArrayAddress(Context ctx,String s){
        row = getResources().getStringArray(R.array.row);
        fm = new FilterMessages();
        String add = s.trim();
        String[] address = add.split(",");
        fm.deleteThreadbyAddress(ctx, address,row);
    }
}
