package com.example.oguzhan.silencer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Locations";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE LocationTable (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT ," +
                "lat REAL," +
                "lon REAL" +
                ")";
        db.execSQL(CREATE_TABLE);
    }
    public void dropTable(SQLiteDatabase db){
        String DROP_TABLE = "DROP TABLE LocationTable";

        db.execSQL(DROP_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addLocation(String name,double lat,double lon){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",name);
        values.put("lat",lat);
        values.put("lon",lon);

        db.insert("LocationTable", null, values);
        db.close();
    }
    public ArrayList<LocRecord> getLocations(){
        ArrayList<LocRecord> list = new ArrayList<LocRecord>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("LocationTable",new String[]{"id","name","lat","lon"},null,null,null,null,null);
        while(cursor.moveToNext()){
            LocRecord loc = new LocRecord(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2),cursor.getDouble(3));
            list.add(loc);
        }
        return list;
    }
    public void deleteRecord(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("LocationTable","id = "+id,null);
    }
}
