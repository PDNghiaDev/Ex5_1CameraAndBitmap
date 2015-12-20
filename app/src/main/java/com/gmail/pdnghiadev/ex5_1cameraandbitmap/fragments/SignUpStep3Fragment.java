package com.gmail.pdnghiadev.ex5_1cameraandbitmap.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gmail.pdnghiadev.ex5_1cameraandbitmap.R;
import com.gmail.pdnghiadev.ex5_1cameraandbitmap.untils.UserInfoConstant;


/**
 * Created by PDNghiaDev on 11/28/2015.
 * Class perform 2 button
 * Button SendEmail will show one list other app that you can select to send email
 * Button Restart will move the user back to SignUpStep1
 */
public class SignUpStep3Fragment extends Fragment implements View.OnClickListener {
    private Button mBtnSendEmail, mBtnRestart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_step_3_fragment, container, false);

        loadComponents(view);
        mBtnSendEmail.setOnClickListener(this);
        mBtnRestart.setOnClickListener(this);

        return view;
    }

    private void loadComponents(View view) {
        mBtnSendEmail = (Button) view.findViewById(R.id.btnSendEmail);
        mBtnRestart = (Button) view.findViewById(R.id.btnRestart);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSendEmail) { //Button SendEmail
            Bundle bundle = getArguments(); //Get Bundle to SingUpStep2
            String firstName = bundle.getString(UserInfoConstant.FIRST_NAME);
            String lastName = bundle.getString(UserInfoConstant.LAST_NAME);
            String email = bundle.getString(UserInfoConstant.EMAIL);
            String phoneNumber = bundle.getString(UserInfoConstant.PHONE_NUMBER);
            String salary = String.valueOf(bundle.getInt(UserInfoConstant.SALARY));
            String file = bundle.getString(UserInfoConstant.DESTINATION);
            String subject = "User's registration info.";
            String content = firstName + "_" + lastName + "\n" + phoneNumber + "\n" + salary;

            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:")); //Intent-filter Email
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            intent.putExtra(Intent.EXTRA_TEXT, content);
            Uri uri = Uri.parse("file://" + file);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
        if (v.getId() == R.id.btnRestart) { //Button Restart

            FragmentManager fragmentManager = getFragmentManager();
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); i++) {
                fragmentManager.popBackStack();
            }

            SignUpStep1Fragment fragment = new SignUpStep1Fragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.signUp, fragment, UserInfoConstant.SIGNUP_STEP1);
            transaction.commit();
        }
    }
}
