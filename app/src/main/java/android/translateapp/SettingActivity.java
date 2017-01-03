package android.translateapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ben.translateapp.R;

public class SettingActivity extends AppCompatActivity {
    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Settings");

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get ListView object from xml


        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data
        SharedPreferences userSettings = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        Log.v("E_VALUE", userSettings.getString("UserName", ""));
        RadioGroup radioGroup;
        radioGroup = (RadioGroup) findViewById(R.id.rdbGroup);


        if(userSettings.getString("UserName","") == "1")
        {
            radioGroup.check(R.id.rdbUserOne);
        }
        else if(userSettings.getString("UserName","") == "2")
        {
            radioGroup.check(R.id.rdbUserTwo);
        }
        else
        {
            radioGroup.check(R.id.rdbUserOne);
        }


        CheckBox checkBoxGroup;
        checkBoxGroup = (CheckBox) findViewById(R.id.chkNotification);


        if(userSettings.getBoolean("Notifications", true) == true)
        {
            checkBoxGroup.setChecked(true);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            //Als de 'back' item is geklikt dan wordt deze intent gesloten
            this.finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void saveSettings() {
        RadioGroup radioGroup;
        radioGroup = (RadioGroup) findViewById(R.id.rdbGroup);
        // get selected radio button from radioGroup
        int selectedId = radioGroup.getCheckedRadioButtonId();
        String selectedChk;
        if(selectedId == 2131492986)
        {
            selectedChk = "1";
        }
        else
        {
            selectedChk = "2";
        }


        CheckBox checkBox = (CheckBox) findViewById(R.id.chkNotification);
        Boolean notifications = false;
        if(checkBox.isChecked()){
            notifications = true;
        }


       SharedPreferences userSettings = getSharedPreferences("UserPreferences", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = userSettings.edit();
        prefEditor.putString("UserName", selectedChk);
        prefEditor.putBoolean("Notifications", notifications);
        prefEditor.apply();
        this.finish();
    }

    public void onClick(View v) {
         saveSettings();
    }

}
