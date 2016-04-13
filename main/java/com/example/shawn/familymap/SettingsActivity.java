package com.example.shawn.familymap;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener {
    String[] colors = { "Blue", "Red", "Green", "Yellow", "Orange", "Pink", "Purple" };
    String[] mapTypes = { "Normal", "Hybrid", "Satellite", "Terrain" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize view selectors
        Spinner spnr_color_storylines = (Spinner) findViewById(R.id.spnr_color_storylines);
        Spinner spnr_color_treelines = (Spinner) findViewById(R.id.spnr_color_treelines);
        final Spinner spnr_color_spouselines = (Spinner) findViewById(R.id.spnr_color_spouselines);
        Spinner spnr_map_type = (Spinner) findViewById(R.id.spnr_map_type);
        Switch swch_lines_tree = (Switch) findViewById(R.id.swch_lines_tree);
        Switch swch_lines_spouse = (Switch) findViewById(R.id.swch_lines_spouse);
        Switch swch_lines_lifestory = (Switch) findViewById(R.id.swch_lines_lifestory);
        RelativeLayout btn_logout = (RelativeLayout) findViewById(R.id.btn_logout);
        RelativeLayout btn_resync = (RelativeLayout) findViewById(R.id.btn_resync);

        // Set spinner options
        ArrayAdapter aa_colors = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, colors);
        aa_colors.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter aa_mapTypes = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, mapTypes);
        aa_mapTypes.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spnr_color_spouselines.setAdapter(aa_colors);
        spnr_color_storylines.setAdapter(aa_colors);
        spnr_color_treelines.setAdapter(aa_colors);
        spnr_map_type.setAdapter(aa_mapTypes);

        // Display currently saved settings
        spnr_color_spouselines.setSelection(aa_colors.getPosition(DataModel.SINGLETON.getSettings().get("spouselines").getValue()));
        swch_lines_spouse.setSelected(DataModel.SINGLETON.getSettings().get("spouselines").getState());
        spnr_color_treelines.setSelection(aa_colors.getPosition(DataModel.SINGLETON.getSettings().get("treelines").getValue()));
        swch_lines_tree.setSelected(DataModel.SINGLETON.getSettings().get("treelines").getState());
        spnr_color_storylines.setSelection(aa_colors.getPosition(DataModel.SINGLETON.getSettings().get("storylines").getValue()));
        swch_lines_lifestory.setSelected(DataModel.SINGLETON.getSettings().get("storylines").getState());
        spnr_map_type.setSelection(aa_mapTypes.getPosition(DataModel.SINGLETON.getSettings().get("map_type").getValue()));

        // Set event listeners
        spnr_color_spouselines.setOnItemSelectedListener(this);
        spnr_color_treelines.setOnItemSelectedListener(this);
        spnr_color_storylines.setOnItemSelectedListener(this);
        spnr_map_type.setOnItemSelectedListener(this);

        swch_lines_lifestory.setOnCheckedChangeListener(this);
        swch_lines_spouse.setOnCheckedChangeListener(this);
        swch_lines_tree.setOnCheckedChangeListener(this);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataModel.SINGLETON.setLoggedIn(false);
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btn_resync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginFragment.btnSignIn.callOnClick();
                Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p/>
     * Impelmenters can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Object selectedValue = parent.getItemAtPosition(position);
        String spinnerString = "";
        switch (parent.getId()) {
            case R.id.spnr_color_spouselines:
                spinnerString = "spouselines";
                break;
            case R.id.spnr_color_storylines:
                spinnerString = "storylines";
                break;
            case R.id.spnr_color_treelines:
                spinnerString = "treelines";
                break;
            case R.id.spnr_map_type:
                spinnerString = "map_type";
                break;
        }
        DataModel.SINGLETON.getSettings().get(spinnerString).setValue(selectedValue.toString());
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Called when the checked state of a compound button has changed.
     *
     * @param buttonView The compound button view whose state has changed.
     * @param isChecked  The new checked state of buttonView.
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String switchString = "";
        switch(buttonView.getId()) {
            case R.id.swch_lines_lifestory:
                switchString = "storylines";
                break;
            case R.id.swch_lines_spouse:
                switchString = "spouselines";
                break;
            case R.id.swch_lines_tree:
                switchString = "treelines";
                break;
        }
        DataModel.SINGLETON.getSettings().get(switchString).setState(isChecked);
    }
}
