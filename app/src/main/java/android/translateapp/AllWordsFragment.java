package android.translateapp;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import ben.translateapp.R;


public class AllWordsFragment extends Fragment {

    private SimpleAdapter simpleAdapter;
    //arraylist with nested hashmap with a key-value pair with 2 strings (each item has 2 keys en 2values: key & value nl en key & value fr)
    private List<HashMap<String, String>> listItems = new ArrayList<>();
    private HashMap<String, String> newWord = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final SharedPreferences userSettings = this.getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
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
                    Words word = data.getValue(Words.class);

                    String wordKey = data.getKey();
                    // make a new hashmap to put in the key(these keys we use in the simpleAdapter) and values(words from the database)
                    HashMap<String, String> wordMap = new HashMap<>();

                    // get the french and the dutchword per item (key, value) and put them in the HashMap named wordMap
                    if (word.FrenchWord != null)
                    {
                        wordMap.put("French", word.FrenchWord);
                        wordMap.put("Dutch", word.DutchWord);
                        wordMap.put("WordKey", wordKey);

                        // add the wordMap in the list named listItems that expects a hashmap
                        listItems.add(wordMap);
                    }
                   }
                // A new word has been added, add it to the displayed list
                simpleAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // loop through database
                for (DataSnapshot data: dataSnapshot.getChildren()) {
                    // make new instance of the word class to work with the variables
                    Words word = data.getValue(Words.class);

                    String wordKey = data.getKey();
                    // get the french and the dutchword per item (key, value) and put them in the HashMap named wordMap
                    newWord.put("French", word.FrenchWord);
                    newWord.put("Dutch", word.DutchWord);
                    newWord.put("WordKey", wordKey);

                    if(userSettings.getBoolean("Notifications", true) == true) {
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(getContext())
                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.logo))
                                        .setSmallIcon(R.drawable.logo)
                                        .setContentTitle("TranslateApp zegt:")
                                        .setContentText("Het woord " + word.FrenchWord + " werd aan de lijst toegevoegd!");
                        NotificationManager manager = ( NotificationManager ) getActivity().getSystemService( getActivity().NOTIFICATION_SERVICE );
                        manager.notify(0, builder.build());
                    }

                }
                // add the lastAdded word in the list named listItems that expects a hashmap
                listItems.add(newWord);

                // A new word has been added, add it to the displayed list
                simpleAdapter.notifyDataSetChanged();
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

        // initialize the simpleadapter (needs 5 arguments: current instance, string with all the items(listitems),
        // xml layout resource file where to put these items, keys from the hashmap and last the id's from the textviews to populate them
        simpleAdapter = new SimpleAdapter(getActivity(), listItems, R.layout.fragment_all_words,
                                                        new String[]{"French", "Dutch"},
                                                        new int[] {R.id.all_words_french, R.id.all_words_dutch});

        View rootview = inflater.inflate(R.layout.fragment_main_activity_tab, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        final ListView listView = (ListView) rootview.findViewById(R.id.tab_listview);
        listView.setAdapter(simpleAdapter);

        // click on item, go to detail activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Words word = new Words();
                // get hashmap of the word that was clicked
                HashMap h = listItems.get(position);

                // get values out of the hashmap by the key
                String fr = (String) h.get("French");
                String nl = (String) h.get("Dutch");
                String wordKey = (String) h.get("WordKey");

                // new intent, pass fr and nl word
                Intent i = new Intent(getActivity(), WordDetailActivity.class);
                i.putExtra("FRENCH_WORD", fr);
                i.putExtra("DUTCH_WORD", nl);
                i.putExtra("WORDKEY", wordKey);
                i.putExtra("COUNTWORDS", word.Countwords);

                // start detail activity
                startActivity(i);
            }
        });

        return rootview;
    }

}
