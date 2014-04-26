package fr.unice.rallyequiz.ltiplitre; /**
 * Created by balde on 15/04/14.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper
{

    // le variables static
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Nom
    private static final String DATABASE_NAME = "android_api";

    // Login table nom
    private static final String TABLE_LOGIN = "connection";

    //  nom des colones de tables de databse
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Ceations des tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop les vieux table si il existe
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

        // Creation des tables encore
        onCreate(db);
    }

    /**
     * les details de l'utilisateur dans database
     * */
    public void addUser(String name, String email, String uid, String created_at)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); //fermer la connection database
    }

    /**
     *renvoyer le status de connection
     * returner vrai si la requette existe dans la base de donnes
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        return rowCount;
    }


    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // effacer toutes les colones
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }

}