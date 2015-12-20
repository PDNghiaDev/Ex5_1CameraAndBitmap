package com.gmail.pdnghiadev.ex5_1cameraandbitmap.fragments;




import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.gmail.pdnghiadev.ex5_1cameraandbitmap.R;
import com.gmail.pdnghiadev.ex5_1cameraandbitmap.presenter.SignUpStep1Presenter;
import com.gmail.pdnghiadev.ex5_1cameraandbitmap.presenter.SignUpStep1PresenterImpl;
import com.gmail.pdnghiadev.ex5_1cameraandbitmap.untils.CustomDialog;
import com.gmail.pdnghiadev.ex5_1cameraandbitmap.untils.UserInfoConstant;
import com.gmail.pdnghiadev.ex5_1cameraandbitmap.view.SignUpStep1View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by PDNghiaDev on 11/25/2015.
 * Class perform 5 functions
 * 1 - Check for FirstName is empty
 * 2 - Check for LastName is empty
 * 3 - Check for Email is empty and invalid
 * 4 - Check for PhoneNumber is empty and phone's length
 * 5 - Check for the user have select gender
 */
public class SignUpStep1Fragment extends Fragment implements View.OnClickListener, SignUpStep1View {
    private static final int SELECT_FILE = 1;
    private static final String TAG = "SignUpStep1Fragment";
    private Button mBtnNext;
    private EditText mFirstname, mLastname, mEmail, mPhonenumber;
    private String mStrFirstname, mStrLastname, mStrEmail, mStrPhonenumber, mStrDestination;
    private RadioGroup mGender;
    private SignUpStep1Presenter signUpPresenter;
    private ImageView mAvatar;
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    private File destination;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sign_up_step_1_fragment, container, false);

        loadComponents(view);

        signUpPresenter = new SignUpStep1PresenterImpl(this);
        mBtnNext.setOnClickListener(this);
        mAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhoto();
            }
        });


        //        if (!destination.exists()) {
//            try {
//                destination.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }else {
//            mAvatar.setImageBitmap(convertFileToBitmap(destination.getPath()));
//            mStrDestination = destination.getPath();
//        }


        return view;
    }

    public void loadComponents(View view) {
        mBtnNext = (Button) view.findViewById(R.id.btnNext);
        mFirstname = (EditText) view.findViewById(R.id.editFirstName);
        mLastname = (EditText) view.findViewById(R.id.editLastName);
        mEmail = (EditText) view.findViewById(R.id.editEmail);
        mPhonenumber = (EditText) view.findViewById(R.id.editPhoneNumber);
        mGender = (RadioGroup) view.findViewById(R.id.rgGender);
        mAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
    }


    @Override
    public void onClick(View view) {
        mStrFirstname = mFirstname.getText().toString();
        mStrLastname = mLastname.getText().toString();
        mStrEmail = mEmail.getText().toString();
        mStrPhonenumber = mPhonenumber.getText().toString();
        signUpPresenter.validateCredentials(mStrFirstname, mStrLastname, mStrEmail, mStrPhonenumber, checkGender());

    }

    public Boolean checkGender(){ // Check for Gender
        return mGender.getCheckedRadioButtonId() < 0;
    }

    @Override
    public void setFistnameError() {
        mFirstname.setError(getString(R.string.first_name_error));
    }

    @Override
    public void setLastnameError() {
        mLastname.setError(getString(R.string.last_name_error));
    }

    @Override
    public void setEmailError() {
        mEmail.setError(getString(R.string.email_error));
    }

    @Override
    public void setEmailInvalidError() {
        mEmail.setError(getString(R.string.email_invalid_error));
    }

    @Override
    public void setPhonenumberError() {
        mPhonenumber.setError(getString(R.string.phone_number_error));
    }

    @Override
    public void setPhonenumberInvalidError() {
        mPhonenumber.setError(getString(R.string.phone_number_invalid_error));
    }

    @Override
    public void setGenderError() {
        CustomDialog dialog = new CustomDialog("Please choose your gender");
        dialog.show(getFragmentManager(), UserInfoConstant.SIGNUP_STEP1);
    }

    @Override
    public void navigationToStep2() {
        mStrDestination = destination.getPath();
        SignUpStep2Fragment fragment = new SignUpStep2Fragment();
        Bundle bundle = new Bundle();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        bundle.putString(UserInfoConstant.FIRST_NAME, mStrFirstname);
        bundle.putString(UserInfoConstant.LAST_NAME, mStrLastname);
        bundle.putString(UserInfoConstant.EMAIL, mStrEmail);
        bundle.putString(UserInfoConstant.PHONE_NUMBER, mStrPhonenumber);
        bundle.putString(UserInfoConstant.DESTINATION, mStrDestination);
        fragment.setArguments(bundle);
        ft.replace(R.id.signUp, fragment);
        ft.addToBackStack(UserInfoConstant.SIGNUP_STEP2);
        ft.commit();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void selectPhoto() {
        final String[] items = {"Take photo", "Choose from library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take photo")) {
                            dispatchTakePictureIntent();
                        }else if (items[item].equals("Choose from library")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                        }else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);
            }else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        destination = new File(Environment.getExternalStorageDirectory(), "avatar.jpg");


        FileOutputStream fo;
        try {
            if (!destination.exists()) {
                destination.createNewFile();
            }
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mAvatar.setImageBitmap(thumbnail);
    }

    private Bitmap convertFileToBitmap(String file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file);

        return bitmap;
    }

    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePatch = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePatch, options);
        final int REQUIRED_SIZE = 400;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE) {
            scale *= 2;
        }
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePatch, options);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        destination = new File(Environment.getExternalStorageDirectory(), "avatar.jpg");
        FileOutputStream fo;
        try {
            if (!destination.exists()) {
                destination.createNewFile();
            }
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mAvatar.setImageBitmap(bm);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
