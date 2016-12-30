package android.translateapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ben.translateapp.R;

public class AllWordsFragment extends Fragment {

    private ArrayAdapter<String> mAllWordsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] data = {
                "all words",
                "all words",
                "all words",
                "all words"
        };
        List<String> allWordsList = new ArrayList<String>(Arrays.asList(data));

        // Now that we have some dummy forecast data, create an ArrayAdapter.
        // The ArrayAdapter will take data from a source and use it to populate the ListView it's attached to.
        mAllWordsAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.fragment_all_words, // The name of the layout ID.
                        R.id.all_words, // The ID of the textview to populate.
                        allWordsList);

        View rootview = inflater.inflate(R.layout.fragment_main_activity_tab, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootview.findViewById(R.id.tab_listview);
        listView.setAdapter(mAllWordsAdapter);

        return rootview;
    }

}
