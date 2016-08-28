package br.com.bemypet.bemypet.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kassi on 27/08/16.
 */

public class Notificacao implements Serializable{

    private String cpfAdotante;
    private String cpfDoador;
    private String idPet;
    private Long data;

    public Notificacao(){

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

    public Map<String,Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("adotante", cpfAdotante);
        result.put("doador", cpfDoador);
        result.put("pet", idPet);
        result.put("data", data);
        return result;
    }

}
