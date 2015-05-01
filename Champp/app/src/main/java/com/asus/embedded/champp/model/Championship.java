package com.asus.embedded.champp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Championship implements Serializable{
    private String name;
    private String modal;
    private boolean isIndividual;
    private boolean isCup;
    private ArrayList<Participant> participants;
    private boolean isStarted = false;

    public Championship(String name, String modal, boolean isIndividual, boolean isCup) throws EmptyFieldException {
        if(name.isEmpty() || modal.isEmpty()){ throw new EmptyFieldException();}
        this.name = name;
        this.modal = modal;
        this.isIndividual = isIndividual;
        this.isCup = isCup;
        this.participants = new ArrayList<>();

    }

    public Championship(String name) {
        this.name = name;
        this.participants = new ArrayList<>();
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

    public boolean isStarted(){ return isStarted;}
    @Override
    public boolean equals(Object o) {
        if(o != null && o instanceof Championship){
            if(name.equals(((Championship)o).getName())){
                return true;
            }
        }
        return false;
    }

    public void addParticipant(String name) throws EmptyFieldException, SameNameException {
        for (Participant p : participants) {
            if(p.getName().equals(name)){throw new SameNameException();}
        }
        participants.add(new Participant(name));
    }

    public ArrayList<Participant> getParticipants() {
        return participants;
    }

    public void startedChamp() {
        isStarted = true;
        if(!isCup()) {
            int rounds = 0;
            if (participants.size() % 2 == 0) {
                rounds = participants.size()/2;
            } else {
                rounds = (participants.size()/2) + 1;
            }
            
        }
    }
}
