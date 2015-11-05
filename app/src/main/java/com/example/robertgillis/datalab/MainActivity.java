package com.example.robertgillis.datalab;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements LoginFragment.MyMenuListener {

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
