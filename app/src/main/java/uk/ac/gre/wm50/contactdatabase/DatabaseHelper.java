package uk.ac.gre.wm50.contactdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "details";
    public static final String ID_COLUMN = "person_id";
    public static final String NAME_COLUMN = "name";
    public static final String DOB_COLUMN = "dob";
    public static final String EMAIL_COLUMN = "email";
    public static final String AVATAR_COLUMN = "avatar";

    private final SQLiteDatabase database;

    private static final String DATABASE_CREATE = String.format(
            "CREATE TABLE %s (" +
                    "   %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s TEXT, " +
                    "   %s INTEGER)", // Add a new column for avatar as an integer
            DATABASE_NAME, ID_COLUMN, NAME_COLUMN, DOB_COLUMN, EMAIL_COLUMN, AVATAR_COLUMN);

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);

        Log.v(this.getClass().getName(), DATABASE_NAME + " database upgrade to version " +
                newVersion + " - old data lost");
        onCreate(db);
    }

    public long insertDetails(String name, String dob, String email, int avatarResourceId) {
        ContentValues rowValues = new ContentValues();

        rowValues.put(NAME_COLUMN, name);
        rowValues.put(DOB_COLUMN, dob);
        rowValues.put(EMAIL_COLUMN, email);
        rowValues.put(AVATAR_COLUMN, avatarResourceId); // Store the avatar resource ID

        return database.insertOrThrow(DATABASE_NAME, null, rowValues);
    }


    public List<Contact> getDetails(){
        List<Contact> details = new ArrayList<>();

        Cursor cursor = database.query(DATABASE_NAME, new String[]{ID_COLUMN, NAME_COLUMN, DOB_COLUMN, EMAIL_COLUMN, AVATAR_COLUMN}, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String dob = cursor.getString(2);
            String email = cursor.getString(3);
            int avatar = cursor.getInt(4);

            details.add(new Contact(id, name, dob, email, avatar));
            cursor.moveToNext();
        }
        cursor.close();

        return details;
    }

}
