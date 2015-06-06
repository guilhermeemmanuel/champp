package com.asus.embedded.champp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.asus.embedded.champp.LeagueActivity;
import com.asus.embedded.champp.R;
import com.asus.embedded.champp.adapters.RankingAdapter;
import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.Participant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankingActivity extends Activity {

    private RankingAdapter rankingAdapter;
    private ListView lvRanking;
    private Championship c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_adapter);
        lvRanking = (ListView) findViewById(R.id.lv_ranking);

        Intent i = getIntent();
        c = (Championship) i.getSerializableExtra("CHAMP");
        List<Participant> parts = c.getParticipants();
        Collections.sort(parts);
        rankingAdapter = new RankingAdapter(this, parts);

        lvRanking.setAdapter(rankingAdapter);
        if(c.isCampeao()) {
            showCampeao(parts.get(0));
        }

    }

    public void showCampeao(Participant participant) {

        Participant campeao = participant;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_campeao_trofeu, null));
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage("Parabens " + campeao.getName() + " você é o novo campeao da " + c.getName() + "!!!")
                .setIcon(R.mipmap.campeao)
                .setTitle("CAMPEÃO");
        // 3. Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


            }
        });


        builder.show();
    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    public String generateRanking(){
        List<Participant> aux = new ArrayList<Participant>();
        String concatena = null;

        for(Championship c : ChampionshipController.getInstance(getApplicationContext()).getChamps()) {

                if (c.getName().equals("liga")) {
                    aux = c.getParticipants();
                    concatena = c.getName() + "\n";
                    Log.v("kkkkkkkkkkkkk", concatena);
                    for (Participant p : aux) {
                        concatena += p.getName() + " " + p.getPontuacao() + "\n";
                        Log.v("ZZZZZZZZZZZ", concatena);
                    }
                    concatena = "\n";
               }
        }

        return concatena;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ranking, menu);
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
