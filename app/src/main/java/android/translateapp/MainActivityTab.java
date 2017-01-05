package android.translateapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ben.translateapp.R;

public class MainActivityTab extends AppCompatActivity {


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences userSettings = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = userSettings.edit();
        prefEditor.putString("UserName", "1");
        prefEditor.putBoolean("Notifications", true);
        prefEditor.apply();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);





        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        final Words word = new Words();

        final List<String> listWords = new ArrayList<String>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.v("TRANSLATE","New value detected, snapshot children: " + Long.toString(dataSnapshot.child("words").getChildrenCount()) );

                for (DataSnapshot postSnapshot : dataSnapshot.child("words").getChildren()) {
                    List<String> listSubWords = new ArrayList<String>();
                    //Getting the data from snapshot
                    Words word = postSnapshot.getValue(Words.class);


                        listSubWords.add(postSnapshot.getKey());
                        listSubWords.add(word.UserID);
                        listSubWords.add(word.DutchWord);
                        listSubWords.add(word.FrenchWord);
                        listWords.add(listSubWords.toString());
//                       Log.v("E_VALUE", listSubWords.get(1));
                  //  Log.v("E_VALUE",postSnapshot.getKey());


                }
             //   Log.v("E_VALUE",listWords.toString());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getMessage());
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Checken op welk element de gebruiker geklikt heeft in de menutab
        switch (item.getItemId()) {
            case R.id.action_add_word:
                // De gebruiker heeft op 'Add Word' geklikt

                // Het starten van een nieuwe intent om de Add Word Activity te openen
                Intent intent = new Intent(this, AddwordActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_settings:
                // De gebruiker heeft op settings geklikt
                Intent settingIntent = new Intent(this, SettingActivity.class);
                startActivity(settingIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return the fragment that belongs to the page
            switch(position){
                case 0: return new MyWordsFragment();
                case 1: return new AllWordsFragment();
                case 2: return new TopWordsFragment();
                default: return new MyWordsFragment();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Mijn woorden";
                case 1:
                    return "Alle woorden";
                case 2:
                    return "Top 200";
            }
            return null;
        }


    }


}
