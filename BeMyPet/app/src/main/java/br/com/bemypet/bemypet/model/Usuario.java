package br.com.bemypet.bemypet.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kassi on 30/07/16.
 */
public class Usuario implements Serializable{

    private String nome;
    private String cpf;
    private String email;
    private Endereco endereco;
    private String telefone;
    private List<Pet> pets;

    //atributos de adotante
    private Boolean jaTevePete;
    private Boolean possuiTelaNasJanelas;

    public Usuario() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
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

    //métodos para quando é doador
    public boolean cadastrarPet(Pet pet){
        return false;
    }

    public boolean analisarAdocao(Usuario adotante, Pet pet){
        return false;
    }

    //método para quando é adotante
    public boolean devolverPet(Pet pet){
        return false;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "telefone='" + telefone + '\'' +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", endereco=" + endereco +
                '}';
    }
}
