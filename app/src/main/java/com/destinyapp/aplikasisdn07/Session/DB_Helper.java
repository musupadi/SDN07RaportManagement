package com.destinyapp.aplikasisdn07.Session;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DB_Helper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME = "session.db";
    private static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "session";
    public static final String COLUMN_USERNME = "username";
    public static final String COLUMN_PERSON = "person";

    public DB_Helper(Context context){super(
    context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (" +
                COLUMN_USERNME+" TEXT PRIMARY KEY, "+
                COLUMN_PERSON+" TEXT NOT NULL);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void saveSession(User user){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNME, user.getUsername());
        values.put(COLUMN_PERSON,user.getPerson());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }

    public void userLogout(String username,Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+"");
        Toast.makeText(context, "Logout Berhasil", Toast.LENGTH_SHORT).show();
    }

    public Cursor checkSession(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query ="SELECT * FROM "+TABLE_NAME+"";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }
}
