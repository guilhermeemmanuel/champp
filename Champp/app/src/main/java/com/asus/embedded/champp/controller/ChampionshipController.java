package com.asus.embedded.champp.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.ExceededCharacterException;
import com.asus.embedded.champp.model.InvalidScoreException;
import com.asus.embedded.champp.model.Participant;
import com.asus.embedded.champp.model.SameNameException;
import com.asus.embedded.champp.persistence.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class ChampionshipController {
    private static ChampionshipController cp;
    private DatabaseHelper dbHelper;

    private ChampionshipController(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //DB OK
    public void createChampionship(String name, String modal, boolean isIndividual, boolean isCup) throws EmptyFieldException, SameNameException, ExceededCharacterException {
        Championship c = new Championship(name, modal, isIndividual, isCup);
        dbHelper.insertChampionship(c);
    }
    //DB OK
    public Championship addParticipant(String nameChamp, String participant) throws EmptyFieldException, SameNameException, ExceededCharacterException {
        for (Championship champ : getChamps()) {

            if(champ.equals(new Championship(nameChamp))){
                champ.addParticipant(participant);
                dbHelper.insertParticipant(nameChamp,new Participant(participant));
                return champ;
            }
        }
        return null;
    }

    public static ChampionshipController getInstance(Context context) {
        if(cp == null){
            cp = new ChampionshipController(context);
        }
        return cp;
    }

    //DB OK
    public List<Championship> getChamps() {
        return dbHelper.getAllChampionships();
    }

    // DB OK
    public boolean champsIsEmpty(){
        return getChamps().isEmpty();
    }


    //DB OK
    public Championship startChamp(String name) throws ExceededCharacterException, EmptyFieldException {
        for (Championship championship : getChamps()) {
            if (championship.equals(new Championship(name))){
                championship.startedChamp();
                dbHelper.startChamp(name);
                dbHelper.insertMatches(name, championship.getMatches());
                return championship;
            }
        }
        return null;
    }

    //BD OK
    public Championship getChamp(String name) throws ExceededCharacterException, EmptyFieldException {
        for (Championship c : getChamps()) {
            if(c.equals(new Championship(name))) {
                return c;
            }
        }
        return null;
    }

    //BD in progress
    //FIXME ele seta todos os jogos ao mesmo tempo
    //TODO falta adicionar jogos novos
    public Championship setMatchScore(String champName, int matchNumber, int home, int visitant) throws ExceededCharacterException, EmptyFieldException, InvalidScoreException {
        for (Championship championship : getChamps()) {
            if(championship.equals(new Championship(champName))) {
                championship.setMatchScore(matchNumber, home, visitant);
                dbHelper.setMatchScore(champName, matchNumber, home, visitant);
                return championship;
            }
        }
        return null;
    }

    public void deleteChampionship(String champName) {
        dbHelper.deleteChamp(champName);
    }

}
