package com.example.nickjm6.pokeapp;

import android.content.Context;
import android.graphics.Color;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonToken;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private String serverAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitOnEnterTextView();
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

    private void submitOnEnterTextView(){
        final TextView editText = findViewById(R.id.pokemon);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    queryPokemon(String.valueOf(editText.getText()));
                    return true;
                }
                return false;
            }
        });
    }

    private void queryPokemon(final String pokename){
        Spinner genDropdown = findViewById(R.id.generation);
        String generation = genDropdown.getSelectedItem().toString();
        String endpoint = "pokemon";
        final RequestParams params = new RequestParams();
        params.add("name", pokename);
        params.add("generation", generation);
        HttpRequest.get(serverAddress, endpoint, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    Pokemon pokemon = new Pokemon();
                    pokemon.setName(response.getString("name"));
                    pokemon.setNationalNumber(response.getInt("number"));
                    pokemon.setType1(response.getString("type1"));
                    try{
                        pokemon.setType2(response.getString("type2"));
                    } catch (JSONException e){ }
                    try{
                        pokemon.setEvolvesInto(response.getJSONArray("evolvesInto").getJSONObject(0).getString("name"));
                    } catch (JSONException e){}
                    try{
                        pokemon.setEvolvesFrom(response.getJSONObject("evolvesFrom").getString("name"));
                    } catch(JSONException e){}
                    JSONArray weaknesses = response.getJSONArray("weaknesses");
                    for(int i = 0; i < weaknesses.length(); i++)
                        pokemon.addWeakness(weaknesses.getString(i));
                    JSONArray superWeaknesses = response.getJSONArray("superweaknesses");
                    for(int i = 0; i < superWeaknesses.length(); i++)
                        pokemon.addSuperWeakness(superWeaknesses.getString(i));
                    JSONArray resistances = response.getJSONArray("resistances");
                    for(int i = 0; i < resistances.length(); i++)
                        pokemon.addResistance(resistances.getString(i));
                    JSONArray superResistances = response.getJSONArray("superresistances");
                    for(int i = 0; i < superResistances.length(); i++)
                        pokemon.addSuperResistance(superResistances.getString(i));
                    JSONArray unaffeccted = response.getJSONArray("unaffected");
                    for(int i = 0; i < unaffeccted.length(); i++)
                        pokemon.addUnaffected(unaffeccted.getString(i));
                    Log.d("Pokemon", pokemon.toString());
                    TextView textView = findViewById(R.id.pokemonResponse);
                    textView.setText(pokemon.toString());
                    textView.setTextColor(Color.BLACK);
                } catch (JSONException e){
                    TextView textView = findViewById(R.id.pokemonResponse);
                    textView.setText("Error parsing response");
                    textView.setTextColor(Color.RED);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                TextView textView = findViewById(R.id.pokemonResponse);
                try{
                    Log.e("PokeQuery", errorResponse.getString("message"));
                    textView.setText(errorResponse.getString("message"));
                    textView.setTextColor(Color.RED);

                } catch (JSONException e){
                    Log.e("PokeQuery", "Error Querying Pokemon");
                    textView.setText("Error Querying Pokemon");
                    textView.setTextColor(Color.RED);
                }
            }
        });
    }

    private void getPokeList(final Context context){
        String endpoint = "pokemonList";
        HttpRequest.get(serverAddress, endpoint, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray arr = response.getJSONArray("pokelist");
                    String[] pokemon = new String[arr.length()];
                    for(int i = 0; i < arr.length(); i++)
                        pokemon[i] = arr.getString(i);
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
