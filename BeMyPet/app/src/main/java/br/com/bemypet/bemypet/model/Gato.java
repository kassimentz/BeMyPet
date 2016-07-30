package br.com.bemypet.bemypet.model;

/**
 * Created by Kassi on 30/07/16.
 */
public class Gato extends Pet {

    private Boolean castrado;

    public Gato(){

    }

    public Boolean getCastrado() {
        return castrado;
    }

    public void setCastrado(Boolean castrado) {
        this.castrado = castrado;
    }
}
