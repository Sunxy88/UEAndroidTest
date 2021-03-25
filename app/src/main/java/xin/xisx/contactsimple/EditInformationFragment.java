package xin.xisx.contactsimple;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditInformationFragment extends Fragment {

    private static final String GIVEN_NAME_KEY = "givennamekey";
    private static final String SURNAME_KEY = "surnamekey";
    private static final String BIRTHDAY_KEY = "birthday";
    private static final String URI_KEY = "uri";
    private static final int REQUEST_TAKE_PHOTO = 2;

    private IEdit activiy;
    private Activity aty;

    private View rootView;

    private EditText etSurname;
    private Button btnSurnameConfirm;
    private EditText etGivenname;
    private Button btnGivennameConfirm;
    private TextView etBirthday;
    private ImageView photoImageView;
    private Button btnPhoto;

    private String currentPhotoPath;
    private Uri photoUri;

    public static EditInformationFragment getInstance() {
        EditInformationFragment instance = new EditInformationFragment();
        return instance;
    }

    public static EditInformationFragment getInstance(String surname, String givenname, String birthday, String uri) {
        if (surname == null && givenname == null && birthday == null && uri == null) {
            return getInstance();
        }

        EditInformationFragment instance = new EditInformationFragment();
        Bundle args = new Bundle();

        if (surname != null) {
            args.putString(SURNAME_KEY, surname);
        }
        if (givenname != null) {
            args.putString(GIVEN_NAME_KEY, givenname);
        }
        if (birthday != null) {
            args.putString(BIRTHDAY_KEY, birthday);
        }
        if (uri != null) {
            args.putString(URI_KEY, uri);
        }
        instance.setArguments(args);

        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_edit_information, container, false);

        etSurname = rootView.findViewById(R.id.surname);
        etGivenname = rootView.findViewById(R.id.name);
        btnSurnameConfirm = rootView.findViewById(R.id.frag_et_surname_confirm);
        btnGivennameConfirm = rootView.findViewById(R.id.frag_et_givenname_confirm);
        etBirthday = rootView.findViewById(R.id.birthday);
        photoImageView = rootView.findViewById(R.id.photo_image);
        btnPhoto = rootView.findViewById(R.id.btnPhoto);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        aty = getActivity();
        if (aty instanceof IEdit) {
            this.activiy = (IEdit) aty;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Bundle arguments = getArguments();
        if (arguments != null) {
            String surname = arguments.getString(SURNAME_KEY);
            String givenname = arguments.getString(GIVEN_NAME_KEY);
            String birthday = arguments.getString(BIRTHDAY_KEY);
            String stringUri = arguments.getString(URI_KEY);
            if (surname != null) {
                etSurname.setText(surname);
            }
            if (givenname != null) {
                etGivenname.setText(givenname);
            }
            if (birthday != null) {
                etBirthday.setText(birthday);
            }
            if (stringUri != null) {
                Log.i("URIString", stringUri);
                photoUri = new Uri.Builder()
                        .scheme("content")
                        .authority("xin.xisx.contactsimple")
                        .encodedPath("/x" + stringUri.substring(stringUri.lastIndexOf('/')))
                        .build();
                Log.i("URI", photoUri.toString());
//                photoImageView.setImageBitmap(BitmapFactory.decodeFile(stringUri));
                try {
                    photoImageView.setImageBitmap(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        btnSurnameConfirm.setOnClickListener(v -> {
            String surname = etSurname.getText().toString();
            activiy.onSurnameConfirmed(surname);
        });

        btnGivennameConfirm.setOnClickListener(v -> {
            String givenname = etGivenname.getText().toString();
            activiy.onGivennameConfirmed(givenname);
        });

        etBirthday.setOnClickListener(v -> birthdayCallBack());
        etBirthday.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                birthdayCallBack();
            }
        });

        btnPhoto.setOnClickListener(v -> dispatchTakePicutreIntent());

        photoImageView.setImageDrawable(getResources().getDrawable(R.drawable.start_image));

        if (photoUri == null) {
            photoImageView.setImageDrawable(getResources().getDrawable(R.drawable.start_image));
        }
    }

    public EditText getEtSurname() {
        return etSurname;
    }

    public EditText getEtGivenname() {
        return etGivenname;
    }

    public TextView getEtBirthday() {
        return etBirthday;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public String getCurrentPhotoPath() {
        return currentPhotoPath;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == aty.RESULT_OK) {
            Bitmap thumbnail = null;
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (thumbnail != null) {
                photoImageView.setImageBitmap(thumbnail);
                Log.i("AddPhoto", "done");
            }
            galleryAddPic();
        }
    }

    private void birthdayCallBack() {
        String originalBirthday = etBirthday.getText().toString();
        activiy.onClickBirthday(etSurname.getText().toString(), etGivenname.getText().toString(), originalBirthday);
    }

    private void dispatchTakePicutreIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(aty.getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(aty,
                        "xin.xisx.contactsimple",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = aty.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

//        To add the photo in the gallery.
//        It has to grant the permission of Storage on the device in settings.
        File storageDir = Environment.getExternalStorageDirectory().getAbsoluteFile();

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        Log.i("FilePath", currentPhotoPath);
        return image;
    }


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(photoUri);
        Log.i("BroadCast", photoUri.toString());
        aty.sendBroadcast(mediaScanIntent);
    }
}