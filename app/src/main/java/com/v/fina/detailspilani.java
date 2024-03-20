package com.v.fina;

import android.app.Activity;
import android.app.Service;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import Modules.MyDBHelper;
import Modules.AQIResult;
import Modules.Result;
import Modules.SEnsrResult;
import Modules.login;
import Modules.sensdata;
import Modules.MyDBHelper;
import fragments.StartFragment;

import static android.app.PendingIntent.getActivity;


public class detailspilani extends StartFragment {

    private TextView temp;
    private TextView hum;
    private TextView pm2p5;
    private TextView pm10;
    private TextView aqi;
    private TextView gps;
    private TextView thingname;
    private TextView category;
    private TextView co;
    private TextView so;
    private TextView no2;
    private TextView no;
    private TextView sound;
    private TextView ozone;
    private TextView pol;
    private RequestQueue requestQueue;
    MyDBHelper myDBHelper;
    Modules.login login=new login();
    Result result3=new Result();
    SEnsrResult result4=new SEnsrResult();
    Modules.AQIResult aqiResult = new AQIResult();
    Modules.sensdata sensdata=new sensdata();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //set up activity, and view constraints
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.fragment_details);
        myDBHelper=new MyDBHelper(getActivity());

    }

   /* public void pi(){
        Cursor cursor=myDBHelper.getdata();
        gps.setText(cursor.getString(2));
        thingname.setText(cursor.getString(3));
        category.setText(cursor.getString(4));
        pol.setText(cursor.getString(5));
        aqi.setText(cursor.getString(6));
        temp.setText(cursor.getString(7));
        hum.setText(cursor.getString(8));
        pm2p5.setText(cursor.getString(9));
        pm10.setText(cursor.getString(10));
        no2.setText(cursor.getString(11));
        co.setText(cursor.getString(12));
        so.setText(cursor.getString(13));
        ozone.setText(cursor.getString(14));
        sound.setText(cursor.getString(15));
    }*/
  /* public RequestQueue getRequestQueue()
   {
       if (requestQueue==null)
           requestQueue= Volley.newRequestQueue(getActivity());

       return requestQueue;
   }

    *//*
         public method to add the Request to the the single
    instance of RequestQueue created above.Setting a tag to every
    request helps in grouping them. Tags act as identifier
    for requests and can be used while cancelling them
    *//*
    public void addToRequestQueue(Request request,String tag)
    {
        request.setTag(tag);
        getRequestQueue().add(request);

    }

*//*
Cancel all the requests matching with the given tag
     *//*

    public void cancelAllRequests(String tag)
    {
        getRequestQueue().cancelAll(tag);
    }*/
   //RequestQueue queue=AppController.getInstance().getRequestQueue();
   public void Pilani (){


       final String url = "https://boschclimo-aqi.com/services/api/v1/users/login";
       HashMap<String, String> params = new HashMap<String, String>();
       params.put("password","****");
       params.put("username","*******");

       JsonObjectRequest req2 = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
           @Override
           public void onResponse(JSONObject response) {
               try {

                   String result = "authToken" + response.getString("authToken") + "OrgKey" + response.getString("OrgKey");

                   login.setAuthToken(response.getString("authToken"));
                   login.setOrgKey(response.getString("OrgKey"));
                  // Toast.makeText(getApplicationContext(), "Auth BOSCH OK", Toast.LENGTH_LONG).show();

               } catch (JSONException e) {
                   e.printStackTrace();
               }
               aqip( );

           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Pilani();
               VolleyLog.e("Errorxsdx", error.getMessage());
              // Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

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
        //final String urlaqi="http://52.28.187.167/services/api/v1/thing/aqi/summarylist";
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
                        aqiResult.setThingKey(result.getString("thingKey"));
                        aqiResult.setThingName((result.getString("thingName")));
                        aqiResult.setCategory(result.getString("category"));
                        aqiResult.setCity(result.getString("city"));
                        aqiResult.setColor(result.getString("color"));
                        aqiResult.setPollutant(result.getString("pollutant"));

                        /*String gps1=aqiResult.getLatitude().toString()+"  "+aqiResult.getLongitude().toString();
                        gps=getApplicationContext().findViewById(R.id.latnlon);
                        //gps.setText(gps1);
                        String thimgname=aqiResult.getThingName()+" "+aqiResult.getCity();
                        thingname=getActivity().findViewById(R.id.thingnamectiy);
                        //thingname.setText(thimgname);
                        String categor=aqiResult.getCategory();
                        category=getActivity().findViewById(R.id.cat);
                        // category.setText(categor);
                        String poll=aqiResult.getPollutant();
                        pol=getActivity().findViewById(R.id.pol);
                        /// pol.setText(poll);

                        String aqip=aqiResult.getAqi().toString();
                        aqi=getActivity().findViewById(R.id.aqi);*/
                        // aqi.setText(aqip);
                    }
                    // sensorpilan();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SensorsPilani();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq",error.getMessage());
                aqip();
               // Toast.makeText(getAppl,error.getMessage(),Toast.LENGTH_LONG).show();
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
    public void SensorsPilani(){
        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        //final String thingkey=aqiResult.getThingKey();
        String Baseurl="https://boschclimo-aqi.com/services/api/v1/property/current/averagedData";

        HashMap<String, String> params1 = new HashMap<String, String>();
        params1.put("avgDuration","1h");
        params1.put("propertiesList","SENS_TEMPERATURE,SENS_RELATIVE_HUMIDITY,SENS_PM2P5,SENS_PM10,SENS_NITROGEN_DIOXIDE,SENS_CARBON_MONOXIDE,SENS_SULPHUR_DIOXIDE,SENS_OZONE,SENS_SOUND");
        params1.put("thingKey",aqiResult.getThingKey());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Baseurl, new JSONObject(params1), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray=response.getJSONArray("result");
                    //for (int i=0;i<jsonArray.length();i++) {
                    JSONObject res = jsonArray.getJSONObject(0);
                    sensdata.setTemperature(res.getString("value"));
                    // Toast.makeText(getActivity(),sensdata.getTemperature(),Toast.LENGTH_LONG).show();
                    ///JSONObject res1 = jsonArray.getJSONObject(3);
                    ////Toast.makeText(MainActivity.this,res1.getString("value")+"2",Toast.LENGTH_LONG).show();

                    sensdata.setTemperature(res.getString("value"));
                    //temp = getActivity().findViewById(R.id.temp);
                    // temp.setText(sensdata.getTemperature());
                    JSONObject result1 = jsonArray.getJSONObject(1);
                    sensdata.setHumidity(result1.getString("value"));
                   // hum = getActivity().findViewById(R.id.hum);
                    //hum.setText(sensdata.getHumidity());
                    JSONObject result2 = jsonArray.getJSONObject(2);
                    sensdata.setPm2p5(result2.getString("value"));
                    ///pm2p5 = getActivity().findViewById(R.id.pm2p5);
                    //pm2p5.setText(sensdata.getPm2p5());
                    JSONObject result3 = jsonArray.getJSONObject(3);
                    sensdata.setPm10(result3.getString("value"));
                    //pm10 = getActivity().findViewById(R.id.pm10);
                    // pm10.setText(sensdata.getPm10());
                    JSONObject result4 = jsonArray.getJSONObject(4);
                    sensdata.setNo2(result4.getString("value"));
                    //no2 = getActivity().findViewById(R.id.no2);
                    // no2.setText(sensdata.getNo2());
                    JSONObject result5 = jsonArray.getJSONObject(5);
                    sensdata.setCo(result5.getString("value"));
                    ///co = getActivity().findViewById(R.id.co);
                    /// co.setText(sensdata.getCo());
                    JSONObject result6 = jsonArray.getJSONObject(6);
                    sensdata.setSo2(result6.getString("value"));
                    //so = getActivity().findViewById(R.id.so2);
                    // so.setText(sensdata.getSo2());
                    JSONObject result7 = jsonArray.getJSONObject(7);
                    sensdata.setOzone(result7.getString("value"));
                   // ozone = getActivity().findViewById(R.id.ozone);
                    // ozone.setText(sensdata.getOzone());
                    JSONObject result8 = jsonArray.getJSONObject(8);
                    sensdata.setSound(result8.getString("value"));
                    //sound = getActivity().findViewById(R.id.sound);
                    // sound.setText(sensdata.getSound());

                    String gps1=aqiResult.getLatitude().toString()+"  "+aqiResult.getLongitude().toString();
                    String aqipilani=aqiResult.getAqi().toString();
                    myDBHelper.insertData("pilani",gps1,aqiResult.getThingName(),aqiResult.getCategory(),aqiResult.getPollutant(),aqipilani,sensdata.getTemperature(),
                            sensdata.getHumidity(),sensdata.getPm2p5(),sensdata.getPm10(),sensdata.getNo2(),sensdata.getCo(),sensdata.getSo2(),sensdata.getOzone(),sensdata.getSound());
                    //myDBHelper.close();
                    /*Cursor cursor=myDBHelper.getdata();
                    gps.setText(cursor.getString(2));
                    thingname.setText(cursor.getString(3));
                    category.setText(cursor.getString(4));
                    pol.setText(cursor.getString(5));
                    aqi.setText(cursor.getString(6));
                    temp.setText(cursor.getString(7));
                    hum.setText(cursor.getString(8));
                    pm2p5.setText(cursor.getString(9));
                    pm10.setText(cursor.getString(10));
                    no2.setText(cursor.getString(11));
                    co.setText(cursor.getString(12));
                    so.setText(cursor.getString(13));
                    ozone.setText(cursor.getString(14));
                    sound.setText(cursor.getString(15));*/

                    //Cursor c=myDBHelper.raw

                    /* JSONObject result9 = jsonArray.getJSONObject(9);
                    sensdata.setPm10(result9.getString("value"));
                    pm10 = getView().findViewById(R.id.pm10);
                    pm10.setText(sensdata.getPm10());*/
                    // }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // Toast.makeText(MainActivity.this, (CharSequence) response,Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                SensorsPilani();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", " application/json; charset=utf-8");

                headers.put("Accept", " application/json");
                headers.put("api_key", " apiKey");
                headers.put("Authorization", auth);
                headers.put("X-OrganizationKey", orgkey);
                return headers;
            }
        };
        ///Volley.newRequestQueue(getActivity()).add(request);
        addToRequestQueue(request,"pilani");
    }
}
