package com.example.first;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserData.db";
    private static final int DATABASE_VERSION = 1;

    // Define the table schema
    private static final String TABLE_NAME = "Users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_CNIC = "cnic";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PASSWORD = "password";

    // Create the table query
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_CNIC + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PHONE + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the database table
        db.execSQL(TABLE_CREATE);

        db.execSQL("create Table user (cnic Text primary key,password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the table if it exists and recreate it (you can handle upgrades differently)
        db.execSQL("DROP TABLE IF EXISTS users" );

    }

    // Method to add a new user to the database
    public long addUser(String name, String cnic, String email, String phone, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_CNIC, cnic);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PHONE, phone);
        values.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, values);
        // Insert the user data into the table
        if (result==-1){
            return 0;
        }
        else {
            return 1;
        }
    }

    // Method to check if a user with the given CNIC and password exists
    public Boolean checkUser(String cnic,String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        try {
            Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE cnic=? AND password=?", new String[]{cnic, password});
            if (cursor.getCount() > 0) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            myDB.close(); // Ensure that the database is always closed
        }
    }
    public Boolean checkcnicPassword(String cnic, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE cnic=? AND password=?", new String[]{cnic, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
     static class User {
        private String cnic;
        private String password;

        public User(String cnic, String password) {
            this.cnic = cnic;
            this.password = password;
        }



         public String getCnic() {
            return cnic;
        }

        public void setCnic(String cnic) {
            this.cnic = cnic;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public User getUser(String cnic, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE cnic=? AND password=?", new String[]{cnic, password});
        String retrievedCnic = "";
        String retrievedPassword = "";

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int cnicIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_CNIC);
                int passwordIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD);

                if (cnicIndex >= 0) {
                    retrievedCnic = cursor.getString(cnicIndex);
                }

                if (passwordIndex >= 0) {
                    retrievedPassword = cursor.getString(passwordIndex);
                }
            }

            cursor.close();
        }

        User user = new User(retrievedCnic, retrievedPassword);
        return user;
    }






}
