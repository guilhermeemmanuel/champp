package com.asus.embedded.champp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.asus.embedded.champp.model.Participant;

import java.util.List;

public class CupActivity extends ActionBarActivity {

    private ListView matchesLv;
    private TextView champNameTv;
    private Championship c;
    private MatchesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cup);

        champNameTv = (TextView) findViewById(R.id.cup_champ_tv);

        matchesLv = (ListView) findViewById(R.id.matches_cup_lv);

        Intent i = getIntent();

        c = (Championship) i.getSerializableExtra("CHAMP");


        champNameTv.setText(c.getName() + " - " + getString(R.string.table));

                    List<Match> participants = c.getMatches();
                    adapter = new MatchesAdapter(this, participants, true);
                    adapter.addListener(new MatchListener() {
                        @Override
                        public void setScore(int matchNumber, int home, int visitant) {
                            try {
                                if(home == visitant && !c.isHomeWin()) {
                                    dialogPenalities(matchNumber, home, visitant);
                                }
                                else {
                                    c = ChampionshipController.getInstance(getApplicationContext()).setMatchScore(c.getName(), matchNumber, home, visitant, 0, 0);

                                    adapter.updateItems(c.getMatches());

                                    if (c.isChampion()) {
                                        onCreateDialog();
                                    }

                                }
                } catch (Exception ex) {
                    //quando entrar aqui eh porque ele nao colocou nada no edittext
                    Toast.makeText(CupActivity.this,R.string.invalid_field,Toast.LENGTH_LONG).show();
                }
            }
        });
        matchesLv.setAdapter(adapter);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle("    " + c.getName());

    }


    @Override
    protected void onStart() {
        if (c.isChampion()){
            onCreateDialog();
        }
        super.onStart();
    }

    public void dialogPenalities(final int matchNumber, final int home, final int visitant) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View layout = inflater.inflate(R.layout.penalities_layout, null);
        builder.setView(layout);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                EditText homeEt = (EditText) layout.findViewById(R.id.home_penalty);
                EditText visEt = (EditText) layout.findViewById(R.id.vis_penalty);


                try {
                    int homePen = Integer.parseInt(homeEt.getText().toString());
                    int visPen = Integer.parseInt(visEt.getText().toString());

                    c = ChampionshipController.getInstance(getApplicationContext()).setMatchScore(c.getName(), matchNumber, home, visitant, homePen, visPen);

                    adapter.updateItems(c.getMatches());

                    if (c.isChampion()){
                        onCreateDialog();
                    }


                } catch (Exception ex) {

                }

            }
        });


        builder.show();
    }

    public void onCreateDialog() {
        String congratSt = getResources().getString(R.string.congrat);
        String youWinSt = getResources().getString(R.string.you_win);
        String championSt = getResources().getString(R.string.champion);

        Participant champion = c.getChampion();

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cup, menu);

        return true;
    }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_list_champs) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setScore(final View view) {
        setScoreSure(view);
    }

    public void setScoreSure(View view){
        int position = matchesLv.getPositionForView((View) view.getParent());
        View v = matchesLv.getChildAt(position);
        EditText home_score_et = ((EditText) v.findViewById(R.id.home_team_score_et));
        EditText visitant_score_et = ((EditText) v.findViewById(R.id.visitant_team_score_et));


        try {
            int homeScore = Integer.parseInt(home_score_et.getText().toString());
            Log.i("homeScore", homeScore + "");
            int visitantScore = Integer.parseInt(visitant_score_et.getText().toString());
            int matchNumber = adapter.getItem((Integer) view.getTag()).getNumber();
            c = ChampionshipController.getInstance(getApplicationContext()).setMatchScore(c.getName(), matchNumber, homeScore, visitantScore, 0, 0);

            adapter.updateItems(c.getMatches());

            Toast.makeText(this,homeScore + " x " + visitantScore,Toast.LENGTH_LONG).show();
        } catch (InvalidScoreException e) {
            Toast.makeText(this,R.string.invalid_field,Toast.LENGTH_LONG).show();;
        } catch (ExceededCharacterException e) {
            Toast.makeText(this,R.string.char_exceeded,Toast.LENGTH_LONG).show();
        } catch (EmptyFieldException e) {
            Toast.makeText(this,R.string.invalid_field,Toast.LENGTH_LONG).show();
        }


    }

}
