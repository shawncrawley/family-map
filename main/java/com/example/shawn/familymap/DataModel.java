package com.example.shawn.familymap;

import com.google.android.gms.maps.model.Marker;

import java.util.HashMap;

/**
 * Class for storing all of the data for the Family Map user
 */
public class DataModel {
    public static DataModel SINGLETON = new DataModel();

    private HashMap<String, Event> events;
    private HashMap<String, Person> people;
    private HashMap<Marker, Event> markers;
//    private Filters filters;

    private DataModel() {
        events = new HashMap<>();
        people = new HashMap<>();
        markers = new HashMap<>();
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
}
