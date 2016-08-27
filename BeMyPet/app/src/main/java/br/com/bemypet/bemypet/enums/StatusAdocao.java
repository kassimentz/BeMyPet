package br.com.bemypet.bemypet.enums;

import java.util.HashMap;
import java.util.Map;

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


    public Map<String,Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();
        result.put("friendlyName", friendlyName);
        return result;
    }
}
