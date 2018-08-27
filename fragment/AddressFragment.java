package sunny.smspromocleaner.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
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
import sunny.smspromocleaner.MainController;
import sunny.smspromocleaner.R;

import static android.content.Context.MODE_PRIVATE;

public class AddressFragment extends Fragment {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    String keyAddress = "keyAddress";
    EditText etAddress;
    FancyButton bAddress;


    public AddressFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View address = inflater.inflate(R.layout.fragment_address, container, false);
        init(address);

        return address;
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("==onPause");
        String add = etAddress.getText().toString();
        editor.putString(keyAddress, add);
        editor.apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("==onResume");
        String add = pref.getString(keyAddress, null);
        etAddress.setText(add);
    }

    private void init(View v) {

        pref = getContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        etAddress = v.findViewById(R.id.et_address);
        bAddress = v.findViewById(R.id.btn_address);

        bAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLogFB();
                String add = etAddress.getText().toString();
                editor.putString(keyAddress, add);
                editor.apply();
                System.out.println("==cek array " + add);
                if (TextUtils.isEmpty(add)) etAddress.setError("cannot be empty");
                toArrayAddress(getContext(), add);
            }
        });
    }

    public void sendLogFB(){
        MainController mc = new MainController();
        mc.logFirebase(Global.ID_BUTTON_ADDRESS,
                Global.NAME_BUTTON_ADDRESS,
                Global.TYPE_BUTTON,
                getContext());
    }
    public void toArrayAddress(Context ctx, String s) {
        String[] row = getResources().getStringArray(R.array.row);
        FilterMessages fm = new FilterMessages();
        String add = s.trim();
        String[] address = add.split(",");
        fm.deleteThreadbyAddress(ctx, address, row);
    }

}
