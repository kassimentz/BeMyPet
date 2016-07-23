package br.com.bemypet.bemypet.enums;

/**
 * Created by kassi on 23/07/16.
 */
public enum Especie {

    CACHORRO("Cachorro"),
    GATO("Gato"),
    PASSARO("Pássaro"),
    HAMSTER("Hamster");

    private String friendlyName;

    private Especie(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override
    public String toString(){
        return friendlyName;
    }
}
