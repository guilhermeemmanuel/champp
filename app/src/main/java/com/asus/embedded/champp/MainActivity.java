package com.asus.embedded.champp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.adapters.ListMyChampsAdapter;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    private ListView champLv;
    private ListMyChampsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        champLv = (ListView) findViewById(R.id.champ_list_view);

        champLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Championship item = adapter.getItem(position);
                int icon = adapter.getIdImage(item);
                Intent intent = new Intent(MainActivity.this, ChampCharacteristicsActivity.class);
                intent.putExtra("CHAMP", item);
                intent.putExtra("ICON", icon);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        List<Championship> champs = ChampionshipController.getInstance(getApplicationContext()).getChamps();
        adapter = new ListMyChampsAdapter(this, champs);
        champLv.setAdapter(adapter);
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

    public void deleteItem(final View vAct) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.delete_champs_dialog)
                .setTitle(R.string.delete_button);
        // 3. Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               sureDeleteItem(vAct);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.show();
    }

    public void sureDeleteItem(View v){
        ChampionshipController.getInstance(getApplicationContext()).deleteChampionship(adapter.getItem((Integer) v.getTag()).getName());
        adapter.updateItems(ChampionshipController.getInstance(getApplicationContext()).getChamps());
        Toast.makeText(this,R.string.champ_deleted,Toast.LENGTH_LONG).show();
   }

    public void newChamp(View view) {
        Intent intent = new Intent(this, ModalActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){

        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
