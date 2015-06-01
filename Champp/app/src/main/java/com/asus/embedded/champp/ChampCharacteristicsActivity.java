package com.asus.embedded.champp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.AdapterView;


import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.embedded.champp.adapters.ParticipantsAdapter;
import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.ExceededCharacterException;
import com.asus.embedded.champp.model.Participant;
import com.asus.embedded.champp.model.SameNameException;

import java.util.List;


public class ChampCharacteristicsActivity extends ActionBarActivity {

    private TextView nameTv;
    private Championship c;
    private ListView participantsLv;
    private ParticipantsAdapter adapter;
    private Button startBt;
    private Button showTableBt;
    private TextView modal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champ_characteristics);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        nameTv = (TextView) findViewById(R.id.name_tv);
        modal = (TextView) findViewById(R.id.modal_tv);
        /*typeModalTv = (TextView) findViewById(R.id.type_modality_tv);
        typeCompetitionTv = (TextView) findViewById(R.id.type_of_competition_tv);*/

        startBt = (Button) findViewById(R.id.buttonInitChamp);
        showTableBt = (Button) findViewById(R.id.buttonShowTable);


        participantsLv = (ListView) findViewById(R.id.participants_list_view);
        participantsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Participant item = adapter.getItem(position);

                Intent intent = new Intent(ChampCharacteristicsActivity.this, ParticipantCharacteristcsActivity.class);
                intent.putExtra("PARTICIPANT", item);
                intent.putExtra("CAMPEAO", c);
                startActivityForResult(intent,1);
            }
        });

        Intent i = getIntent();

        c = (Championship) i.getSerializableExtra("CAMPEAO");
        setTitle(c.getName());
        nameTv.setText(c.getName());
        modal.setText(c.getModal());
        /*typeModalTv.setText(c.isIndividual() ? "Individual" : "Group");
        typeCompetitionTv.setText(c.isCup() ? "Cup" : "League");*/

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("BD", "onStart");
        try {
            c = ChampionshipController.getInstance(getApplicationContext()).getChamp(c.getName());
        } catch (Exception ex) {

        }
        invalidateOptionsMenu();
        if(c.isStarted()){
            startBt.setVisibility(View.GONE);
            showTableBt.setVisibility(View.VISIBLE);
        }
        else {
            startBt.setVisibility(View.VISIBLE);
            showTableBt.setVisibility(View.GONE);
        }
        List<Participant> participants = c.getParticipants();
        adapter = new ParticipantsAdapter(this, participants, c.isStarted());
        participantsLv.setAdapter(adapter);

    }



    public void deleteItem(final View v) {
        if(!c.isStarted()){
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.deleteParticipanteDialog)
                    .setTitle(R.string.btnDelete);
            // 3. Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    sureDeleteItem(v);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            builder.show();

        }else {
            Toast.makeText(this, R.string.champStarted, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ChampCharacteristicsActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void sureDeleteItem(View v){

        c = ChampionshipController.getInstance(getApplicationContext()).deleteParticipant(c.getName(), adapter.getItem((Integer) v.getTag()).getName());
        adapter.updateItens(c.getParticipants());
        Toast.makeText(this,R.string.participantDeleted,Toast.LENGTH_LONG).show();
    }

    public void initChamp(View v){
        if(!c.isStarted()){
            if(c.getParticipants().size() < 2){
                Toast.makeText(this,R.string.champUnstarted,Toast.LENGTH_LONG).show();
            }else{
                try {
                    c = ChampionshipController.getInstance(getApplicationContext()).startChamp(c.getName());
                } catch (ExceededCharacterException e) {
                    Toast.makeText(this,R.string.charExceeded,Toast.LENGTH_LONG).show();
                } catch (EmptyFieldException e) {
                    Toast.makeText(this,R.string.fieldEmpty,Toast.LENGTH_LONG).show();
                }
                Intent intent;
                if (c.isCup()){
                    intent = new Intent(this, CupActivity.class);
                }else{
                    intent = new Intent(this, LeagueActivity.class);
                }
                intent.putExtra("CAMPEAO",c);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this,R.string.champStarted,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ChampCharacteristicsActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }

    public void showTable(View v){
        Intent intent;
        if (c.isCup()){
            intent = new Intent(ChampCharacteristicsActivity.this, CupActivity.class);
        }else{
            intent = new Intent(this, LeagueActivity.class);
        }
        intent.putExtra("CAMPEAO",c);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_champ_characteristics, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem register = menu.findItem(R.id.action_add_participante);
        if(c.isStarted())
        {
            register.setVisible(false);
        }
        else
        {
            register.setVisible(true);
        }
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
                intent.putExtra("CAMPEAO", c);
                startActivityForResult(intent, 1);
                return true;
            case R.id.action_settings:
                  return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == 1) {
                String name = data.getStringExtra("NEW_PART");
                try {
                    this.c = ChampionshipController.getInstance(getApplicationContext()).addParticipant(c.getName(),name);
                    Toast.makeText(this,R.string.participantCreated, Toast.LENGTH_SHORT).show();
                }  catch (EmptyFieldException e) {
                    Toast.makeText(this,R.string.fieldEmpty, Toast.LENGTH_SHORT).show();
                } catch (SameNameException e) {
                    Toast.makeText(this,R.string.sameName, Toast.LENGTH_SHORT).show();
                } catch (ExceededCharacterException e) {
                    Toast.makeText(this,R.string.charExceeded,Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}
