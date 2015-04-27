package com.asus.embedded.champp.model;

import java.io.Serializable;

/**
 * Created by Nicolas on 27/04/2015.
 */
public class Participant implements Serializable {

    private String name;

    public Participant(String name){

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
