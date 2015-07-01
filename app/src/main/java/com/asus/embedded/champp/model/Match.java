package com.asus.embedded.champp.model;

import android.util.Log;

import java.io.Serializable;

public class Match implements Serializable {

    private Participant home;
    private Participant visitant;
    private int number;
    private String round;
    private boolean finished = false;
    private int visitantScore;
    private int homeScore;

    private int homePenalty;
    private int visPenalty;
    private boolean isHomeWin;

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

    public int getHomePenalty() {
        return homePenalty;
    }

    public int getVisPenalty() {
        return visPenalty;
    }

    public boolean isHomeWin() {
        return isHomeWin;
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

    public void setScore(int home, int visitant, int homePenalty, int visPenalty, boolean homeWin, boolean isCup) throws InvalidScoreException {
        if (home < 0 || visitant < 0){
            throw new InvalidScoreException();
        }
        if(isCup && !homeWin && home == visitant  && homePenalty == visPenalty) {
            throw new InvalidScoreException();
        }
        if(!finished) {
            this.homeScore = home;
            this.visitantScore = visitant;
            this.homePenalty = homePenalty;
            this.visPenalty = visPenalty;
            this.isHomeWin = homeWin;
            this.finished = true;
            Log.i("mudei", "" + getHome().getName() + " X " + getVisitant().getName() );
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

    public Participant winParticipant(){

        if (isFinished()){
            if (visitantScore > homeScore){
                return visitant;
            }else if (homeScore > visitantScore){
                return home;
            } else {
                if(isHomeWin) {
                    return home;
                }
                else {
                    if(homePenalty > visPenalty) {
                        return home;
                    }
                    else {
                        return visitant;
                    }
                }
            }

        }
        return null;
    }

    public Participant loseParticipant() {
        Participant win = winParticipant();
        if(home.equals(win)) {
            return visitant;
        }
        return home;
    }



    public void setHome(Participant participant) {
        this.home = participant;
    }

    public void setVisitant (Participant participant) {
        this.visitant = participant;
    }

    public void sumPoints(){

        Log.d("BD", "ps");
            if (visitantScore > homeScore) {
                visitant.winMatch();
                visitant.addWin();
                visitant.addGoalsPro(visitantScore);
                visitant.addGoalsAgainst(homeScore);
                home.addGoalsPro(homeScore);
                home.addGoalsAgainst(visitantScore);
                home.addNumberDefeats();
                home.addNumberGames();
                visitant.addNumberGames();
                visitant.getBalance();
                home.getBalance();
                Log.v("Visitante ganhoou", visitant.getScore() + "" + visitant.getName());
                Log.v("Home perdeuu", home.getScore() + home.getName());


            } else if (visitantScore < homeScore) {
                home.winMatch();
                home.addWin();
                visitant.addNumberDefeats();
                visitant.addNumberGames();
                home.addNumberGames();
                home.addGoalsPro(homeScore);
                home.addGoalsAgainst(visitantScore);
                visitant.addGoalsPro(visitantScore);
                visitant.addGoalsAgainst(homeScore);
                visitant.getBalance();
                home.getBalance();
                Log.v("Visitante perdeu", visitant.getScore() + "" + visitant.getName());
                Log.v("Home ganhooouu", home.getScore() + home.getName());


            } else if (visitantScore == homeScore) {
                visitant.empateMatch();
                home.empateMatch();
                home.addDraw();
                visitant.addDraw();
                visitant.addNumberGames();
                home.addNumberGames();
                visitant.addGoalsPro(visitantScore);
                visitant.addGoalsAgainst(homeScore);
                home.addGoalsPro(homeScore);
                home.addGoalsAgainst(visitantScore);
                home.getBalance();
                visitant.getBalance();
                Log.v("Visitante empatouu", visitant.getScore() + "" + visitant.getName());
                Log.v("Home empatoouu", home.getScore() + home.getName());


            }

    }

    private Match(Participant home, Participant visitant, String round, int no, boolean finished, int homeScore, int visScore,
            int homePenalty, int visPenalty, boolean isHomeWin) {
        this.home = home;
        this.visitant = visitant;
        this.round = round;
        this.number = no;
        this.finished = finished;
        this.homeScore = homeScore;
        this.visitantScore = visScore;
        this.homePenalty = homePenalty;
        this.visPenalty = visPenalty;
        this.isHomeWin = isHomeWin;
    }

    public static Match createFromBD(Participant home, Participant visitant, String round, int no, boolean finished, int homeScore, int visScore,
                                     int homePenalty, int visPenalty, boolean isHomeWin) {
        return new Match(home, visitant, round,no, finished, homeScore, visScore, homePenalty, visPenalty, isHomeWin);
    }

}




