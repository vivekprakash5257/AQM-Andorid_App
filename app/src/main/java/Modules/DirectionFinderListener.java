package Modules;

import android.view.Menu;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import Modules.Route;

//Direction finder interface

public interface DirectionFinderListener {
   // boolean onCreateOptionsMenu(Menu menu);

    void onDirectionFinderStart();
    void onDirectionFinderSuccess(List<Route> route);
}
