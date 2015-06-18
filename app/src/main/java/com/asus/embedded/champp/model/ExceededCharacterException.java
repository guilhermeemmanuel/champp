package com.asus.embedded.champp.model;

public class ExceededCharacterException extends Exception {

    public ExceededCharacterException() {
        super("Character Limit Exceeded");

    }
}
