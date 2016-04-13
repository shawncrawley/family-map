package com.example.shawn.familymap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersonActivity extends AppCompatActivity {
    ExpandableListAdapterPerson listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Person currentPerson = MapFragment.currentPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentPerson = (Person)extras.getSerializable("EXTRA_CURRENT_PERSON");
        }

        Iconify.with(new FontAwesomeModule());
        TextView firstName = (TextView) findViewById(R.id.firstName);
        TextView lastName = (TextView) findViewById(R.id.lastName);
        TextView gender = (TextView) findViewById(R.id.gender);

        firstName.setText(currentPerson.getFirstName());
        lastName.setText(currentPerson.getLastName());
        String genderText = "";
        switch(currentPerson.getGender()) {
            case 'm':
                genderText = "Male";
                break;
            case 'f':
                genderText = "Female";
                break;
        }
        gender.setText(genderText);

        expListView = (ExpandableListView) findViewById(R.id.personExpandableList);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapterPerson(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (groupPosition == 0) {
                    Intent intent = new Intent(PersonActivity.this, MapActivity.class);
                    intent.putExtra("EXTRA_CURRENT_EVENT", currentPerson.getEventsList().get(childPosition));
                    startActivity(intent);
                } else {
                    String personString = parent.getItemAtPosition(childPosition + 2).toString().toLowerCase();
                    Intent intent = new Intent(PersonActivity.this, PersonActivity.class);
                    if (personString.contains("mother")) {
                        intent.putExtra("EXTRA_CURRENT_PERSON", DataModel.SINGLETON.getPeople().get(currentPerson.getMotherId()));
                    } else if (personString.contains("father")) {
                        intent.putExtra("EXTRA_CURRENT_PERSON", DataModel.SINGLETON.getPeople().get(currentPerson.getFatherId()));
                    } else if (personString.contains("spouse")) {
                        intent.putExtra("EXTRA_CURRENT_PERSON", DataModel.SINGLETON.getPeople().get(currentPerson.getSpouseId()));
                    }
                    startActivity(intent);
                }
                return false;
            }
        });
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
                Intent intent = new Intent(PersonActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
         * Preparing the list data
         */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        listDataHeader.add("Life Events");
        listDataHeader.add("Family");

        // Adding child data
        List<String> lifeEvents = new ArrayList<String>();
        for (Event event : currentPerson.getEventsList()) {
            lifeEvents.add("{fa-map-marker} " + event.getInfo());
        }

        List<String> family = new ArrayList<String>();
        if (currentPerson.getSpouseId() != null) {
            family.add("{fa-female} Spouse: " + DataModel.SINGLETON.getPeople().get(currentPerson.getSpouseId()).getFullName());
        }
        if (currentPerson.getFatherId() != null) {
            family.add("{fa-male} Father: " + DataModel.SINGLETON.getPeople().get(currentPerson.getFatherId()).getFullName());
        }
        if (currentPerson.getMotherId() != null) {
            family.add("{fa-female} Mother: " + DataModel.SINGLETON.getPeople().get(currentPerson.getMotherId()).getFullName());
        }

        if (lifeEvents.size() != 0) {
            listDataChild.put(listDataHeader.get(0), lifeEvents); // Header, Child data
        }
        if (family.size() != 0) {
            listDataChild.put(listDataHeader.get(1), family);
        }
    }
}
