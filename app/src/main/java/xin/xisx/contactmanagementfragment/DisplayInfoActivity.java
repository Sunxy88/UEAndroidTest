package xin.xisx.contactmanagementfragment;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DisplayInfoActivity extends AppCompatActivity {

    private TextView infoTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);
        infoTV = findViewById(R.id.display_info_tv);
        User user = getIntent().getParcelableExtra("user");
        infoTV.setText(user.toString());
    }
}
