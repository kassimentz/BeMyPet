package br.com.bemypet.bemypet.api;

/**
 * Created by Cassio on 11/08/16.
 */
public class StringUtils {

    public static final boolean isNullOrEmpty(String str){
        return str == null ||  str.length() == 0;
    }
}
