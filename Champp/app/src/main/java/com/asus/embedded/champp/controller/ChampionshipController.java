package com.asus.embedded.champp.controller;

import android.util.Log;

import com.asus.embedded.champp.model.Championship;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guilherme-PC on 16/04/2015.
 */
public class ChampionshipController {


    private static ChampionshipController cp;

    private List<Championship> champs;

    private ChampionshipController() {
        //TODO pegar todos os campeonatos do banco.
        champs = new ArrayList<Championship>();
    }

    public boolean createChampionship(String name, String modal, boolean isIndividual, boolean isCup) {
        Championship c = new Championship(name, modal, isIndividual, isCup);
        if(!champs.contains(c)){
            champs.add(c);
            Log.i("champ",name);
            Log.i("champ",modal);
            Log.i("champ","" + isIndividual);
            Log.i("champ","" + isCup);

            return true;
        }
        else{
            return false;
        }
    }

    public static ChampionshipController getInstance() {
        if(cp == null){
            cp = new ChampionshipController();
        }
        return cp;
    }

    public List<String> getChampsName() {
        List<String> result = new ArrayList<>();
        if(!champsIsEmpty()){
            for (Championship champ : champs){
                result.add(champ.getName());
            }
        }
        return result;
    }

    public boolean champsIsEmpty(){
        return champs.isEmpty();
    }
}
