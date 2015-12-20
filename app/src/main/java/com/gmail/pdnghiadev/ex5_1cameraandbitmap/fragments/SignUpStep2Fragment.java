package com.gmail.pdnghiadev.ex5_1cameraandbitmap.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gmail.pdnghiadev.ex5_1cameraandbitmap.R;
import com.gmail.pdnghiadev.ex5_1cameraandbitmap.untils.CustomDialog;
import com.gmail.pdnghiadev.ex5_1cameraandbitmap.untils.UserInfoConstant;


/**
 * Created by PDNghiaDev on 11/25/2015.
 * Class perform 2 functions
 * 1 - The user select their salary from SeekBar
 * 2 - The user select their sports from CheckBox
 */
public class SignUpStep2Fragment extends Fragment implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private SeekBar mSalary;
    private TextView mCountSalary;
    private Button mBtnDone;
    private CheckBox mFootball, mTennis, mPingpong, mSwimming, mVolleyball, mBasketball;
    private int salary = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sign_up_step_2_fragment, container, false);

        loadComponents(view);

        mSalary.setOnSeekBarChangeListener(this);
        mBtnDone.setOnClickListener(this);

        return view;
    }

    private void loadComponents(View view) {
        mSalary = (SeekBar) view.findViewById(R.id.skbSalary);
        mCountSalary = (TextView) view.findViewById(R.id.txtSalary);
        mBtnDone = (Button) view.findViewById(R.id.btnDone);
        mFootball = (CheckBox) view.findViewById(R.id.ckFootball);
        mTennis = (CheckBox) view.findViewById(R.id.ckTennis);
        mPingpong = (CheckBox) view.findViewById(R.id.ckPingpong);
        mSwimming = (CheckBox) view.findViewById(R.id.ckSwimming);
        mVolleyball = (CheckBox) view.findViewById(R.id.ckVolleyball);
        mBasketball = (CheckBox) view.findViewById(R.id.ckBasketball);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        salary = progress * 100;
        mCountSalary.setText("Your salary: " + salary + " dollars");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mCountSalary.setText("Your salary: " + salary + " dollars");
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mCountSalary.setText("Your salary: " + salary + " dollars");
    }

    @Override
    public void onClick(View v) {
        if (!getHobbies()){
            CustomDialog dialog = new CustomDialog("Please choose the sport that you like");
            dialog.show(getFragmentManager(), UserInfoConstant.SIGNUP_STEP2);
        }else {
            SignUpStep3Fragment fragment = new SignUpStep3Fragment();
            Bundle bundle = getArguments();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            bundle.putInt(UserInfoConstant.SALARY, salary);
            fragment.setArguments(bundle);
            ft.replace(R.id.signUp, fragment);
            ft.addToBackStack(UserInfoConstant.SIGNUP_STEP3);
            ft.commit();
        }
    }

    public Boolean getHobbies() {
        return mFootball.isChecked() || mTennis.isChecked()
                || mPingpong.isChecked() || mSwimming.isChecked()
                || mVolleyball.isChecked() || mBasketball.isChecked();

    }
}
