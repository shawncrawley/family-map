package com.example.shawn.familymap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class FilterActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Switch swch_evnt_birth = (Switch) findViewById(R.id.swch_evnt_birth);
        Switch swch_evnt_baptism = (Switch) findViewById(R.id.swch_evnt_baptism);
        Switch swch_evnt_census = (Switch) findViewById(R.id.swch_evnt_census);
        Switch swch_evnt_christening = (Switch) findViewById(R.id.swch_evnt_christening);
        Switch swch_evnt_death = (Switch) findViewById(R.id.swch_evnt_death);
        Switch swch_evnt_marriage = (Switch) findViewById(R.id.swch_evnt_marriage);
        Switch swch_evnt_fatherside = (Switch) findViewById(R.id.swch_evnt_fatherside);
        Switch swch_evnt_motherside = (Switch) findViewById(R.id.swch_evnt_motherside);
        Switch swch_evnt_male = (Switch) findViewById(R.id.swch_evnt_male);
        Switch swch_evnt_female = (Switch) findViewById(R.id.swch_evnt_female);

        // Setup display according to user's preferences
        swch_evnt_birth.setChecked(DataModel.SINGLETON.getFilters().get("birth"));
        swch_evnt_baptism.setChecked(DataModel.SINGLETON.getFilters().get("baptism"));
        swch_evnt_census.setChecked(DataModel.SINGLETON.getFilters().get("census"));
        swch_evnt_christening.setChecked(DataModel.SINGLETON.getFilters().get("christening"));
        swch_evnt_death.setChecked(DataModel.SINGLETON.getFilters().get("death"));
        swch_evnt_marriage.setChecked(DataModel.SINGLETON.getFilters().get("marriage"));
        swch_evnt_fatherside.setChecked(DataModel.SINGLETON.getFilters().get("fatherside"));
        swch_evnt_motherside.setChecked(DataModel.SINGLETON.getFilters().get("motherside"));
        swch_evnt_male.setChecked(DataModel.SINGLETON.getFilters().get("male"));
        swch_evnt_female.setChecked(DataModel.SINGLETON.getFilters().get("female"));

        // Set on change listeners
        swch_evnt_birth.setOnCheckedChangeListener(this);
        swch_evnt_baptism.setOnCheckedChangeListener(this);
        swch_evnt_census.setOnCheckedChangeListener(this);
        swch_evnt_christening.setOnCheckedChangeListener(this);
        swch_evnt_death.setOnCheckedChangeListener(this);
        swch_evnt_marriage.setOnCheckedChangeListener(this);
        swch_evnt_fatherside.setOnCheckedChangeListener(this);
        swch_evnt_motherside.setOnCheckedChangeListener(this);
        swch_evnt_male.setOnCheckedChangeListener(this);
        swch_evnt_female.setOnCheckedChangeListener(this);
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
            case R.id.swch_evnt_birth:
                switchString = "birth";
                break;
            case R.id.swch_evnt_baptism:
                switchString = "baptism";
                break;
            case R.id.swch_evnt_census:
                switchString = "census";
                break;
            case R.id.swch_evnt_christening:
                switchString = "christening";
                break;
            case R.id.swch_evnt_death:
                switchString = "death";
                break;
            case R.id.swch_evnt_marriage:
                switchString = "marriage";
                break;
            case R.id.swch_evnt_fatherside:
                switchString = "fatherside";
                break;
            case R.id.swch_evnt_motherside:
                switchString = "motherside";
                break;
            case R.id.swch_evnt_male:
                switchString = "male";
                break;
            case R.id.swch_evnt_female:
                switchString = "female";
                break;
        }
        DataModel.SINGLETON.getFilters().put(switchString, isChecked);
    }
}
