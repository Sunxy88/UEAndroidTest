package xin.xisx.contactmanagementfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements IEdit {

    private FragmentManager fragmentManager;
    private EditInformationFragment editInformationFragment;
    private DisplayFragment displayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
//        根据Bundle里的数据初始化fragment
        editInformationFragment = new EditInformationFragment();
//        需要将name、birthday和department中的数据填充
//        然后利用fragment manager将fragment添加进activity
        fragmentManager.beginTransaction()
                    .add(R.id.rootLayout, editInformationFragment)
                    .commit();
    }

    @Override
    public void onSurnameConfirmed(String surname) {
//        将surname的信息传递给displayFragment展示
        String info = "Surname: " + surname;
        replace(info);
    }

    @Override
    public void onGivennameConfirmed(String givenname) {
//        将givenname的信息传递给displayFragment展示
        String info = "Given name: " + givenname;
        replace(info);
    }

    @Override
    public void onClickBirthday() {

    }

    private void replace(String info) {
        displayFragment = DisplayFragment.newInstance(info);
        fragmentManager.beginTransaction()
                .replace(R.id.rootLayout, displayFragment)
                .addToBackStack(editInformationFragment.getClass().toString())
                .commit();
    }
}
