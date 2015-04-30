package com.asus.embedded.champp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.embedded.champp.adapters.ParticipantsAdapter;
import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.Participant;

import java.util.List;


public class ChampCharacteristicsActivity extends ActionBarActivity {

    private TextView nameTv, modalTv, typeModalTv, typeCompetitionTv;
    private Championship c;
    private ListView participantsLv;
    private ParticipantsAdapter partipantsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champ_characteristics);

        nameTv = (TextView) findViewById(R.id.name_tv);
        modalTv = (TextView) findViewById(R.id.modal_tv);
        typeModalTv = (TextView) findViewById(R.id.type_modality_tv);
        typeCompetitionTv = (TextView) findViewById(R.id.type_of_competition_tv);
        participantsLv = (ListView) findViewById(R.id.participants_list_view);

        Intent i = getIntent();

        c = (Championship) i.getSerializableExtra("CHAMP");
        nameTv.setText(c.getName());
        modalTv.setText(c.getModal());
        typeModalTv.setText(c.isIndividual() ? "Individual" : "Group");
        typeCompetitionTv.setText(c.isCup() ? "Cup" : "League");

        //Vinicius
        participantsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //pegando a posicao do participante
                Participant item = partipantsAdapter.getItem(position);
                Intent intent = new Intent(ChampCharacteristicsActivity.this, ChampCharacteristicsActivity.class);
                intent.putExtra("CHAMP", item);
                startActivity(intent);


            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Participant> participants = c.getParticipants();
        ParticipantsAdapter adapter = new ParticipantsAdapter(this,participants);
        participantsLv.setAdapter(adapter);

    }

    //Vinicius
    public void deleteItem(final View v) {
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


    }
    //Vinicius
    public void sureDeleteItem(View v){
        partipantsAdapter.removeItem((Integer) v.getTag());
        Toast.makeText(this,R.string.participantDeleted,Toast.LENGTH_LONG).show();
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
                this.c = ChampionshipController.getInstance().addParticipant(c.getName(),name);
            }
        }

    }
}
