package android.translateapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ben.translateapp.R;

public class WordDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        Intent i = this.getIntent();

        setTitle(i.getExtras().getString("FRENCH_WORD"));


        // get data that was passed
        TextView frenchWord = (TextView) findViewById(R.id.detail_word_french);
        TextView dutchWord = (TextView) findViewById(R.id.detail_word_dutch);

        // receive data
        String french = i.getExtras().getString("FRENCH_WORD");
        String dutch = i.getExtras().getString("DUTCH_WORD");

        //set the text of the textview equal to the item that was clicked
        frenchWord.setText(french);
        dutchWord.setText(dutch);

    }
}
