package com.example.robertgillis.datalab;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import data.UserInfoDB;
import model.UserInfo;

public class MainActivity extends AppCompatActivity implements LoginFragment.MyMenuListener, MenuFragment.OnUserInfoInteractionListener, UserFragment.ResetPasswordListener {

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = getSharedPreferences(
                getString(R.string.SHARED_PREFS), MODE_PRIVATE);

        if (savedInstanceState != null)
            return;
        boolean loggedIn = mSharedPreferences.getBoolean(
                getString(R.string.LOGGEDIN), false);
        if (findViewById(R.id.fragment_container) != null) {

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (!loggedIn) {
                LoginFragment loginFragment = new LoginFragment();
                fragmentTransaction.add(R.id.fragment_container, loginFragment)
                        .commit();
            }
            else {
                MenuFragment menuFragment = new MenuFragment();
                fragmentTransaction.add(R.id.fragment_container, menuFragment)
                        .commit();
            }
        }
    }

    @Override
    public void showUserFragment(int position) {
        UserFragment userFragment = new UserFragment();
        Bundle args = new Bundle();
        args.putInt(UserFragment.POSITION, position);
        userFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, userFragment)
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void launchResetPassword(String email) {
        ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
        Bundle args = new Bundle();
        args.putString(ResetPasswordFragment.EMAIL, email);
        resetPasswordFragment.setArguments(args);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, resetPasswordFragment)
                .addToBackStack(null)
                .commit();
    }

    public static List<UserInfo> getUserList(Context c) {
        UserInfoDB userInfoDB = new UserInfoDB(c);
        List<UserInfo> list = userInfoDB.selectUsers();
        userInfoDB.closeDB();
        return list;
    }

    @Override
    public void startMenu() {
        MenuFragment menuFragment = new MenuFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,menuFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            mSharedPreferences = getSharedPreferences(getString(R.string.SHARED_PREFS), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putBoolean(getString(R.string.LOGGEDIN), false);
            editor.commit();
            LoginFragment loginFragment = new LoginFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, loginFragment).commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
