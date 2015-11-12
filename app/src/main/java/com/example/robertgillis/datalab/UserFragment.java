package com.example.robertgillis.datalab;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import data.UserInfoDB;
import model.UserInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    public final static String POSITION = "position";
    private int mUserPosition = -1;
    private TextView mEmail;
    private TextView mPassword;
    private Button mResetPwd;
    private Button mDelete;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_user, container, false);

        mDelete = (Button) v.findViewById(R.id.delete_user);
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserInfoDB userInfoDB = new UserInfoDB(view.getContext());
                userInfoDB.deleteUserByEmail(mEmail.getText().toString());
                userInfoDB.closeDB();

                getFragmentManager().popBackStackImmediate();
            }
        });

        mResetPwd = (Button) v.findViewById(R.id.reset_pwd_button);
        mResetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ResetPasswordListener)getActivity()).launchResetPassword(mEmail.getText().toString());
            }
        });

        return v;

    }

    @Override
    public void onStart() {
        super.onStart();

        Bundle args = getArguments();
        if (args != null) {
            mUserPosition = args.getInt(POSITION);
        }
        if (mUserPosition != -1) {
            updateUserView(mUserPosition);
        }
    }
    public void updateUserView(int id) {
        Activity activity = getActivity();
        mEmail = (TextView) activity.findViewById(R.id.email_text);
        mPassword = (TextView) activity.findViewById(R.id.pwd_text);
        List<UserInfo> list =   MainActivity.getUserList(
                activity.getApplicationContext());

        if (id != -1) {
            UserInfo userInfo = (UserInfo) list.get(id);
            mEmail.setText(userInfo.getLogin());
            mPassword.setText(userInfo.getPassword());
        }
    }

    public interface ResetPasswordListener {
        public void launchResetPassword(String email);
    }
}
