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

import android.widget.AdapterView;
import android.widget.Button;


import android.widget.ImageView;
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

    //private TextView nameTv;
    private Championship c;
    private ListView participantsLv;
    private ParticipantsAdapter adapter;
    private Button startBt, showTableBt, showRankingBt;
    private TextView modalTv, participantsTv;
    private ImageView iconChamp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champ_characteristics);

        iconChamp = (ImageView) findViewById(R.id.icon_champ);

        //nameTv = (TextView) findViewById(R.id.championship_name_tv);
        modalTv = (TextView) findViewById(R.id.modal_championship_tv);
        participantsTv = (TextView) findViewById(R.id.participants_tv);

        startBt = (Button) findViewById(R.id.init_champ_bt);
        showTableBt = (Button) findViewById(R.id.show_table_bt);
        showRankingBt = (Button) findViewById(R.id.ranking_bt);


        participantsLv = (ListView) findViewById(R.id.participants_list_view);
        participantsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Participant item = adapter.getItem(position);

                Intent intent = new Intent(ChampCharacteristicsActivity.this, ParticipantCharacteristcsActivity.class);
                intent.putExtra("PARTICIPANT", item);
                intent.putExtra("CHAMP", c);
                startActivityForResult(intent,1);
            }
        });

        Intent i = getIntent();

        c = (Championship) i.getSerializableExtra("CHAMP");
        int icon = (int) i.getSerializableExtra("ICON");

        iconChamp.setImageResource(icon);
        //nameTv.setText(c.getName());
        modalTv.setText(c.getModal());

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("    " + c.getName());

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

        showRankingBt.setVisibility(View.GONE);

        if(c.isStarted()){
            startBt.setVisibility(View.GONE);
            showTableBt.setVisibility(View.VISIBLE);
            if(!c.isCup()){
                showRankingBt.setVisibility(View.VISIBLE);
            }
        }else {
            startBt.setVisibility(View.VISIBLE);
            showTableBt.setVisibility(View.GONE);
        }




        List<Participant> participants = c.getParticipants();
        if(participants.isEmpty()){
            participantsTv.setVisibility(View.GONE);
        }else{
            participantsTv.setVisibility(View.VISIBLE);
        }
        adapter = new ParticipantsAdapter(this, participants, c.isStarted());
        participantsLv.setAdapter(adapter);

    }



    public void deleteItem(final View v) {
        if(!c.isStarted()){
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.delete_participant_dialog)
                    .setTitle(R.string.delete_button);
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
            Toast.makeText(this, R.string.champ_started, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ChampCharacteristicsActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void sureDeleteItem(View v){

        c = ChampionshipController.getInstance(getApplicationContext()).deleteParticipant(c.getName(), adapter.getItem((Integer) v.getTag()).getName());
        adapter.updateItems(c.getParticipants());
        Toast.makeText(this,R.string.participant_deleted,Toast.LENGTH_LONG).show();
    }


    public void initChamp(View view) {


        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.sure_start_champ)
                .setTitle(R.string.init_champ);
        // 3. Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                sureInitChamp();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.show();

    }

    public void sureInitChamp(){
        if(!c.isStarted()){
            if(c.getParticipants().size() < 2){
                Toast.makeText(this,R.string.champ_unstarted,Toast.LENGTH_LONG).show();
            }else{
                try {
                    c = ChampionshipController.getInstance(getApplicationContext()).startChamp(c.getName());
                } catch (ExceededCharacterException e) {
                    Toast.makeText(this,R.string.char_exceeded,Toast.LENGTH_LONG).show();
                } catch (EmptyFieldException e) {
                    Toast.makeText(this,R.string.field_empty,Toast.LENGTH_LONG).show();
                }
                Intent intent;
                if (c.isCup()){
                    intent = new Intent(this, CupActivity.class);
                }else{
                    intent = new Intent(this, LeagueActivity.class);
                }
                intent.putExtra("CHAMP",c);
                startActivity(intent);
            }
        }else{
            Toast.makeText(this,R.string.champ_started,Toast.LENGTH_LONG).show();
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
        intent.putExtra("CHAMP",c);
        startActivity(intent);
    }

    public void showRanking(View v){
        Intent intent = new Intent(ChampCharacteristicsActivity.this, RankingActivity.class);
        intent.putExtra("CHAMP", c);
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
        MenuItem register = menu.findItem(R.id.action_add_participant);
        if(c.isStarted()){
            register.setVisible(false);
        }else{
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
            case R.id.action_add_participant:
                Intent intent = new Intent(ChampCharacteristicsActivity.this, AddParticipantActivity.class);
                intent.putExtra("CHAMP", c);
                startActivityForResult(intent, 1);
                return true;
            case R.id.action_list_champs:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
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
                    Toast.makeText(this,R.string.participant_created, Toast.LENGTH_LONG).show();
                }  catch (EmptyFieldException e) {
                    Toast.makeText(this,R.string.field_empty, Toast.LENGTH_LONG).show();
                } catch (SameNameException e) {
                    Toast.makeText(this,R.string.same_name, Toast.LENGTH_LONG).show();
                } catch (ExceededCharacterException e) {
                    Toast.makeText(this,R.string.char_exceeded,Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}
