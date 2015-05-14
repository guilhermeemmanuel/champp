package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.embedded.champp.adapters.MatchesAdapter;
import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.Match;

import java.util.List;


public class LeagueActivity extends ActionBarActivity {

    private ListView matchesLv;
    private Championship c;
    private MatchesAdapter adapter;
    private TextView league_matches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league);

        league_matches = (TextView) findViewById(R.id.league_matches);


        matchesLv = (ListView) findViewById(R.id.matches_League_Lv);

        Intent i = getIntent();

        c = (Championship) i.getSerializableExtra("CHAMP");

        league_matches.setText(c.getName() + " - " + getString(R.string.table));



    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Match> participants = c.getMatches();
        adapter = new MatchesAdapter(this, participants);
        matchesLv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_league, menu);
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

    public void setScore(View view) {
        View v = matchesLv.getChildAt((Integer) view.getTag());

        try {

            //FIXME cuidado quando o usuario nao colocar nada no textview
            int homeScore = Integer.parseInt(((EditText) v.findViewById(R.id.home_team_score_et)).getText().toString());
            int visitantScore = Integer.parseInt(((EditText) v.findViewById(R.id.visitant_team_score_et)).getText().toString());
            int matchNumber = adapter.getItem((Integer) view.getTag()).getNumber();
            c = ChampionshipController.getInstance().setMatchScore(c.getName(), matchNumber, homeScore, visitantScore);

            adapter.updateItens(c.getMatches());

        } catch(Exception ex) {

        }



    }


}
