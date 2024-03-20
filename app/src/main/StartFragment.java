package fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.v.fina.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Modules.AQIResult;
import Modules.Result;
import Modules.SEnsrResult;
import Modules.login;
import Modules.sensdata;
//import Modules.loginbosch;


//import asc.clemson.electricfeedback.R;
//Welcome page for launching the app
// gives 2 buttons the user can select
// one does manual route generation that does not require gps locations
// the other launches background gps tracking

public class StartFragment extends Fragment {


    String k;
    ///Modules.loginbosch loginbosch=new loginbosch();
    Modules.login login=new login();
    Result result3=new Result();
    SEnsrResult result4=new SEnsrResult();
    private TextView pilaniatemp;
    private TextView pilanihum;
    private TextView pilanipm2p5;
    private TextView pilanipm10;
    private TextView pilaniaqi;
    private TextView gpspilani;
    private TextView delhitemp;
    private TextView delhihum;
    private TextView delhipm2p5;
    private TextView delhipm10;
    private TextView delhiaqi;
    private TextView gpsdelhi;
    Modules.AQIResult aqiResult = new AQIResult();
    Modules.sensdata sensdata=new sensdata();
    private  RequestQueue requestQueue;
    private SwipeRefreshLayout swipeRefreshLayout;
   /* Result result3=new Result();
    SEnsrResult sEnsrResult=new SEnsrResult();*/


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_start, container, false);
        //CardView cardView= rootView.findViewById(R.id.pilani);

         //AuthP();
         //AuthD();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AuthP();
        AuthD();

        swipeRefreshLayout = getView().findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                AuthP();
                AuthD();
            }
        });
        swipeRefreshLayout.setColorSchemeColors(Color.RED);
        //TextView pilaniatemp              = (TextView) getView().findViewById(R.id.pilani_temp);
        /*final Auth auth=new Auth();
        Context context=getContext();
        auth.res(context);
        String r= sEnsrResult.getValue();

        pilaniatemp.setText(r);
*/

        //Set up buttons
        ImageButton refreshImageButton,refreshbuttond;
        //TextView textView=getView().findViewById(R.id.pilani_temp);
        CardView cardView=getView().findViewById(R.id.pilani);
        CardView cardView1=getView().findViewById(R.id.delhi);
        Button manBtn = getView().findViewById(R.id.button1);
        Button trackBtn = getView().findViewById(R.id.button2);
        refreshImageButton=getView().findViewById(R.id.pilani_refreshButton);
        refreshbuttond=getView().findViewById(R.id.delhi_refreshButton);

       /// pilaniatemp=getView().findViewById(R.id.pilani_temp);
        //ImageButton refrPilani =getView().findViewById(R.id.pilani_refreshButton);

        //CardView cardView=getView().findViewById(R.id.positionBasedCard);
        //CardView cardView1=getView().findViewById(R.id.bt2);
      refreshImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Context context=getContext();
               // auth.res(context);
                //Auth auth=new Auth();
                AuthP();

            }

        });
      refreshbuttond.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              AuthD();
          }
      });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DetailsFragment();
                replaceFragment(fragment);
            }
        });
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment=new DetailFragmentdelhi();
                replaceFragment(fragment);
            }
        });
        manBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MapsFragment();
                replaceFragment(fragment);
            }
        });

        trackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new TrackingFragment();
                replaceFragment(fragment);

            }
        });

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

    }

    //Create a new fragment and switch the view
    public void replaceFragment(Fragment someFragment) {
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
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

    public void AuthP (){


        //private ViewGroup viewGroup;
        /// View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pil, viewGroup, false);
        //Intent intent=new Intent(StartFragment)
        //public void res() {
        // public void res(final Context context) {
        // ImageButton refreshImageButton;

        ///refreshImageButton=

        final String url = "http://52.28.187.167/services/api/v1/users/login";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("password","Q2xpbW9AOTAz");
        params.put("username","Q1NJUl9QSUxPVA==");


       // RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        // Pass second argument as "null" for GET requests

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //String res=response.toString();
                    //GsonBuilder gsonBuilder= new GsonBuilder();
                    //Gson gson=gsonBuilder.create();
                    //Login login=gson.fromJson(response, Login.class);
                    // thing thing=new thing();
                    String result = "authToken" + response.getString("authToken") + "OrgKey" + response.getString("OrgKey");

                    login.setAuthToken(response.getString("authToken"));
                    login.setOrgKey(response.getString("OrgKey"));


                    //AuthToken=response.getString("authToken");
                    ///OrgKey=response.getString("OrgKey");

                    Toast.makeText(getActivity(), "Auth OK", Toast.LENGTH_LONG).show();
                    //allthing();
                    aqipilani( );
                    //AuthBOSCH();

                    // thing.allthing(response.getString("authToken"),response.getString("OrgKey"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
       /// StartFragment.addToRequestQueue(req,"postRequest");
        ///mRequestQueue.add(req);
        addToRequestQueue(req,"post req");
    }

    public void allthing( ){
        final String urlth="http://52.28.187.167/services/api/v1/getAllthings";


        //RequestQueue m2RequestQueue = Volley.newRequestQueue(this);

        //Login login=new Login();
        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, urlth, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("result");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject result=jsonArray.getJSONObject(0);
                        String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                        result3.setThingKey(result.getString("thingKey"));
                        JSONObject result2= jsonArray.getJSONObject(1);
                        result3.setThingKey2(result2.getString("thingKey"));


                        // String thingkey=result.getString("thingKey");
                        //String thingname=result.getString("thingName");
                        // String t=thingkey+thingname;
                        // Toast.makeText(getApplicationContext(),result1,Toast.LENGTH_LONG).show();
                        Toast.makeText(getActivity(),"ThingKey", Toast.LENGTH_SHORT).show();
                        /*getsensor("SENS_RELATIVE_HUMIDITY");
                        getsensor("SENS_PM2P5");
                        getsensor("SENS_PM10");*/
                        getsensorpilani("SENS_TEMPERATURE");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq",error.getMessage());
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
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
        //m2RequestQueue.add(jsonObjectRequest);
    }
    public void AuthBOSCH (){


        //private ViewGroup viewGroup;
        /// View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pil, viewGroup, false);
        //Intent intent=new Intent(StartFragment)
        //public void res() {
        // public void res(final Context context) {
        // ImageButton refreshImageButton;

        ///refreshImageButton=

        final String url = "https://boschclimo-aqi.com/services/api/v1/users/login";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("password","Climo%40903");
        params.put("username","CSIR_PILOT");


        // RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        // Pass second argument as "null" for GET requests

        JsonObjectRequest req2 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //String res=response.toString();
                    //GsonBuilder gsonBuilder= new GsonBuilder();
                    //Gson gson=gsonBuilder.create();
                    //Login login=gson.fromJson(response, Login.class);
                    // thing thing=new thing();
                    String result = "authToken" + response.getString("authToken") + "OrgKey" + response.getString("OrgKey");

                    //loginbosch.setAuthToken(response.getString("authToken"));
                    //loginbosch.setOrgKey(response.getString("OrgKey"));


                    //AuthToken=response.getString("authToken");
                    ///OrgKey=response.getString("OrgKey");

                    Toast.makeText(getActivity(), "Auth BOSCH OK", Toast.LENGTH_LONG).show();
                    //allthing();
                    aqipilani( );

                    // thing.allthing(response.getString("authToken"),response.getString("OrgKey"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

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
        /// StartFragment.addToRequestQueue(req,"postRequest");
        ///mRequestQueue.add(req);
        addToRequestQueue(req2,"post req2");
    }

    public void aqipilani(){
        final String urlaqi="http://52.28.187.167/services/api/v1/thing/aqi/summarylist";
        //final String urlaqi="https://boschclimo-aqi.com/services/api/v1/thing/aqi/summarylist";
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
                        aqiResult.setThingKey(result.getString("thingKey"));
                        aqiResult.setThingName((result.getString("thingName")));
                        aqiResult.setCategory(result.getString("category"));
                        aqiResult.setCity(result.getString("city"));
                        aqiResult.setColor(result.getString("color"));

                        JSONObject resultd= jsonArray.getJSONObject(1);
                        aqiResult.setThingKeyd(resultd.getString("thingKey"));
                        aqiResult.setAqid(resultd.getDouble("aqi"));
                        aqiResult.setLatituded(resultd.getDouble("latitude"));
                        aqiResult.setLongituded(resultd.getDouble("longitude"));
                        aqiResult.setThingKeyd(resultd.getString("thingKey"));
                        aqiResult.setThingNamed((resultd.getString("thingName")));
                        aqiResult.setCategoryd(resultd.getString("category"));
                        aqiResult.setCityd(resultd.getString("city"));
                        aqiResult.setColord(resultd.getString("color"));


                        //String gps=aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();

                        //allsenssor();
                        //
                        //Toast.makeText(getApplicationContext(),aqiResult.getCategory(),Toast.LENGTH_LONG).show();
                        /*for(int j= 0;j<sesnor.length;j++) {
                            getsensor(sesnor[i]);
                        }*/

                    }
                    getsensorpilani("SENS_TEMPERATURE");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq",error.getMessage());
                aqipilani();
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
    void getsensorpilani(final String property){

        // String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/f1086f69-8043-c59d-f4dc-2885d9b860e8-sensoragent/"+property+"/IST";

        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        //final String thingkey=result3.getThingKey();
        final String thingkey=aqiResult.getThingKey();
       // final String lat=aqiResult.getLatitude();
        String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/"+thingkey+"/"+property+"/IST";
        JsonObjectRequest jsonObjectRequest1= new JsonObjectRequest(Request.Method.GET, Baseurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("result");
                    //for (int i=0;i<jsonObject.length();i++){
                    //JSONObject result=jsonArray.getJSONObject(i);
                    String value="TEMP: "+jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    /*String gps1=aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*/
                    pilaniatemp=getView().findViewById(R.id.pilani_temp);
                    pilaniatemp.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    humpilani("SENS_RELATIVE_HUMIDITY");
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                getsensorpilani("SENS_TEMPERATURE");
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
        //Volley.newRequestQueue(getActivity()).add(jsonObjectRequest1);
        addToRequestQueue(jsonObjectRequest1,"temp");


    }

    void humpilani(final String property){

        // String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/f1086f69-8043-c59d-f4dc-2885d9b860e8-sensoragent/"+property+"/IST";

        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        //final String thingkey=result3.getThingKey();
        final String thingkey=aqiResult.getThingKey();
        // final String lat=aqiResult.getLatitude();
        String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/"+thingkey+"/"+property+"/IST";
        JsonObjectRequest jsonObjectRequest1= new JsonObjectRequest(Request.Method.GET, Baseurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("result");
                    //for (int i=0;i<jsonObject.length();i++){
                    //JSONObject result=jsonArray.getJSONObject(i);
                    String value="HUM: "+jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    String gps1="GPS: "+aqiResult.getLatitude().toString()+"   "+aqiResult.getLongitude().toString();
                    gpspilani=getView().findViewById(R.id.pilani_gpsTextView);
                    gpspilani.setText(gps1);
                    String aqip=aqiResult.getAqi().toString();
                    pilaniaqi=getView().findViewById(R.id.pilani_air_qualityTextView);

                    Double aq= aqiResult.getAqi();
                    if(aq >= 0&& aq <=50){
                        pilaniaqi.setBackgroundColor(Color.parseColor("FFACAF50"));}
                    else if(aq>=51 && aq<=100) {
                        pilaniaqi.setBackgroundColor(0xD88BC34A);
                    }
                    else if(aq>=101 && aq<=200){
                        pilaniaqi.setBackgroundColor(0xFFFFEB3B);
                    }
                    else if (aq>=201 && aq<=300){
                        pilaniaqi.setBackgroundColor(0xFFFF9800);
                    }
                    else if(aq>=301 && aq<=400){
                        pilaniaqi.setBackgroundColor(Color.parseColor("FFF44336"));
                    }
                    else{
                        pilaniaqi.setBackgroundColor(0xBCE91E63);
                    }

                    pilaniaqi.setText(aqip);
                    pilanihum=getView().findViewById(R.id.pilani_hum);
                    pilanihum.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    pm2p5pilani("SENS_PM2P5");
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                humpilani("SENS_RELATIVE_HUMIDITY");
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
        //Volley.newRequestQueue(getActivity()).add(jsonObjectRequest1);
        addToRequestQueue(jsonObjectRequest1,"hum");


    }
    void pm2p5pilani(final String property){

        // String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/f1086f69-8043-c59d-f4dc-2885d9b860e8-sensoragent/"+property+"/IST";

        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        //final String thingkey=result3.getThingKey();
        final String thingkey=aqiResult.getThingKey();
        // final String lat=aqiResult.getLatitude();
        String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/"+thingkey+"/"+property+"/IST";
        JsonObjectRequest jsonObjectRequest1= new JsonObjectRequest(Request.Method.GET, Baseurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("result");
                    //for (int i=0;i<jsonObject.length();i++){
                    //JSONObject result=jsonArray.getJSONObject(i);
                    String value="PM2P5: "+jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    /*String gps1="GPS: "+aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*/
                    pilanipm2p5=getView().findViewById(R.id.pilani_pm25);
                    pilanipm2p5.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    pm10pilani("SENS_PM10");
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                pm2p5pilani("SENS_PM2P5");
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
        //Volley.newRequestQueue(getActivity()).add(jsonObjectRequest1);
        addToRequestQueue(jsonObjectRequest1,"ppm2p5");


    }
    void pm10pilani(final String property){

        // String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/f1086f69-8043-c59d-f4dc-2885d9b860e8-sensoragent/"+property+"/IST";

        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        //final String thingkey=result3.getThingKey();
        final String thingkey=aqiResult.getThingKey();
        // final String lat=aqiResult.getLatitude();
        String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/"+thingkey+"/"+property+"/IST";
        JsonObjectRequest jsonObjectRequest1= new JsonObjectRequest(Request.Method.GET, Baseurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("result");
                    //for (int i=0;i<jsonObject.length();i++){
                    //JSONObject result=jsonArray.getJSONObject(i);
                    String value="PM10: "+jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    /*String gps1="GPS: "+aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*/
                    pilanipm10=getView().findViewById(R.id.pilani_pm10);
                    pilanipm10.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();

                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                pm10pilani("SENS_PM10");
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
        //Volley.newRequestQueue(getActivity()).add(jsonObjectRequest1);
        addToRequestQueue(jsonObjectRequest1,"ppm10");


    }
    public void AuthD (){


        //private ViewGroup viewGroup;
        /// View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pil, viewGroup, false);
        //Intent intent=new Intent(StartFragment)
        //public void res() {
        // public void res(final Context context) {
        // ImageButton refreshImageButton;

        ///refreshImageButton=

        final String url = "http://52.28.187.167/services/api/v1/users/login";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("password","Q2xpbW9AOTAz");
        params.put("username","Q1NJUl9QSUxPVA==");


        //RequestQueue mRequestQueue = Volley.newRequestQueue(getActivity());

        // Pass second argument as "null" for GET requests

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //String res=response.toString();
                    //GsonBuilder gsonBuilder= new GsonBuilder();
                    //Gson gson=gsonBuilder.create();
                    //Login login=gson.fromJson(response, Login.class);
                    // thing thing=new thing();
                    String result = "authToken" + response.getString("authToken") + "OrgKey" + response.getString("OrgKey");

                    login.setAuthToken(response.getString("authToken"));
                    login.setOrgKey(response.getString("OrgKey"));


                    //AuthToken=response.getString("authToken");
                    ///OrgKey=response.getString("OrgKey");

                    Toast.makeText(getActivity(), "Auth2 Ok", Toast.LENGTH_LONG).show();
                    //allthing();
                    aqidelhi( );

                    // thing.allthing(response.getString("authToken"),response.getString("OrgKey"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorxsdx", error.getMessage());
                AuthD();
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
        //mRequestQueue.add(req);
        addToRequestQueue(req,"d");
    }

    public void allthingd( ){
        final String urlth="http://52.28.187.167/services/api/v1/getAllthings";


        //RequestQueue m2RequestQueue = Volley.newRequestQueue(this);

        //Login login=new Login();
        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.GET, urlth, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("result");
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject result=jsonArray.getJSONObject(0);
                        String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                        result3.setThingKey(result.getString("thingKey"));
                        JSONObject result2= jsonArray.getJSONObject(1);
                        result3.setThingKey2(result2.getString("thingKey"));


                        // String thingkey=result.getString("thingKey");
                        //String thingname=result.getString("thingName");
                        // String t=thingkey+thingname;
                        // Toast.makeText(getApplicationContext(),result1,Toast.LENGTH_LONG).show();
                       //Toast.makeText(getActivity(), result2.getString("thingKey"), Toast.LENGTH_SHORT).show();
                        /*getsensor("SENS_RELATIVE_HUMIDITY");
                        getsensor("SENS_PM2P5");
                        getsensor("SENS_PM10");*/
                        getsensordelhi("SENS_TEMPERATURE");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq",error.getMessage());
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
        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
        //m2RequestQueue.add(jsonObjectRequest);
    }
    public void aqidelhi(){
        final String urlaqi="http://52.28.187.167/services/api/v1/thing/aqi/summarylist";
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
                        aqiResult.setThingKey(result.getString("thingKey"));
                        aqiResult.setThingName((result.getString("thingName")));
                        aqiResult.setCategory(result.getString("category"));
                        aqiResult.setCity(result.getString("city"));
                        aqiResult.setColor(result.getString("color"));

                        JSONObject resultd= jsonArray.getJSONObject(1);
                        aqiResult.setThingKeyd(resultd.getString("thingKey"));
                        aqiResult.setAqid(resultd.getDouble("aqi"));
                        aqiResult.setLatituded(resultd.getDouble("latitude"));
                        aqiResult.setLongituded(resultd.getDouble("longitude"));
                        aqiResult.setThingKeyd(resultd.getString("thingKey"));
                        aqiResult.setThingNamed((resultd.getString("thingName")));
                        aqiResult.setCategoryd(resultd.getString("category"));
                        aqiResult.setCityd(resultd.getString("city"));
                        aqiResult.setColord(resultd.getString("color"));


                        //String gps=aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();

                        //allsenssor();
                        //
                        //Toast.makeText(getApplicationContext(),aqiResult.getCategory(),Toast.LENGTH_LONG).show();
                        /*for(int j= 0;j<sesnor.length;j++) {
                            getsensor(sesnor[i]);
                        }*/

                    }
                    getsensordelhi("SENS_TEMPERATURE");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq",error.getMessage());
                aqidelhi();
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
        //Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
        addToRequestQueue(jsonObjectRequest,"aqid");

    }
    void getsensordelhi(final String property){

        // String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/f1086f69-8043-c59d-f4dc-2885d9b860e8-sensoragent/"+property+"/IST";

        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        //final String thingkey=result3.getThingKey();
        final String thingkey=aqiResult.getThingKeyd();
        // final String lat=aqiResult.getLatitude();
        String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/"+thingkey+"/"+property+"/IST";
        JsonObjectRequest jsonObjectRequest1= new JsonObjectRequest(Request.Method.GET, Baseurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("result");
                    //for (int i=0;i<jsonObject.length();i++){
                    //JSONObject result=jsonArray.getJSONObject(i);
                    String value="TEMP: "+jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    /*String gps1=aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*/
                    delhitemp=getView().findViewById(R.id.delhi_temp);
                    delhitemp.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    humdelhi("SENS_RELATIVE_HUMIDITY");
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                getsensordelhi("SENS_TEMPERATURE");
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
        //Volley.newRequestQueue(getActivity()).add(jsonObjectRequest1);
        addToRequestQueue(jsonObjectRequest1,"dtemp");


    }

    void humdelhi(final String property){

        // String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/f1086f69-8043-c59d-f4dc-2885d9b860e8-sensoragent/"+property+"/IST";

        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        //final String thingkey=result3.getThingKey();
        final String thingkey=aqiResult.getThingKeyd();
        // final String lat=aqiResult.getLatitude();
        String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/"+thingkey+"/"+property+"/IST";
        JsonObjectRequest jsonObjectRequest1= new JsonObjectRequest(Request.Method.GET, Baseurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("result");
                    //for (int i=0;i<jsonObject.length();i++){
                    //JSONObject result=jsonArray.getJSONObject(i);
                    String value="HUM: "+jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    String gpsd="GPS: "+aqiResult.getLatituded().toString()+"  "+aqiResult.getLongituded().toString();
                    gpsdelhi=getView().findViewById(R.id.delhi_gpsTextView);
                    gpsdelhi.setText(gpsd);
                    String aqidd=aqiResult.getAqid().toString();

                    delhiaqi=getView().findViewById(R.id.delhi_air_qualityTextView);
                    /*switch (aq){
                        case aq :if(aq >= 0&& aq <=50)
                            delhiaqi.setBackgroundColor(asdsad);
                            break;
                    }*/
                    Double aq= aqiResult.getAqid();
                    if(aq >= 0&& aq <=50){
                        delhiaqi.setBackgroundColor(Color.parseColor("FFACAF50"));}
                    else if(aq>=51 && aq<=100) {
                            delhiaqi.setBackgroundColor(0xD88BC34A);
                        }
                    else if(aq>=101 && aq<=200){
                            delhiaqi.setBackgroundColor(0xFFFFEB3B);
                        }
                    else if (aq>=201 && aq<=300){
                            delhiaqi.setBackgroundColor(0xFFFF9800);
                        }
                    else if(aq>=301 && aq<=400){
                            delhiaqi.setBackgroundColor(Color.parseColor("FFF44336"));
                        }
                    else{
                            delhiaqi.setBackgroundColor(0xBCE91E63);
                        }

                    delhiaqi.setText(aqidd);
                    //delhiaqi.setBackgroundColor(0xFF4CAF50);
                    delhihum=getView().findViewById(R.id.delhi_hum);
                    delhihum.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    pm2p5delhi("SENS_PM2P5");
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                humdelhi("SENS_RELATIVE_HUMIDITY");
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
        //Volley.newRequestQueue(getActivity()).add(jsonObjectRequest1);
        addToRequestQueue(jsonObjectRequest1,"dhum");

    }
    void pm2p5delhi(final String property){

        // String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/f1086f69-8043-c59d-f4dc-2885d9b860e8-sensoragent/"+property+"/IST";

        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        //final String thingkey=result3.getThingKey();
        final String thingkey=aqiResult.getThingKeyd();
        // final String lat=aqiResult.getLatitude();
        String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/"+thingkey+"/"+property+"/IST";
        JsonObjectRequest jsonObjectRequest1= new JsonObjectRequest(Request.Method.GET, Baseurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("result");
                    //for (int i=0;i<jsonObject.length();i++){
                    //JSONObject result=jsonArray.getJSONObject(i);
                    String value="PM2P5: "+jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    /*String gps1="GPS: "+aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*/
                    delhipm2p5=getView().findViewById(R.id.delhi_pm25);
                    delhipm2p5.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    pm10delhi("SENS_PM10");
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                pm2p5delhi("SENS_PM2P5");
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
        //Volley.newRequestQueue(getActivity()).add(jsonObjectRequest1);
        addToRequestQueue(jsonObjectRequest1,"dpm2p5");


    }
    void pm10delhi(final String property){

        // String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/f1086f69-8043-c59d-f4dc-2885d9b860e8-sensoragent/"+property+"/IST";

        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        //final String thingkey=result3.getThingKey();
        final String thingkey=aqiResult.getThingKeyd();
        // final String lat=aqiResult.getLatitude();
        String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/"+thingkey+"/"+property+"/IST";
        JsonObjectRequest jsonObjectRequest1= new JsonObjectRequest(Request.Method.GET, Baseurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("result");
                    //for (int i=0;i<jsonObject.length();i++){
                    //JSONObject result=jsonArray.getJSONObject(i);
                    String value="PM10: "+jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    /*String gps1="GPS: "+aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*/
                    delhipm10=getView().findViewById(R.id.delhi_pm10);
                    delhipm10.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();

                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                pm10delhi("SENS_PM10");
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
        //Volley.newRequestQueue(getActivity()).add(jsonObjectRequest1);
        addToRequestQueue(jsonObjectRequest1,"dpm10");


    }

}
