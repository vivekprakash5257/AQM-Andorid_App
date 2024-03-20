package Modules;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLBASE extends SQLiteOpenHelper {

        //Initialize all the fields needed for database
        public static final String DATABASE_NAME = "aqm.db";
        public static final String TABLE_NAME = "pilani";
        public static final String TABLE_NAME2="delhi";
        public static final String COL_1 = "ID";
        public static final String Date = "";
        public static final String Location = "location";
        public static final String Aqi = "aqi";
        public static final String Cat = "cat";
        public static final String Pollutant="pol";
        public static final String Temp="tem";
        public static final String Hum="hum";
        public static final String Pm2p5="pm2p5";
        public static final String Pm10="pm10";
        public static final String Co="co";
        public static final String So="so";
        public static final String No2="no2";
        public static final String Ozone="ozone";
        public static final String No="nos";
        public static final String Sound="sound";

        public static final String LBR = "(";
        public static final String RBR = ")";
        public static final String COM = ",";

        //Just pass context of the app to make it simpler
        public SQLBASE(Context context) {
            super( context, DATABASE_NAME, null, 0412 );

        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            //Creating table

            db.execSQL( "create table " + TABLE_NAME + LBR + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT" + COM +
                    Date + " DATETIME DEFAULT CURRENT_TIMESTAMP" + COM + Location + " VARCHAR(40)" + COM + Aqi + " VARCHAR(10)" + COM + Cat + " VARCHAR(20)"+COM+Pollutant+" VARCHAR(10)"
                    +COM+ Temp + " VARCHAR(10)" +COM+Hum+" VARCHAR(10)"+COM+Pm2p5+" VARCHAR(10)"+COM+Pm10+" VARCHAR(10)"+COM+Co+" VARCHAR(10)"+COM+So+" VARCHAR(10)"+COM+No2+" VARCHAR(10)"+COM+Ozone+" VARCHAR(10)"+COM+No+" VARCHAR(10)"+COM
                    +Sound+" VARCHAR(10)"+RBR );
           /* db.execSQL( "create table " + TABLE_NAME2 + LBR + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT" + COM +
                    Date + "DATETIME DEFAULT CURRENT_TIMESTAMP" + COM + Location + "DOUBLE PRECISION" + COM + aqi + " REAL" + COM + cat + " TEXT"+COM+pollutant+"TEXT"
                    +COM+temp+"REAL"+COM+hum+"REAL"+COM+pm2p5+"REAL"+COM+pm10+"REAL"+COM+co+"REAL"+COM+so+"REAL"+COM+no2+"REAL"+COM+ozone+"REAL"+COM+no+"REAL"+COM
                    +sound+"REAL"+RBR );*/

            // Another way of writing the CREATE TABLE query
       /* db.execSQL( "create table student_data (ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, Surname TEXT," +
                "Marks INTEGER, Date TEXT)" );*/

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {

            //Dropping old table
            db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate( db );

        }

        //Insert data in database
        public boolean insertData(String table,String location, String aqi, String cat, String pollutant,String temp,String hum,String pm2p5,String pm10,String co,String so,String no2, String ozone, String no,String sound){


            //Get the instance of SQL Database which we have created
            SQLiteDatabase db = getWritableDatabase();

            //To pass all the values in database
            ContentValues contentValues = new ContentValues();
            contentValues.put( Location, location );
            contentValues.put( Aqi,aqi );
            contentValues.put( Cat, cat );
            contentValues.put( Pollutant, pollutant);
            contentValues.put(Temp,temp);
            contentValues.put(Hum,hum);
            contentValues.put(Pm2p5,pm2p5);
            contentValues.put(Pm10,pm10);
            contentValues.put(Co,co);
            contentValues.put(So,so);
            contentValues.put(No2,no2);
            contentValues.put(Ozone,ozone);
            //contentValues.put(No,no);
            contentValues.put(Sound,sound);

            long result = db.insert( table, null, contentValues );

            if(result == -1)
                return false;
            else
                return true;
        }

        //Cursor class is used to move around in the database
        public Cursor getData(){

            //Get the data from database
            SQLiteDatabase db = getWritableDatabase();
            Cursor res = db.rawQuery( "select * from " + TABLE_NAME, null );
            return res;
        }

        //Delete data from the databse using ID (Primary Key)
        public Integer deleteData(String id){

            SQLiteDatabase db = getWritableDatabase();
            return db.delete( TABLE_NAME, "ID = ?", new String [] {id} );
        }
    }

