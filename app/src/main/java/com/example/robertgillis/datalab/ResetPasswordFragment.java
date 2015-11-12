package com.example.robertgillis.datalab;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import data.UserInfoDB;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResetPasswordFragment extends Fragment {
    public static String EMAIL;
    private String mEmail;
    private EditText mNewPwd;
    private EditText mConfirmPwd;
    private Button mReset;

    public ResetPasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_reset_password, container, false);

        final EditText newPassword = (EditText) v.findViewById(R.id.new_password);
        final EditText confirmPassword = (EditText) v.findViewById(R.id.confirm_password);


        mReset = (Button) v.findViewById(R.id.reset_button);
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
                    Toast.makeText(getActivity(), "Passwords don't match", Toast.LENGTH_SHORT)
                            .show();

                }
                else {
                    UserInfoDB userInfoDB = new UserInfoDB(view.getContext());
                    userInfoDB.resetPassword(mEmail, newPassword.getText().toString());
                    userInfoDB.closeDB();

                    ((LoginFragment.MyMenuListener )getActivity()).startMenu();
                }

            }
        });
        return v;


    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        mEmail = args.getString(EMAIL);
        TextView textView = (TextView) getActivity().findViewById(R.id.display_email);
        textView.setText(mEmail);

    }
}
