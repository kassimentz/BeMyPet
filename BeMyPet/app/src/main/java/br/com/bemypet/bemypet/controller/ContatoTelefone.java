package br.com.bemypet.bemypet.controller;

import br.com.bemypet.bemypet.model.Usuario;

/**
 * Created by Kassi on 30/07/16.
 */
public class ContatoTelefone {

    private Usuario remetente;
    private Usuario destinario;

    public ContatoTelefone() {
    }

    public ContatoTelefone(Usuario remetente, Usuario destinario) {
        this.remetente = remetente;
        this.destinario = destinario;

        //chamar a api do android que faz a ligacao passando o telefone de quem liga e quem recebe
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public void setRemetente(Usuario remetente) {
        this.remetente = remetente;
    }

    public Usuario getDestinario() {
        return destinario;
    }

    public void setDestinario(Usuario destinario) {
        this.destinario = destinario;
    }
}
