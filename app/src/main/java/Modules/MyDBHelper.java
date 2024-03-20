package Modules;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;


public class MyDBHelper extends SQLiteOpenHelper {

   /* private TextView temp;
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
    private TextView pol;*/

    private static String DATABASE_NAME="aqm2";
    private static int DATABASE_VERSION= 2;

    public MyDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String TABLE_NAME = "pilani";
        String TABLE_NAME_DELHI="delhi";
        String Create_TABLE_pilani="CREATE TABLE "+ TABLE_NAME +"( id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, "+
                "Gps LONG, "+"Thing TEXT, "+"Cat TEXT, "+"Pol TEXT, "+"Aqi TEXT, "+"Tem TEXT, "+"Hum TEXT, "+
                "Pmp TEXT, "+"Pm TEXT, "+"No2 TEXT, "+"Co TEXT, "+"So2 TEXT, "+"Ozone TEXT, "+"Sound TEXT ) ";
        String Create_TABLE_delhi="CREATE TABLE "+TABLE_NAME_DELHI+"( id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP, "+
                "Gps LONG, "+"Thing TEXT, "+"Cat TEXT, "+"Pol TEXT, "+"Aqi TEXT, "+"Tem TEXT, "+"Hum TEXT, "+
                "Pmp TEXT, "+"Pm TEXT, "+"No2 TEXT, "+"Co TEXT, "+"So2 TEXT, "+"Ozone TEXT, "+"Sound TEXT ) ";

        sqLiteDatabase.execSQL(Create_TABLE_pilani);
        sqLiteDatabase.execSQL(Create_TABLE_delhi);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS pilani";
        sqLiteDatabase.execSQL(sql);

        onCreate(sqLiteDatabase);

    }
    public boolean insertData(String table,String location, String thing,String cat, String pollutant, String aqi, String temp,String hum,String pm2p5,String pm10,String no2,String co,String so, String ozone,String sound){


        //Get the instance of SQL Database which we have created
        SQLiteDatabase db = getWritableDatabase();

        //To pass all the values in database
        ContentValues contentValues = new ContentValues();
        contentValues.put( "Gps", location );
        contentValues.put("Thing",thing);
        contentValues.put( "Cat", cat );
        contentValues.put( "Pol", pollutant);
        contentValues.put( "Aqi",aqi );
        contentValues.put("Tem",temp);
        contentValues.put("Hum",hum);
        contentValues.put("Pmp",pm2p5);
        contentValues.put("Pm",pm10);
        contentValues.put("No2",no2);
        contentValues.put("Co",co);
        contentValues.put("So2",so);
        contentValues.put("Ozone",ozone);
        //contentValues.put(No,no);
        contentValues.put("Sound",sound);

        long result = db.insert( table, null, contentValues );

        if(result == -1)
            return false;
        else
            return true;
    }
    public Cursor getdata(String table){

        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=db.rawQuery("SELECT * from "+table,null);
        cursor.moveToLast();

       /* gps=.findViewById(R.id.latnlon);
        gps.setText(gps1);
        thingname=getActivity().findViewById(R.id.thingnamectiy);
        thingname.setText(thimgname);
        category=getActivity().findViewById(R.id.cat);
        category.setText(categor);
        pol=getActivity().findViewById(R.id.pol);
        pol.setText(poll);
        aqi=getActivity().findViewById(R.id.aqi);
        aqi.setText(aqip);

        temp = getActivity().findViewById(R.id.temp);
        temp.setText(sensdata.getTemperature());
        hum = getActivity().findViewById(R.id.hum);
        hum.setText(sensdata.getHumidity());
        pm2p5 = getActivity().findViewById(R.id.pm2p5);
        pm2p5.setText(sensdata.getPm2p5());
        pm10 = getActivity().findViewById(R.id.pm10);
        pm10.setText(sensdata.getPm10());
        no2 = getActivity().findViewById(R.id.no2);
        no2.setText(sensdata.getNo2());
        co = getActivity().findViewById(R.id.co);
        co.setText(sensdata.getCo());
        so = getActivity().findViewById(R.id.so2);
        so.setText(sensdata.getSo2());
        ozone = getActivity().findViewById(R.id.ozone);
        ozone.setText(sensdata.getOzone());
        sound = getActivity().findViewById(R.id.sound);
        sound.setText(sensdata.getSound());*/

        return cursor;

    }
}
