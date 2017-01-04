package android.translateapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ben.translateapp.R;

public class WordDetailActivity extends AppCompatActivity {

    //arraylist with nested hashmap with a key-value pair with 2 strings (each item has 2 keys en 2values: key & value nl en key & value fr)
    private List<HashMap<String, String>> listItems = new ArrayList<>();
    private HashMap<String, String> newWord = new HashMap<>();

    private ShareActionProvider mShareActionProvider;

    // SharedPreferences userSettings = getSharedPreferences("UserPreferences", MODE_PRIVATE);
    // private String userID = userSettings.getString("UserName", "");
    private String userID = "1";
    private Boolean alreadyVote = false;
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


        // database connection
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        // get data from database
        ChildEventListener getItems = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                // loop through database
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    // make new instance of the word class to work with the variables
                    Votes vote = data.getValue(Votes.class);

                    String wordKey = data.getKey();
                    // make a new hashmap to put in the key(these keys we use in the simpleAdapter) and values(words from the database)
                    HashMap<String, String> wordMap = new HashMap<>();

                    // get the french and the dutchword per item (key, value) and put them in the HashMap named wordMap
                    if(vote.UserID.equals(userID)) {
                        if(data.getKey().equals(vote.WordID))
                        {
                            alreadyVote = true;
                        }
                    }
                    Log.v("E_VALUE", alreadyVote.toString());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // loop through database
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    // make new instance of the word class to work with the variables
                    Votes vote = data.getValue(Votes.class);

                    String wordKey = data.getKey();
                    // get the french and the dutchword per item (key, value) and put them in the HashMap named wordMap
                    if(vote.UserID.equals(userID)) {
                        if(data.getKey().equals(vote.WordID))
                        {
                            alreadyVote = true;
                        }
                    }

                    Log.v("E_VALUE", alreadyVote.toString());
                }
                // add the lastAdded word in the list named listItems that expects a hashmap
                listItems.add(newWord);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {


            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addChildEventListener(getItems);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_word_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        /*if (menuItem.getItemId() == android.R.id.home) {
            this.finish();
        }*/

        int id = menuItem.getItemId();

        //Checken op welk element de gebruiker geklikt heeft in de menutab
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                this.finish();

            case R.id.action_share:
                // De gebruiker heeft op share geklikt
                TextView frenchWord = (TextView) findViewById(R.id.detail_word_french);
                TextView dutchWord = (TextView) findViewById(R.id.detail_word_dutch);

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, frenchWord.getText().toString() + " betekent " + dutchWord.getText().toString() + " - vanuit TranslateApp");
                startActivity(Intent.createChooser(shareIntent, "Deel woord via:"));

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(menuItem);

        }
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
