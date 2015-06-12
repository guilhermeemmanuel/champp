package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class ChampionActivity extends ActionBarActivity {
    private TextView championTv;
    static final String CHAMPION = "CHAMPION";
    static final String CHAMPIONSHIP = "CHAMPIONSHIP";
    private String champName;
    private String participantName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion);

        championTv =(TextView) findViewById(R.id.campeao);
       if(savedInstanceState != null){
           participantName = (String) savedInstanceState.getString(CHAMPION);
           champName = (String) savedInstanceState.getString(CHAMPIONSHIP);
           Log.i("Entrou","aqui");
       }else {
           Intent i = getIntent();

           participantName = (String) i.getStringExtra(CHAMPION);
           champName = (String) i.getStringExtra(CHAMPIONSHIP);
           Log.i("Entrou","aqui2");
       }

        String congrat = getResources().getString(R.string.congrat);
        String youWin = getResources().getString(R.string.youWin);
        championTv.setText(congrat + participantName + youWin + champName + " !");
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_campeao, menu);
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

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(CHAMPION, participantName);
        savedInstanceState.putString(CHAMPIONSHIP, champName);

    }


}
