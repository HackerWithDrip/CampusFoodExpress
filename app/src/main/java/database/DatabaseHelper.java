package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Login.db";
    public static final int DATABASE_VERSION = 1;
    private  Context context;

    // Vendor table
    public static final String TABLE_VENDOR = "Vendors";
    public static final String COLUMN_BUSINESS_ID = "businessID";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_BUSINESS_NAME = "businessName";
    public static final String COLUMN_BUSINESS_CONTACT_NUMBER = "businessContactNumber";
    public static final String COLUMN_BUSINESS_HOURS = "businessHours";
    public static final String COLUMN_BUSINESS_LOCATION = "businessLocation";
    public static final String COLUMN_BUSINESS_BIO = "businessBio";

    // Menu table

    public static final String TABLE_MENU = "Menu";
    public static final String COLUMN_MENU_ID = "menuID";
    public static final String COLUMN_MENU_DESCRIPTION = "menuDescription";

    // Common column for Vendor and Menu table
    public static final String COLUMN_VENDOR_ID = "vendorID"; // Foreign key from Vendors

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create Vendors table
        String createVendorsTable = "CREATE TABLE " + TABLE_VENDOR + "(" +
                COLUMN_BUSINESS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USERNAME + " TEXT," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_BUSINESS_NAME + " TEXT," +
                COLUMN_BUSINESS_CONTACT_NUMBER + " TEXT," +
                COLUMN_BUSINESS_HOURS + " TEXT," +
                COLUMN_BUSINESS_LOCATION + " TEXT," +
                COLUMN_BUSINESS_BIO + " TEXT)";

        // Create Menu table
        String createMenuTable = "CREATE TABLE " + TABLE_MENU + "(" +
                COLUMN_MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_VENDOR_ID + " INTEGER," +
                COLUMN_MENU_DESCRIPTION + " TEXT," +
                "FOREIGN KEY(" + COLUMN_VENDOR_ID + ") REFERENCES " + TABLE_VENDOR + "(" + COLUMN_BUSINESS_ID + "))";

        db.execSQL(createVendorsTable);
        db.execSQL(createMenuTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENDOR);
        onCreate(db);
    }

    public Boolean insertVendorData(String username, String password, String businessName,
                                    String businessContactNumber, String businessHours,
                                    String businessLocation, String businessBio)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_BUSINESS_NAME, businessName);
        cv.put(COLUMN_BUSINESS_CONTACT_NUMBER, businessContactNumber);
        cv.put(COLUMN_BUSINESS_HOURS, businessHours);
        cv.put(COLUMN_BUSINESS_LOCATION, businessLocation);
        cv.put(COLUMN_BUSINESS_BIO, businessBio);

        long result = db.insert(TABLE_VENDOR, null, cv);
        return result != -1;
    }

    public Boolean insertMenuData(int vendorID, String menuDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_VENDOR_ID, vendorID);
        cv.put(COLUMN_MENU_DESCRIPTION, menuDescription);

        long result = db.insert(TABLE_MENU, null, cv);
        return result != -1;
    }

    public Boolean checkUsername(String username){
        SQLiteDatabase vendorDB = this.getWritableDatabase();
        Cursor cursor = vendorDB.rawQuery("SELECT * FROM Vendors WHERE username = ?",new String[]{username});
        return cursor.getCount()>0;
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase vendorDB = this.getWritableDatabase();
        Cursor cursor = vendorDB.rawQuery("SELECT * FROM Vendors WHERE username = ? and password = ?",new String[]{username,password});
        return cursor.getCount()>0;
    }

    public void updateData(String id_row, String businessName, String businessContacts, String businessBio, String businessLocation, String businessHours){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_BUSINESS_NAME,businessName);
        cv.put(COLUMN_BUSINESS_CONTACT_NUMBER,businessContacts);
        cv.put(COLUMN_BUSINESS_BIO,businessBio);
        cv.put(COLUMN_BUSINESS_LOCATION,businessLocation);
        //cv.put(COLUMN_PAGES,businessHours);

        long results = db.update(TABLE_VENDOR,cv,"id=?",new String[]{id_row});

        if(results > 0){
            Toast.makeText(context,"Updated successfully!",Toast.LENGTH_SHORT).show();

        }else
        {
            Toast.makeText(context,"Update Failed!",Toast.LENGTH_SHORT).show();

        }
    }

}
