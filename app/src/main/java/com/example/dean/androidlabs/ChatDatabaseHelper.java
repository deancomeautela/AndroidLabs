package com.example.dean.androidlabs;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dean on 2017-10-17.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    static String DATABASE_NAME = "Messages.db";
    static String TABLE_NAME = "Message_t";

    public static String getTableName() {
        return TABLE_NAME;
    }

    static int VERSION_NUM = 5;

    public static String getKeyId() {
        return KEY_ID;
    }

    final static String KEY_ID = "_id", KEY_MESSAGE = "Message";

    public static String getKeyMessage() {
        return KEY_MESSAGE;
    }

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
        Log.i("ChatDataBaseHelper","Calling chatDatabaseHelper constructor");
    }
   public void onCreate(SQLiteDatabase db){
       db.execSQL( "CREATE TABLE " + TABLE_NAME + " ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_MESSAGE+" text);" );
       Log.i("ChatDataBaseHelper","Calling onCreate");
   }
public void onUpgrade(SQLiteDatabase db,int prevVersion, int curVersion){
    Log.i("ChatDataBaseHelper","Calling onUpgrade, prevVersion="+prevVersion+"curVersion"+curVersion);
    db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    onCreate(db);

}



}
