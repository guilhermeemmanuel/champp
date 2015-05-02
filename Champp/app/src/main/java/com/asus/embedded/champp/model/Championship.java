package com.asus.embedded.champp.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Championship implements Serializable{
    private String name;
    private String modal;
    private boolean isIndividual;
    private boolean isCup;
    private ArrayList<Participant> participants;
    private boolean isStarted = false;
    private List<Match> matches;

    public Championship(String name, String modal, boolean isIndividual, boolean isCup) throws EmptyFieldException {
        if(name.isEmpty() || modal.isEmpty()){ throw new EmptyFieldException();}
        this.name = name;
        this.modal = modal;
        this.isIndividual = isIndividual;
        this.isCup = isCup;
        this.participants = new ArrayList<>();
        this.matches = new ArrayList<>();

    }

    public Championship(String name) {
        this.name = name;
        this.participants = new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getModal() {
        return modal;
    }

    public boolean isCup() {
        return isCup;
    }

    public boolean isIndividual() {
        return isIndividual;
    }

    public boolean isStarted(){ return isStarted;}
    @Override
    public boolean equals(Object o) {
        if(o != null && o instanceof Championship){
            if(name.equals(((Championship)o).getName())){
                return true;
            }
        }
        return false;
    }

    public void addParticipant(String name) throws EmptyFieldException, SameNameException {
        for (Participant p : participants) {
            if(p.getName().equals(name)){throw new SameNameException();}
        }
        participants.add(new Participant(name));
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void startedChamp() {
        isStarted = true;
        if(isCup()) {
            //eh copa

        }
        else {
            //eh liga
            if (participants.size() % 2 == 1) {
                try {
                    Participant NIL = new Participant("NIL");
                    NIL.turnNilParticipant();
                    participants.add(0, NIL);
                } catch (Exception ex) {
                    //Nao deve entrar aqui nunca
                }
            }
            Log.d("CHAMP","CREATE LEAGUE");

            int t = participants.size();
            int m = participants.size() / 2;
            for (int i = 0; i < t - 1; i++) {
                //System.out.print((i + 1) + "a rodada: ");
                for (int j = 0; j < m; j++) {
                    //Clube está de fora nessa rodada?
                    if (participants.get(j).getName().isEmpty())
                        continue;

                    //Teste para ajustar o mando de campo
                    if (j % 2 == 1 || i % 2 == 1 && j == 0)
                        this.matches.add(new Match(participants.get(t - j - 1),participants.get(j),"round " + i, 0));
                    else
                        this.matches.add(new Match(participants.get(j),participants.get(t - j - 1),"round " + i, 0));
                }
                //System.out.println();
                //Gira os clubes no sentido horário, mantendo o primeiro no lugar
                participants.add(1, participants.remove(participants.size()-1));
            }
        }
        deleteNilParticipant();
    }

    public List<Match> getMatches() {
        return matches;
    }


    private void deleteNilParticipant() {
        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i).getName().isEmpty()) {
                participants.remove(i);
                return;
            }
        }
    }
}
