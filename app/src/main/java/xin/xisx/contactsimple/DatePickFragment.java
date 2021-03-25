package xin.xisx.contactsimple;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

public class DatePickFragment extends Fragment {

    private static final String ORIGINAL = "original";

    private Activity activity;

    private TextView date;
    private Button confirmBtn;
    private Button cancelBtn;

    private String originalDate;
    private String newDate;

    public static DatePickFragment getInstance(String original) {
        DatePickFragment datePickFragment = new DatePickFragment();
        datePickFragment.setOriginalDate(original);
        Bundle args = new Bundle();

        args.putString(ORIGINAL, original);
        datePickFragment.setArguments(args);

        return datePickFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            originalDate = getArguments().getString(ORIGINAL);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_date_pick, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        date = view.findViewById(R.id.date_TV);
        confirmBtn = view.findViewById(R.id.date_confirm_btn);
        cancelBtn = view.findViewById(R.id.date_cancel_btn);


        date.setText(originalDate);
        date.setOnClickListener(v -> showDatePickerDialog());

        final IEdit cbk = (IEdit) activity;
        confirmBtn.setOnClickListener(v -> {
            newDate = date.getText().toString();
            if (cbk != null) {
                cbk.onBirthdayConfirmed(newDate);
            }
        });

        cancelBtn.setOnClickListener(v -> cbk.onBirthdayConfirmed(originalDate));
        date.performClick();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    public void setOriginalDate(String originalDate) {
        this.originalDate = originalDate;
    }

    private void showDatePickerDialog() {
//        Pop up a date selector.
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(activity, (view, year, monthOfYear, dayOfMonth) -> {
            date.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }
}
