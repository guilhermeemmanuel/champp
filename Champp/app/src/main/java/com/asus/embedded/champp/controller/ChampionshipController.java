package com.asus.embedded.champp.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.ExceededCharacterException;
import com.asus.embedded.champp.model.InvalidScoreException;
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

    public void createChampionship(String name, String modal, boolean isIndividual, boolean isCup) throws EmptyFieldException, SameNameException, ExceededCharacterException {
        Championship c = new Championship(name, modal, isIndividual, isCup);
        dbHelper.insertChampionship(c);

        //if(champs.contains(c)){ throw new SameNameException();}

        //champs.add(c);
    }

    public Championship addParticipant(String nameChamp, String participant) throws EmptyFieldException, SameNameException, ExceededCharacterException {
        for (Championship champ : champs) {

            if(champ.equals(new Championship(nameChamp))){
                champ.addParticipant(participant);
                return champ;
            }
        }
        return null;
    }

    public static ChampionshipController getInstance(Context context) {
        if(cp == null){
            cp = new ChampionshipController(context);
            try {

                /*
                cp.createChampionship("Copa do Brasil", "Futebol", false, true);
                cp.addParticipant("Copa do Brasil", "Palmeiras");
                cp.addParticipant("Copa do Brasil", "Santos");
                cp.addParticipant("Copa do Brasil", "São Paulo");
                cp.addParticipant("Copa do Brasil", "Flamengo");
                cp.addParticipant("Copa do Brasil", "Vasco");
                cp.addParticipant("Copa do Brasil", "Treze");
                cp.addParticipant("Copa do Brasil", "Cruzeiro");
                cp.addParticipant("Copa do Brasil", "Atletico");


                cp.createChampionship("Campeonato Brasileiro", "Futebol", false, false);
                cp.addParticipant("Campeonato Brasileiro", "Palmeiras");
                cp.addParticipant("Campeonato Brasileiro", "Santos");
                cp.addParticipant("Campeonato Brasileiro", "Corinthians");
                cp.addParticipant("Campeonato Brasileiro", "Joinvile");
*/
            } catch (Exception ex) {

            }
        }
        return cp;
    }

    public List<Championship> getChamps() {
        return dbHelper.getAllChampionships();
    }

/*
    public List<String> getChampsName() {
        List<String> result = new ArrayList<>();
        if(!champsIsEmpty()){
            for (Championship champ : champs){
                result.add(champ.getName());
            }
        }
        return result;
    }
*/

    public boolean champsIsEmpty(){
        return champs.isEmpty();
    }


    public Championship startChamp(String name) throws ExceededCharacterException, EmptyFieldException {
        for (Championship championship : champs) {
            if (championship.equals(new Championship(name))){
                championship.startedChamp();
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
