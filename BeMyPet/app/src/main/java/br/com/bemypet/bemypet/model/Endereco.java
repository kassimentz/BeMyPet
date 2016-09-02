package br.com.bemypet.bemypet.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import br.com.bemypet.bemypet.api.StringUtils;

/**
 * Created by Kassi on 30/07/16.
 */
public class Endereco implements Serializable{

    private String logradouro;
    private Integer numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private String pais;
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

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Map<String,Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("logradouro", logradouro);
        result.put("numero", numero);
        result.put("complemento", complemento);
        result.put("bairro", bairro);
        result.put("cidade", cidade);
        result.put("estado", estado);
        result.put("pais", pais);
        result.put("cep", cep);
        result.put("latitude", latitude);
        result.put("longitude", longitude);

        return result;
    }

    @Override
    public String toString() {
        String sComplemento = "", sBairro = "", sCidade = "", sEstado = "", sPais = "";

        if(!StringUtils.isNullOrEmpty(complemento)){
            sComplemento = "/" + complemento;
        }

        if(!StringUtils.isNullOrEmpty(bairro)){
            sBairro = " - "+bairro+ ". ";
        }

        if(!StringUtils.isNullOrEmpty(cidade)){
            sCidade = cidade+ ", " ;
        }

        if(!StringUtils.isNullOrEmpty(estado)){
            sEstado = estado+ " / ";
        }

        if(!StringUtils.isNullOrEmpty(pais)){
            sPais = pais;
        }

        return logradouro + "," + numero + sComplemento + sBairro + sCidade + sEstado + sPais;
    }
}
