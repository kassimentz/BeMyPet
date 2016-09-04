package br.com.bemypet.bemypet.controller;

import br.com.bemypet.bemypet.model.Usuario;

//TODO 1.1
/**
 * Created by Kassi on 30/07/16.
 */
public class ContatoEmail {

    private Usuario remetente;
    private Usuario destinatario;
    private String mensagem;

    public ContatoEmail() {
    }

    public ContatoEmail(Usuario remetente, Usuario destinatario, String mensagem) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.mensagem = mensagem;

        //chamar a api de enviar mensagem do android
    }

    public Usuario getRemetente() {
        return remetente;
    }

    public void setRemetente(Usuario remetente) {
        this.remetente = remetente;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
