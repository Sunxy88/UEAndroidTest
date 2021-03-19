package xin.xisx.contactmanagementfragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class EditInformationFragment extends Fragment {

    private static final String GIVEN_NAME_KEY = "givennamekey";
    private static final String SURNAME_KEY = "surnamekey";
    private static final String BIRTHDAY_KEY = "birthday";

    private IEdit activiy;

    private View rootView;

    private EditText etSurname;
    private Button btnSurnameConfirm;
    private EditText etGivenname;
    private Button btnGivennameConfirm;
    private TextView etBirthday;

    public static EditInformationFragment getInstance() {
        EditInformationFragment instance = new EditInformationFragment();
        return instance;
    }

    public static EditInformationFragment getInstance(String surname, String givenname, String birthday) {
        EditInformationFragment instance = new EditInformationFragment();
        Bundle args = new Bundle();

        args.putString(SURNAME_KEY, surname);
        args.putString(GIVEN_NAME_KEY, givenname);
        args.putString(BIRTHDAY_KEY, birthday);
        instance.setArguments(args);

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit_information, container, false);

        etSurname = rootView.findViewById(R.id.surname);
        etGivenname = rootView.findViewById(R.id.name);
        btnSurnameConfirm = rootView.findViewById(R.id.frag_et_surname_confirm);
        btnGivennameConfirm = rootView.findViewById(R.id.frag_et_givenname_confirm);
        etBirthday = rootView.findViewById(R.id.birthday);

        Bundle arguments = getArguments();
        if (arguments != null) {
            String surname = arguments.getString(SURNAME_KEY);
            String givenname = arguments.getString(GIVEN_NAME_KEY);
            String birthday = arguments.getString(BIRTHDAY_KEY);
            etSurname.setText(surname);
            etGivenname.setText(givenname);
            etBirthday.setText(birthday);
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        if (activity instanceof IEdit) {
            this.activiy = (IEdit) activity;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnSurnameConfirm.setOnClickListener(v -> {
            String surname = etSurname.getText().toString();
            activiy.onSurnameConfirmed(surname);
        });

        btnGivennameConfirm.setOnClickListener(v -> {
            String givenname = etGivenname.getText().toString();
            activiy.onGivennameConfirmed(givenname);
        });

        etBirthday.setOnClickListener(v -> birthdayCallBack());
        etBirthday.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                birthdayCallBack();
            }
        });
    }

    public EditText getEtSurname() {
        return etSurname;
    }

    public EditText getEtGivenname() {
        return etGivenname;
    }

    public TextView getEtBirthday() {
        return etBirthday;
    }

    private void birthdayCallBack() {
        String originalBirthday = etBirthday.getText().toString();
        activiy.onClickBirthday(etSurname.getText().toString(), etGivenname.getText().toString(), originalBirthday);
    }
}