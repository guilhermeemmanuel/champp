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
import com.asus.embedded.champp.model.ListMyChampsAdapter;

import java.util.List;

public class MyChampsActivity extends ListActivity {

    private ListView listView;
    private ListMyChampsAdapter adapter;
    private List<Championship> champs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_my_champs);

        champs = ChampionshipController.getInstance().getChamps();
        adapter = new ListMyChampsAdapter(this, (java.util.ArrayList<Championship>) champs);
        setListAdapter(adapter);
    }

    public void deleteItem(View v) {

        adapter.removeItem((Integer) v.getTag());
        Toast.makeText(this,R.string.champDelete,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
         //Pega o item que foi selecionado.
        Championship item = adapter.getItem(position);
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
