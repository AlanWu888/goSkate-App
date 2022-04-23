package com.example.goskate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper  extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "tricks.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table tricks(trickID INTEGER primary key, trickName TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop Table if exists tricks.db");
    }

    // method for adding data to the database
    public boolean insertData(int trickID, String trickName) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trickID", trickID);
        contentValues.put("trickName", trickName);

        long result=DB.insert("tricks", null, contentValues);
        if (result==-1) {
            return false;
        } else {
            return true;
        }
    }

    // method for updating data to the database
    public boolean updateData(int trickID, String trickName) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("trickID", trickID);
        contentValues.put("trickName", trickName);

        Cursor cursor = DB.rawQuery("Select * from tricks where trickID = ?", new String[] {String.valueOf(trickID)});
        if (cursor.getCount() > 0) {
            long result=DB.update("tricks", contentValues, "name=?", new String[] {String.valueOf(trickID)});
            if (result==-1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    // method for deleting data from the database
    public boolean deleteData(int trickID, String trickName) {
        SQLiteDatabase DB = this.getWritableDatabase();

        Cursor cursor = DB.rawQuery("Select * from tricks where trickID = ?", new String[] {String.valueOf(trickID)});
        if (cursor.getCount() > 0) {
            long result=DB.delete("tricks", "name=?", new String[] {String.valueOf(trickID)});
            if (result==-1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    // method for getting data from the database
    public Cursor getData(int trickID, String trickName) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from tricks where trickID = ?", new String[] {String.valueOf(trickID)});

        return cursor;
    }
}
