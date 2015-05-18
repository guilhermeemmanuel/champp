package com.asus.embedded.champp.model;

import android.util.Log;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Championship implements Serializable {
    private String name;
    private String modal;
    private boolean isIndividual;
    private boolean isCup;
    private ArrayList<Participant> participants;
    private boolean isStarted = false;
    private List<Round> rounds;
    private boolean isCampeao = false;
    private Participant campeao;

    public Championship(String name, String modal, boolean isIndividual, boolean isCup) throws EmptyFieldException {
        if (name.isEmpty() || modal.isEmpty()) {
            throw new EmptyFieldException();
        }
        this.name = name;
        this.modal = modal;
        this.isIndividual = isIndividual;
        this.isCup = isCup;
        this.participants = new ArrayList<Participant>();
        this.rounds = new ArrayList<Round>();

    }

    public Championship(String name) {
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

    public void addParticipant(String name) throws EmptyFieldException, SameNameException {
        for (Participant p : participants) {
            if (p.getName().equals(name)) {
                throw new SameNameException();
            }
        }
        participants.add(new Participant(name));
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void startedChamp() {
        isStarted = true;
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
                    r.getMatches().add(new Match(participants.get(i * 2), participants.get(i * 2 + 1), "preliminars ", i));
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

    public void setMatchScore(int number, int home, int visitant) {
            for (Match match : getMatches()) {
                if (match.equals(new Match(number))) {
                    Log.i("mudei", match.getHome().getName() + " " + home + " X " + match.getVisitant().getName() + " " + visitant);
                    match.setScore(home, visitant);
                    if (isProximosConfrontos()){
                        proximosConfrontos();
                    }
                    match.sumPoints();
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


    public boolean isCampeao(){
        return isCampeao;
    }

    public Participant campeao(){
        return campeao;
    }

    //copa
    public void proximosConfrontos() {
        ArrayList<Participant> wins = new ArrayList<Participant>();
        Log.i("gerar" , "proximox");

        int quantWins = 0;

        for (Match match : rounds.get(rounds.size() - 1).getMatches()) {
                  wins.add(match.winParticipant());
                   quantWins++;
        }

        if(quantWins == 1 && isCup()){
            isCampeao = true;
            campeao = wins.get(wins.size() -1);
        }

        if (isCup()) {
            //eh copa
            int jogosAnteriores = (Util.getNearLowPotency(2, participants.size()))/2;
            int org = Util.getNearLowPotency(2, wins.size());
            if (org == wins.size()) {
                //participantes eh potencia de 2
                Round r = new Round(org);
                int games = org / 2;
                for (int i = 0; i < games; i++) {
                    r.getMatches().add(new Match(wins.get(i * 2), wins.get(i * 2 + 1), "round of " + org, i + jogosAnteriores));
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

}