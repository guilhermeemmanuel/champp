package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.ExceededCharacterException;
import com.asus.embedded.champp.model.SameNameException;

import java.lang.String;


public class NewChampActivity extends ActionBarActivity {
    private final int BASKETBALL = R.string.basketball;
    private final int FOOTBALL = R.string.football;
    private final int FUTSAL = R.string.futsal;
    private final int HANDBALL = R.string.handball;
    private final int TENNIS = R.string.tennis;
    private final int VOLLEY = R.string.volley;

    private EditText nameEt;
    private RadioButton individualRb, groupRb, cupRb, leagueRb;
    private LinearLayout modalLayout, compLayout;
    private ImageView modalIcon;
    private int modal;
    private TextView modalityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_champ);

        nameEt = (EditText) findViewById(R.id.new_champ_name_et);
        modalityText = (TextView) findViewById(R.id.modality_tv);

        individualRb = (RadioButton) findViewById(R.id.radio_champ_individual);
        groupRb = (RadioButton) findViewById(R.id.radio_champ_group);

        modalLayout = (LinearLayout) findViewById(R.id.modality_layout);
        compLayout = (LinearLayout) findViewById(R.id.league_settings_layout);

        cupRb = (RadioButton) findViewById(R.id.radio_champ_cup);
        leagueRb = (RadioButton) findViewById(R.id.radio_champ_league);

        modalIcon = (ImageView) findViewById(R.id.modal_icon);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent i = getIntent();
        modal = (int) i.getSerializableExtra("MODAL");
        modalityText.setText(getResources().getString(modal));

        switch(modal){
            case BASKETBALL:
                groupRb.setChecked(true);
                individualRb.setChecked(false);
                modalLayout.setVisibility(View.GONE);
                modalIcon.setImageResource(R.mipmap.basketball);
                break;
            case FOOTBALL:
                groupRb.setChecked(true);
                individualRb.setChecked(false);
                modalLayout.setVisibility(View.GONE);
                modalIcon.setImageResource(R.mipmap.football);
                break;
            case FUTSAL:
                groupRb.setChecked(true);
                individualRb.setChecked(false);
                modalLayout.setVisibility(View.GONE);
                modalIcon.setImageResource(R.mipmap.futsal);
                break;
            case HANDBALL:
                groupRb.setChecked(true);
                individualRb.setChecked(false);
                modalLayout.setVisibility(View.GONE);
                modalIcon.setImageResource(R.mipmap.handball);
                break;
            case TENNIS:
                cupRb.setChecked(true);
                leagueRb.setChecked(false);
                compLayout.setVisibility(View.GONE);
                modalIcon.setImageResource(R.mipmap.tennis);
                break;
            case VOLLEY:
                groupRb.setChecked(true);
                individualRb.setChecked(false);
                modalLayout.setVisibility(View.GONE);
                modalIcon.setImageResource(R.mipmap.volley);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_champ, menu);
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


    public void createChamp(View view){
        String nameCp = nameEt.getText().toString();
        String modalCp = getResources().getString(modal);
        boolean indivCp = individualRb.isChecked();
        boolean cupCp = cupRb.isChecked();


        try {
            ChampionshipController.getInstance(getApplicationContext()).validateName(nameCp);
            Intent i = new Intent(this, SettingsChampActivity.class);
            i.putExtra("NAMECP", nameCp);
            i.putExtra("MODALCP", modalCp);
            i.putExtra("INDIVCP", indivCp);
            i.putExtra("CUPCP", cupCp);
            startActivity(i);
        } catch (ExceededCharacterException e) {
            Toast.makeText(this,R.string.char_exceeded,Toast.LENGTH_LONG).show();
        } catch (EmptyFieldException e) {
            Toast.makeText(this,R.string.field_empty, Toast.LENGTH_LONG).show();
        } catch (SameNameException e) {
            Toast.makeText(this,R.string.same_champ, Toast.LENGTH_LONG).show();
        }
    }






}
