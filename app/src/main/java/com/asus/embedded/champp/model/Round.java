package com.asus.embedded.champp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Round implements Serializable {

    private int number;
    private List<Match> matches;


    public Round(int number) {
        this.number = number;
        this.matches = new ArrayList<>();
    }

    public List<Match> getMatches() {
        return matches;
    }

    public int getNumber() {
        return number;
    }

}
