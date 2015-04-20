package com.asus.embedded.champp;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.asus.embedded.champp.R;
import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.Championship;

import java.util.List;

public class MyChampsActivity extends ListActivity {
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> champs = ChampionshipController.getInstance().getChampsName();
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, champs);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String clickedName = adapter.getItem(position).toString();
        Log.v("TESTE", clickedName.toString());

        List<Championship> champList = ChampionshipController.getChamps();
        for(Championship champ : champList){
            if (champ.getName().equals(clickedName)){
                Log.v("TESTE", "ENTROU");
                champList.remove(champ);
                //after removing the User , the app shows a msg success and returns to the main screen
                // MainActivity
                Toast.makeText(getApplicationContext(), champ.getName() + " has been removed", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_champs, menu);
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
