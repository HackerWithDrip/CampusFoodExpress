package com.example.campusfoodexpress;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase vendorDB) {
        vendorDB.execSQL("CREATE TABLE Vendors(businessID INTEGER primary key AUTOINCREMENT, username TEXT, password TEXT, businessName TEXT, businessContactNumber TEXT," +
                "businessHours TEXT,businessLocation TEXT,businessBio TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase vendorDB, int i, int i1) {
        vendorDB.execSQL("DROP TABLE IF EXISTS Vendors");
    }

    public Boolean insertData(int businessID,String username, String password,String businessName, String businessContactNumber,
                              String businessHours,String businessLocation, String businessBio){
        SQLiteDatabase vendorDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("business ID",businessID);
        cv.put("username",username);
        cv.put("password",password);
        cv.put("businessName",businessName);
        cv.put("businessContactNumber",businessContactNumber);
        cv.put("businessHours",businessHours);
        cv.put("businessLocation",businessLocation);
        cv.put("businessBio",businessBio);

        long result = vendorDB.insert("Vendors",null,cv);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Boolean checkUsername(String username){
        SQLiteDatabase vendorDB = this.getWritableDatabase();
        Cursor cursor = vendorDB.rawQuery("SELECT * FROM Vendors WHERE username = ?",new String[]{username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase vendorDB = this.getWritableDatabase();
        Cursor cursor = vendorDB.rawQuery("SELECT * FROM Vendors WHERE username = ? and password = ?",new String[]{username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;

    }
}
