package com.asus.embedded.champp.model;

import java.io.Serializable;

/**
 * Created by Guilherme-PC on 01/05/2015.
 */
public class Match implements Serializable {

    private Participant home;
    private Participant visitant;
    private int number;
    private String round;

    public Match(Participant home, Participant visitant, String round, int number){
        this.home = home;
        this.visitant = visitant;
        this.round = round;
        this.number = number;
    }

    public String getRound() {
        return round;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return home.getName() + " x " + visitant.getName();
    }
}
