package android.translateapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ben.translateapp.R;


public class TopWordsFragment extends Fragment {

    private SimpleAdapter simpleAdapter;
    //arraylist with nested hashmap with a key-value pair with 2 strings (each item has 2 keys en 2values: key & value nl en key & value fr)
    private List<HashMap<String, String>> listItems = new ArrayList<>();
    private HashMap<String, String> newWord = new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            SharedPreferences userSettings = this.getActivity().getSharedPreferences("UserPreferences", Context.MODE_PRIVATE);
          final String userID = userSettings.getString("UserName", "");
        // database connection

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        // get data from database
        ChildEventListener getItems = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                listItems.clear();
                newWord.clear();
                // loop through database
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    // make new instance of the word class to work with the variables
                    Words word = data.getValue(Words.class);
                    String wordKey = data.getKey();
                    // make a new hashmap to put in the key(these keys we use in the simpleAdapter) and values(words from the database)
                    HashMap<String, String> wordMap = new HashMap<>();
                    //  String userID = userSettings.getString("UserName", "");
                    // get the french and the dutchword per item (key, value) and put them in the HashMap named wordMap

                    if(word.UserID.equals(userID))
                    {
                        wordMap.put("French", word.FrenchWord);
                        wordMap.put("Dutch", word.DutchWord);
                        wordMap.put("WordKey", wordKey);
                        listItems.add(wordMap);
                    }

                    // add the wordMap in the list named listItems that expects a hashmap


                }
                // A new word has been added, add it to the displayed list
                simpleAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                listItems.clear();
                newWord.clear();
                // loop through database
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Words word = data.getValue(Words.class);

                    String wordKey = data.getKey();


                    // get the french and the dutchword per item (key, value) and put them in the HashMap named wordMap
                    if(word.UserID.equals(userID)) {
                        newWord.put("French", word.FrenchWord);
                        newWord.put("Dutch", word.DutchWord);
                        newWord.put("WordKey", wordKey);
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
        simpleAdapter = new SimpleAdapter(getActivity(), listItems, R.layout.fragment_top_words,
                new String[]{"French", "Dutch"},
                new int[]{R.id.top_words_french, R.id.top_words_dutch});

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

                // start detail activity
                startActivity(i);
            }
        });

        return rootview;

    }
}
