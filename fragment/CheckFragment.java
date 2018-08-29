package sunny.smspromocleaner.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import mehdi.sakout.fancybuttons.FancyButton;
import sunny.smspromocleaner.FilterMessages;
import sunny.smspromocleaner.R;
import sunny.smspromocleaner.Utils;

public class CheckFragment extends Fragment {

    FancyButton bCheck;
    EditText etResult;

    String[] row;
    String[] resFreqAdd;

    public CheckFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View check = inflater.inflate(R.layout.fragment_check, container, false);

        init(check);
        return check;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachFragment(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onDetach();
    }

    public void init(View v){

        bCheck = v.findViewById(R.id.btn_calculate);
        etResult = v.findViewById(R.id.et_result);

        row = getResources().getStringArray(R.array.row);


        bCheck.setOnClickListener((View v1) -> {
            FilterMessages fm = new FilterMessages();
            resFreqAdd = fm.countWordbyContent(getContext(),row);
            etResult.setText(Utils.arrayToString(resFreqAdd).replaceAll("\\[|\\]",""));
        });
    }

}

