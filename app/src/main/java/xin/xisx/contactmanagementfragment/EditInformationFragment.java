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

import androidx.annotation.Nullable;

public class EditInformationFragment extends Fragment {

    private IEdit activiy;

    private EditText etSurname;
    private Button btnSurnameConfirm;
    private EditText etGivenname;
    private Button btnGivennameConfirm;
    private EditText etBirthday;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_information, container, false);
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

        etSurname = view.findViewById(R.id.surname);
        etGivenname = view.findViewById(R.id.name);
        btnSurnameConfirm = view.findViewById(R.id.frag_et_surname_confirm);
        btnGivennameConfirm = view.findViewById(R.id.frag_et_givenname_confirm);
        etBirthday = view.findViewById(R.id.birthday);

        btnSurnameConfirm.setOnClickListener(v -> {
            String surname = etSurname.getText().toString();
            activiy.onSurnameConfirmed(surname);
        });

        btnGivennameConfirm.setOnClickListener(v -> {
            String givenname = etGivenname.getText().toString();
            activiy.onGivennameConfirmed(givenname);
        });

        etBirthday.setOnClickListener(v -> activiy.onClickBirthday());
    }
}