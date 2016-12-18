package android.translateapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ben.translateapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("userID", "1");
        editor.commit();
*/

        setContentView(R.layout.activity_main);
    }
}
