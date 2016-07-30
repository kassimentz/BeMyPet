package br.com.bemypet.bemypet.model;

import br.com.bemypet.bemypet.enums.Operadora;

/**
 * Created by Kassi on 30/07/16.
 */
public class Telefone {

    private Integer ddd;
    private Integer numero;
    private Operadora operadora;

    public Telefone(){

    }

    public Integer getDdd() {
        return ddd;
    }

    public void setDdd(Integer ddd) {
        this.ddd = ddd;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Operadora getOperadora() {
        return operadora;
    }

    public void setOperadora(Operadora operadora) {
        this.operadora = operadora;
    }
}
