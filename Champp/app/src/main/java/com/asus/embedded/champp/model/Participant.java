package com.asus.embedded.champp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Participant implements Serializable {

    private String name;
    private List<Integrant> integrants;
    private int counterPontos;

    public Participant(String name) throws EmptyFieldException {
        if(name.isEmpty()){ throw new EmptyFieldException();}
        this.name = name;
        this.counterPontos = 0;
        this.integrants = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    //CRIADO VINICIUS
    public void winMatch(){
        this.counterPontos+=3;
    }
    //CRIADO VINICIUS
    public void empateMatch(){
        this.counterPontos+=1;
    }
    //CRIADO VINICIUS
    public int getPontuacao(){
        return counterPontos;
    }


    public void setName(String name) {
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
}


