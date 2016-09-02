package br.com.bemypet.bemypet.controller;

import java.util.Date;

import br.com.bemypet.bemypet.model.Usuario;

//TODO 1.1
/**
 * Created by Kassi on 30/07/16.
 */
public class DenunciarAnuncio {

    private Usuario denunciante;
    private Usuario denunciado;
    private Date dataDenuncia;
    private String motivo;

    public DenunciarAnuncio(){

    }

    public DenunciarAnuncio(Usuario denunciante, Usuario denunciado, Date dataDenuncia, String motivo) {
        this.denunciante = denunciante;
        this.denunciado = denunciado;
        this.dataDenuncia = dataDenuncia;
        this.motivo = motivo;
    }

    public Usuario getDenunciante() {
        return denunciante;
    }

    public void setDenunciante(Usuario denunciante) {
        this.denunciante = denunciante;
    }

    public Usuario getDenunciado() {
        return denunciado;
    }

    public void setDenunciado(Usuario denunciado) {
        this.denunciado = denunciado;
    }

    public Date getDataDenuncia() {
        return dataDenuncia;
    }

    public void setDataDenuncia(Date dataDenuncia) {
        this.dataDenuncia = dataDenuncia;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
