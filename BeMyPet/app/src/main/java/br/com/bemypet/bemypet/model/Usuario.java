package br.com.bemypet.bemypet.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private String tokenFCM;

    //atributos de adotante
    private String jaTevePete;
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

    public String getJaTevePete() {
        return jaTevePete;
    }

    public void setJaTevePete(String jaTevePete) {
        this.jaTevePete = jaTevePete;
    }

    public Boolean getPossuiTelaNasJanelas() {
        return possuiTelaNasJanelas;
    }

    public void setPossuiTelaNasJanelas(Boolean possuiTelaNasJanelas) {
        this.possuiTelaNasJanelas = possuiTelaNasJanelas;
    }

    public String getTokenFCM() {
        return tokenFCM;
    }

    public void setTokenFCM(String tokenFCM) {
        this.tokenFCM = tokenFCM;
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
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", endereco=" + endereco +
                ", telefone='" + telefone + '\'' +
                ", pets=" + pets +
                ", tokenFCM='" + tokenFCM + '\'' +
                ", jaTevePete='" + jaTevePete + '\'' +
                ", possuiTelaNasJanelas=" + possuiTelaNasJanelas +
                '}';
    }

    public Map<String,Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("nome", nome);
        result.put("cpf", cpf);
        result.put("email", email);
        result.put("endereco", endereco.toMap());
        result.put("telefone", telefone);
        result.put("tokenFCM", tokenFCM);
        result.put("jaTevePete", jaTevePete);
        result.put("possuiTelaNasJanelas", possuiTelaNasJanelas);

        return result;
    }
}
