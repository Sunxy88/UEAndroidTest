package xin.xisx.contactmanagementfragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class DateActivity extends AppCompatActivity {

    private TextView date;
    private Button confirmBtn;
    private Button cancelBtn;
    private Intent itt;

    private String str_birthday;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        itt = getIntent();
        initView();
    }

    private void initView() {
//        Date selector
        this.date = findViewById(R.id.date_TV);
        str_birthday = getIntent().getStringExtra("btdy");
        if (str_birthday != null) {
            date.setText(str_birthday);
        }
        date.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                showDatePickerDialog();
            }
        });
        date.setOnClickListener((v) -> showDatePickerDialog());

        this.confirmBtn = findViewById(R.id.date_confirm_btn);
        this.cancelBtn = findViewById(R.id.date_cancel_btn);

        confirmBtn.setOnClickListener(v -> {
            str_birthday = date.getText().toString();
            itt.putExtra("btdy", str_birthday);
            this.setResult(RESULT_OK, itt);
            this.finish();
        });

        cancelBtn.setOnClickListener(v -> {
            itt.putExtra("btdy", str_birthday);
            this.setResult(RESULT_CANCELED, itt);
            this.finish();
        });
    }

    private void showDatePickerDialog() {
//        Pop up a date selector.
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(DateActivity.this, (view, year, monthOfYear, dayOfMonth) -> {
            date.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }
}
