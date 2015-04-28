package com.asus.embedded.champp.controller;

import android.util.Log;

import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.InvalidChampException;
import com.asus.embedded.champp.model.Participant;

import java.util.ArrayList;
import java.util.List;

public class ChampionshipController {
    private static ChampionshipController cp;
    private static List<Championship> champs;

    private ChampionshipController() {
        //TODO pegar todos os campeonatos do banco.
        champs = new ArrayList<Championship>();
    }

    public void createChampionship(String name, String modal, boolean isIndividual, boolean isCup) throws EmptyFieldException, InvalidChampException {
        Championship c = new Championship(name, modal, isIndividual, isCup);

        if(champs.contains(c)){ throw new InvalidChampException();}

        champs.add(c);
        Log.i("champ",name);
        Log.i("champ",modal);
        Log.i("champ","" + isIndividual);
        Log.i("champ","" + isCup);
    }

    public Championship addParticipant(String nameChamp, String participant) {
        for (Championship champ : champs) {
            if(champ.equals(new Championship(nameChamp))){
                champ.addParticipant(participant);
                return champ;
            }
        }
        return null;
    }

    public static ChampionshipController getInstance() {
        if(cp == null){
            cp = new ChampionshipController();
        }
        return cp;
    }

    public static List<Championship> getChamps() {
        return champs;
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
}
