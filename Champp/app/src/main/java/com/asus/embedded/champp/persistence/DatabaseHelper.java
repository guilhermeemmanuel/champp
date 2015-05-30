package com.asus.embedded.champp.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.asus.embedded.champp.model.Championship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guiga on 29/05/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CHAMPP_BD";
    private static final int DATABASE_VERSION = 5;

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("BD", "oncreate");
        sqLiteDatabase.execSQL("CREATE TABLE CHAMPIONSHIP (NOME TEXT, MODAL TEXT, isCup INTEGER DEFAULT 0, isIndividual INTEGER DEFAULT 0);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("BD", "onupgrade");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CHAMPIONSHIP");

        onCreate(sqLiteDatabase);
    }

    public void insertChampionship(Championship championship) {
        Log.d("BD", "insert Championship");
        SQLiteDatabase sqlLite = this.getWritableDatabase();

        ContentValues content = new ContentValues();

        content.put("NOME", championship.getName());
        content.put("MODAL", championship.getModal());
        content.put("isCup", championship.isCup() ? 1 : 0);
        content.put("isIndividual", championship.isIndividual() ? 1 : 0);
        sqlLite.insert("CHAMPIONSHIP", null, content);
        sqlLite.close();
    }



    public List<Championship> getAllChampionships() {
        Log.d("BD", "get all championships");
        List<Championship> champList = new ArrayList<Championship>();
        // Select All Query
        String selectQuery = "SELECT  * FROM CHAMPIONSHIP";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.d("BD", "iteracao");
                try{
                    Championship championship = new Championship(cursor.getString(0), cursor.getString(1),(cursor.getInt(cursor.getColumnIndex("isCup")) == 1),(cursor.getInt(cursor.getColumnIndex("isIndividual")) == 1));
                    champList.add(championship);
                } catch (Exception ex) {
                    Log.d("BD", "erro");
                }
                //contact.setID(Integer.parseInt(cursor.getString(0)));
                //contact.setName(cursor.getString(1));
                //contact.setPhoneNumber(cursor.getString(2));
                // Adding contact to list
                //contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return champList;
    }

}
