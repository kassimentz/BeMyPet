package br.com.bemypet.bemypet.enums;

/**
 * Created by kassi on 23/07/16.
 */
public enum Sexo {

    MACHO("Macho"),
    FEMEA("Femea");

    private String friendlyName;

    private Sexo(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override
    public String toString(){
        return friendlyName;
    }
}
