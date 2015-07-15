package com.asus.embedded.champp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.asus.embedded.champp.adapters.RankingAdapter;
import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.Participant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RankingActivity extends ActionBarActivity {

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

        if(c.isChampion()) {
            showChampion(parts.get(0));
        }

    }

    public void showChampion(Participant participant) {
        String congratSt = getResources().getString(R.string.congrat);
        String youWinSt = getResources().getString(R.string.you_win);
        String championSt = getResources().getString(R.string.champion);

        Participant champion = participant;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_champion_trophy, null));
        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(congratSt + " " + champion.getName() + ", " + youWinSt + " " + c.getName() + "!!!")
                .setIcon(R.mipmap.champion)
                .setTitle(championSt);
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
        String concat = null;

        for(Championship c : ChampionshipController.getInstance(getApplicationContext()).getChamps()) {

                if (c.getName().equals("liga")) {
                    aux = c.getParticipants();
                    concat = c.getName() + "\n";
                    Log.v("kkkkkkkkkkkkk", concat);
                    for (Participant p : aux) {
                        concat += p.getName() + " " + p.getScore() + "\n";
                        Log.v("ZZZZZZZZZZZ", concat);
                    }
                    concat = "\n";
               }
        }

        return concat;
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
        else if (id == R.id.action_list_champs) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showTitle(View view){
        switch (view.getId()){
            case R.id.tv_team_score:
                Toast.makeText(this, R.string.p_toast, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_number_games:
                Toast.makeText(this, R.string.g_toast, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_number_win:
                Toast.makeText(this, R.string.w_toast, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_number_defeats:
                Toast.makeText(this, R.string.l_toast, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_draw:
                Toast.makeText(this, R.string.d_toast, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_goals_pro:
                Toast.makeText(this, R.string.f_toast, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_goals_against:
                Toast.makeText(this, R.string.a_toast, Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_balance:
                Toast.makeText(this, R.string.b_toast, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
