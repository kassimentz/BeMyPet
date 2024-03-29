package br.com.bemypet.bemypet.controller;

//TODO 1.1
import br.com.bemypet.bemypet.enums.ConfBuscaCastrado;
import br.com.bemypet.bemypet.enums.ConfBuscaComFoto;
import br.com.bemypet.bemypet.model.Pet;

/**
 * Created by Kassi on 30/07/16.
 */
public class Busca {

    private Integer raioEmKm;
    private String cidade;
    private String estado;
    private String pais;
    private Integer idadeAproximada;
    private String sexo;
    private ConfBuscaCastrado castrado;
    private ConfBuscaComFoto comFoto;
    private String especie;
    private String raca;

    public Busca() {
    }

    public Pet buscar(){
        //verificar os campos setados e realizar a busca
        return null;
    };

    public Integer getRaioEmKm() {
        return raioEmKm;
    }

    public void setRaioEmKm(Integer raioEmKm) {
        this.raioEmKm = raioEmKm;
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

    public Integer getIdadeAproximada() {
        return idadeAproximada;
    }

    public void setIdadeAproximada(Integer idadeAproximada) {
        this.idadeAproximada = idadeAproximada;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public ConfBuscaCastrado getCastrado() {
        return castrado;
    }

    public void setCastrado(ConfBuscaCastrado castrado) {
        this.castrado = castrado;
    }

    public ConfBuscaComFoto getComFoto() {
        return comFoto;
    }

    public void setComFoto(ConfBuscaComFoto comFoto) {
        this.comFoto = comFoto;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}
