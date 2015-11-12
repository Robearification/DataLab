package model;

import android.widget.EditText;

import java.util.List;

/**
 * Created by Robear on 2015/11/12.
 */
public class UserInfo {
    private String mLogin;
    private String mPassword;

    public UserInfo(String login, String password) {
        mLogin = login;
        mPassword = password;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        mLogin = login;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "mLogin='" + mLogin + '\'' +
                ", mPassword='" + mPassword + '\'' +
                '}';
    }
}
