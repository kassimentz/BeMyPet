package br.com.bemypet.bemypet;

import java.util.List;

import br.com.bemypet.bemypet.enums.Especie;
import br.com.bemypet.bemypet.enums.Sexo;
import br.com.bemypet.bemypet.enums.StatusAdocao;

/**
 * Created by Kassi on 30/07/16.
 */
public abstract class Pet {

    private String nome;
    private Especie especie;
    private Integer idadeAproximade;
    private Double pesoAproximado;
    private Sexo sexo;
    private String raca;
    private String saude;
    private String temperamento;
    private String sociavelCom;
    private String historico;
    private Boolean cadastroAtivo;
    private Doador doador;
    private Adotante adotante;
    private int imagens[];
    private Usuario dono;
    private StatusAdocao statusAdocao;



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setEspecie(Especie especie) {
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

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
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

    public Doador getDoador() {
        return doador;
    }

    public void setDoador(Doador doador) {
        this.doador = doador;
    }

    public Adotante getAdotante() {
        return adotante;
    }

    public void setAdotante(Adotante adotante) {
        this.adotante = adotante;
    }

    public int[] getImagens() {
        return imagens;
    }

    public void setImagens(int[] imagens) {
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
}
