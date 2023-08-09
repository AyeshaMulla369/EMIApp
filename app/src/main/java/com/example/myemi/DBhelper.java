package com.example.myemi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {


    private static final int VERSION = 1;
    //table |period|paydate|current_balance|intrest|principle
    private static final String STATUS_TABLE_NAME = "STATUS_TABLE";
    public static final String STATUS_ID = "_STATUS_ID";

    public static final String P_KEY = "STATUS_P";
    public static final String N_KEY = "STATUS_N";
    public static final String R_KEY = "STATUS_R";
    public static final String STATUS_NAME_KEY = "STATUS_DATE";
    private static final String CREATE_STATUS_TABLE =
            "CREATE TABLE " + STATUS_TABLE_NAME + " ( "+ STATUS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                    P_KEY+ " FLOAT NOT NULL, " +N_KEY+ " FLOAT NOT NULL, " + R_KEY + " FLOAT NOT NULL, "+
                    STATUS_NAME_KEY + " TEXT NOT NULL"+
                    ");" ;
    private static final String DROP_STATUS_TABLE = "DROP TABLE IF EXISTS " +STATUS_TABLE_NAME;
    private static final String SELECT_STATUS_TABLE = "SELECT * FROM "+ STATUS_TABLE_NAME;



    //notification table
    private static final String NOTIFICATION_TABLE_NAME = "NOTIFICATION_TABLE";


    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    public static final String DATE_KEY = "NOTIFICATION_DATE";

    private static final String CREATE_NOTIFICATION_TABLE =
            "CREATE TABLE " + NOTIFICATION_TABLE_NAME + " ( "+ NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "+
                    STATUS_ID + " INTEGER NOT NULL , "+
                    DATE_KEY+" DATE NOT NULL , "+
                    " FOREIGN KEY ("+ STATUS_ID+") REFERENCES "+ STATUS_TABLE_NAME + "("+STATUS_ID+")"+
                    ");" ;
    private static final String DROP_NOTIFICATION_TABLE = "DROP TABLE IF EXISTS " +NOTIFICATION_TABLE_NAME;
    private static final String SELECT_NOTIFICATION_TABLE = "SELECT * FROM "+ NOTIFICATION_TABLE_NAME;



    public DBhelper(@Nullable Context context) {
        super(context, "EmiUser.db" , null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STATUS_TABLE);
        db.execSQL(CREATE_NOTIFICATION_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try{
            db.execSQL(DROP_STATUS_TABLE);
            db.execSQL(DROP_NOTIFICATION_TABLE);
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    long addClass(double p, double n , double r, String name){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(P_KEY , p);
        values.put(N_KEY , n);
        values.put(R_KEY , r);
        values.put(STATUS_NAME_KEY , name);


        return database.insert(STATUS_TABLE_NAME , null,values);
    }

    Cursor getClassTable(){
        SQLiteDatabase database = this.getReadableDatabase();

        return database.rawQuery(SELECT_STATUS_TABLE , null);
    }

    int deleteClass(long did){
        SQLiteDatabase database = this.getReadableDatabase();
        return database.delete(STATUS_TABLE_NAME , STATUS_ID+"=?" , new String[]{String.valueOf(did)});
    }

    long updateClass(long did , String name){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS_NAME_KEY , name);

        return database.update(STATUS_TABLE_NAME ,values ,STATUS_ID+"=?" , new String[]{String.valueOf(did)});
    }

    String getNames(long sid){
        String names = "";
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = STATUS_ID+"="+sid;
        Cursor cursor = database.query(STATUS_TABLE_NAME , null ,whereClause , null , null ,null,null);
        if(cursor.moveToFirst())
            names = cursor.getString(cursor.getColumnIndexOrThrow(STATUS_NAME_KEY));
        return names;
    }

    float getPKey(long sid ){
        float p =0;
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = STATUS_ID+"="+sid;
        Cursor cursor = database.query(STATUS_TABLE_NAME , null ,whereClause , null , null ,null,null);
        if(cursor.moveToFirst())
            p = cursor.getFloat(cursor.getColumnIndexOrThrow(P_KEY));
        return p;
    }
    float getNKey(long sid ){
        float n =0;
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = STATUS_ID+"="+sid;
        Cursor cursor = database.query(STATUS_TABLE_NAME , null ,whereClause , null , null ,null,null);
        if(cursor.moveToFirst())
            n = cursor.getFloat(cursor.getColumnIndexOrThrow(N_KEY));
        return n;
    }
    float getRKey(long sid ){
        float r =0;
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = STATUS_ID+"="+sid;
        Cursor cursor = database.query(STATUS_TABLE_NAME , null ,whereClause , null , null ,null,null);
        if(cursor.moveToFirst())
            r = cursor.getFloat(cursor.getColumnIndexOrThrow(R_KEY));
        return r;
    }


    long addNotification(long sid, String date){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(STATUS_ID , sid);
        values.put(DATE_KEY , date);


        return database.insert(NOTIFICATION_TABLE_NAME , null,values);
    }

    String getDates( long notid ,long sid ){
        String date = "";
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = NOTIFICATION_ID + "='"+notid +"' AND "+STATUS_ID+"="+sid;
        Cursor cursor = database.query(NOTIFICATION_TABLE_NAME , null ,whereClause , null , null ,null,null);
        if(cursor.moveToFirst())
            date = cursor.getString(cursor.getColumnIndexOrThrow(DATE_KEY));
        return date;
    }

    long getNotId(long sid ){
        long p =0;
        SQLiteDatabase database = this.getReadableDatabase();
        String whereClause = STATUS_ID+"="+sid;
        Cursor cursor = database.query(NOTIFICATION_TABLE_NAME , null ,whereClause , null , null ,null,null);
        if(cursor.moveToFirst())
            p = cursor.getLong(cursor.getColumnIndexOrThrow(NOTIFICATION_ID));
        return p;
    }

    
}
