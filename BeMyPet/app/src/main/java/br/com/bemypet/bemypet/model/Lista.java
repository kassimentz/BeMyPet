package br.com.bemypet.bemypet.model;

import java.util.Date;

/**
 * Created by Kassi on 30/07/16.
 */
public abstract class Lista {

    private Usuario denunciado;
    private Usuario denunciante;
    private Date data;
    private String motivo;

    public Usuario getDenunciado() {
        return denunciado;
    }

    public void setDenunciado(Usuario denunciado) {
        this.denunciado = denunciado;
    }

    public Usuario getDenunciante() {
        return denunciante;
    }

    public void setDenunciante(Usuario denunciante) {
        this.denunciante = denunciante;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
