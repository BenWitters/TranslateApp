package android.translateapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.StringTokenizer;

import ben.translateapp.R;

public class AllWordsFragment extends Fragment {

    private SimpleAdapter simpleAdapter;
    //arraylist with nested hashmap with a key-value pair with 2 strings (each item has 2 keys en 2values: key & value nl en key & value fr)
    private List<HashMap<String, String>> listItems = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

                    // make a new hashmap to put in the key(these keys we use in the simpleAdapter) and values(words from the database)
                    HashMap<String, String> wordMap = new HashMap<>();

                    // get the french and the dutchword per item (key, value) and put them in the HashMap named wordMap
                    wordMap.put("French", word.FrenchWord);
                    wordMap.put("Dutch", word.DutchWord);

                    // add the wordMap in the list named listItems that expects a hashmap
                    listItems.add(wordMap);

                }
                // A new word has been added, add it to the displayed list
                simpleAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                //Comment newComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                String commentKey = dataSnapshot.getKey();

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
        ListView listView = (ListView) rootview.findViewById(R.id.tab_listview);
        listView.setAdapter(simpleAdapter);


        return rootview;
    }

}
