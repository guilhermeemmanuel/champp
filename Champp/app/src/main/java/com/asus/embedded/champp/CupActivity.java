package com.asus.embedded.champp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.embedded.champp.adapters.MatchesAdapter;
import com.asus.embedded.champp.adapters.ParticipantsAdapter;
import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.listeners.MatchListener;
import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.Match;
import com.asus.embedded.champp.model.Participant;

import java.util.List;


public class CupActivity extends ActionBarActivity {

    private ListView matchesLv;
    private TextView champNameTv;
    private Championship c;
    private MatchesAdapter adapter;
    private Button btnCampeao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cup);

        btnCampeao = (Button) findViewById(R.id.btn_campeao);

        champNameTv = (TextView) findViewById(R.id.champ_name);


        matchesLv = (ListView) findViewById(R.id.matchesLv);

        Intent i = getIntent();

        c = (Championship) i.getSerializableExtra("CAMPEAO");


        champNameTv.setText(c.getName() + " - " + getString(R.string.table));

                    List<Match> participants = c.getMatches();
                    adapter = new MatchesAdapter(this, participants);
                    adapter.addListener(new MatchListener() {
                        @Override
                        public void setScore(int matchNumber, int home, int visitant) {
                            try {
                                c = ChampionshipController.getInstance().setMatchScore(c.getName(), matchNumber, home, visitant);

                                adapter.updateItens(c.getMatches());

                                if (c.isCampeao()){
                                    onCreateDialog();
                                    //Descomenta aqui somente se quiser que chame a activity de ver campeao , mas comente a linha
                                    // de cima
                                    //Participant campeao = c.campeao();
                                    //Intent intent = new Intent(CupActivity.this,CampeaoActivity.class);
                                    //intent.putExtra("CAMPEAO",campeao.getName());
                                    //intent.putExtra("CAMPEONATO",c.getName());
                                    //startActivity(intent);
                                }

                } catch (Exception ex) {
                    //quando entrar aqui eh porque ele nao colocou nada no edittext
                    Toast.makeText(CupActivity.this,"Insira valores validos nos campos de resultado",Toast.LENGTH_LONG).show();
                }
            }
        });
        matchesLv.setAdapter(adapter);



    }


    @Override
    protected void onStart() {

        if (c.isCampeao()){
            onCreateDialog();
        }

        //if(c.isCampeao()){
           // btnCampeao.setVisibility(View.VISIBLE);

        //}
        //else {
            btnCampeao.setVisibility(View.GONE);
        //}

        super.onStart();
    }



   /* public void showCampeao(View v){

        if (c.isCampeao()){
            Participant campeao = c.campeao();
            Intent intent = new Intent(CupActivity.this,CampeaoActivity.class);
            intent.putExtra("CAMPEAO",campeao.getName());
            intent.putExtra("CAMPEONATO",c.getName());
            startActivity(intent);
        }

    }*/


    public void onCreateDialog() {

        Participant campeao = c.campeao();

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setScore(final View view) {

        setScoreSure(view);

        // 1. Instantiate an AlertDialog.Builder with its constructor
        //AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        //builder.setMessage(R.string.setScoreDialog)
        //        .setTitle(R.string.setScoreDialogTitle);
        // 3. Add the buttons
        //builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
         //   public void onClick(DialogInterface dialog, int id) {
        //        setScoreSure(view);
        //    }
        //});
        //builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
         //   public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
           // }
        //});

        //builder.show();
    }

    public void setScoreSure(View view){

        // adapter.setScore(view);

        int position = matchesLv.getPositionForView((View) view.getParent());
        //matchesLv.getItemAtPosition(position);


        View v = matchesLv.getChildAt(position);

        //Log.d("test",matchesLv.getItemAtPosition((Integer) view.getTag()).getClass().toString());

        EditText home_score_et = ((EditText) v.findViewById(R.id.home_team_score_et));
        EditText visitant_score_et = ((EditText) v.findViewById(R.id.visitant_team_score_et));


        try {
            int homeScore = Integer.parseInt(home_score_et.getText().toString());
            Log.i("homeScore", homeScore + "");
            int visitantScore = Integer.parseInt(visitant_score_et.getText().toString());
            int matchNumber = adapter.getItem((Integer) view.getTag()).getNumber();
            c = ChampionshipController.getInstance().setMatchScore(c.getName(), matchNumber, homeScore, visitantScore);

            adapter.updateItens(c.getMatches());

            Toast.makeText(this,homeScore + " x " + visitantScore,Toast.LENGTH_LONG).show();




        } catch (Exception ex) {
            //quando entrar aqui eh porque ele nao colocou nada no edittext
            Toast.makeText(this,"Insira valores validos nos campos de resultado",Toast.LENGTH_LONG).show();
        }









    }

}
