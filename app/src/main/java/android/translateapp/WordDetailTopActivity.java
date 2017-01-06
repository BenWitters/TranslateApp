package android.translateapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ben.translateapp.R;

public class WordDetailTopActivity extends AppCompatActivity {

    //arraylist with nested hashmap with a key-value pair with 2 strings (each item has 2 keys en 2values: key & value nl en key & value fr)
    private List<HashMap<String, String>> listItems = new ArrayList<>();
    private HashMap<String, String> newWord = new HashMap<>();


    private Boolean alreadyVote = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_detailtop);


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
        final String wordkey = i.getExtras().getString("WORDKEY");

        //set the text of the textview equal to the item that was clicked
        frenchWord.setText(french);
        dutchWord.setText(dutch);
        wordKey.setText(wordkey);
        SharedPreferences userSettings = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        final String userID = userSettings.getString("UserName", "");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
         DatabaseReference ref = database.getReference();
        // database connection
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("TRANSLATE","New value detected, snapshot children: " + Long.toString(dataSnapshot.child("words").getChildrenCount()) );

                for (DataSnapshot postSnapshot : dataSnapshot.child("votes").getChildren()) {
                    List<String> listSubWords = new ArrayList<String>();
                    //Getting the data from snapshot
                    Votes votes = postSnapshot.getValue(Votes.class);

                    if(votes.UserID.equals(userID)){
                        if(votes.WordID.equals(wordkey))
                        {
                            alreadyVote = true;
                        }
                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
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
                shareIntent.putExtra(Intent.EXTRA_TEXT, frenchWord.getText().toString() + " betekent " + dutchWord.getText().toString() + " - vanuit TranslateApp");
                startActivity(Intent.createChooser(shareIntent, "Deel woord via:"));

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(menuItem);

        }
    }

}
