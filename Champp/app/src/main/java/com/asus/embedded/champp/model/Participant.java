package com.asus.embedded.champp.model;

import java.io.Serializable;

public class Participant implements Serializable {

    private String name;

    public Participant(String name) throws EmptyFieldException {
        if(name.isEmpty()){ throw new EmptyFieldException();}
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
