package sunny.smspromocleaner;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.analytics.FirebaseAnalytics;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by Wayan-MECS on 8/24/2018.
 */

public class MainController extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    public void showDialogCodeNumber(String i, Context ctx) {
        final Dialog dialog = new Dialog(ctx, R.style.PauseDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_content);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final TextView tvCount = dialog.findViewById(R.id.tv_count);
        final LottieAnimationView ltAnim = dialog.findViewById(R.id.lottieAnimationView2);

        ltAnim.setAnimation("clear.json");
        ltAnim.playAnimation();
        ltAnim.loop(false);

        tvCount.setText(String.format(" %s ", i));

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuItem exitItem = menu.add(0, 1, 0, "Exit");
        exitItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case 1:
                finish();
                break;
        }

        return false;
    }

    public void logFirebase(String id, String name, String type, Context ctx){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(ctx);
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, type);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

}
