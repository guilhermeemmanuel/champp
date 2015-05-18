package com.asus.embedded.champp.model;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.asus.embedded.champp.R;
import com.asus.embedded.champp.adapters.RankingAdapter;
import com.asus.embedded.champp.controller.ChampionshipController;

import java.util.ArrayList;
import java.util.List;

public class RankingActivity extends Activity {

    private RankingAdapter rankingAdapter;
    private ListView lvRanking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ranking_adapter);
        lvRanking = (ListView) findViewById(R.id.lv_ranking);

        rankingAdapter = new RankingAdapter(this, ChampionshipController.getChamps().get(0).getParticipants());
        lvRanking.setAdapter(rankingAdapter);

    }

    public String generateRanking(){
        List<Participant> aux = new ArrayList<Participant>();
        String concatena = null;

        for(Championship c : ChampionshipController.getChamps()) {
            if (c.getName().equals("Copa do Brasil")) {
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
