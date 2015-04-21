package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.ListMyChampsAdapter;

import java.util.List;


public class MainActivity extends ActionBarActivity {



    private ListView champListView;
    private ListMyChampsAdapter adapter;
    private List<Championship> champs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        champListView = (ListView) findViewById(R.id.champ_list_view);
        champListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Championship item = adapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, champCharacteristics.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        List<Championship> champs = ChampionshipController.getInstance().getChamps();
        adapter = new ListMyChampsAdapter(this, champs);
        champListView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    public void deleteItem(View v) {

        adapter.removeItem((Integer) v.getTag());
        Toast.makeText(this,"successfully deleted championship ",Toast.LENGTH_LONG).show();
    }

    public void newChamp(View view) {
        Intent intent = new Intent(this, NewChampActivity.class);
        startActivityForResult(intent, 1);

    }

    public void myChamps(View view){
        if(ChampionshipController.getInstance().champsIsEmpty()) {
            Toast.makeText(this, "No championships", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent2 = new Intent(this, MyChampsActivity.class);
            startActivity(intent2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){

        }
    }
}
