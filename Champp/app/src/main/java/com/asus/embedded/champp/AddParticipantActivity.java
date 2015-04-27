package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.InvalidChampException;


public class AddParticipantActivity extends ActionBarActivity {

    EditText namePartipant;
    Championship c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_participant);

        Intent intent = getIntent();
        c = (Championship) intent.getSerializableExtra("CHAMP");

        namePartipant = (EditText) findViewById(R.id.name_participant);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_participant, menu);
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

    public void createParticipant(View view){
         String name = namePartipant.getText().toString();


            Intent intent = new Intent();
            intent.putExtra("NEW_PART", name);

            setResult(1, intent);
            finish();
        Toast.makeText(this,"Participante Criado", Toast.LENGTH_SHORT).show();

}
}
