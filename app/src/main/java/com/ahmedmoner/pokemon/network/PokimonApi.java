package com.ahmedmoner.pokemon.network;

import com.ahmedmoner.pokemon.model.PokemonResponse;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface PokimonApi {
    @GET("pokemon")
    Observable<PokemonResponse> getpokemons();
}
