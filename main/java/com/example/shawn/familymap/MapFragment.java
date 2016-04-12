package com.example.shawn.familymap;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Iconify.with(new FontAwesomeModule());
        setHasOptionsMenu(true);

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                IconDrawable icon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_map_marker)
                        .sizeDp(20)
                        .colorRes(R.color.birthEvent)
                        .alpha(255);

                Canvas canvas = new Canvas();
                Bitmap bitmap = Bitmap.createBitmap(icon.getIntrinsicWidth(), icon.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                canvas.setBitmap(bitmap);

                HashMap<String, Event> events = DataModel.SINGLETON.getEvents();
                for (String eventKey : DataModel.SINGLETON.getEvents().keySet()) {
//                    icon.colorRes(DataModel.SINGLETON.getFilter().getEventColors().get(events.get(eventKey).getDescription()));
                    icon.draw(canvas);
                    BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

                    LatLng location = new LatLng(events.get(eventKey).getLatitude(), events.get(eventKey).getLongitude());
                    Marker marker = mMap.addMarker(new MarkerOptions()
                            .icon(bitmapDescriptor)
                            .position(location));

                    DataModel.SINGLETON.addMarker(marker, events.get(eventKey));
                }

                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        Event event = DataModel.SINGLETON.getMarkers().get(marker);
                        Person person = DataModel.SINGLETON.getPeople().get(event.getPersonId());
                        MapviewFragment.personNameText.setText(person.getFirstName() + " " + person.getLastName());
                        MapviewFragment.personInfoText.setText(event.getDescription() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")");
                        if (person.getGender() == 'm') {
                            MapviewFragment.genderIcon.setImageDrawable(
                                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_male)
                                            .colorRes(R.color.maleIcon)
                            );
                        } else {
                            MapviewFragment.genderIcon.setImageDrawable(
                                    new IconDrawable(getActivity(), FontAwesomeIcons.fa_female)
                                            .colorRes(R.color.femaleIcon)
                            );
                        }
                        zoomToEvent(event);
                        return true;
                    }
                });
            }
        });
    }

    public void zoomToEvent(Event event) {
        LatLng location = new LatLng(event.getLatitude(), event.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
    }
}
