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

    private void queryPokemon(String pokename){
        Spinner genDropdown = findViewById(R.id.generation);
        String generation = genDropdown.getSelectedItem().toString();
        String endpoint = "pokemon";
        RequestParams params = new RequestParams();
        params.add("name", pokename);
        params.add("generation", generation);
        HttpRequest.get(serverAddress, endpoint, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try{
                    Log.d("Type 1", response.getString("type1"));
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try{
                    Log.e("PokeQuery", errorResponse.getString("message"));
                } catch (JSONException e){
                    Log.e("PokeQuery", "Error Querying Pokemon");
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
