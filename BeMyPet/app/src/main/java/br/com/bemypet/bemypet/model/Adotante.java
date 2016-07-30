package br.com.bemypet.bemypet.model;

import java.util.List;

/**
 * Created by Kassi on 30/07/16.
 */
public class Adotante extends Usuario {

    private List<Pet> pets;
    private Boolean jaTevePete;
    private Boolean possuiTelaNasJanelas;

    public Adotante(){

    }

    public boolean devolverPet(Pet pet){
        return false;
    }

    public Boolean getJaTevePete() {
        return jaTevePete;
    }

    public void setJaTevePete(Boolean jaTevePete) {
        this.jaTevePete = jaTevePete;
    }

    public Boolean getPossuiTelaNasJanelas() {
        return possuiTelaNasJanelas;
    }

    public void setPossuiTelaNasJanelas(Boolean possuiTelaNasJanelas) {
        this.possuiTelaNasJanelas = possuiTelaNasJanelas;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
