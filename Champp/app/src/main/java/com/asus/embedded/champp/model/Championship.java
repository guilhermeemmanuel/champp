package com.asus.embedded.champp.model;

/**
 * Created by Guilherme-PC on 16/04/2015.
 */
public class Championship {

    private String name;
    private String modal;
    private boolean isIndividual;
    private boolean isCup;

    public Championship(String name, String modal, boolean isIndividual, boolean isCup){
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
