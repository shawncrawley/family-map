package com.example.shawn.familymap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = this.getSupportFragmentManager();
        Fragment loginFragment = fm.findFragmentById(R.id.fragment_container);
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
            fm.beginTransaction().add(R.id.fragment_container, loginFragment).commit();
        }
    }
}
