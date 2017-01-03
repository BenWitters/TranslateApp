package android.translateapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ben.translateapp.R;

public class WordDetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detail);

        Intent i = this.getIntent();

        setTitle(i.getExtras().getString("FRENCH_WORD"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // get data that was passed
        TextView frenchWord = (TextView) findViewById(R.id.detail_word_french);
        TextView dutchWord = (TextView) findViewById(R.id.detail_word_dutch);
        TextView wordKey = (TextView) findViewById(R.id.detail_word_id);

        // receive data
        String french = i.getExtras().getString("FRENCH_WORD");
        String dutch = i.getExtras().getString("DUTCH_WORD");
        String wordkey = i.getExtras().getString("WORDKEY");

        //set the text of the textview equal to the item that was clicked
        frenchWord.setText(french);
        dutchWord.setText(dutch);
        wordKey.setText(wordkey);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void vote() {
        SharedPreferences userSettings = getSharedPreferences("UserPreferences", MODE_PRIVATE);

        String userID;


        TextView mWordKey;
        String wordKey;

        //Variabelen ophalen van de textviews
        mWordKey = (TextView)findViewById(R.id.detail_word_id);
        wordKey = mWordKey.getText().toString();


            //Connectie maken met de FirebaseDatabase
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference();

            userID = userSettings.getString("UserName", "");

            //Klasse woord aanmaken
            Votes vote = new Votes(userID, wordKey);

            //Woord toevoegen aan de database
            ref.child("votes").push().setValue(vote);

            Toast.makeText(this, "Stem uitgebracht!", Toast.LENGTH_SHORT).show();



    }

    public void onClick(View v) {
        vote();
    }
}
