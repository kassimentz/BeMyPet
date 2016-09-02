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
    private Boolean lida;

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

    public Boolean getLida() {
        return lida;
    }

    public void setLida(Boolean lida) {
        this.lida = lida;
    }

    public Map<String,Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", getId());
        result.put("adotante", getCpfAdotante());
        result.put("doador", getCpfDoador());
        result.put("pet", getIdPet());
        result.put("data", getData());
        result.put("image", getImage());
        result.put("lida", getLida());
        result.put("statusNotificacao", getStatusNotificacao());

        return result;
    }

    @Override
    public String toString() {
        return "Notificacao{" +
                "id=" + id +
                ", cpfAdotante='" + cpfAdotante + '\'' +
                ", cpfDoador='" + cpfDoador + '\'' +
                ", idPet='" + idPet + '\'' +
                ", data=" + data +
                ", image='" + image + '\'' +
                ", statusNotificacao='" + statusNotificacao + '\'' +
                ", lida=" + lida +
                '}';
    }
}
