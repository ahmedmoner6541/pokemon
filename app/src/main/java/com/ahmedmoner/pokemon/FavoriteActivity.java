package com.ahmedmoner.pokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ahmedmoner.pokemon.adapters.PokemonAdapter;
import com.ahmedmoner.pokemon.databinding.ActivityFavoriteBinding;
import com.ahmedmoner.pokemon.model.Pokemon;
import com.ahmedmoner.pokemon.viewmodels.PokemonViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoriteActivity extends AppCompatActivity {
    private PokemonViewModel viewModel;
    private RecyclerView recyclerView;
    private PokemonAdapter adapter;
    ActivityFavoriteBinding  activityFavoriteBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        activityFavoriteBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);

        recyclerView = findViewById(R.id.pokemon_recyclerView_fav );
        adapter = new PokemonAdapter(this);
        recyclerView.setAdapter(adapter);
        setupSwipe();


        activityFavoriteBinding.btnFavToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FavoriteActivity.this, MainActivity.class));

            }
        });
//        Button tohome = findViewById(R.id.btnFavToHome);
//
//        tohome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(FavoriteActivity.this, MainActivity.class));
//            }
//        });
        viewModel = new ViewModelProvider(this).get(PokemonViewModel.class);

        viewModel.getFavoritePokemon();

        viewModel.getFavList().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(List<Pokemon> pokemons) {
                ArrayList<Pokemon> list = new ArrayList<>();
                list.addAll(pokemons);//حول من لستت لاراي ليست
                adapter.setList(list);
            }
        });

    }


        public void setupSwipe() {
            ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    // getposation item in recycler view
                    int swipePokeposation = viewHolder.getAdapterPosition();

                    Pokemon swipwPokemon = adapter.getpokemonAt(swipePokeposation);

                    viewModel.deletPokemon(swipwPokemon.getName());
                    Toast.makeText(FavoriteActivity.this, "pokemon daleted from database", Toast.LENGTH_SHORT).show();

                    adapter.notifyDataSetChanged();
                }
            };
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }

}