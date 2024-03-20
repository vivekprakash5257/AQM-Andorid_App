package fragments;

import android.Manifest;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.PolyUtil;
import com.google.maps.android.ui.IconGenerator;
import com.v.fina.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Modules.AQIResult;
import Modules.DirectionFinder;
import Modules.DirectionFinderListener;
import Modules.Result;
import Modules.Route;
import Modules.SEnsrResult;
import Modules.login;
import Modules.sensdata;

import static android.content.ContentValues.TAG;
import static android.graphics.Typeface.BOLD;
import static android.graphics.Typeface.ITALIC;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_HYBRID;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NONE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_NORMAL;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_TERRAIN;

public class MapsFragment extends Fragment implements OnMapReadyCallback, DirectionFinderListener, AdapterView.OnItemSelectedListener {

    GoogleMap mMap;
    private static final LatLngBounds BOUNDS_INDIA = new LatLngBounds(
            new LatLng(6.4626999, 68.1097),
            new LatLng(35.513327, 97.39535869999999)
    );
    //SEnsrResult sEnsrResult=new SEnsrResult();
    private Marker marker;

    String k;
    Modules.login login=new login();
    Result result3=new Result();
    SEnsrResult result4=new SEnsrResult();
    Modules.AQIResult aqiResult = new AQIResult();
    Modules.sensdata sensdata=new sensdata();

    private Button btnFeedback;
    private EditText etOrigin;
    private EditText etDestination;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionsGranted = false;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static View view;

    private int numOfRoutes = 3;
    private Polyline preferredRoute;
    double tolerance = 30; //meters
    private List<Route> backupRoutes = null;
    private Boolean directionsFound = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;

    private boolean mShowPermissionDeniedDialog = false;
    private CheckBox mTrafficCheckbox;

    private CheckBox mMyLocationCheckbox;

    private CheckBox mBuildingsCheckbox;

    private CheckBox mIndoorCheckbox;

    private Spinner mSpinner;
    private  RequestQueue requestQueue;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         view = inflater.inflate(R.layout.fragment_maps, container, false);
        //getActivity().stopService(new Intent(getActivity(), TrackingService.class));



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mSpinner = (Spinner) getView().findViewById(R.id.layers_spinner);
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        this, R.array.layers_array, android.R.layout.simple_spinner_item);*/
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(getActivity(),R.array.layers_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
        mSpinner.setOnItemSelectedListener(this);

        mTrafficCheckbox = (CheckBox) getView().findViewById(R.id.traffic);
        mMyLocationCheckbox = (CheckBox) getView().findViewById(R.id.my_location);
        mBuildingsCheckbox = (CheckBox) getView().findViewById(R.id.buildings);
        mIndoorCheckbox = (CheckBox) getView().findViewById(R.id.indoor);
        directionsFound = false;

        btnFeedback = getView().findViewById(R.id.btnFeedback);
        etOrigin = (EditText) getView().findViewById(R.id.etOrigin);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        etDestination = (EditText) getView().findViewById(R.id.etDestination);
        MapFragment fragment = (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);

        getLocationPermission();

        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (directionsFound) {
                    ///packageUpFeedback();
                }else{
                    sendRequest();
                    //Hide keyboard
                    InputMethodManager inputManager = (InputMethodManager)
                            getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }

            }
        });
        mTrafficCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateTraffic();
            }
        });
        mBuildingsCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateBuildings();

            }
        });
        mIndoorCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateIndoor();
            }
        });
        mMyLocationCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMyLocation();
            }
        });
    }

    private void packageUpFeedback() {
        //bundle up the preferred route
            //default preferred route is set to the first generated route
        ArrayList<LatLng> preferredPoints = new ArrayList<>();
        preferredPoints.addAll(preferredRoute.getPoints());
        Bundle bigBundle = new Bundle();
        bigBundle.putParcelableArrayList("preferredPoints", preferredPoints);

        //bundle up the other route
        ArrayList<LatLng> otherPoints = new ArrayList<>();
        for (int i=0; i < polylinePaths.size(); i++) {
            if (polylinePaths.get(i) != (preferredRoute)){
                otherPoints.addAll(polylinePaths.get(i).getPoints());
                bigBundle.putParcelableArrayList("otherPoints",otherPoints);
            }
        }

        //switch to the ManualFeedbackFragment
        Fragment fragment =  new ManualFeedbackFragment();
        fragment.setArguments(bigBundle);
        replaceFragment(fragment);
    }
    public RequestQueue getRequestQueue()
    {
        if (requestQueue==null)
            requestQueue= Volley.newRequestQueue(getActivity());

        return requestQueue;
    }

    /*
         public method to add the Request to the the single
    instance of RequestQueue created above.Setting a tag to every
    request helps in grouping them. Tags act as identifier
    for requests and can be used while cancelling them
    */
    public void addToRequestQueue(Request request,String tag)
    {
        request.setTag(tag);
        getRequestQueue().add(request);

    }

