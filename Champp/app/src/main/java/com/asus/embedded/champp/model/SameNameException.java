package com.asus.embedded.champp.model;

public class SameNameException extends Exception {

    public SameNameException() {
        super("There is already another with the same name");
    }
}




