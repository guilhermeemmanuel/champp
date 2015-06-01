package com.asus.embedded.champp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Participant implements Serializable, Comparable<Participant> {

    private static final int NAME_LIMIT = 15;
    private String name;
    private List<Integrant> integrants;
    private int counterPontos;

    public Participant(String name) throws EmptyFieldException, ExceededCharacterException {
        setName(name);
        this.counterPontos = 0;
        this.integrants = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void winMatch(){
        this.counterPontos+=3;
    }

    public void empateMatch(){
        this.counterPontos+=1;
    }

    public int getPontuacao(){
        return counterPontos;
    }

    public void setName(String name) throws EmptyFieldException, ExceededCharacterException {
        if(name.isEmpty()){ throw new EmptyFieldException();}
        if (name.length() > NAME_LIMIT){
            throw new ExceededCharacterException();
        }
        this.name = name;
    }

    public List<Integrant> getIntegrants() {
        return integrants;
    }

    public void addIntegrant(Integrant integrant) {
        this.integrants.add(integrant);
    }

    protected void turnNilParticipant() {
        this.name = "";
    }

    @Override
    public int compareTo(Participant participant) {
        return (participant.getPontuacao() - getPontuacao());
    }



    public static Participant createFromBD(String name) {
        try {
            Participant p = new Participant("test");
            p.turnNilParticipant();
            return p;
        } catch (Exception ex) {

        }
        return null;
    }
}


