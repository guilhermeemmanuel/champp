package com.asus.embedded.champp.controller;

import android.util.Log;

import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.SameNameException;

import java.util.ArrayList;
import java.util.List;

public class ChampionshipController {
    private static ChampionshipController cp;
    private static List<Championship> champs;

    private ChampionshipController() {
        //TODO pegar todos os campeonatos do banco.
        champs = new ArrayList<Championship>();
    }

    public void createChampionship(String name, String modal, boolean isIndividual, boolean isCup) throws EmptyFieldException, SameNameException {
        Championship c = new Championship(name, modal, isIndividual, isCup);

        if(champs.contains(c)){ throw new SameNameException();}

        champs.add(c);
        Log.i("champ",name);
        Log.i("champ",modal);
        Log.i("champ","" + isIndividual);
        Log.i("champ","" + isCup);
    }

    public Championship addParticipant(String nameChamp, String participant) throws EmptyFieldException, SameNameException {
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
            try {
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
            } catch (Exception ex) {

            }
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


    public Championship startChamp(String name){
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
    public Championship setMatchScore(String champName, int matchNumber, int home, int visitant) {
        for (Championship championship : champs) {
            if(championship.equals(new Championship(champName))) {
                championship.setMatchScore(matchNumber, home, visitant);
                return championship;
            }
        }
        return null;
    }
}
