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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EditGivennameFragment extends Fragment {

    private IEditName activity;
    private EditText etGivenname;
    private Button btnConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_givenname, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnConfirm = view.findViewById(R.id.frag_et_givenname_confirm);
        etGivenname = view.findViewById(R.id.frag_et_givenname);

        btnConfirm.setOnClickListener(v -> {
            String name = etGivenname.getText().toString();
            activity.onGivennameConfirmed(name);
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