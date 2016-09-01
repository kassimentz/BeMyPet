package br.com.bemypet.bemypet.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kassi on 27/08/16.
 */

public class Notificacao implements Serializable{

    private Long id;
    private String cpfAdotante;
    private String cpfDoador;
    private String idPet;
    private Long data;
    private String image;
    private String statusNotificacao;

    public Notificacao(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpfAdotante() {
        return cpfAdotante;
    }

    public void setCpfAdotante(String cpfAdotante) {
        this.cpfAdotante = cpfAdotante;
    }

    public String getCpfDoador() {
        return cpfDoador;
    }

    public void setCpfDoador(String cpfDoador) {
        this.cpfDoador = cpfDoador;
    }

    public String getIdPet() {
        return idPet;
    }

    public void setIdPet(String idPet) {
        this.idPet = idPet;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatusNotificacao() {
        return statusNotificacao;
    }

    public void setStatusNotificacao(String statusNotificacao) {
        this.statusNotificacao = statusNotificacao;
    }

    public Map<String,Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("adotante", cpfAdotante);
        result.put("doador", cpfDoador);
        result.put("pet", idPet);
        result.put("data", data);
        result.put("image", image);
        result.put("statusNotificacao", statusNotificacao);
        return result;
    }

}
