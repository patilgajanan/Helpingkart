package com.appsplanet.helpingkart.Activity.AppIntroduction.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.appsplanet.helpingkart.R;


/**
 * Created by Dhiraj on 7/29/2016.
 */
public class AppIntroduction2 extends Fragment {

    Button log_in_button,sign_up_button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_introduction_2, container, false);


        return rootView;

    }

//    public void login() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View v = inflater.inflate(R.layout.dialog_login, null);
//        alertDialog.setView(v);
//
//        Button button_owr_login_screen_login = (Button) v.findViewById(R.id.button_owr_login_screen_login);
//        button_owr_login_screen_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        alertDialog.show();
//    }
//
//    public void signUp() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        final View v = inflater.inflate(R.layout.dialog_sign_up, null);
//        alertDialog.setView(v);
//
//        Button button_owr_login_screen_login = (Button) v.findViewById(R.id.button_owr_login_screen_login);
//        button_owr_login_screen_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        alertDialog.show();
//
//    }

}