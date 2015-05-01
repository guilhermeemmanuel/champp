package com.asus.embedded.champp.model;

/**
 * Created by Guilherme-PC on 01/05/2015.
 */
public class Match {

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
}
