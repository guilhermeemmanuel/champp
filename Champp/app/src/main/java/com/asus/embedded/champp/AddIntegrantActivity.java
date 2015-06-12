package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.asus.embedded.champp.model.Participant;

public class AddIntegrantActivity extends ActionBarActivity {
    EditText nameIntegrant;
    Participant p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_integrant);

        Intent intent = getIntent();
        p = (Participant) intent.getSerializableExtra("PARTICIPANT");

        nameIntegrant = (EditText) findViewById(R.id.new_integrant_name_et);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_integrant, menu);
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

    public void createIntegrant(View view) {
        String name = nameIntegrant.getText().toString();
        //Excessao de times vazios
        if (name.trim().equals("")) {
            Toast.makeText(this, R.string.integrant_empty_name, Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent();
            intent.putExtra("NEW_INTEGRANT", name);

            setResult(1, intent);
            finish();
        }
    }
}
