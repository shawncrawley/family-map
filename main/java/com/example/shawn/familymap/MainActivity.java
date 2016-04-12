package com.example.shawn.familymap;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends AppCompatActivity {

    private Menu _menu;
    private int menuLoadCount = 0;

    public Menu get_menu() {
        return _menu;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
        setContentView(R.layout.activity_main);

        FragmentManager fm = this.getSupportFragmentManager();
        Fragment loginFragment = fm.findFragmentById(R.id.fragment_container);
        if (loginFragment == null) {
            loginFragment = new LoginFragment();
            fm.beginTransaction().add(R.id.fragment_container, loginFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this._menu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menuLoadCount > 0) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_main, menu);

            menu.findItem(R.id.menu_item_search).setIcon(
                    new IconDrawable(this, FontAwesomeIcons.fa_search)
                            .colorRes(R.color.menuIcon)
                            .actionBarSize());
            menu.findItem(R.id.menu_item_filter).setIcon(
                    new IconDrawable(this, FontAwesomeIcons.fa_filter)
                            .colorRes(R.color.menuIcon)
                            .actionBarSize());
            menu.findItem(R.id.menu_item_settings).setIcon(
                    new IconDrawable(this, FontAwesomeIcons.fa_gear)
                            .colorRes(R.color.menuIcon)
                            .actionBarSize());
        }
        this.menuLoadCount++;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_item_filter:
                intent = new Intent(MainActivity.this, FilterActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_item_search:
                intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_item_settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
