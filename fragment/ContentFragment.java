package sunny.smspromocleaner.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import mehdi.sakout.fancybuttons.FancyButton;
import sunny.smspromocleaner.FilterMessages;
import sunny.smspromocleaner.Global;
import sunny.smspromocleaner.activity.MainController;
import sunny.smspromocleaner.R;

import static android.content.Context.MODE_PRIVATE;

public class ContentFragment extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String keyContent = "keyContent";
    EditText etContent;
    FancyButton bContent;

    public ContentFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View content = inflater.inflate(R.layout.fragment_content, container, false);
        init(content);

        return content;
    }

    private void init(View v) {

        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        etContent = v.findViewById(R.id.et_content);
        bContent = v.findViewById(R.id.btn_content);

        bContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLogFB();
                String cont = etContent.getText().toString().trim().toLowerCase();
                editor.putString(keyContent, cont);
                editor.apply();
                System.out.println("==cek array " + cont);
                if (TextUtils.isEmpty(cont)) etContent.setError("cannot be empty");
                toArrayContent(getContext(), cont);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

        System.out.println("==onPause");
        String cont = etContent.getText().toString();
        editor.putString(keyContent, cont);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("==onResume");
        String add = pref.getString(keyContent, null);
        etContent.setText(add);
    }

    public void sendLogFB() {
        MainController mc = new MainController();
        mc.logFirebase(Global.ID_BUTTON_CONTENT,
                Global.NAME_BUTTON_CONTENT,
                Global.TYPE_BUTTON,
                getContext());
    }

    public void toArrayContent(Context ctx, String cont) {
        String[] row = getResources().getStringArray(R.array.row);
        FilterMessages fm = new FilterMessages();
        String[] content = cont.split(",");
        fm.deleteThreadbyContent(ctx, content, row);
    }
}
