package database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.campusfoodexpress.vendor.VendorData;
import com.google.android.material.snackbar.Snackbar;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "CampusFoodExpress.db";
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
    public static final String COLUMN_BUSINESS_BIO = "businessDescription";

    // Customer table
    public static final String TABLE_CUSTOMER = "Customers";
    public static final String COLUMN_CUSTOMER_ID = "customerID";
    public static final String COLUMN_CUSTOMER_FIRSTNAME = "customerFirstname";
    public static final String COLUMN_CUSTOMER_LASTNAME = "customerLastname";
    public static final String COLUMN_CUSTOMER_CONTACT_NUMBER = "customerContactNumber";
    public static final String COLUMN_CUSTOMER_USERNAME = "customerUsername";
    public static final String COLUMN_CUSTOMER_PASSWORD = "customerPassword";

    // Menu table
    public static final String TABLE_MENU = "Menu";
    public static final String COLUMN_MENU_ID = "menuID";
    public static final String COLUMN_MENU_DESCRIPTION = "menuDescription";

    // Common column for Vendor and Menu table
    public static final String COLUMN_VENDOR_ID = "vendorID"; // Foreign key from Vendors

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
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

        // Create Customer table
        String createCustomerTable = "CREATE TABLE " + TABLE_CUSTOMER + "(" +
                COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CUSTOMER_FIRSTNAME + " TEXT," +
                COLUMN_CUSTOMER_LASTNAME + " TEXT," +
                COLUMN_CUSTOMER_CONTACT_NUMBER + " TEXT," +
                COLUMN_CUSTOMER_USERNAME + " TEXT," +
                COLUMN_CUSTOMER_PASSWORD + " TEXT)";

        // Create Menu table
        String createMenuTable = "CREATE TABLE " + TABLE_MENU + "(" +
                COLUMN_MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_VENDOR_ID + " INTEGER," +
                COLUMN_MENU_DESCRIPTION + " TEXT," +
                "FOREIGN KEY(" + COLUMN_VENDOR_ID + ") REFERENCES " + TABLE_VENDOR + "(" + COLUMN_BUSINESS_ID + "))";

        db.execSQL(createVendorsTable);
        db.execSQL(createCustomerTable);
        //db.execSQL(createMenuTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VENDOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        onCreate(db);
    }

    public Boolean insertVendorData(String username, String password, String businessName,
                                    String businessContactNumber, String businessHours,
                                    String businessLocation, String businessDescription)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, username);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_BUSINESS_NAME, businessName);
        cv.put(COLUMN_BUSINESS_CONTACT_NUMBER, businessContactNumber);
        cv.put(COLUMN_BUSINESS_HOURS, businessHours);
        cv.put(COLUMN_BUSINESS_LOCATION, businessLocation);
        cv.put(COLUMN_BUSINESS_BIO, businessDescription);

        long result = db.insert(TABLE_VENDOR, null, cv);
        if(result == -1){
            Toast.makeText(context,"Failed!",Toast.LENGTH_SHORT).show();
        }
        return result !=-1;
    }

    public Boolean insertCustomerData(String customerUsername, String customerPassword, String customerFName,
                                    String customerLName, String customerContactNumber)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CUSTOMER_USERNAME, customerUsername);
        cv.put(COLUMN_CUSTOMER_PASSWORD, customerPassword);
        cv.put(COLUMN_CUSTOMER_FIRSTNAME, customerFName);
        cv.put(COLUMN_CUSTOMER_LASTNAME, customerLName);
        cv.put(COLUMN_CUSTOMER_CONTACT_NUMBER, customerContactNumber);

        long result = db.insert(TABLE_CUSTOMER, null, cv);
        if(result == -1){
            Toast.makeText(context,"Failed!",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Registered successfully!",Toast.LENGTH_SHORT).show();
        }
        return result !=-1;
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

    public Boolean checkVendorUsernamePassword(String username, String password){
        SQLiteDatabase campusFoodExpressDB = this.getWritableDatabase();
        Cursor cursor = campusFoodExpressDB.rawQuery("SELECT * FROM Vendors WHERE username = ? and password = ?",new String[]{username,password});
        return cursor.getCount()>0;
    }

    public Boolean checkCustomerUsernamePassword(String username, String password){
        SQLiteDatabase campusFoodExpressDB = this.getWritableDatabase();
        Cursor cursor = campusFoodExpressDB.rawQuery("SELECT * FROM Customers WHERE customerUsername = ? and customerPassword = ?",new String[]{username,password});
        return cursor.getCount()>0;
    }


    public VendorData getVendorDataByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_VENDOR, null, COLUMN_USERNAME + " = ?", new String[]{username}, null, null, null);

        if (cursor.moveToFirst()) {
            // Retrieve data from cursor
            @SuppressLint("Range") String businessName = cursor.getString(cursor.getColumnIndex(COLUMN_BUSINESS_NAME));
            @SuppressLint("Range") String businessContactNumber = cursor.getString(cursor.getColumnIndex(COLUMN_BUSINESS_CONTACT_NUMBER));
            @SuppressLint("Range") String businessLocation = cursor.getString(cursor.getColumnIndex(COLUMN_BUSINESS_LOCATION));
            @SuppressLint("Range") String businessDescription = cursor.getString(cursor.getColumnIndex(COLUMN_BUSINESS_BIO));
            @SuppressLint("Range") String businessHours = cursor.getString(cursor.getColumnIndex(COLUMN_BUSINESS_HOURS));

            // Retrieve other relevant data

            // Create and return a VendorData object with the retrieved data
            return new VendorData(businessName, businessContactNumber,businessHours,businessLocation,businessDescription);
        } else {
            return null; // Vendor data not found
        }
    }

    public boolean deleteVendor(String username){
        SQLiteDatabase campusFoodExpressDB = this.getWritableDatabase();
        String whereClause = "username = ?";
        String[] whereArgs = {username};

        // Delete the entry from the table
        int vendor = campusFoodExpressDB.delete(TABLE_VENDOR, whereClause, whereArgs);
        return vendor !=-1;
    }

    public  boolean updateVendorDetails(String username,String businessName, String businessContacts,String businessLocation, String businessDescription,String openingTime, String closingTime){
        SQLiteDatabase campusFoodExpressDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // Delete the entry from the table
        cv.put(COLUMN_BUSINESS_NAME,businessName);
        cv.put(COLUMN_BUSINESS_CONTACT_NUMBER,businessContacts);
        cv.put(COLUMN_BUSINESS_BIO,businessDescription);
        cv.put(COLUMN_BUSINESS_LOCATION, businessLocation);
        cv.put(COLUMN_BUSINESS_HOURS,openingTime + " To " + closingTime);
        long results = campusFoodExpressDB.update(TABLE_VENDOR,cv,COLUMN_USERNAME + " = ?",new String[]{username});

        if(results < 0){
            Toast.makeText(context,"Update Failed!",Toast.LENGTH_SHORT).show();
        }
        return results>0;
    }

}
