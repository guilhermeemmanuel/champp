package com.asus.embedded.champp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guiga on 14/05/2015.
 */
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
