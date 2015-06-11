package com.asus.embedded.champp.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.ExceededCharacterException;
import com.asus.embedded.champp.model.Integrant;
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

    public Participant addIntegrant(String champ, Participant participant, String integrant) throws ExceededCharacterException, EmptyFieldException, SameNameException {
        participant.addIntegrant(integrant);
        dbHelper.insertIntegrant(champ,participant,integrant);
        return participant;
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

    public List<Participant> getParticipants(String nameChamp) {
        return dbHelper.getAllParticipants(nameChamp);
    }

    public List<Integrant> getIntegrants(String nameParticipant) {
        return new ArrayList<>();
        //return dbHelper.getAllIntegrants(nameParticipant);
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

    //BD OK
    public Championship setMatchScore(String champName, int matchNumber, int home, int visitant) throws ExceededCharacterException, EmptyFieldException, InvalidScoreException {
        for (Championship championship : getChamps()) {
            if(championship.equals(new Championship(champName))) {
                championship.setMatchScore(matchNumber, home, visitant);
                dbHelper.setMatchScore(champName, matchNumber, home, visitant);
                dbHelper.setPoints(champName, championship.getParticipants());
                if(championship.isNextRoundCreated()) {
                    dbHelper.insertMatches(champName, championship.getLastRound());
                }
                dbHelper.isChampion(champName, championship.isCampeao(), championship.campeao().getName());
                return championship;
            }
        }
        return null;
    }

    //BD OK
    public void deleteChampionship(String champName) {
        dbHelper.deleteChamp(champName);
    }


    //BD OK
    public Championship deleteParticipant(String champName, String participant) {
        dbHelper.deleteParticipant(champName, participant);
        try {
            return getChamp(champName);
        } catch (Exception ex) {

        }
        return null;
    }

    public Participant deleteIntegrant(String champName, Participant participant, String integrant) {
        participant.deleteIntegrant(integrant);
        dbHelper.deleteIntegrant(champName, participant.getName(), integrant);
        return participant;
    }

}
