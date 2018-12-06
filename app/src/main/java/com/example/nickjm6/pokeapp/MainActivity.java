package com.example.nickjm6.pokeapp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private String[] pokemon = {"Pikachu", "Bulbasaur", "Eevee", "Eeves"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<String> adapterPokemon = new ArrayAdapter<>
                (this, android.R.layout.select_dialog_item, pokemon);
        AutoCompleteTextView actv = findViewById(R.id.pokemon);
        actv.setThreshold(1);
        actv.setAdapter(adapterPokemon);
        actv.setTextColor(Color.RED);

        int currentGen = getResources().getInteger(R.integer.currentGen);
        String[] generationArr = getGenArr(currentGen);
        ArrayAdapter<String> adapterGeneration = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item , generationArr);
        Spinner spinner = findViewById(R.id.generation);

        adapterGeneration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterGeneration);
    }

    private String[] getGenArr(int currentGen){
        String[] result = new String[currentGen + 1];
        result[0] = "Generation";
        for(int i = 1; i <= currentGen; i++)
            result[i] = String.valueOf(i);
        return result;
    }
}
