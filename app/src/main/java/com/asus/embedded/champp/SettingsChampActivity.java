package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.ExceededCharacterException;
import com.asus.embedded.champp.model.SameNameException;


public class SettingsChampActivity extends ActionBarActivity {
    private View cupLay, leagueLay;
    private RadioButton turn,returno;
    String nameCp, modalCp;
    boolean indivCp, cupCp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_champ);

        cupLay = findViewById(R.id.cup_settings_layout);
        leagueLay = findViewById(R.id.league_settings_layout);

        turn = (RadioButton) findViewById(R.id.radio_champ_turn);
        returno = (RadioButton) findViewById(R.id.radio_champ_return);

        Intent intent = getIntent();
        nameCp = (String) intent.getSerializableExtra("NAMECP");
        modalCp = (String) intent.getSerializableExtra("MODALCP");
        indivCp = (boolean) intent.getSerializableExtra("INDIVCP");
        cupCp = (boolean) intent.getSerializableExtra("CUPCP");

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(cupCp){
            cupLay.setVisibility(View.VISIBLE);
            leagueLay.setVisibility(View.GONE);
        }else{
            leagueLay.setVisibility(View.VISIBLE);
            cupLay.setVisibility(View.GONE);
        }
    }

    public void ReturnMyChamp(View view){

        if (nameCp.trim().equals("")) {
            Toast.makeText(this, R.string.champ_empty_name, Toast.LENGTH_LONG).show();
        } else {
            try {
                ChampionshipController.getInstance(getApplicationContext()).createChampionship(nameCp, modalCp, indivCp, cupCp);
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }catch (EmptyFieldException e) {
                Toast.makeText(this,R.string.field_empty, Toast.LENGTH_LONG).show();
            } catch (SameNameException e) {
                Toast.makeText(this,R.string.same_champ, Toast.LENGTH_LONG).show();
            } catch (ExceededCharacterException e) {
                Toast.makeText(this,R.string.char_exceeded,Toast.LENGTH_LONG).show();
            }

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
