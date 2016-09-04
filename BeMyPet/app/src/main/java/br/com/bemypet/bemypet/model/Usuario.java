package br.com.bemypet.bemypet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.bemypet.bemypet.api.StringUtils;

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
    private List<String> imagens;
    private List<Notificacao> notificacoes;

    //atributos de adotante
    private String jaTevePet;
    private Boolean possuiTelaNasJanelas;

    public Usuario() {
        pets = new ArrayList<Pet>();
        imagens = new ArrayList<>();
        notificacoes = new ArrayList<Notificacao>();
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
        return this.pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }

    public void addPet(Pet pet){
        if(pets == null){
            pets = new ArrayList<Pet>();
        }
        this.pets.add(pet);
    }

    public String getJaTevePet() {
        return jaTevePet;
    }

    public void setJaTevePet(String jaTevePet) {
        this.jaTevePet = jaTevePet;
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

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens;
    }

    public void addImage(String img){
        if(imagens == null){
            imagens = new ArrayList<>();
        }
        this.imagens.add(img);
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

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public void addNotificacao(Notificacao notificacao){
        if(notificacoes == null){
            notificacoes = new ArrayList<>();
        }
        this.notificacoes.add(notificacao);
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
                ", imagens=" + imagens +
                ", notificacoes=" + notificacoes +
                ", jaTevePet='" + jaTevePet + '\'' +
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
        result.put("jaTevePet", jaTevePet);
        result.put("possuiTelaNasJanelas", possuiTelaNasJanelas);
        result.put("imagens", imagens);
        result.put("notificacoes", notificacoesToMap());

        return result;
    }

    private List<Map<String,Object>> notificacoesToMap(){

        List<Map<String,Object>> listaNotificacoes = new ArrayList<>();
        Map<String,Object> mapNotificacoes = new HashMap<>();
        for (Notificacao not : notificacoes) {
            listaNotificacoes.add(not.toMap());
        }
        return listaNotificacoes;
    }
}
