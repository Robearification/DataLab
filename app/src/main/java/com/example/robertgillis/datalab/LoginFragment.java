package com.example.robertgillis.datalab;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment{

    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private MyMenuListener mMyMenuListener;

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
                SharedPreferences mSharedPreferences = getActivity().getSharedPreferences
                        (getString(R.string.SHARED_PREFS),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean(getString(R.string.LOGGEDIN), true);
                editor.commit();
                ((MyMenuListener) getActivity()).startMenu();
            }
        });
        return v;
    }

    public interface MyMenuListener {
        public void startMenu();
    }

}
