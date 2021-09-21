package com.ahmedmoner.pokemon.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ahmedmoner.pokemon.model.Pokemon;

@Database(entities = Pokemon.class , version = 1 ,exportSchema = false)

public abstract class PokemonDb extends RoomDatabase {

    public abstract PokemonDao pokemonDao();

}
