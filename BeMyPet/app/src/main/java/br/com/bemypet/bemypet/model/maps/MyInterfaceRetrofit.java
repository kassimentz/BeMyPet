package br.com.bemypet.bemypet.model.maps;

import retrofit2.Call;

import br.com.bemypet.bemypet.model.maps.Retorno;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kassi on 30/08/16.
 */
public interface MyInterfaceRetrofit {

    @GET("maps/api/directions/json")
    Call<Retorno> searchPositions(
            @Query("origin") String origin,
            @Query("destination") String destination,
            @Query("key") String key
    );
}
