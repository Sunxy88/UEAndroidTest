package xin.xisx.contactmanagementfragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.os.PersistableBundle;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IEdit {

    private static final String GIVEN_NAME_KEY = "givennamekey";
    private static final String SURNAME_KEY = "surnamekey";
    private static final String BIRTHDAY_KEY = "birthday";

    private FragmentManager fragmentManager;
    private EditInformationFragment editInformationFragment;
    private DisplayFragment displayFragment;
    private DatePickFragment datePickFragment;

    private Map<String, String> cache = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        initFragment(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState,
                                    @NonNull PersistableBundle outPersistentState) {
        cache.put(SURNAME_KEY, editInformationFragment.getEtSurname().getText().toString());
        cache.put(GIVEN_NAME_KEY, editInformationFragment.getEtGivenname().getText().toString());
        cache.put(BIRTHDAY_KEY, editInformationFragment.getEtBirthday().getText().toString());
        outState.putString(SURNAME_KEY, cache.get(SURNAME_KEY));
        outState.putString(GIVEN_NAME_KEY, cache.get(GIVEN_NAME_KEY));
        outState.putString(BIRTHDAY_KEY, cache.get(BIRTHDAY_KEY));
        super.onSaveInstanceState(outState, outPersistentState);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        cache.put(SURNAME_KEY, savedInstanceState.getString(SURNAME_KEY));
        cache.put(GIVEN_NAME_KEY, savedInstanceState.getString(GIVEN_NAME_KEY));
        cache.put(BIRTHDAY_KEY, savedInstanceState.getString(BIRTHDAY_KEY));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        editInformationFragment = EditInformationFragment.getInstance(cache.get(SURNAME_KEY), cache.get(GIVEN_NAME_KEY), cache.get(BIRTHDAY_KEY));
        fragmentManager.beginTransaction()
                .replace(R.id.rootLayout, editInformationFragment)
                .commit();
        super.onResume();
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
    public void onClickBirthday(String surname, String givenname, String original) {
        cache.put(GIVEN_NAME_KEY, givenname);
        cache.put(SURNAME_KEY, surname);
        datePickFragment = DatePickFragment.getInstance(original);
        fragmentManager.beginTransaction()
                .replace(R.id.rootLayout, datePickFragment)
                .addToBackStack(editInformationFragment.getClass().toString())
                .commit();
    }

    @Override
    public void onBirthdayConfirmed(String birthday) {
        editInformationFragment = EditInformationFragment.getInstance(cache.get(SURNAME_KEY), cache.get(GIVEN_NAME_KEY), birthday);
        cache.put(BIRTHDAY_KEY, birthday);
        fragmentManager.beginTransaction()
                .replace(R.id.rootLayout, editInformationFragment)
                .commit();
    }

    private void initFragment(Bundle savedInstanceState) {
        editInformationFragment = EditInformationFragment.getInstance();
        fragmentManager.beginTransaction()
                .replace(R.id.rootLayout, editInformationFragment)
                .commit();
    }

    private void replace(String info) {
        displayFragment = DisplayFragment.newInstance(info);

        fragmentManager.beginTransaction()
                .replace(R.id.rootLayout, displayFragment)
                .addToBackStack(editInformationFragment.getClass().toString())
                .commit();
    }
}
