package com.asus.embedded.champp.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Championship implements Serializable {
    private static final int NAME_LIMIT = 25;
    private final int MODAL_LIMIT = 15;

    //BD
    private String name;
    //BD
    private String modal;
    //BD
    private boolean isIndividual;
    //BD
    private boolean isCup;
    //BD
    private List<Participant> participants;
    //BD
    private boolean isStarted = false;
    //BD
    private List<Round> rounds;
    //BD
    private boolean isChampion = false;

    private Participant champion;

    private boolean isHomeWin;

    //BOOLEAN DE CONTROLE
    private boolean nextRoundCreated = false;

    public Championship(String name, String modal, boolean isIndividual, boolean isCup, boolean isHomeWin) throws EmptyFieldException, ExceededCharacterException {
        if (name.isEmpty() || modal.isEmpty()) {
            throw new EmptyFieldException();
        }
        if (name.length() > NAME_LIMIT || modal.length() > MODAL_LIMIT){
            throw new ExceededCharacterException();
        }
        this.name = name;
        this.modal = modal;
        this.isIndividual = isIndividual;
        this.isCup = isCup;
        this.participants = new ArrayList<Participant>();
        this.rounds = new ArrayList<Round>();
        this.isHomeWin = isHomeWin;

    }

    public Championship(String name) throws EmptyFieldException, ExceededCharacterException {
        if (name.isEmpty()) {
            throw new EmptyFieldException();
        }
        if (name.length() > NAME_LIMIT){
            throw new ExceededCharacterException();
        }
        this.name = name;
        this.participants = new ArrayList<Participant>();
        this.rounds = new ArrayList<Round>();
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

    public boolean isStarted() {
        return isStarted;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Championship) {
            if (name.equals(((Championship) o).getName())) {
                return true;
            }
        }
        return false;
    }

    public void addParticipant(String name) throws EmptyFieldException, SameNameException, ExceededCharacterException {
        for (Participant p : participants) {
            if (p.getName().equals(name)) {
                throw new SameNameException();
            }
        }
        participants.add(new Participant(name));
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void startedChamp() {

        isStarted = true;

        participants = Util.suffle(participants);

        if (isCup()) {
            //eh copa
            int org = Util.getNearLowPotency(2, participants.size());

            if (org == participants.size()) {
                //participantes eh potencia de 2
                int games = org / 2;
                Round r = new Round(org);
                for (int i = 0; i < games; i++) {
                    r.getMatches().add(new Match(participants.get(i * 2), participants.get(i * 2 + 1), "round of " + org, i));
                }
                this.rounds.add(r);

            } else {
                //sera necessario um round preliminar
                int dif = participants.size() - org;
                Round r = new Round(-1);
                for (int i = 0; i < dif; i++) {
                    r.getMatches().add(new Match(participants.get(i * 2), participants.get(i * 2 + 1), "preliminars", i));
                }
                this.rounds.add(r);

            }


        } else {
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
            Log.d("CHAMP", "CREATE LEAGUE");

            int number = 0;
            int t = participants.size();
            int m = participants.size() / 2;
            for (int i = 0; i < t - 1; i++) {
                Round r = new Round(i);
                //System.out.print((i + 1) + "a rodada: ");
                for (int j = 0; j < m; j++) {
                    //Clube está de fora nessa rodada?
                    if (participants.get(j).getName().isEmpty())
                        continue;

                    //Teste para ajustar o mando de campo
                    if (j % 2 == 1 || i % 2 == 1 && j == 0) {
                        r.getMatches().add(new Match(participants.get(t - j - 1), participants.get(j), "round " + i, number));
                    }
                    else {
                        r.getMatches().add(new Match(participants.get(j), participants.get(t - j - 1), "round " + i, number));
                    }
                    number++;
                }
                this.rounds.add(r);
                //System.out.println();
                //Gira os clubes no sentido horário, mantendo o primeiro no lugar
                participants.add(1, participants.remove(participants.size() - 1));
            }
        }
        deleteNilParticipant();
    }

    public List<Match> getMatches() {
        List<Match> matches = new ArrayList<Match>();
        for (Round round : rounds) {
            matches.addAll(round.getMatches());
        }
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

    public void setMatchScore(int number, int home, int visitant, int homePenalty, int visPenalty) throws InvalidScoreException {
            for (Match match : getMatches()) {
                if (match.equals(new Match(number))) {
                    Log.i("mudei", match.getHome().getName() + " " + home + " X " + match.getVisitant().getName() + " " + visitant);
                    match.setScore(home, visitant, homePenalty, visPenalty, isHomeWin, isCup);
                    match.sumPoints();
                    if (isProximosConfrontos()){
                        if(!isCup()) {
                            isChampion = true;
                        }
                        nextConfrontations();
                    }

                    Log.i("mudei", "" + home);
                }
            }

    }

    public boolean isProximosConfrontos() {
        for (Match match : getMatches()) {
            if (!match.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public boolean isChampion(){
        return isChampion;
    }

    public Participant getChampion(){
        return champion;
    }

    public boolean isHomeWin() {
        return isHomeWin;
    }

    public void setIsHomeWin(boolean isHomeWin) {
        this.isHomeWin = isHomeWin;
    }

    //copa
    public void nextConfrontations() {
        ArrayList<Participant> wins = new ArrayList<Participant>();
        Log.i("gerar" , "proximox");

        int quantWins = 0;

        for (Match match : rounds.get(rounds.size() - 1).getMatches()) {
                  wins.add(match.winParticipant());
                   quantWins++;
        }



        if(quantWins == 1 && isCup()){
            nextRoundCreated = false;
            isChampion = true;
            champion = wins.get(wins.size() -1);
        }

        if (isCup()) {
            //eh copa
            int previousMatches = (Util.getNearLowPotency(2, participants.size()))/2;
            int org = Util.getNearLowPotency(2, wins.size());
            if (org == wins.size()) {
                nextRoundCreated = true;
                //participantes eh potencia de 2
                Round r = new Round(org);
                int games = org / 2;
                for (int i = 0; i < games; i++) {
                    r.getMatches().add(new Match(wins.get(i * 2), wins.get(i * 2 + 1), "round of " + org, i + previousMatches));
                }
            this.rounds.add(r);

            }
            //else {
                //sera necessario um round preliminar
              //  int dif = wins.size() - org;

                //for (int i = 0; i < dif; i++) {
                    //getMatches().add(new Match(wins.get(i * 2), wins.get(i * 2 + 1), "preliminars ", i));
                //}


            //}
        }

    }


    public boolean isNextRoundCreated() {
        return nextRoundCreated;
    }

    public List<Match> getLastRound() {
        return rounds.get(rounds.size() -1).getMatches();
    }

    public boolean hasRound(int round) {
        for (Round r : rounds) {
            if(r.getNumber() == round) {
                return true;
            }
        }
        return false;
    }

    private Championship(String name, String modal, boolean isCup, boolean isIndividual, List<Participant> participants, boolean isStarted, boolean isChampion,
            List<Match> matches, Participant champion, boolean isHomeWin) {
        Log.d("BD",name);
        this.name = name;
        this.modal = modal;
        this.isCup = isCup;
        this.isIndividual = isIndividual;
        this.participants = participants;
        this.isStarted = isStarted;
        this.isChampion = isChampion;
        this.champion = champion;
        this.isHomeWin = isHomeWin;
        this.rounds = new ArrayList<>();
        for (Match match : matches) {
            match.setHome(getParticipant(match.getHome().getName()));
            match.setVisitant(getParticipant(match.getVisitant().getName()));
            int r = 0;
            Log.d("BD",match.getRound());
            if(match.getRound().equals("preliminars")) {
                r = -1;
            }
            else {
                String round = match.getRound();
                if(isCup){
                    round = round.replaceFirst("round of ","");
                }
                else {
                    round = round.replaceFirst("round ","");
                }
                r = Integer.parseInt(round);
            }
            if (!hasRound(r)) {
                Round round = new Round(r);
                round.getMatches().add(match);
                rounds.add(round);
            }
            else {
                for (Round round : rounds) {
                    if(round.getNumber() == r) {
                        round.getMatches().add(match);
                    }
                }
            }
            //FIXME Talvez seja preciso reordenar a lista de rounds
        }
    }

    public Participant getParticipant(String name) {
        for (Participant p : participants) {
            if(p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }


    public static Championship createFromBD(String name, String modal, boolean isCup, boolean isIndividual, List<Participant> participants, boolean isStarted, boolean isCampeao
            , List<Match> matches, Participant campeao, boolean isHomeWin) {
        return new Championship(name, modal, isCup, isIndividual, participants, isStarted, isCampeao, matches, campeao, isHomeWin);
    }

}