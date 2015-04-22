package com.asus.embedded.champp.model;

import java.io.Serializable;

public class Championship implements Serializable{
    private String name;
    private String modal;
    private boolean isIndividual;
    private boolean isCup;

    public Championship(String name, String modal, boolean isIndividual, boolean isCup) throws EmptyFieldException {
        if(name.isEmpty() || modal.isEmpty()){ throw new EmptyFieldException();}
        this.name = name;
        this.modal = modal;
        this.isIndividual = isIndividual;
        this.isCup = isCup;

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

    @Override
    public boolean equals(Object o) {
        if(o != null && o instanceof Championship){
            if(name.equals(((Championship)o).getName())){
                return true;
            }
        }
        return false;
    }
}
