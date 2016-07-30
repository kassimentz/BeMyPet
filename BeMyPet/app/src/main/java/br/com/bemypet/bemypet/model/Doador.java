package br.com.bemypet.bemypet.model;

import java.util.List;

/**
 * Created by Kassi on 30/07/16.
 */
public class Doador extends Usuario {

    private List<Pet> pets;

    public Doador(){

    }

    public boolean cadastrarPet(Pet pet){
        return false;
    }

    public boolean analisarAdocao(Adotante adotante, Pet pet){
        return false;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
