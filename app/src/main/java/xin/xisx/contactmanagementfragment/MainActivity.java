package xin.xisx.contactmanagementfragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements IEditName {

    private static final String preferenceName = "infomations";
    private static final int DATE_SELECTOR_ACTIVITY = 0;
    private static final int NAME_EDIT_ACTIVITY = 1;

    private DisplaySurnameFragment displaySurnameFragment;
    private DisplayGivennameFragment displayGivennameFragment;
    private EditSurnameFragment editSurnameFragment;
    private EditGivennameFragment editGivennameFragment;

    private SharedPreferences sharedPreferences;
//    private EditText surname;
//    private EditText name;
    private EditText birthday;
    private EditText cityOfBirth;
    private EditText addTel;
    private LinearLayout telList;
    private Spinner deptSpinner;
    private Button btn_submit;
    private List<String> departmentList = new ArrayList<>();

    private String str_surname;
    private String str_name;
    private String str_birthday;
    private String str_cityOfBirth;
    private String deparment;
    private Set<String> telNumbers = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment(savedInstanceState);
        initLayout();
    }

    private void initFragment(Bundle savedInstanceState) {
        editSurnameFragment = new EditSurnameFragment();
        editGivennameFragment = new EditGivennameFragment();

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction().add(R.id.surnameFragmentContainer, editSurnameFragment).commit();
            getFragmentManager().beginTransaction().add(R.id.givennameFragmentContainer, editGivennameFragment).commit();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        readData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DATE_SELECTOR_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                str_birthday = data.getStringExtra("btdy");
                birthday.setText(str_birthday);
            } else if (resultCode == RESULT_CANCELED) {
                str_birthday = getResources().getString(R.string.birthdayForm);
                birthday.setHint(str_birthday);
            }
        } else if (requestCode == NAME_EDIT_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                str_name = data.getStringExtra("name");
//                name.setText(str_name);
            } else {
                str_name = getResources().getString(R.string.name_enter);
//                name.setHint(str_name);
            }
        }
    }

    public void resetAction(MenuItem item) {
        Log.i("LifeCycle", "CollectActivity.resetAction()");
        clearInput();
        str_surname = "";
        str_name = "";
        str_birthday = "";
        str_cityOfBirth = "";
        deparment = "";
        telNumbers.clear();
        saveData();
    }

    public void addTel(View view) {
        String tel = addTel.getText().toString();
        if (telNumbers.contains(tel)) {
            Toast.makeText(
                    MainActivity.this,
                    tel + " already existed.",
                    Toast.LENGTH_SHORT)
                    .show();
            addTel.setText("");
        }
        if (emptyStringCheck(tel)) {
            return ;
        }
        telNumbers.add(tel);
        addTelInList(tel);
    }

    public void searchAction(MenuItem item) {
        Log.i("LifeCycle", "CollectActivity.searchAction()");
        if (cityOfBirth == null || cityOfBirth.length() == 0) {
            Snackbar.make(
                    this.findViewById(R.id.rootLayout),
                    R.string.empty_string,
                    Snackbar.LENGTH_LONG
            ).show();
        }
        str_cityOfBirth = cityOfBirth.getText().toString();
        String strUri = "http://fr.wikipedia.org/?search=" + str_cityOfBirth;
        Uri uri = Uri.parse(strUri);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void shareAction(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.share);
        intent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.my_birthcity_is) + str_cityOfBirth);

