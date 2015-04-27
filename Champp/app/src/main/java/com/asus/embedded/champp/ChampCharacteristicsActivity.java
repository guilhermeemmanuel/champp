package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.asus.embedded.champp.model.Championship;


public class ChampCharacteristicsActivity extends ActionBarActivity {

    private TextView nameTv, modalTv, typeModalTv, typeCompetitionTv;
    private Championship c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champ_characteristics);

        nameTv = (TextView) findViewById(R.id.name_tv);
        modalTv = (TextView) findViewById(R.id.modal_tv);
        typeModalTv = (TextView) findViewById(R.id.type_modality_tv);
        typeCompetitionTv = (TextView) findViewById(R.id.type_of_competition_tv);

        Intent i = getIntent();

        c = (Championship) i.getSerializableExtra("CHAMP");
        nameTv.setText(c.getName());
        modalTv.setText(c.getModal());
        typeModalTv.setText(c.isIndividual() ? "Individual" : "Group");
        typeCompetitionTv.setText(c.isCup() ? "Cup" : "League");

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_champ_characteristics, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_add_participante:
                Intent intent = new Intent(ChampCharacteristicsActivity.this, AddParticipantActivity.class);
                intent.putExtra("CHAMP", c);
                startActivityForResult(intent, 2);
                return true;
            case R.id.action_settings:
                  return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
