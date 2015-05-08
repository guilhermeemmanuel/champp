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
    private boolean finished = false;
    private int visitantScore;
    private int homeScore;

    public Match(Participant home, Participant visitant, String round, int number){
        this.home = home;
        this.visitant = visitant;
        this.round = round;
        this.number = number;
    }

    protected Match (int number) {
        this.number = number;
    }

    public String getRound() {
        return round;
    }

    public int getNumber() {
        return number;
    }

    public Participant getHome() {
        return home;
    }

    public Participant getVisitant() {
        return visitant;
    }

    @Override
    public String toString() {
        return home.getName() + " x " + visitant.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        return number == match.number;

    }

    @Override
    public int hashCode() {
        return number;
    }


    public void setScore(int home, int visitant) {
        if(!finished) {
            this.homeScore = home;
            this.visitantScore = visitant;
            this.finished = true;
        }
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getVisitantScore() {
        return visitantScore;
    }

    public boolean isFinished() {
        return finished;
    }
}