//        Force an app chooser
        Intent chooser = Intent.createChooser(intent, "Open with");
        startActivity(chooser);
    }

    private void addTelInList(String tel) {
        LinearLayout telll = new LinearLayout(this);
        TextView telTV = new TextView(this);
        telTV.setText(tel);
        Button remove = new Button(this);
        remove.setHint("Remove");
        remove.setOnClickListener((v) ->  {
            telll.removeAllViews();
            telNumbers.remove(tel);
        });
        Button call = new Button(this);
        call.setHint("Call");
        call.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + tel);
            intent.setData(data);
            startActivity(intent);
        });
        telll.addView(telTV);
        telll.addView(remove);
        telll.addView(call);
        telList.addView(telll);
    }

    private void clearInput() {
//        surname.setText("");
//        name.setText("");
        birthday.setText("");
        cityOfBirth.setText("");
        addTel.setText("");
        telList.removeAllViewsInLayout();
        telNumbers.clear();
        deptSpinner.setSelection(0, true);
    }

    private boolean emptyStringCheck(String str) {
        if (str == null || str.length() == 0) {
            Toast.makeText(MainActivity.this, "Empty string", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private void initLayout() {
        injectView();

        sharedPreferences = getSharedPreferences(preferenceName, Context.MODE_PRIVATE);

        readDepartments();

//        name.setOnFocusChangeListener((v, hasFocus) -> {
//            if (hasFocus) {
//                nameChangeAction();
//            }
//        });

//        name.setOnClickListener(v -> nameChangeAction());

        birthday.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                dateChangeAction();
            }
        });
        birthday.setOnClickListener((v) -> dateChangeAction());

        btn_submit.setOnClickListener((v) -> submitAction());

        readData();
        initViews();
    }

    private void initViews() {
//        surname.setText(str_surname);
//        name.setText(str_name);
        birthday.setText(str_birthday);
        cityOfBirth.setText(str_cityOfBirth);
        for (int i = 0; i < departmentList.size(); i++) {
            if (departmentList.get(i).equals(deparment)) {
                deptSpinner.setSelection(i, true);
                break;
            }
        }

        for (String tel : telNumbers) {
            addTelInList(tel);
        }
    }

    private void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("surname", str_surname);
        editor.putString("name", str_name);
        editor.putString("birthday", str_birthday);
        editor.putString("birthCity", str_cityOfBirth);
        editor.putString("department", deparment);
        editor.putStringSet("telNums", telNumbers);
        editor.apply();
    }

    private void readData() {
        str_surname = sharedPreferences.getString("surname", getResources().getString(R.string.surname_enter));
        str_name = sharedPreferences.getString("name", getResources().getString(R.string.name_enter));
        str_birthday = sharedPreferences.getString("birthday", getResources().getString(R.string.birthday));
        str_cityOfBirth = sharedPreferences.getString("surname", getResources().getString(R.string.city_of_birth));
        deparment = sharedPreferences.getString("department", "À choisir");
        telNumbers = sharedPreferences.getStringSet("telNums", new HashSet<>());
    }

    private String[] readDepartments() {
        String[] depts = getResources().getStringArray(R.array.departments);
        for (String dept : depts) {
            departmentList.add(dept);
        }
        return depts;
    }

    private void submitAction() {
        readDataFromView();
        User user = new User(str_surname, str_name, str_birthday, str_cityOfBirth, deparment, telNumbers);
        Intent intent = new Intent(this, DisplayInfoActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

//    private void printName() {
//        str_name = name.getText().toString();
//        Intent intent = new Intent(getResources().getString(R.string.ACTION_VIEW));
//        intent.putExtra("name", str_name);
//        startActivity(intent);
//    }

    private void readDataFromView() {
//        str_surname = surname.getText().toString();
//        str_name = name.getText().toString();
        str_birthday = birthday.getText().toString();
        str_cityOfBirth = cityOfBirth.getText().toString();
    }

    private void dateChangeAction() {
        Intent itt = new Intent(this, DateActivity.class);
        str_birthday = birthday.getText().toString();
        itt.putExtra("btdy", str_birthday);
        startActivityForResult(itt, DATE_SELECTOR_ACTIVITY);
    }

//    private void nameChangeAction() {
//        Intent intent = new Intent(Intent.ACTION_EDIT);
//        intent.putExtra("name", str_name);
//        startActivityForResult(intent, NAME_EDIT_ACTIVITY);
//    }

    private void injectView() {
//        surname = findViewById(R.id.surname);
//        name = findViewById(R.id.name);
        birthday = findViewById(R.id.birthday);
        cityOfBirth = findViewById(R.id.city_of_birth);
        addTel = findViewById(R.id.addTel);
        telList = findViewById(R.id.telList);
        deptSpinner = findViewById(R.id.department_spinner);
        btn_submit = findViewById(R.id.btn_submit);
    }

    @Override
    public void onSurnameConfirmed(String surname) {
        str_surname = surname;
        displaySurnameFragment = DisplaySurnameFragment.newInstance(surname);
        getFragmentManager()
            .beginTransaction()
            .replace(R.id.surnameFragmentContainer, displaySurnameFragment)
            .addToBackStack(editSurnameFragment.getClass().toString())
            .commit();
    }

    @Override
    public void onGivennameConfirmed(String givenname) {
        str_name = givenname;
        displayGivennameFragment = DisplayGivennameFragment.newInstance(givenname);
        getFragmentManager()
            .beginTransaction()
            .replace(R.id.givennameFragmentContainer, displayGivennameFragment)
            .addToBackStack(editGivennameFragment.getClass().toString())
            .commit();

    }
}
