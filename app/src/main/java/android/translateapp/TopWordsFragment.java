package android.translateapp;

import android.content.Context;
import android.net.Uri;
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


public class TopWordsFragment extends Fragment {

    private ArrayAdapter<String> mTopWordsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] data = {
                "top words",
                "top words",
                "top words",
                "top words"
        };
        List<String> topWordsList = new ArrayList<String>(Arrays.asList(data));

        // The ArrayAdapter will take data from a source  and use it to populate the ListView it's attached to.
        mTopWordsAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.fragment_top_words, // The name of the layout ID.
                        R.id.top_words, // The ID of the textview to populate.
                        topWordsList);

        View rootView = inflater.inflate(R.layout.fragment_main_activity_tab, container, false);

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) rootView.findViewById(R.id.tab_listview);
        listView.setAdapter(mTopWordsAdapter);

        return rootView;
    }

}
