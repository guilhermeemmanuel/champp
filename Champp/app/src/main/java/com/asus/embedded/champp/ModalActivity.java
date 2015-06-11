package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ModalActivity extends ActionBarActivity {
    private final int BASKETBALL = R.string.basketball;
    private final int FOOTBALL = R.string.football;
    private final int FUTSAL = R.string.futsal;
    private final int HANDBALL = R.string.handball;
    private final int TENNIS = R.string.tennis;
    private final int VOLLEY = R.string.volley;


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
        switch(view.getId()){
            case R.id.basketball_ib:
                intent.putExtra("MODAL", BASKETBALL);
                break;
            case R.id.football_ib:
                intent.putExtra("MODAL", FOOTBALL);
                break;
            case R.id.futsal_ib:
                intent.putExtra("MODAL", FUTSAL);
                break;
            case R.id.handball_ib:
                intent.putExtra("MODAL", HANDBALL);
                break;
            case R.id.tennis_ib:
                intent.putExtra("MODAL", TENNIS);
                break;
            case R.id.volley_ib:
                intent.putExtra("MODAL", VOLLEY);
                break;
        }
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            finish();
        }
    }
}
