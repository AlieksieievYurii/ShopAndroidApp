package com.example.whiteuser.shop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper
{
    public static final String NAME_DATABASE = "goods_db";
    public static final String NAME_TABLE = "goods";
    public static final int VERSION = 1;


    public static final String KEY_NAME = "name_order";
    public static final String KEY_ID = "_id";
    public static final String KEY_PRICE = "price_order";



    public static final String TAG_BASE = "tag_db";

    public DbHelper(Context context)
    {
        super(context, NAME_DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s INTEGER)",
                NAME_TABLE,
                KEY_ID,
                KEY_NAME,
                KEY_PRICE));
        Log.i(TAG_BASE,"--> DataBase is created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE " + NAME_TABLE);
        onCreate(db);
        Log.i(TAG_BASE,"--> DataBase is droped!");
    }
}
