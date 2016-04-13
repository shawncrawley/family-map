package com.example.shawn.familymap;

import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

/**
 * Class for storing all of the data for the Family Map user
 */
public class DataModel {
    public static DataModel SINGLETON = new DataModel();

    private Boolean loggedIn = false;
    private HashMap<String, Event> events;
    private HashMap<String, Person> people;
    private HashMap<Marker, Event> markers;
    private HashMap<String, Setting> settings;
    private String[] settingKeys = {"storylines", "treelines", "spouselines", "map_type"};
    private HashMap<String, Boolean> filters;
    private String[] filterKeys = {"baptism", "birth", "census", "christening", "death", "marriage", "fatherside", "motherside", "male", "female"};

    private DataModel() {
        events = new HashMap<>();
        people = new HashMap<>();
        markers = new HashMap<>();
        settings = new HashMap<>();
        filters = new HashMap<>();

        settings.put(settingKeys[0], new Setting("Red", true));
        settings.put(settingKeys[1], new Setting("Green", true));
        settings.put(settingKeys[2], new Setting("Blue", true));
        settings.put(settingKeys[3], new Setting("Normal", true));

        for (String key : filterKeys) {
            filters.put(key, true);
        }
    }

    public void addEvent(String id, Event event) {
        events.put(id, event);
    }

    public void addPerson(String id, Person person) {
        people.put(id, person);
    }

    public void addMarker(Marker marker, Event event) {
        markers.put(marker, event);
    }

    public HashMap<String, Event> getEvents() {
        return events;
    }

    public HashMap<String, Person> getPeople() {
        return people;
    }

    public HashMap<Marker, Event> getMarkers() {
        return markers;
    }

    public HashMap<String, Setting> getSettings() {
        return settings;
    }

    public HashMap<String, Boolean> getFilters() {
        return filters;
    }

    public Boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(Boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
