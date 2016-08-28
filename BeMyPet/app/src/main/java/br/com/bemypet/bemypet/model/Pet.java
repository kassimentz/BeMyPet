package br.com.bemypet.bemypet.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<String> imagens;
    private Usuario dono;
    private String statusAdocao;
    private String castrado;

    public Pet() {
        imagens = new ArrayList<>();
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

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens;
    }

    public Usuario getDono() {
        return dono;
    }

    public void setDono(Usuario dono) {
        this.dono = dono;
    }

    public String getStatusAdocao() {
        return statusAdocao;
    }

    public void setStatusAdocao(String statusAdocao) {
        this.statusAdocao = statusAdocao;
    }

    public String getCastrado() {
        return castrado;
    }

    public void setCastrado(String castrado) {
        this.castrado = castrado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addImage(String img){
        if(imagens == null){
            imagens = new ArrayList<>();
        }
        this.imagens.add(img);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", especie='" + especie + '\'' +
                ", idadeAproximade=" + idadeAproximade +
                ", pesoAproximado=" + pesoAproximado +
                ", sexo='" + sexo + '\'' +
                ", raca='" + raca + '\'' +
                ", saude='" + saude + '\'' +
                ", temperamento='" + temperamento + '\'' +
                ", sociavelCom='" + sociavelCom + '\'' +
                ", historico='" + historico + '\'' +
                ", cadastroAtivo=" + cadastroAtivo +
                ", doador=" + doador +
                ", adotante=" + adotante +
                ", imagens=" + imagens +
                ", dono=" + dono +
                ", statusAdocao=" + statusAdocao +
                ", castrado=" + castrado +
                '}';
    }


    public Map<String,Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("nome", nome);
        result.put("especie", especie);
        result.put("idadeAproximade", idadeAproximade);
        result.put("pesoAproximado", pesoAproximado);
        result.put("sexo", sexo);
        result.put("raca", raca);
        result.put("saude", saude);
        result.put("temperamento", temperamento);
        result.put("sociavelCom", sociavelCom);
        result.put("historico", historico);
        result.put("cadastroAtivo", cadastroAtivo);
        result.put("doador", doador.toMap());
        result.put("adotante", adotante.toMap());
        result.put("imagens", imagens);
        result.put("dono", dono.toMap());
        result.put("statusAdocao", statusAdocao);
        result.put("castrado", castrado);

        return result;
    }
}
