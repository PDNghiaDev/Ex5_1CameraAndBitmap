package com.gmail.pdnghiadev.ex5_1cameraandbitmap.presenter;


import com.gmail.pdnghiadev.ex5_1cameraandbitmap.model.SignUpStep1Interactor;
import com.gmail.pdnghiadev.ex5_1cameraandbitmap.model.SignUpStep1InteractorImpl;
import com.gmail.pdnghiadev.ex5_1cameraandbitmap.untils.OnNextListener;
import com.gmail.pdnghiadev.ex5_1cameraandbitmap.view.SignUpStep1View;

/**
 * Created by PDNghiaDev on 11/26/2015.
 */
public class SignUpStep1PresenterImpl implements SignUpStep1Presenter, OnNextListener {
    private SignUpStep1View signUpView;
    private SignUpStep1Interactor signUpInteractor;

    public SignUpStep1PresenterImpl(SignUpStep1View signUpView) {
        this.signUpView = signUpView;
        this.signUpInteractor = new SignUpStep1InteractorImpl();
    }

    @Override
    public void validateCredentials(String firstname, String lastname, String email, String phonenumber, Boolean gender) {
        signUpInteractor.next(firstname, lastname, email, phonenumber, gender, this);
    }

    @Override
    public void onFistnameError() {
        signUpView.setFistnameError();
    }

    @Override
    public void onLastnameError() {
        signUpView.setLastnameError();
    }

    @Override
    public void onEmailError() {
        signUpView.setEmailError();
    }

    @Override
    public void onEmailInvalidError() {
        signUpView.setEmailInvalidError();
    }

    @Override
    public void onPhonenumberError() {
        signUpView.setPhonenumberError();
    }

    @Override
    public void onPhonenumberInvalidError() {
        signUpView.setPhonenumberInvalidError();
    }

    @Override
    public void onGenderError() {
        signUpView.setGenderError();
    }

    @Override
    public void onSuccess() {
        signUpView.navigationToStep2();
    }
}
