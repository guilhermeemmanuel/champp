package com.asus.embedded.champp.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.Match;
import com.asus.embedded.champp.model.Participant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guiga on 29/05/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CHAMPP_BD";
    private static final int DATABASE_VERSION = 14;

    public DatabaseHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("BD", "oncreate");
        sqLiteDatabase.execSQL("CREATE TABLE CHAMPIONSHIP (NOME TEXT, MODAL TEXT, isCup INTEGER DEFAULT 0, isIndividual INTEGER DEFAULT 0, " +
                "isStarted INTEGER DEFAULT 0, isCampeao INTEGER DEFAULT 0);");
        sqLiteDatabase.execSQL("CREATE TABLE PARTICIPANT (NOME TEXT, CHAMP TEXT);");
        sqLiteDatabase.execSQL("CREATE TABLE MATCH (champName TEXT, HOME TEXT, VISITANT TEXT, ROUND TEXT, no INTEGER DEFAULT 0," +
                "FINISHED INTEGER DEFAULT 0, visScore INTEGER DEFAULT 0, homeScore INTEGER DEFAULT 0)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("BD", "onupgrade");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CHAMPIONSHIP");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PARTICIPANT");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS MATCH");

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
        content.put("isStarted", championship.isStarted() ? 1 : 0);
        content.put("isCampeao", championship.isCampeao() ? 1 : 0);
        sqlLite.insert("CHAMPIONSHIP", null, content);
        sqlLite.close();
    }

    public void insertParticipant(String champName, Participant participant) {
        SQLiteDatabase sqlLite = this.getWritableDatabase();

        ContentValues content = new ContentValues();

        content.put("NOME", participant.getName());
        content.put("CHAMP", champName);
        sqlLite.insert("PARTICIPANT", null, content);
        sqlLite.close();
    }

    public void insertMatches(String champName, List<Match> matches) {
        for (Match match : matches) {
            SQLiteDatabase sqlLite = this.getWritableDatabase();

            ContentValues content = new ContentValues();

            content.put("champName", champName);
            content.put("HOME", match.getHome().getName());
            content.put("VISITANT", match.getVisitant().getName());
            content.put("ROUND", match.getRound());
            content.put("no", match.getNumber());
            content.put("FINISHED", match.isFinished() ? 1 : 0);
            content.put("visScore", match.getVisitantScore());
            content.put("homeScore", match.getHomeScore());

            sqlLite.insert("MATCH", null, content);
            sqlLite.close();
        }
    }

    public void startChamp(String champName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("isStarted", 1);

        // updating row
        db.update("CHAMPIONSHIP", values, "NOME" + " = ?",
                new String[] { String.valueOf(champName) });
    }

    public void setMatchScore(String champName, int matchNumber, int home, int visitant) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.d("BD","score");

        ContentValues values = new ContentValues();
        values.put("finished", 1);
        values.put("homeScore", home);
        values.put("visScore", visitant);

        // updating row
        db.update("MATCH", values, "champName" + " = ?",
                new String[] { String.valueOf(champName) });
    }

    //FIXME pegar apenas os desse campeonato
    public List<Match> getAllMatches(String champName) {
        List<Match> matches = new ArrayList<>();

        String selectQuery = "SELECT  * FROM MATCH WHERE champName = '" + champName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.d("BD", "iteracaoA");
                try{
                    String homeName = cursor.getString(1);
                    String visName = cursor.getString(2);
                    String round = cursor.getString(3);
                    int no = cursor.getInt(cursor.getColumnIndex("no"));
                    boolean finished = (cursor.getInt(cursor.getColumnIndex("FINISHED")) == 1);
                    int homeScore = cursor.getInt(cursor.getColumnIndex("homeScore"));
                    int visScore = cursor.getInt(cursor.getColumnIndex("visScore"));
                    Match m = Match.createFromBD(new Participant(homeName), new Participant(visName), round, no,finished, homeScore, visScore);
                    matches.add(m);
                } catch (Exception ex) {
                    Log.d("BD", "erro");
                }
            } while (cursor.moveToNext());
        }

        return matches;
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
                Log.d("BD", "champ");
                try{
                    //List<Match> matches = new ArrayList<>();
                    Log.d("BD",cursor.getString(0));
                    List<Match> matches = getAllMatches(cursor.getString(0));
                    Championship championship = Championship.createFromBD(cursor.getString(0), cursor.getString(1), (cursor.getInt(cursor.getColumnIndex("isCup")) == 1), (cursor.getInt(cursor.getColumnIndex("isIndividual")) == 1),
                            getAllParticipants(cursor.getString(0)), (cursor.getInt(cursor.getColumnIndex("isStarted")) == 1),
                            (cursor.getInt(cursor.getColumnIndex("isCampeao")) == 1), matches);
                    champList.add(championship);
                } catch (Exception ex) {
                    Log.d("BD", ex.getMessage());
                }
            } while (cursor.moveToNext());
        }

        // return contact list
        return champList;
    }


    //FIXME pegar apenas os desse campeonato
    public List<Participant> getAllParticipants(String champName) {
        List<Participant> participants = new ArrayList<>();

        String selectQuery = "SELECT  * FROM PARTICIPANT WHERE CHAMP = '" + champName + "'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Log.d("BD", "iteracao");
                try{
                    Participant p = new Participant(cursor.getString(0));
                    participants.add(p);
                } catch (Exception ex) {
                    Log.d("BD", "erro");
                }
            } while (cursor.moveToNext());
        }

        return participants;
    }

}
