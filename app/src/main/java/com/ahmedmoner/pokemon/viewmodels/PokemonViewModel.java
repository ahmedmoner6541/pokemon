package com.ahmedmoner.pokemon.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ahmedmoner.pokemon.model.Pokemon;
import com.ahmedmoner.pokemon.model.PokemonResponse;
import com.ahmedmoner.pokemon.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class PokemonViewModel extends ViewModel {
    private static final String TAG = "PokemonViewModel";

    private Repository repository;
    MutableLiveData<ArrayList<Pokemon>> pokemonlist = new MutableLiveData<>();

    private LiveData<List<Pokemon>> favList = null;

    public LiveData<List<Pokemon>> getFavList() {
        return favList;
    }

    public MutableLiveData<ArrayList<Pokemon>> getPokemonlist() {
        return pokemonlist;
    }

    // TODO: 9/19/2021 سبب اضافه هذا الكونستتراكور   عشان اعرف استخدم الكالم ده في الاكتيفتتي وعشان اعرف اباصيله الريبوزيتوري
    // inject   هتاللي احنا ضفناها عل يكونستراكتور الريبوزيتروي هيا الي  8:5
    @ViewModelInject
    public PokemonViewModel(Repository repository) {
        this.repository = repository;
    }

    @SuppressLint("CheckResult")
    public void getpokemons() {
        repository.getpokemons()
                .subscribeOn(Schedulers.io())
                .map(new Function<PokemonResponse, ArrayList<Pokemon>>() {
                    @Override
                    public ArrayList<Pokemon> apply(PokemonResponse pokemonResponse) throws Throwable {
                        ArrayList<Pokemon> list = pokemonResponse.getResults();
                        for (Pokemon pokemon : list) {
                            String url = pokemon.getUrl();
                            String[] pokemonIndex = url.split("/");
                            pokemon.setUrl("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonIndex[pokemonIndex.length - 1] + ".png");
                        }
                        return list;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> pokemonlist.setValue(result),
                        error -> Log.e("viwModel", error.getMessage()));
    }

    public void insertPokemon(Pokemon pokemon) {
        repository.insertPokemon(pokemon);
    }

    public void deletPokemon(String pokemonName) {
        repository.deletPokemon(pokemonName);
    }

    public void getFavoritePokemon() {
        favList = repository.getFavoritePokemon();
    }

}
