package br.com.bemypet.bemypet.controller;

import br.com.bemypet.bemypet.model.Endereco;
import br.com.bemypet.bemypet.model.Usuario;

/**
 * Created by Kassi on 30/07/16.
 */
public class MontarRota {

    private Usuario origem;
    private Usuario destino;

    public MontarRota() {
    }

    public MontarRota(Usuario origem, Usuario destino) {
        this.origem = origem;
        this.destino = destino;

        //utilizar os enderecos dos usuarios para montar a rota
        calcularRota(origem.getEndereco(), origem.getEndereco());
    }

    public void calcularRota(Endereco origem, Endereco destino){

    }

    public Usuario getOrigem() {
        return origem;
    }

    public void setOrigem(Usuario origem) {
        this.origem = origem;
    }

    public Usuario getDestino() {
        return destino;
    }

    public void setDestino(Usuario destino) {
        this.destino = destino;
    }
}
