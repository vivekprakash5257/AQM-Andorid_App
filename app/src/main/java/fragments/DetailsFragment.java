package fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import Modules.MyDBHelper;

public class DetailsFragment extends Fragment implements View.OnClickListener {
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    Modules.login login=new login();
    Result result3=new Result();
    SEnsrResult result4=new SEnsrResult();
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
   /* private TextView delhitemp;
    private TextView delhihum;
    private TextView delhipm2p5;
    private TextView delhipm10;
    private TextView delhiaqi;
    private TextView gpsdelhi;*/
    Modules.AQIResult aqiResult = new AQIResult();
    Modules.sensdata sensdata=new sensdata();
    MyDBHelper myDBHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myDBHelper=new MyDBHelper(getActivity());
        // Inflate the layout for this fragment
       return inflater.inflate(R.layout.fragment_details, container, false);
       // return null;

    }
    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPilani();
        //AuthP();

        ImageButton refreshbuttond;
        refreshbuttond=getView().findViewById(R.id.refreshButtondetail);
        refreshbuttond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pilani();
                getPilani();
            }
        });
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


    @Override
    public void onClick(View view) {
        //AuthP();
        getPilani();
    }

    private void getPilani() {
                    Cursor cursor=myDBHelper.getdata("pilani");
                    gps=getActivity().findViewById(R.id.latnlon);
                    gps.setText(cursor.getString(2));
                    thingname=getActivity().findViewById(R.id.thingnamectiy);
                    thingname.setText(cursor.getString(3));
                     category=getActivity().findViewById(R.id.cat);
                    category.setText(cursor.getString(4));
                    pol=getActivity().findViewById(R.id.pol);
                    pol.setText(cursor.getString(5));
                    aqi=getActivity().findViewById(R.id.aqi);
                    aqi.setText(cursor.getString(6));
                    temp = getActivity().findViewById(R.id.temp);
                    temp.setText(cursor.getString(7));
                    hum = getActivity().findViewById(R.id.hum);
                    hum.setText(cursor.getString(8));
                    pm2p5 = getActivity().findViewById(R.id.pm2p5);
                    pm2p5.setText(cursor.getString(9));
                    pm10 = getActivity().findViewById(R.id.pm10);
                    pm10.setText(cursor.getString(10));
                    no2 = getActivity().findViewById(R.id.no2);
                    no2.setText(cursor.getString(11));
                    co = getActivity().findViewById(R.id.co);
                    co.setText(cursor.getString(12));
                    so = getActivity().findViewById(R.id.so2);
                    so.setText(cursor.getString(13));
                    ozone = getActivity().findViewById(R.id.ozone);
                    ozone.setText(cursor.getString(14));
                    sound = getActivity().findViewById(R.id.sound);
                    sound.setText(cursor.getString(15));
    }

    public void Pilani (){


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
                    Toast.makeText(getActivity(), "Auth BOSCH OK", Toast.LENGTH_LONG).show();

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

                        String gps1=aqiResult.getLatitude().toString()+"  "+aqiResult.getLongitude().toString();
                        gps=getActivity().findViewById(R.id.latnlon);
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
                        aqi=getActivity().findViewById(R.id.aqi);
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
                    temp = getActivity().findViewById(R.id.temp);
                   // temp.setText(sensdata.getTemperature());
                    JSONObject result1 = jsonArray.getJSONObject(1);
                    sensdata.setHumidity(result1.getString("value"));
                    hum = getActivity().findViewById(R.id.hum);
                    //hum.setText(sensdata.getHumidity());
                    JSONObject result2 = jsonArray.getJSONObject(2);
                    sensdata.setPm2p5(result2.getString("value"));
                    pm2p5 = getActivity().findViewById(R.id.pm2p5);
                    //pm2p5.setText(sensdata.getPm2p5());
                    JSONObject result3 = jsonArray.getJSONObject(3);
                    sensdata.setPm10(result3.getString("value"));
                    pm10 = getActivity().findViewById(R.id.pm10);
                   // pm10.setText(sensdata.getPm10());
                    JSONObject result4 = jsonArray.getJSONObject(4);
                    sensdata.setNo2(result4.getString("value"));
                    no2 = getActivity().findViewById(R.id.no2);
                   // no2.setText(sensdata.getNo2());
                    JSONObject result5 = jsonArray.getJSONObject(5);
                    sensdata.setCo(result5.getString("value"));
                    co = getActivity().findViewById(R.id.co);
                   /// co.setText(sensdata.getCo());
                    JSONObject result6 = jsonArray.getJSONObject(6);
                    sensdata.setSo2(result6.getString("value"));
                    so = getActivity().findViewById(R.id.so2);
                   // so.setText(sensdata.getSo2());
                    JSONObject result7 = jsonArray.getJSONObject(7);
                    sensdata.setOzone(result7.getString("value"));
                    ozone = getActivity().findViewById(R.id.ozone);
                   // ozone.setText(sensdata.getOzone());
                    JSONObject result8 = jsonArray.getJSONObject(8);
                    sensdata.setSound(result8.getString("value"));
                    sound = getActivity().findViewById(R.id.sound);
                   // sound.setText(sensdata.getSound());

                    String gps1=aqiResult.getLatitude().toString()+"  "+aqiResult.getLongitude().toString();
                    String aqipilani=aqiResult.getAqi().toString();
                    myDBHelper.insertData("pilani",gps1,aqiResult.getThingName(),aqiResult.getCategory(),aqiResult.getPollutant(),aqipilani,sensdata.getTemperature(),
                            sensdata.getHumidity(),sensdata.getPm2p5(),sensdata.getPm10(),sensdata.getNo2(),sensdata.getCo(),sensdata.getSo2(),sensdata.getOzone(),sensdata.getSound());
                    //myDBHelper.close();


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
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
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

   /* public void AuthP (){


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

                    Toast.makeText(getActivity(), "Auth OK", Toast.LENGTH_LONG).show();
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
                VolleyLog.e("Errorxsdx", error.getMessage());
                AuthP ();
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
       // mRequestQueue.add(req);
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
                        *//*getsensor("SENS_RELATIVE_HUMIDITY");
                        getsensor("SENS_PM2P5");
                        getsensor("SENS_PM10");*//*
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
    public void aqipilani(){
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
                        aqiResult.setPollutant(result.getString("pollutant"));

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
                        aqiResult.setPollutantd(result.getString("pollutant"));



                        //String gps=aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();

                        //allsenssor();
                        //
                        //Toast.makeText(getApplicationContext(),aqiResult.getCategory(),Toast.LENGTH_LONG).show();
                        *//*for(int j= 0;j<sesnor.length;j++) {
                            getsensor(sesnor[i]);
                        }*//*

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
                    String value=jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    //k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    *//*String gps1=aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*//*
                    temp=getView().findViewById(R.id.temp);
                    temp.setText(value);
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
                    String value=jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    //k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    String gps1=aqiResult.getLatitude().toString()+"  "+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.latnlon);
                    gps.setText(gps1);
                    String thimgname=aqiResult.getThingName()+" "+aqiResult.getCity();
                    thingname=getView().findViewById(R.id.thingnamectiy);
                    thingname.setText(thimgname);
                    String categor=aqiResult.getCategory();
                    category=getView().findViewById(R.id.cat);
                    category.setText(categor);
                    String poll=aqiResult.getPollutant();
                    pol=getView().findViewById(R.id.pol);
                    pol.setText(poll);

                    String aqip=aqiResult.getAqi().toString();
                    aqi=getView().findViewById(R.id.aqi);
                    aqi.setText(aqip);
                    hum=getView().findViewById(R.id.hum);
                    hum.setText(value);
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
       // Volley.newRequestQueue(getActivity()).add(jsonObjectRequest1);
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
                    String value=jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    //k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    *//*String gps1="GPS: "+aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*//*
                    pm2p5=getView().findViewById(R.id.pm2p5);
                    pm2p5.setText(value);
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
        addToRequestQueue(jsonObjectRequest1,"pm25");


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
                    String value=jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    //k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    *//*String gps1="GPS: "+aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*//*
                    pm10=getView().findViewById(R.id.pm10);
                    pm10.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    copilani("SENS_CARBON_MONOXIDE");

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
        addToRequestQueue(jsonObjectRequest1,"pm10");



    }
    void copilani(final String property){

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
                    String value=jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    //k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    *//*String gps1="GPS: "+aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*//*
                    co=getView().findViewById(R.id.co);
                    co.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    so2pilani("SENS_SULPHUR_DIOXIDE");

                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                copilani("SENS_CARBON_MONOXIDE");
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
        addToRequestQueue(jsonObjectRequest1,"co");


    }
    void so2pilani(final String property){

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
                    String value=jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    //k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    *//*String gps1="GPS: "+aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*//*
                    so=getView().findViewById(R.id.so2);
                    so.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    no2pilani("SENS_NITROGEN_DIOXIDE");

                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                so2pilani("SENS_SULPHUR_DIOXIDE");
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
        addToRequestQueue(jsonObjectRequest1,"so2");

    }
    void no2pilani(final String property){

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
                    String value=jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    //k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    *//*String gps1="GPS: "+aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*//*
                    no2=getView().findViewById(R.id.no2);
                    no2.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    nopilani("SENS_NITRIC_OXIDE");

                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                no2pilani("SENS_NITROGEN_DIOXIDE");
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
       // Volley.newRequestQueue(getActivity()).add(jsonObjectRequest1);
        addToRequestQueue(jsonObjectRequest1,"no2");

    }
    void nopilani(final String property){

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
                    String value=jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    //k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    *//*String gps1="GPS: "+aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*//*
                   // no=getView().findViewById(R.id.no);
                    //no.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    ozonepilani("SENS_OZONE");

                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                nopilani("SENS_NITRIC_OXIDE");
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
       // Volley.newRequestQueue(getActivity()).add(jsonObjectRequest1);
        addToRequestQueue(jsonObjectRequest1,"no");

    }
    void ozonepilani(final String property){

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
                    String value=jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    //k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    *//*String gps1="GPS: "+aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*//*
                    ozone=getView().findViewById(R.id.ozone);
                    ozone.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    soundpilani("SENS_SOUND");

                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                ozonepilani("SENS_OZONE");
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
        addToRequestQueue(jsonObjectRequest1,"ozone");


    }
    void soundpilani(final String property){

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
                    String value=jsonObject.getString("value");
                    // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    //k=value;
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    //Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    *//*String gps1="GPS: "+aqiResult.getLatitude().toString()+aqiResult.getLongitude().toString();
                    gps=getView().findViewById(R.id.pilani_gpsTextView);
                    gps.setText(gps1);*//*
                    sound=getView().findViewById(R.id.sound);
                    sound.setText(value);
                    Toast.makeText(getActivity(),value,Toast.LENGTH_LONG).show();
                    //no2pilani("SENS_NITROGEN_DIOXIDE");

                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                soundpilani("SENS_SOUND");
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
        addToRequestQueue(jsonObjectRequest1,"sound");


    }*/
}
