package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.embedded.champp.adapters.MatchesAdapter;
import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.listeners.MatchListener;
import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.ExceededCharacterException;
import com.asus.embedded.champp.model.InvalidScoreException;
import com.asus.embedded.champp.model.Match;

import java.util.List;


public class LeagueActivity extends ActionBarActivity {

    private ListView matchesLv;
    private Championship c;
    private MatchesAdapter adapter;
    private TextView leagueMatchesTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league);

        leagueMatchesTv = (TextView) findViewById(R.id.league_matches_tv);


        matchesLv = (ListView) findViewById(R.id.league_matches_lv);

        Intent i = getIntent();

        c = (Championship) i.getSerializableExtra("CHAMP");

        leagueMatchesTv.setText(c.getName() + " - " + getString(R.string.table));

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("    " + c.getName());

    }



    @Override
    protected void onStart() {
        super.onStart();

        List<Match> participants = c.getMatches();
        adapter = new MatchesAdapter(this, participants, false);
        adapter.addListener(new MatchListener() {
            @Override
            public void setScore(int matchNumber, int home, int visitant) {
                try {
                    c = ChampionshipController.getInstance(getApplicationContext()).setMatchScore(c.getName(), matchNumber, home, visitant, 0, 0);

                    adapter.updateItems(c.getMatches());

                } catch (Exception ex) {
                    //quando entrar aqui eh porque ele nao colocou nada no edittext
                    Toast.makeText(LeagueActivity.this,R.string.invalid_field,Toast.LENGTH_LONG).show();
                }
            }
        });
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
        switch (item.getItemId()) {
            case R.id.action_ranking:
                Intent intent = new Intent(getApplicationContext(),RankingActivity.class);
                intent.putExtra("CHAMP", c);
                startActivity(intent);
                return true;
            case R.id.action_list_champs:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setScore(View view) {
       View v = matchesLv.getChildAt((Integer) view.getTag());

        try {

            int homeScore = Integer.parseInt(((EditText) v.findViewById(R.id.home_team_score_et)).getText().toString());
            int visitantScore = Integer.parseInt(((EditText) v.findViewById(R.id.visitant_team_score_et)).getText().toString());
            int matchNumber = adapter.getItem((Integer) view.getTag()).getNumber();
            c = ChampionshipController.getInstance(getApplicationContext()).setMatchScore(c.getName(), matchNumber, homeScore, visitantScore, 0, 0);

            adapter.updateItems(c.getMatches());

        } catch (InvalidScoreException e) {
            Toast.makeText(this,R.string.invalid_field,Toast.LENGTH_LONG).show();;
        } catch (ExceededCharacterException e) {
            Toast.makeText(this,R.string.char_exceeded,Toast.LENGTH_LONG).show();
        } catch (EmptyFieldException e) {
            Toast.makeText(this,R.string.invalid_field,Toast.LENGTH_LONG).show();
        }


    }


}
