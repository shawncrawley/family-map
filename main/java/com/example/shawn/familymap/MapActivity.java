package com.example.shawn.familymap;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

public class MapActivity extends FragmentActivity {
    Event currentEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Bundle extras = getIntent().getExtras();

        FragmentManager fm = this.getSupportFragmentManager();
        Fragment mapviewFragment = fm.findFragmentById(R.id.fragment_map_container);
        if (mapviewFragment == null) {
            mapviewFragment = new MapviewFragment();
            if (extras != null) {
                mapviewFragment.setArguments(extras);
            }
            fm.beginTransaction().add(R.id.fragment_map_container, mapviewFragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_deep, menu);

        menu.findItem(R.id.menu_item_top).setIcon(
                new IconDrawable(this, FontAwesomeIcons.fa_angle_double_up)
                        .colorRes(R.color.menuIcon)
                        .actionBarSize());

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                finish();
                return true;
            case R.id.menu_item_top:
                Intent intent = new Intent(MapActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
