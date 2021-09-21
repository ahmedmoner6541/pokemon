package com.ahmedmoner.pokemon.di;

import android.app.Application;

import androidx.room.Room;

import com.ahmedmoner.pokemon.db.PokemonDao;
import com.ahmedmoner.pokemon.db.PokemonDb;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)

public class DatabaseModule {
    //db builder

    @Provides
    @Singleton
    public static PokemonDb pokemonDb(Application application){//context
        return Room.databaseBuilder(application,PokemonDb.class,"fav_table")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()//هنستخدمها في مييين ثريد لو هنستخدم ار ا كس نستخدمها هنا
                .build();
    }

    @Provides
    @Singleton
    public static PokemonDao proviedDao(PokemonDb pokemonDb){
        return pokemonDb.pokemonDao();
    }

}
