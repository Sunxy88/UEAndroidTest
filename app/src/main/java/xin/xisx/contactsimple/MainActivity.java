package xin.xisx.contactsimple;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IEdit {

    private static final String PREFERENCE = "memory";

    private static final String GIVEN_NAME_KEY = "givennamekey";
    private static final String SURNAME_KEY = "surnamekey";
    private static final String BIRTHDAY_KEY = "birthday";
    private static final String URI_KEY = "uri";

    private SharedPreferences sharedPreferences;
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
        sharedPreferences = getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        initFragment(savedInstanceState);
    }

    @Override
    protected void onStop() {
        saveData();
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveData();
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
        cache.put(SURNAME_KEY, editInformationFragment.getEtSurname().getText().toString());
        cache.put(GIVEN_NAME_KEY, editInformationFragment.getEtGivenname().getText().toString());
        cache.put(BIRTHDAY_KEY, editInformationFragment.getEtBirthday().getText().toString());
        cache.put(URI_KEY, editInformationFragment.getCurrentPhotoPath());
        datePickFragment = DatePickFragment.getInstance(original);
        fragmentManager.beginTransaction()
                .replace(R.id.rootLayout, datePickFragment)
                .addToBackStack(editInformationFragment.getClass().toString())
                .commit();
    }

    @Override
    public void onBirthdayConfirmed(String birthday) {
        cache.put(BIRTHDAY_KEY, birthday);
        editInformationFragment = EditInformationFragment.getInstance(
                cache.get(SURNAME_KEY),
                cache.get(GIVEN_NAME_KEY),
                cache.get(BIRTHDAY_KEY),
                cache.get(URI_KEY)
        );
        fragmentManager.beginTransaction()
                .replace(R.id.rootLayout, editInformationFragment)
                .commit();
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SURNAME_KEY, editInformationFragment.getEtSurname().getText().toString());
        editor.putString(GIVEN_NAME_KEY, editInformationFragment.getEtGivenname().getText().toString());
        editor.putString(BIRTHDAY_KEY, editInformationFragment.getEtBirthday().getText().toString());
//        editor.putString(URI_KEY, editInformationFragment.getPhotoUri().getPath());
        editor.putString(URI_KEY, editInformationFragment.getCurrentPhotoPath());
        editor.apply();
    }

    private void initFragment(Bundle savedInstanceState) {
        String surname = sharedPreferences.getString(SURNAME_KEY, null);
        String givenname = sharedPreferences.getString(GIVEN_NAME_KEY, null);
        String birthday = sharedPreferences.getString(BIRTHDAY_KEY, null);
        String uri = sharedPreferences.getString(URI_KEY, null);
        editInformationFragment = EditInformationFragment.getInstance(surname, givenname, birthday, uri);
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
