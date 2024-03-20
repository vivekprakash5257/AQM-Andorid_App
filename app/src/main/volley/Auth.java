package volley;

import android.content.Context;
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

public class Auth {

    login login=new login();
    Result result3=new Result();
    SEnsrResult result4=new SEnsrResult();
    //private ViewGroup viewGroup;
   /// View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pil, viewGroup, false);
    //Intent intent=new Intent(StartFragment)
    public void res(final Context context) {
       // ImageButton refreshImageButton;

        ///refreshImageButton=

        final String url = "http://52.28.187.167/services/api/v1/users/login";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("password","Q2xpbW9AOTAz");
        params.put("username","Q1NJUl9QSUxPVA==");


        RequestQueue mRequestQueue = Volley.newRequestQueue(context);

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

                    Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                    allthing(context);

                    // thing.allthing(response.getString("authToken"),response.getString("OrgKey"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorxsdx", error.getMessage());
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();

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
        mRequestQueue.add(req);


    }

    public void allthing(final Context context){
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
                        Toast.makeText(context, result2.getString("thingKey"), Toast.LENGTH_SHORT).show();
                        /*getsensor("SENS_RELATIVE_HUMIDITY");
                        getsensor("SENS_PM2P5");
                        getsensor("SENS_PM10");*/
                        getsensor("SENS_TEMPERATURE",context);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq",error.getMessage());
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(context).add(jsonObjectRequest);
        //m2RequestQueue.add(jsonObjectRequest);
    }
    void getsensor(final String property, final Context context){

        // String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/f1086f69-8043-c59d-f4dc-2885d9b860e8-sensoragent/"+property+"/IST";

        final String auth=login.getAuthToken();
        final String orgkey=login.getOrgKey();
        final String thingkey=result3.getThingKey2();
        String Baseurl="http://52.28.187.167/services/api/v1/propertyValue/"+thingkey+"/"+property+"/IST";
        JsonObjectRequest jsonObjectRequest1= new JsonObjectRequest(Request.Method.GET, Baseurl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject jsonObject = response.getJSONObject("result");
                    //for (int i=0;i<jsonObject.length();i++){
                    //JSONObject result=jsonArray.getJSONObject(i);
                    String value=property+jsonObject.getString("value");
                   // result4.setValue(jsonObject.getString("value"));
                    result4.setValue(jsonObject.getString("value"));
                    //((TextView) context.findViewById(R.id.pilani_air_qualityTextView)).setText(value);

                    //String result1 = "thingkey" + result.getString("thingKey")+"result"+result.getString("thingName");
                    // String thingkey=result.getString("thingKey");
                    //String thingname=result.getString("thingName");
                    // String t=thingkey+thingname;
                    Toast.makeText(context,value,Toast.LENGTH_LONG).show();
                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Errorqqqqq3",error.getMessage());
                Toast.makeText(context,error.getMessage(),Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(context).add(jsonObjectRequest1);


    }



}


    /* Add your Requests to the RequestQueue to execute */






