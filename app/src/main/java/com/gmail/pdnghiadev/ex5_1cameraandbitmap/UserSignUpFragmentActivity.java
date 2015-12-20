package com.gmail.pdnghiadev.ex5_1cameraandbitmap;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.gmail.pdnghiadev.ex5_1cameraandbitmap.fragments.SignUpStep1Fragment;
import com.gmail.pdnghiadev.ex5_1cameraandbitmap.untils.UserInfoConstant;


public class UserSignUpFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up_fragment);

        SignUpStep1Fragment fragment = (SignUpStep1Fragment) getSupportFragmentManager().findFragmentByTag(UserInfoConstant.SIGNUP_STEP1);
        if (fragment == null){
            fragment = new SignUpStep1Fragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction(); // Su dung dung goi cua Fragment
            fragmentTransaction.replace(R.id.signUp, fragment, UserInfoConstant.SIGNUP_STEP1);
            fragmentTransaction.commit();
        }

    }

}
