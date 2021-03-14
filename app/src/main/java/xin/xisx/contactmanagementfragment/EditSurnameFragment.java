package xin.xisx.contactmanagementfragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Fragment;


public class EditSurnameFragment extends Fragment {

    private IEditName activity;
    private EditText etSurname;
    private Button btnConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_surname, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnConfirm = view.findViewById(R.id.frag_et_surname_confirm);
        etSurname = view.findViewById(R.id.frag_et_surname);

        btnConfirm.setOnClickListener(v -> {
            String surname = etSurname.getText().toString();
            activity.onSurnameConfirmed(surname);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Activity acty = getActivity();
        if (acty != null && acty instanceof IEditName) {
            activity = (IEditName) acty;
        }
    }
}