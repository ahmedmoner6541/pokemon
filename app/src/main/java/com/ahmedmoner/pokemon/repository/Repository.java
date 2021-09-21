package com.ahmedmoner.pokemon.repository;

import androidx.lifecycle.LiveData;

import com.ahmedmoner.pokemon.db.PokemonDao;
import com.ahmedmoner.pokemon.model.Pokemon;
import com.ahmedmoner.pokemon.model.PokemonResponse;
import com.ahmedmoner.pokemon.network.PokimonApi;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;


// TODO: 9/19/2021 it is supposed to contain 1-daw ->room  2-Api Servise

// TODO: 9/20/2021 من المهم عدم معرفه الفيو موديل لقواعد البيانت
public class Repository {

    private PokimonApi pokimonApiservise;
    private PokemonDao pokemonDao;

    @Inject
    // TODO: 9/19/2021 كده انا فهمت داجار لما تحتتاجي اوبجكتت من الريبوزيتوري خشي هاتيه انتي علطول
    public Repository(PokimonApi pokimonApiservise, PokemonDao pokemonDao) {
        this.pokimonApiservise = pokimonApiservise;
        this.pokemonDao = pokemonDao;
    }

    public Observable<PokemonResponse> getpokemons() {
        return pokimonApiservise.getpokemons();
    }

    public void insertPokemon(Pokemon pokemon) {
        pokemonDao.insertPokemon(pokemon);
    }
    public void deletPokemon(String pokemonName ){
        pokemonDao.deletePokemon(pokemonName);
    }
    public LiveData<List<Pokemon>> getFavoritePokemon(){
        return pokemonDao.getPokemons();
    }
}
