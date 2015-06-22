package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;


public class SettingsChampActivity extends ActionBarActivity {

    private RadioButton about,returno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_champ);

        about = (RadioButton) findViewById(R.id.radio_champ_turn);
        returno = (RadioButton) findViewById(R.id.radio_champ_return);


    }

    public void ReturnMyChamp(View view){
        boolean turnCompetition = about.isChecked();
        boolean returnCompetition = returno.isChecked();

        if(turnCompetition || returnCompetition){
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);

        }


        else {
            Toast.makeText(this, R.string.select_competition, Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_typeof_competition, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
