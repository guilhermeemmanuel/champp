package com.asus.embedded.champp.model;

import java.io.Serializable;

/**
 * Created by Guiga on 07/05/2015.
 */
public class Integrant implements Serializable {

    private String name;

    public Integrant(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
