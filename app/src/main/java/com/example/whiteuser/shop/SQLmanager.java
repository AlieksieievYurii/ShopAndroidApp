package com.example.whiteuser.shop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;


public class SQLmanager
{
    private DbHelper sqlDb;
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    SQLmanager(Context context)
    {
        this.context = context;
        sqlDb = new DbHelper(context);
        sqLiteDatabase = sqlDb.getWritableDatabase();
    }

    public void addGoodsDB(String name, int price)
    {
        /*
        Function returns arrayList with objects Data. Those datas are from SQLite
         */
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbHelper.KEY_NAME,name);
        contentValues.put(DbHelper.KEY_PRICE,price);

        sqLiteDatabase.insert(DbHelper.NAME_TABLE,null,contentValues);
        Log.i(DbHelper.TAG_BASE,"[*] Dada is added! -> "+name+" "+price);
    }

    public void removeGoodsDB(String name, int price)
    {
        sqLiteDatabase.delete(DbHelper.NAME_TABLE,DbHelper.KEY_NAME+" = ? AND "+DbHelper.KEY_PRICE+" = ?",new String[]{name,Integer.toString(price)});
        Log.i(DbHelper.TAG_BASE,"Element with name: "+name+"and price:"+price+" has been removed!");
    }

    public ArrayList<Data> getData()
    {
        ArrayList<Data> arrayList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.query(DbHelper.NAME_TABLE,
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst())
        {
            do {
                arrayList.add(new Data(
                        cursor.getString(cursor.getColumnIndex(DbHelper.KEY_NAME)),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex(DbHelper.KEY_PRICE)))
                ));
            }while(cursor.moveToNext());
            Log.i(DbHelper.TAG_BASE,"Load to ArrayList is okey!");
            cursor.close();
            return arrayList;
        }else
        {
            cursor.close();
            return null;
        }
    }

    public void printSQLtoLogDB()
    {
        Cursor cursor = sqLiteDatabase.query(DbHelper.NAME_TABLE,
                null,
                null,
                null,
                null,
                null,
                null);

        if(cursor.moveToFirst())
        {
            do {
                Log.i(DbHelper.TAG_BASE,cursor.getInt(cursor.getColumnIndex(DbHelper.KEY_ID))+" "+cursor.getString(cursor.getColumnIndex(DbHelper.KEY_NAME)));
            }while(cursor.moveToNext());
        }
    }
}
