package br.com.bemypet.bemypet.enums;

/**
 * Created by kassi on 23/07/16.
 */
public enum StatusAdocao {

    DISPONIVEL("Disponível"),
    EM_ANDAMENTO("Em Andamento"),
    ADOTADO("Adotado");

    private String friendlyName;

    private StatusAdocao(String friendlyName){
        this.friendlyName = friendlyName;
    }

    @Override
    public String toString(){
        return friendlyName;
    }
}
