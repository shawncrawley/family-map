package com.example.shawn.familymap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.HashMap;

/**
 * This class controls the Map Fragment
 */
public class MapFragment extends SupportMapFragment {
    private GoogleMap mMap;
    public static Person currentPerson = null;
    private Event currentEvent = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            currentEvent = (Event) getArguments().getSerializable("EXTRA_CURRENT_EVENT");
        }
        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
        setHasOptionsMenu(true);

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                setMapType();
                drawMarkers();
                if (currentEvent != null) {
                    Person person = DataModel.SINGLETON.getPeople().get(currentEvent.getPersonId());
                    addEventData(person, currentEvent);
                    zoomToEvent(currentEvent);
                }
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Event event = DataModel.SINGLETON.getMarkers().get(marker);
                        Person person = DataModel.SINGLETON.getPeople().get(event.getPersonId());
                        addEventData(person, event);
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mMap != null) {
            mMap.clear();
            setMapType();
            drawMarkers();
        }
    }

    public void setMapType() {
        switch (DataModel.SINGLETON.getSettings().get("map_type").getValue()) {
            case "Normal":
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            case "Hybrid":
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case "Satellite":
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case "Terrain":
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
        }
    }

    public void drawMarkers() {
        IconDrawable icon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker)
                .sizeDp(20)
                .alpha(255);

        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(icon.getIntrinsicWidth(), icon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);

        HashMap<String, Event> events = DataModel.SINGLETON.getEvents();
        for (String eventKey : DataModel.SINGLETON.getEvents().keySet()) {
            Event event = events.get(eventKey);
            if (markerFilterOn(event.getDescription())) {
//                Character gender = DataModel.SINGLETON.getPeople().get(event.getPersonId()).getGender();
//                if ((gender == 'm' && markerFilterOn("male")) || (gender == 'f' && markerFilterOn("female"))) {
//                    if (markerFilterOn("fatherside") && markerFilterOn("motherside")) {
//                        addMarkerToMap(event);
                icon.colorRes(getMarkerColor(event.getDescription()));
                icon.draw(canvas);
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

                LatLng location = new LatLng(event.getLatitude(), event.getLongitude());
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .icon(bitmapDescriptor)
                        .position(location));

                DataModel.SINGLETON.addMarker(marker, event);
//                    } else if (markerFilterOn("fatherside") && !markerFilterOn("motherside")) {
//                        drawMarkersFathersSide();
//                    } else if (!markerFilterOn("fatherside") && markerFilterOn("motherside")) {
//                        drawMarkersMothersSide();
//                    }
            }
            if (DataModel.SINGLETON.getSettings().get("spouselines").getState()) {
                Person person = DataModel.SINGLETON.getPeople().get(event.getPersonId());
                if (person.getSpouseId() != null) {
                    person = DataModel.SINGLETON.getPeople().get(person.getSpouseId());
                    drawSpouseLines(event, person);
                }
            }
            if (DataModel.SINGLETON.getSettings().get("storylines").getState()) {
                drawStoryLines(event);
            }
            if (DataModel.SINGLETON.getSettings().get("treelines").getState()) {
                //TODO
            }
        }
    }

    public int getMarkerColor(String eventDescription) {
        int colorId = 0;
        switch (eventDescription.toLowerCase()) {
            case "baptism":
                colorId = R.color.baptismEvent;
                break;
            case "birth":
                colorId = R.color.birthEvent;
                break;
            case "census":
                colorId = R.color.censusEvent;
                break;
            case "christening":
                colorId = R.color.christeningEvent;
                break;
            case "death":
                colorId = R.color.deathEvent;
                break;
            case "marriage":
                colorId = R.color.marriageEvent;
                break;
            default:
                colorId = R.color.marriageEvent;
                break;
        }
        return colorId;
    }

    public void addMarkerToMap(Event event) {

    }

    public Boolean markerFilterOn(String filter) {
        filter = filter.toLowerCase();
        if (DataModel.SINGLETON.getFilters().get(filter) == null) {
            return true;
        }
        return DataModel.SINGLETON.getFilters().get(filter);
    }

    public void zoomToEvent(Event event) {
        LatLng location = new LatLng(event.getLatitude(), event.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
    }

    public void addEventData(Person person, Event event) {
        MapviewFragment.personNameText.setText(person.getFullName());
        MapviewFragment.personInfoText.setText(event.getInfo());
        if (person.getGender() == 'm') {
            MapviewFragment.genderIcon.setImageDrawable(
                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_male)
                            .sizeDp(40)
                            .colorRes(R.color.maleIcon)
            );
        } else {
            MapviewFragment.genderIcon.setImageDrawable(
                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_female)
                            .sizeDp(40)
                            .colorRes(R.color.femaleIcon)
            );
        }
        currentPerson = person;
    }

    public void drawSpouseLines(Event event, Person spouse) {
        LatLng eventLatLon = new LatLng(event.getLatitude(), event.getLongitude());
        Event spouseEvent = spouse.getEventsList().get(0);
        if (spouseEvent != null) {
            LatLng spouseLatLon = new LatLng(spouseEvent.getLatitude(), spouseEvent.getLongitude());
            String color = DataModel.SINGLETON.getSettings().get("spouselines").getValue();
            mMap.addPolyline(new PolylineOptions()
                    .add(eventLatLon, spouseLatLon)
                    .width(2)
                    .color(getColorFromSettings(color)));
        }
    }

    public void drawTreeLines() {

    }

    public void drawStoryLines(Event event) {
        Person person = DataModel.SINGLETON.getPeople().get(event.getPersonId());
        int numEvents = person.getEventsList().size();
        String color = DataModel.SINGLETON.getSettings().get("storylines").getValue();
        Event event1;
        Event event2;
        for (int i = 0; i < numEvents - 1; i++) {
            event1 = person.getEventsList().get(i);
            event2 = person.getEventsList().get(i+1);
            LatLng latLon1 = new LatLng(event1.getLatitude(), event1.getLongitude());
            LatLng latLon2 = new LatLng(event2.getLatitude(), event2.getLongitude());
            mMap.addPolyline(new PolylineOptions()
                    .add(latLon1, latLon2)
                    .width(2)
                    .color(getColorFromSettings(color)));
        }
    }

    public int getColorFromSettings(String color) {
        int intColor = 0;
        switch (color) {
            case "Blue":
                intColor = ContextCompat.getColor(getView().getContext(), R.color.christeningEvent);
                break;
            case "Red":
                intColor = ContextCompat.getColor(getView().getContext(), R.color.baptismEvent);
                break;
            case "Green":
                intColor = ContextCompat.getColor(getView().getContext(), R.color.censusEvent);
                break;
            case "Yellow":
                intColor = ContextCompat.getColor(getView().getContext(), R.color.yellow);
                break;
            case "Orange":
                intColor = ContextCompat.getColor(getView().getContext(), R.color.orange);
                break;
            case "Pink":
                intColor = ContextCompat.getColor(getView().getContext(), R.color.femaleIcon);
                break;
            case "Purple":
                intColor = ContextCompat.getColor(getView().getContext(), R.color.purple);
                break;
        }
        return intColor;
    }

    public void drawMarkersFathersSide() {
        Person rootPerson = DataModel.SINGLETON.getPeople().get(LoginFragment.userId);
        Person fatherSideMale = rootPerson;
        Person fatherSideFemale = rootPerson;
        while (fatherSideMale.getFatherId() != null) {
            fatherSideMale = DataModel.SINGLETON.getPeople().get(fatherSideMale.getFatherId());
        }
        while (fatherSideFemale.getMotherId() != null) {
            fatherSideFemale = DataModel.SINGLETON.getPeople().get(fatherSideFemale.getFatherId());
        }

    }

    public void drawMarkersMothersSide() {

    }
}
