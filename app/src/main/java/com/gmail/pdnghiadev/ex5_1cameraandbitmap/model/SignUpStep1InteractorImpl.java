package com.gmail.pdnghiadev.ex5_1cameraandbitmap.model;

import android.text.TextUtils;
import android.util.Patterns;

import com.gmail.pdnghiadev.ex5_1cameraandbitmap.untils.OnNextListener;


/**
 * Created by PDNghiaDev on 11/26/2015.
 */
public class SignUpStep1InteractorImpl implements SignUpStep1Interactor {
    @Override
    public void next(String firstname, String lastname, String email, String phonenumber, Boolean gender, OnNextListener listener) {
        boolean error = false;

        if (TextUtils.isEmpty(firstname)){
            listener.onFistnameError();
            error = true;
        }
        if (TextUtils.isEmpty(lastname)){
            listener.onLastnameError();
            error = true;
        }
        if (TextUtils.isEmpty(email)){
            listener.onEmailError();
            error = true;
        }
        if (!isEmailValid(email)){
            listener.onEmailInvalidError();
            error = true;
        }
        if (TextUtils.isEmpty(phonenumber)){
            listener.onPhonenumberError();
            error = true;
        }
        if (isPhonenumberValid(phonenumber)){
            listener.onPhonenumberInvalidError();
            error = true;
        }
        if (gender){
            listener.onGenderError();
            error = true;
        }
        if (!error){
            listener.onSuccess();
        }
    }

    public Boolean isEmailValid(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public Boolean isPhonenumberValid(String phone){
        if (phone.length() == 10 || phone.length() == 11){
            return false;
        }else {
            return true;
        }
    }
}
