package br.com.bemypet.bemypet.enums;

/**
 * Created by Kassi on 30/07/16.
 */
public enum Operadora {

    VIVO("Vivo"),
    CLARO("Claro"),
    OI("Oi"),
    TIM("Tim");

    private String friendlyName;

    private Operadora(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override
    public String toString(){
        return friendlyName;
    }
}
