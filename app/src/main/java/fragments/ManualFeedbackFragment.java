package fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.v.fina.R;

import java.util.ArrayList;
import java.util.List;

import Modules.Route;

//import asc.clemson.electricfeedback.R;

//Fragment that finalizes the route selection and feedback gathering
//sends to Firebase Database
//Implements OnMapReadyCallback for rendering the map
//Implements DirectionFinderListener for generating the alternative route.

public class ManualFeedbackFragment extends Fragment implements OnMapReadyCallback {
    //layout of fragment
    private static View view;
    //passed in arguments, preferred route is the one the user selected before pressing on the FAB
    private ArrayList<LatLng> preferredRouteArray;
    private ArrayList<LatLng> otherRouteArray;
    //editable test box for optional text feedback submitting
    private EditText optionalTextView;
    //list of the generated routes from DirectionfinderListener
    private List<Route> routes;
    private int numOfRoutes = 3;
    //lines of routes to draw on the map
    private List<Polyline> polylinePaths = new ArrayList<>();
    //the map API fragment
    GoogleMap mMap;
    //user selected preferred Route
    private Polyline preferredRoute;
    //tolerance for lining up a marker to a polyline
    double tolerance = 30; //meters

    /** Called when the activity is first created. */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //inflate layout
        /*try {
            view = inflater.inflate(R.layout.fragment_feedback, container, false);
        } catch (android.view.InflateException e) {
            Toast.makeText(getActivity(), "PROBLEM", Toast.LENGTH_SHORT).show();
        }*/

        //catch for arguments
        Bundle bundle = getArguments();
        if (bundle != null){
            if (bundle.containsKey("preferredPoints")){
                preferredRouteArray = bundle.getParcelableArrayList("preferredPoints");
            }
            if (bundle.containsKey("otherPoints")){
                otherRouteArray = bundle.getParcelableArrayList("otherPoints");
            }
        }else
        {
            Toast.makeText(getActivity(), "no Args",Toast.LENGTH_LONG).show();
        }

        return view;
    }

    //self contained database connection and data push
    /*private void loginToFirebase() {

//Call OnCompleteListener if the user is signed in successfully//
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
//If the user has been authenticated...//
                if (task.isSuccessful()) {
//...then call requestLocationUpdates//
                    final String path = getString(R.string.firebase_path);

                    //Top level of database
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                    DatabaseReference userRef = ref.push().child("Users");

                    //Children of Users Directory
                    DatabaseReference feedbackRef = userRef.child("Feedback");
                    DatabaseReference routeRef = userRef.child("Routes");

                    //Children of Feedback directory
                    DatabaseReference winnerRef = feedbackRef.child("Winning Route");
                    DatabaseReference textRef = feedbackRef.child("Optional Feedback");

                    //Children of Routes directory
                    DatabaseReference userRoute = routeRef.child("Winning Route");
                    DatabaseReference altRoute = routeRef.child("Losing Route");

                    //push routes and feedback to firebase
                    userRoute.push().setValue(preferredRouteArray);
                    altRoute.push().setValue(otherRouteArray);
                    //winnerRef.push().setValue(BOOLEAN FOR BEST ROUTE);
                    String optionalText = optionalTextView.getText().toString();
                    textRef.push().setValue(optionalText);
                } else {
//If sign in fails, then log the error//
                    Log.d(TAG, "Firebase authentication failed");
                }
            }
        });
    }*/

    /** Called when the activity has become visible. */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*loatingActionButton fab = getView().findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new StartFragment();
                replaceFragment(fragment);
            }
        });

        optionalTextView = getView().findViewById(R.id.optionalText);

        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.mapFeedback);
        fragment.getMapAsync(this);*/
    }

    //Draw routes as lines on the map
    private void drawPolylines() {

        PolylineOptions preferredPolylineOptions = new PolylineOptions();
// Create polyline options with the already selected LatLng ArrayList
        preferredPolylineOptions.addAll(preferredRouteArray);
        preferredPolylineOptions
                .width(15)
                .color(Color.CYAN);
        mMap.addPolyline(preferredPolylineOptions);

 // add clickable marker to route
mMap.addMarker(new MarkerOptions()
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
        .position(preferredRouteArray.get(preferredRouteArray.size()/2)));

// Create polyline options with the already selected LatLng ArrayList
        PolylineOptions otherPolylineOptions = new PolylineOptions();
        otherPolylineOptions.addAll(otherRouteArray);
        otherPolylineOptions
                .width(15)
                .color(Color.GRAY);
        mMap.addPolyline(otherPolylineOptions);
    // add clickable marker to route
    mMap.addMarker(new MarkerOptions()
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
            .position(otherRouteArray.get(otherRouteArray.size()/2)));

        polylinePaths.add(mMap.addPolyline(preferredPolylineOptions));
        polylinePaths.add(mMap.addPolyline(otherPolylineOptions));

        //autozoom
        float zoomLevel = 10.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(preferredRouteArray.get(preferredRouteArray.size()/2), zoomLevel));

    }

    //once the map is ready to display ...
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            mMap.setMyLocationEnabled(true);
        }

        //drawLines
        drawPolylines();

        //listen for clicks on the polylines
        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                preferredRoute = polyline;
                polyline.setColor(Color.CYAN);
                for (Polyline pline :polylinePaths) {
                    if (!pline.equals(polyline)){
                        pline.setColor(Color.GRAY);
                    }
                }
            }
        });

        //listen for clicks on the markers
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //look though all the polylines
                for(int i = 0; i < polylinePaths.size(); i++){
                    if (PolyUtil.isLocationOnPath(marker.getPosition(), polylinePaths.get(i).getPoints(), true, tolerance)) {
                        polylinePaths.get(i).setColor(Color.CYAN);
                        for (Polyline pline :polylinePaths) {
                            if (!pline.equals(polylinePaths.get(i))){
                                pline.setColor(Color.GRAY);
                            }
                        }
                        return true;
                    }
                }
                return false;
            }
        });
    }

    //Create a new fragment and switch the view
    public void replaceFragment(Fragment someFragment) {
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
