package com.gmail.pdnghiadev.ex5_1cameraandbitmap.model;


import com.gmail.pdnghiadev.ex5_1cameraandbitmap.untils.OnNextListener;

/**
 * Created by PDNghiaDev on 11/26/2015.
 */
public interface SignUpStep1Interactor {

    public void next(String firstname, String lastname, String email, String phonenumber, Boolean gender, OnNextListener listener);
}
