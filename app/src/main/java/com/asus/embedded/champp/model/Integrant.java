package com.asus.embedded.champp.model;

import java.io.Serializable;

public class Integrant implements Serializable {
    private static final int NAME_LIMIT = 15;
    private String name;

    public Integrant(String name) throws EmptyFieldException, ExceededCharacterException {
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws EmptyFieldException, ExceededCharacterException {
        if (name.isEmpty()) {
            throw new EmptyFieldException();
        }
        if (name.length() > NAME_LIMIT){
            throw new ExceededCharacterException();
        }
        this.name = name;
    }
}
