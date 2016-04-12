package com.example.shawn.familymap;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Holds the Map view (the map and the event details)
 */
public class MapviewFragment extends Fragment {
    public static ImageView genderIcon;
    public static TextView personNameText;
    public static TextView personInfoText;
    private final Handler handler = new Handler();
    private Runnable runPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_mapview, container, false);
        genderIcon = (ImageView) v.findViewById(R.id.genderIcon);
        personNameText = (TextView) v.findViewById(R.id.personNameText);
        personInfoText = (TextView) v.findViewById(R.id.personInfoText);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        runPager = new Runnable() {

            @Override
            public void run()
            {
                FragmentManager fm = ((MainActivity)getActivity()).getSupportFragmentManager();
                Fragment mapFragment = fm.findFragmentById(R.id.fragment_container_map);
                if (mapFragment == null) {
                    mapFragment = new MapFragment();
                    fm.beginTransaction().add(R.id.fragment_container_map, mapFragment).commit();
                }
            }
        };
        handler.post(runPager);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        handler.removeCallbacks(runPager);
    }
}
