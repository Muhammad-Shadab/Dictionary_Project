package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary.ModelClasses.ApiResponse;
import com.example.dictionary.ModelClasses.Definitions;
import com.example.dictionary.ModelClasses.Meanings;
import com.example.dictionary.ModelClasses.Phonetics;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    TextView textViewText, textViewWord, textViewPartsOfSpeech, textViewDefinition, textViewExample;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
     editText = findViewById(R.id.editText);
        imageView = findViewById(R.id.imageView);
        textViewText = findViewById(R.id.textViewText);
        textViewWord = findViewById(R.id.textViewWord);

        textViewPartsOfSpeech = findViewById(R.id.textViewPartsOfSpeech);
        textViewDefinition = findViewById(R.id.textViewDefinition);
        textViewExample = findViewById(R.id.textViewExample);

        textViewText.setVisibility(View.INVISIBLE);
        textViewWord.setVisibility(View.INVISIBLE);

        textViewPartsOfSpeech.setVisibility(View.INVISIBLE);
        textViewDefinition.setVisibility(View.INVISIBLE);
        textViewExample.setVisibility(View.INVISIBLE);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = editText.getText().toString();
                if (!word.equals(""))
                {
                    process(word);
                }
                else
                {
                    editText.setError("Please enter word");
                    editText.requestFocus();
                }
            }
        });
    }




    private void process(String word) {

        Call<List<ApiResponse>> call = ApiController.getInstance()
                .apiSet()
                .getResponse(word);

        call.enqueue(new Callback<List<ApiResponse>>() {
            @Override
            public void onResponse(Call<List<ApiResponse>> call, Response<List<ApiResponse>> response) {
                if (response.isSuccessful())
                {
                    List<ApiResponse> apiResponses = response.body();
                    String word = apiResponses.get(0).getWord();
                    textViewWord.setText(word);
                    textViewWord.setVisibility(View.VISIBLE);


                    List<Phonetics> phonetics = apiResponses.get(0).getPhonetics();
                    String text = phonetics.get(0).getText();
                    textViewText.setText(text);
                    textViewText.setVisibility(View.VISIBLE);

                    List<Meanings> meanings = apiResponses.get(0).getMeanings();
                    String partOfSpeech = meanings.get(0).getPartOfSpeech();
                    textViewPartsOfSpeech.setText(partOfSpeech);
                    textViewPartsOfSpeech.setVisibility(View.VISIBLE);


                    List<Definitions> definitions = meanings.get(0).getDefinitions();
                    String definition = definitions.get(0).getDefinition();
                    String example = definitions.get(0).getExample();
                    textViewDefinition.setText(definition);
                    textViewDefinition.setVisibility(View.VISIBLE);

                    textViewExample.setText(example);
                    textViewExample.setVisibility(View.VISIBLE);


                }
                else
                {
                    Toast.makeText(getApplicationContext(), "ERRRRRRROORRRR  " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ApiResponse>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERRRRRRROORRRR  " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}