/*
Cancel all the requests matching with the given tag
     */

    public void cancelAllRequests(String tag)
    {
        getRequestQueue().cancelAll(tag);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        //Auth();
        AuthP();
        updateMapType();
        updateTraffic();
        updateMyLocation();
        updateBuildings();
        updateIndoor();


        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //mMap.setMyLocationEnabled(true);
           // mMap.setMapType(MAP_TYPE_SATELLITE);
            //mMap.setTrafficEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(BOUNDS_INDIA,0));
            getDeviceLocation();
        }

        //TODO: set the clicked route/marker to the preferred route, when switching to feedback it is autoselected.
        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                preferredRoute = polyline;
                polyline.setColor(Color.CYAN);
                if (polyline.getTag()=="P1")
                {
                    ((TextView) getView().findViewById(R.id.tvDistance)).setText(backupRoutes.get(0).distance.text);   //For Distance
                    ((TextView) getView().findViewById(R.id.tvDuration)).setText(backupRoutes.get(0).duration.text);    //Show time
                }else if (polyline.getTag()=="P2") {
                    ((TextView) getView().findViewById(R.id.tvDistance)).setText(backupRoutes.get(1).distance.text);   //For Distance
                    ((TextView) getView().findViewById(R.id.tvDuration)).setText(backupRoutes.get(1).duration.text);    //Show time
                }else if (polyline.getTag()=="P3") {
                    ((TextView) getView().findViewById(R.id.tvDistance)).setText(backupRoutes.get(2).distance.text);   //For Distance
                    ((TextView) getView().findViewById(R.id.tvDuration)).setText(backupRoutes.get(2).duration.text);    //Show time
                }


                for (Polyline pline :polylinePaths) {
                    if (!pline.equals(polyline)){ //if pline is not the one we clicked on
                        pline.setColor(Color.GRAY); //set all other polylines to grey
                    }
                }
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for(int i = 0; i < polylinePaths.size(); i++){
                    if (PolyUtil.isLocationOnPath(marker.getPosition(), polylinePaths.get(i).getPoints(), true, tolerance)) {
                       preferredRoute = polylinePaths.get(i);
                       polylinePaths.get(i).setColor(Color.CYAN);
                        if (polylinePaths.get(i).getTag()=="P1")
                        {
                            ((TextView) getView().findViewById(R.id.tvDistance)).setText(backupRoutes.get(0).distance.text);   //For Distance
                            ((TextView) getView().findViewById(R.id.tvDuration)).setText(backupRoutes.get(0).duration.text);    //Show time
                        }else if (polylinePaths.get(i).getTag()=="P2") {
                            ((TextView) getView().findViewById(R.id.tvDistance)).setText(backupRoutes.get(1).distance.text);   //For Distance
                            ((TextView) getView().findViewById(R.id.tvDuration)).setText(backupRoutes.get(1).duration.text);    //Show time
                        }else if (polylinePaths.get(i).getTag()=="P3")
                        {
                            ((TextView) getView().findViewById(R.id.tvDistance)).setText(backupRoutes.get(2).distance.text);   //For Distance
                            ((TextView) getView().findViewById(R.id.tvDuration)).setText(backupRoutes.get(2).duration.text);    //Show time
                        }
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


    /*public void onTrafficToggled(View view) {

        updateTraffic();
    }*/
    public void updateTraffic() {
        if (!checkReady()) {
            AuthP();
            return;
        }
        mMap.setTrafficEnabled(mTrafficCheckbox.isChecked());
        AuthP();
    }

    private boolean checkReady() {
        if (mMap == null) {
            Toast.makeText(getActivity(), R.string.map_not_ready, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void updateBuildings() {
        if (!checkReady()) {
            return;
        }
        mMap.setBuildingsEnabled(mBuildingsCheckbox.isChecked());
    }
    private void updateIndoor() {
        if (!checkReady()) {
            return;
        }
        mMap.setIndoorEnabled(mIndoorCheckbox.isChecked());
    }
    private  void updateMyLocation(){
        if (!checkReady()) {
            return;
        }
        mMap.setMyLocationEnabled(mMyLocationCheckbox.isChecked());
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(getActivity(), "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    private void sendRequest() {
        String origin = etOrigin.getText().toString();
        String destination = etDestination.getText().toString();
       /* LatLng ori=getLocationFromAddress(getActivity().getBaseContext(),origin);
        Toast.makeText(getActivity(), "origin!", Toast.LENGTH_SHORT).show();
        LatLng dest=getLocationFromAddress(getActivity().getBaseContext(),destination);
        Toast.makeText(getActivity(), "dest", Toast.LENGTH_SHORT).show();*/
       /* MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(ori);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(markerOptions);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(dest);
        markerOptionsd.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(markerOptionsd);*/
        if (origin.isEmpty()) {
            //TODO: set origin to current location if it is left blank
            Toast.makeText(getActivity(), "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    //TODO: Inject our tracked Route into routes[0]
    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();
        backupRoutes = routes;

        //autozoom
        float zoomLevel = 10.0f; //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(routes.get(0).points.get(routes.get(0).points.size()/2), zoomLevel));

        //update button
        //directionsFound = true;
        //TextView btnFeedbackText = getView().findViewById(R.id.btnFeedback);
        //btnFeedbackText.setText(R.string.leave_feedback);
        Toast.makeText(getActivity(), "Directions found! Please select your preferred route.", Toast.LENGTH_LONG).show();
        //CHANGE NUMBER OF ROUTES
        //Check that enough routes are generated
        if (routes.size()<3){
            Toast.makeText(getActivity(), "Could not generate Routes...", Toast.LENGTH_LONG).show();
            return;
        }
        for (int i = 0; i < numOfRoutes; i++) {
            Route route = routes.get(i);

            ((TextView) getView().findViewById(R.id.tvDistance)).setText(route.distance.text);   //For Distance
            ((TextView) getView().findViewById(R.id.tvDuration)).setText(route.duration.text);
           // String ki= route.endAddress;
            LatLng orign=route.startLocation;
            LatLng destination=route.endLocation;
            String or=orign.toString();
            int a=85;
            String aq= String.valueOf(a);
            Toast.makeText(getActivity(),or, Toast.LENGTH_LONG).show();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(orign);
            //markerOptions.snippet("85");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.addMarker(markerOptions);
            MarkerOptions markerOptionsd = new MarkerOptions();
            markerOptionsd.position(destination);
            markerOptionsd.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptionsd);
            //show different labeled markers on each route
            //WARNING: this works for only 2 routes!
            if (i == 0){
                originMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                        .position(route.points.get(route.points.size()/2))));
            }
            if (i == 1){
                destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                        .position(route.points.get(route.points.size()/2))));
            }
            if (i == 2){
                destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.new_marker))
                        .position(route.points.get(route.points.size()/2))));
            }

            int color = 3;
            if(i == 0){
                color = Color.CYAN;
            }if(i==1){
                color = Color.GRAY;
            }if(i==2){
                color = Color.MAGENTA;
            }

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).color(color).width(15).clickable(true);

            for (int j = 0; j < route.points.size(); j++)
                polylineOptions.add(route.points.get(j));

            polylinePaths.add(mMap.addPolyline(polylineOptions));

            if (i == 0){
                polylinePaths.get(i).setTag("P1");
                preferredRoute = polylinePaths.get(i);
            }
            if (i == 1){
                polylinePaths.get(i).setTag("P2"); }
            if(i==2){
                polylinePaths.get(i).setTag("P3");
            }
        }
    }
   /* public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }*/
    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),COURSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(getActivity(),permissions,LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(),permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0 ){
                    for (int i=0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                }
            }
        }
    }

    /**
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
    private void getDeviceLocation() {
        try {
            if (mLocationPermissionsGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), 15));

                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void replaceFragment(Fragment someFragment) {
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        updateMapType();
    }

    private void updateMapType() {
        // No toast because this can also be called by the Android framework in onResume() at which
        // point mMap may not be ready yet.
        if (mMap == null) {
            return;
        }

        String layerName = ((String) mSpinner.getSelectedItem());
        if (layerName.equals(getString(R.string.normal))) {
            mMap.setMapType(MAP_TYPE_NORMAL);
        } else if (layerName.equals(getString(R.string.hybrid))) {
            mMap.setMapType(MAP_TYPE_HYBRID);


        } else if (layerName.equals(getString(R.string.satellite))) {
            mMap.setMapType(MAP_TYPE_SATELLITE);
        } else if (layerName.equals(getString(R.string.terrain))) {
            mMap.setMapType(MAP_TYPE_TERRAIN);
        } else if (layerName.equals(getString(R.string.none_map))) {
            mMap.setMapType(MAP_TYPE_NONE);
        } else {
            Log.i("LDA", "Error setting layer with name " + layerName);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        //donothig.

    }
    public void aqi(String a,String b){



      /*  final Auth auth=new Auth();
        Context context=getContext();
        auth.res(context);*/

        IconGenerator iconFactory = new IconGenerator(getActivity());
        iconFactory.setRotation(0);
        iconFactory.setContentRotation(0);
        iconFactory.setStyle(IconGenerator.STYLE_ORANGE);
        addIcon(iconFactory, makeCharSequence(a), new LatLng(28.365463, 75.583847));
        addIcon(iconFactory,makeCharSequence(b),new LatLng(28.633825,77.134735));
    }
    private void addIcon(IconGenerator iconFactory, CharSequence text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        mMap.addMarker(markerOptions);
        //mMap.setOnInfoWindowLongClickListener();
    }

    private CharSequence makeCharSequence(String a) {
       /* *//*final Auth auth=new Auth();
        Context context=getContext();
        auth.res(context);*//*

        String r= sEnsrResult.getValue();*/

        String prefix = a;
        String suffix = "aqi";
        String sequence = prefix + suffix;
        //String sequence =r;
        SpannableStringBuilder ssb = new SpannableStringBuilder(sequence);
        ssb.setSpan(new StyleSpan(BOLD), 0, prefix.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.setSpan(new StyleSpan(ITALIC), prefix.length(), sequence.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }

    public void AuthP (){

        final String url = "https://boschclimo-aqi.com/services/api/v1/users/login";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("password","Climo@903");
        params.put("username","CSIR_PILOT");

        JsonObjectRequest req2 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    String result = "authToken" + response.getString("authToken") + "OrgKey" + response.getString("OrgKey");

                    login.setAuthToken(response.getString("authToken"));
                    login.setOrgKey(response.getString("OrgKey"));
                    //Toast.makeText(getActivity(), "Auth BOSCH OK", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aqip( );

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AuthP();
                VolleyLog.e("Errorxsdx", error.getMessage());
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

            }

        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", " application/json; charset=utf-8");
                // headers.put("Accept", " application/json");
                headers.put("api_key", " apiKey");
                return headers;
            }
        };
        addToRequestQueue(req2,"post req2");
    }

    public void aqip(){
        final String urlaqi="https://boschclimo-aqi.com/services/api/v1/thing/aqi/summarylist";
        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, urlaqi, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("result");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject result=jsonArray.getJSONObject(0);
                        aqiResult.setAqi(result.getDouble("aqi"));
                        aqiResult.setLatitude(result.getDouble("latitude"));
                        aqiResult.setLongitude(result.getDouble("longitude"));

                        JSONObject resultd= jsonArray.getJSONObject(1);
                        aqiResult.setThingKeyd(resultd.getString("thingKey"));
                        aqiResult.setAqid(resultd.getDouble("aqi"));
                        aqiResult.setLatituded(resultd.getDouble("latitude"));
                        aqiResult.setLongituded(resultd.getDouble("longitude"));

                        aqi(aqiResult.getAqi().toString(),aqiResult.getAqid().toString());

                    }
                    // sensorpilan();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
              //  SensorsPilani();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq",error.getMessage());
                aqip();
                Toast.makeText(getActivity(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", " application/json; charset=utf-8");

                // headers.put("Accept", " application/json");
                headers.put("api_key", " apiKey");
                headers.put("Authorization", auth);
                headers.put("X-OrganizationKey",orgkey);
                return headers;
            }
        };
        // Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
        addToRequestQueue(jsonObjectRequest,"aqi");

    }

    
}
