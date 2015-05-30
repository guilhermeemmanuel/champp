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
    private static List<Championship> champs;
    private DatabaseHelper dbHelper;

    private ChampionshipController(Context context) {
        //TODO pegar todos os campeonatos do banco.
        champs = new ArrayList<Championship>();
        dbHelper = new DatabaseHelper(context);
    }

    //DB OK
    public void createChampionship(String name, String modal, boolean isIndividual, boolean isCup) throws EmptyFieldException, SameNameException, ExceededCharacterException {
        Championship c = new Championship(name, modal, isIndividual, isCup);
        dbHelper.insertChampionship(c);
    }
    //DB OK
    //FIXME pegar apenas os participantes desse campeonato
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


    public Championship startChamp(String name) throws ExceededCharacterException, EmptyFieldException {
        for (Championship championship : getChamps()) {
            if (championship.equals(new Championship(name))){
                championship.startedChamp();
                dbHelper.insertMatches(name, championship.getMatches());
                return championship;
            }
        }
        return null;
    }

    //TODO falta criar o metodo completo
    //FIXME cuidado para nao permitir setar o resultado da mesma partida duas vezes
    public Championship setMatchScore(String champName, int matchNumber, int home, int visitant) throws ExceededCharacterException, EmptyFieldException, InvalidScoreException {
        for (Championship championship : champs) {
            if(championship.equals(new Championship(champName))) {
                championship.setMatchScore(matchNumber, home, visitant);
                return championship;
            }
        }
        return null;
    }

}
