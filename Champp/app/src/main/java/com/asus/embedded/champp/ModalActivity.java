package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ModalActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modal);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_modal, menu);
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

    public void select(View view) {
        Intent intent = new Intent(this, NewChampActivity.class);
        Log.d("Modality", String.valueOf(view.getId() == R.id.basketball_ib));
        switch(view.getId()){
            case R.id.basketball_ib:
                intent.putExtra("MODAL", "basketball");
            case R.id.football_ib:
                intent.putExtra("MODAL", "football");
            case R.id.futsal_ib:
                intent.putExtra("MODAL", "futsal");
            case R.id.handball_ib:
                intent.putExtra("MODAL", "handball");
            case R.id.tennis_ib:
                intent.putExtra("MODAL", "tennis");
            case R.id.volley_ib:
                intent.putExtra("MODAL", "volley");
        }
        startActivity(intent);
    }
}
