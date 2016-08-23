package br.com.bemypet.bemypet.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kassi on 30/07/16.
 */
public class Endereco implements Serializable{

    private String logradouro;
    private Integer numero;
    private String complemento;
    private Integer cep;
    private Double latitude;
    private Double longitude;

    public Endereco(){

    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public Integer getCep() {
        return cep;
    }

    public void setCep(Integer cep) {
        this.cep = cep;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    public Map<String,Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("logradouro", logradouro);
        result.put("numero", numero);
        result.put("complemento", complemento);
        result.put("cep", cep);
        result.put("latitude", latitude);
        result.put("longitude", longitude);

        return result;
    }
}
