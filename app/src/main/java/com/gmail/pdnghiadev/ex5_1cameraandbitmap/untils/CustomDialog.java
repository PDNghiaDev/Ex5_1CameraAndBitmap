package com.gmail.pdnghiadev.ex5_1cameraandbitmap.untils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by PDNghiaDev on 11/27/2015.
 */
public class CustomDialog extends DialogFragment {
    private String mMessage;

    public CustomDialog(String message){
        this.mMessage = message;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Message")
                .setMessage(mMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }
}
