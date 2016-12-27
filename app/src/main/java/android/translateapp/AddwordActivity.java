package android.translateapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ben.translateapp.R;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddwordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addword);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add word");

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            //Als de 'back' item is geklikt dan wordt deze intent gesloten
            this.finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public static boolean empty( final String s ) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    }

    public void addWord() {
        SharedPreferences userSettings = getSharedPreferences("UserPreferences", MODE_PRIVATE);

        //Geklikt op add Word button

        TextView mNederlands;
        TextView mFrans;
        String sNederlands;
        String sFrans;
        String userID;

        //Variabelen ophalen van de textviews
        mNederlands = (TextView)findViewById(R.id.dutchWord);
        mFrans = (TextView)findViewById(R.id.frenchWord);

        sNederlands = mNederlands.getText().toString();
        sFrans = mFrans.getText().toString();

        if(!empty(sNederlands) && !empty(sFrans)){
            //Connectie maken met de FirebaseDatabase
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference();

            SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
            userID = userSettings.getString("UserName", "");

            //Klasse woord aanmaken
            Words oneWord = new Words(sNederlands, sFrans, userID);

            //Woord toevoegen aan de database
            ref.child("words").push().setValue(oneWord);

            Toast.makeText(this, "Woord opgeslagen!", Toast.LENGTH_SHORT).show();

            //Leegmaken van de velden
            mNederlands.setText("");
            mFrans.setText("");
        } else {
            Toast.makeText(this, "Gelieve beide velden in te vullen!", Toast.LENGTH_LONG).show();
        }
    }

    public void onClick(View v) {
        addWord();


    }

}
