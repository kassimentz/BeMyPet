package br.com.bemypet.bemypet.enums;

/**
 * Created by Kassi on 30/07/16.
 */
public enum ConfBuscaCastrado {

    SIM("Sim"),
    NAO("Não"),
    INDIFERENTE("Indiferente");

    private String friendlyName;

    private ConfBuscaCastrado(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override
    public String toString(){
        return friendlyName;
    }
}
