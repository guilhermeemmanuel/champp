package com.asus.embedded.champp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Participant implements Serializable, Comparable<Participant> {

    private static final int NAME_LIMIT = 15;
    private String name;
    private List<Integrant> integrants;
    private int counterScore;
    //Variables for table classificacion ranking
    private int numberGames;
    private int numberWins;
    private int numberDefeats;
    private int goalsPro;
    private int goalsAgainst;
    private int draw;

    public Participant(String name) throws EmptyFieldException, ExceededCharacterException {
        setName(name);
        this.counterScore = 0;
        this.integrants = new ArrayList<Integrant>();
        this.numberGames = 0;
        this.numberWins = 0;
        this.numberDefeats = 0;
        this.goalsPro = 0;
        this.goalsAgainst = 0;
        this.draw = 0;
    }

    public String getName() {
        return name;
    }

    public void winMatch(){
        this.counterScore +=3;
    }

    public void empateMatch(){
        this.counterScore +=1;

    }

    public int getScore(){
        return counterScore;
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
        int scoreComp = participant.getScore() - getScore();
        int winsComp = participant.getNumberWins() - getNumberWins();
        int balanceComp = participant.getBalance() - getBalance();
        int proComp = participant.getGoalsPro() - getGoalsPro();

        if(scoreComp != 0) {
            return scoreComp;
        }
        if(winsComp != 0) {
            return winsComp;
        }
        if(balanceComp != 0) {
            return balanceComp;
        }
        if(proComp != 0) {
            return  proComp;
        }
        return 0;
    }

    private Participant(String name, int score, List<Integrant> integrants,int jogos,int vitorias, int derrotas, int goalsPro, int goalsContra, int empate) {
        this.name = name;
        this.counterScore = score;
        this.integrants = integrants;
        this.numberGames = jogos;
        this.numberWins = vitorias;
        this.numberDefeats = derrotas;
        this.goalsPro = goalsPro;
        this.goalsAgainst = goalsContra;
        this.draw = empate;

    }

    public static Participant createFromBD(String name, int score, List<Integrant> integrants, int jogos,int vitorias,int derrotas,int goalsPro,int goalsContra,int empate) {
        Participant p = new Participant(name, score, integrants,jogos,vitorias,derrotas,goalsPro,goalsContra,empate);
        return p;
    }

    public int getNumberWins() {
        return numberWins;
    }


    public void addWin(){
         this.numberWins+=1;
    }

    public int getNumberGames() {
        return numberGames;
    }
    public void addNumberGames(){
        this.numberGames+=1;
    }

    public int getNumberDefeats() {
        return numberDefeats;
    }

    public void addNumberDefeats(){
        this.numberDefeats+=1;
    }

    public int getGoalsPro() {
        return goalsPro;
    }

    public void addGoalsPro(int goalsPro){
        this.goalsPro+=goalsPro;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void addGoalsAgainst(int goalsAgainst){
        this.goalsAgainst+=goalsAgainst;
    }

    public int getBalance() {
        return goalsPro - goalsAgainst;
    }


    public int getDraw() {
        return draw;
    }
    public void addDraw(){
        this.draw+=1;
    }
}


