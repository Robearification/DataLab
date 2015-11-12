package com.example.robertgillis.datalab;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.OutputStreamWriter;

import data.UserInfoDB;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment{

    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private MyMenuListener mMyMenuListener;
    private SharedPreferences mSharedPreferences;
    private UserInfoDB mUserInfoDB;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mEmail = (EditText) v.findViewById(R.id.email);
        mPassword = (EditText) v.findViewById(R.id.password);

        mLogin = (Button) v.findViewById(R.id.login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateAndStore()) {
                    mSharedPreferences = getActivity().getSharedPreferences(
                            getString(R.string.SHARED_PREFS), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    editor.putBoolean(getString(R.string.LOGGEDIN), true);
                    editor.commit();
                    ((MyMenuListener) getActivity()).startMenu();
                }

            }
        });
        return v;
    }

    private boolean validateAndStore() {
        Activity activity = getActivity();
        EditText mEmail = (EditText) activity.findViewById(R.id.email);
        if (mEmail.getText().length() == 0) {
            Toast.makeText(activity, "Please enter email", Toast.LENGTH_LONG)
                    .show();
            mEmail.requestFocus();
            return false;
        }
        EditText mPassword = (EditText) activity.findViewById(R.id.password);
        if (mPassword.getText().length() == 0) {
            Toast.makeText(activity, "Please enter password", Toast.LENGTH_LONG)
                    .show();
            mPassword.requestFocus();
            return false;
        }
        //Store in file

        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    activity.openFileOutput(getString(R.string.ACCT_FILE)
                            , Context.MODE_PRIVATE));
            outputStreamWriter.write("email = " + mEmail.getText().toString() + ";");
            outputStreamWriter.write("password = " + mPassword.getText().toString());
            outputStreamWriter.close();
            Toast.makeText(activity, "Stored in File Successfully!", Toast.LENGTH_LONG)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        mUserInfoDB = new UserInfoDB(activity);
        if(mUserInfoDB.insertUser(mEmail.getText().toString(), mPassword.getText().toString())) {
            Toast.makeText(activity, "Added login to Local Database", Toast.LENGTH_LONG).show();
        }
        mUserInfoDB.closeDB();
        return true;
    }

    public interface MyMenuListener {
        public void startMenu();
    }

}
