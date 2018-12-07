package com.example.nickjm6.pokeapp;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private String serverAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        serverAddress = this.getResources().getString(R.string.serverAddress);
        getPokeList(this);
        int currentGen = getResources().getInteger(R.integer.currentGen);
        String[] generationArr = getGenArr(currentGen);
        ArrayAdapter<String> adapterGeneration = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item , generationArr);
        Spinner spinner = findViewById(R.id.generation);

        adapterGeneration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterGeneration);
    }

    private void getPokeList(final Context context){
        String endpoint = "pokemonList";
        HttpRequest.get(serverAddress, endpoint, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray("pokelist");
                    String[] pokemon = new String[arr.length()];
                    Log.d("ResStatus","got list");
                    for(int i = 0; i < arr.length(); i++) {
                        Log.d("Pokemmon", arr.getString(i));
                        pokemon[i] = arr.getString(i);
                    }
                    ArrayAdapter<String> adapterPokemon = new ArrayAdapter<String>
                            (context, android.R.layout.select_dialog_item, pokemon);
                    AutoCompleteTextView actv = findViewById(R.id.pokemon);
                    actv.setThreshold(1);
                    actv.setAdapter(adapterPokemon);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    Log.e("List Fail", errorResponse.getString("message"));
                } catch (JSONException e){
                    Log.e("List Response Fail", "Could not retrieve pokelist from server");
                }
            }
        });
    }

    private String[] getGenArr(int currentGen){
        String[] result = new String[currentGen + 1];
        result[0] = "Generation";
        for(int i = 1; i <= currentGen; i++)
            result[i] = String.valueOf(i);
        return result;
    }
}
