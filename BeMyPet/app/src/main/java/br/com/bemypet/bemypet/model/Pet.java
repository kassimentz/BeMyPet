package br.com.bemypet.bemypet.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import br.com.bemypet.bemypet.enums.StatusAdocao;

/**
 * Created by Kassi on 30/07/16.
 */
public class Pet implements Serializable{

    private String id;
    private String nome;
    private String especie;
    private Integer idadeAproximade;
    private Double pesoAproximado;
    private String sexo;
    private String raca;
    private String saude;
    private String temperamento;
    private String sociavelCom;
    private String historico;
    private Boolean cadastroAtivo;
    private Usuario doador;
    private Usuario adotante;
    private List<Uri> imagens;
    private Usuario dono;
    private StatusAdocao statusAdocao;
    private Boolean castrado;

    public Pet() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public Integer getIdadeAproximade() {
        return idadeAproximade;
    }

    public void setIdadeAproximade(Integer idadeAproximade) {
        this.idadeAproximade = idadeAproximade;
    }

    public Double getPesoAproximado() {
        return pesoAproximado;
    }

    public void setPesoAproximado(Double pesoAproximado) {
        this.pesoAproximado = pesoAproximado;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSaude() {
        return saude;
    }

    public void setSaude(String saude) {
        this.saude = saude;
    }

    public String getTemperamento() {
        return temperamento;
    }

    public void setTemperamento(String temperamento) {
        this.temperamento = temperamento;
    }

    public String getSociavelCom() {
        return sociavelCom;
    }

    public void setSociavelCom(String sociavelCom) {
        this.sociavelCom = sociavelCom;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public Boolean getCadastroAtivo() {
        return cadastroAtivo;
    }

    public void setCadastroAtivo(Boolean cadastroAtivo) {
        this.cadastroAtivo = cadastroAtivo;
    }

    public Usuario getDoador() {
        return doador;
    }

    public void setDoador(Usuario doador) {
        this.doador = doador;
    }

    public Usuario getAdotante() {
        return adotante;
    }

    public void setAdotante(Usuario adotante) {
        this.adotante = adotante;
    }

    public List<Uri> getImagens() {
        return imagens;
    }

    public void setImagens(List<Uri> imagens) {
        this.imagens = imagens;
    }

    public Usuario getDono() {
        return dono;
    }

    public void setDono(Usuario dono) {
        this.dono = dono;
    }

    public StatusAdocao getStatusAdocao() {
        return statusAdocao;
    }

    public void setStatusAdocao(StatusAdocao statusAdocao) {
        this.statusAdocao = statusAdocao;
    }

    public Boolean getCastrado() {
        return castrado;
    }

    public void setCastrado(Boolean castrado) {
        this.castrado = castrado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
