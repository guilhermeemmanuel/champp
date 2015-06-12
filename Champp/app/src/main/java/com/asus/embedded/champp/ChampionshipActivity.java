package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.asus.embedded.champp.model.Championship;


public class ChampionshipActivity extends ActionBarActivity {

    private Championship c;
    private TextView nameChampTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_championship);

        nameChampTv = (TextView) findViewById(R.id.name_champ_tv);

        Intent i = getIntent();
        c = (Championship) i.getSerializableExtra("CHAMP");
        nameChampTv.setText(c.getName());

    }

    @Override
    protected void onStart() {
        super.onStart();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_championship, menu);
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

    public void viewTable(View v){
        Intent intent;

        if (c.isCup()){
            intent = new Intent(ChampionshipActivity.this, CupActivity.class);
        }else{
            intent = new Intent(ChampionshipActivity.this, LeagueActivity.class);
        }

        intent.putExtra("CHAMP", c);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ChampionshipActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
