package com.asus.embedded.champp.model;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.asus.embedded.champp.LeagueActivity;
import com.asus.embedded.champp.R;
import com.asus.embedded.champp.adapters.RankingAdapter;
import com.asus.embedded.champp.controller.ChampionshipController;

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

        //PEGA o CAMPEONATO DE INDICE 2 ou seja o terceiro a ser criado.....""
        //NAO SEI COMO FAZER PARA PEGAR OS PROXIMOS CAMPEONATOS

        Intent i = getIntent();
        c = (Championship) i.getSerializableExtra("CHAMP");
        List<Participant> parts = c.getParticipants();
        Collections.sort(parts);
        rankingAdapter = new RankingAdapter(this, parts);
        lvRanking.setAdapter(rankingAdapter);

    }

    public String generateRanking(){
        List<Participant> aux = new ArrayList<Participant>();
        String concatena = null;

        for(Championship c : ChampionshipController.getChamps()) {

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
