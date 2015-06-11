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
        this.integrants = new ArrayList<Integrant>();
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

    public void addIntegrant(String integrant) throws SameNameException, ExceededCharacterException, EmptyFieldException {
        for (Integrant i : integrants) {
            if (i.getName().equals(integrant)) {
                throw new SameNameException();
            }
        }
        integrants.add(new Integrant(integrant));
    }

    public void deleteIntegrant(String integrant) {
        for (int i = 0; i < integrants.size(); i++) {
            if(integrants.get(i).getName().equals(integrant)) {
                integrants.remove(i);
            }
        }

    }

    protected void turnNilParticipant() {
        this.name = "";
    }

    @Override
    public int compareTo(Participant participant) {
        return (participant.getPontuacao() - getPontuacao());
    }

    private Participant(String name, int pontos, List<Integrant> integrants) {
        this.name = name;
        this.counterPontos = pontos;
        this.integrants = integrants;

    }

    public static Participant createFromBD(String name, int pontos, List<Integrant> integrants) {
        Participant p = new Participant(name, pontos, integrants);
        return p;
    }
}


