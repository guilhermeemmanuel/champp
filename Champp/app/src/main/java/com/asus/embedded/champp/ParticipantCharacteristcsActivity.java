package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.Participant;


public class ParticipantCharacteristcsActivity extends ActionBarActivity {

    private TextView name_participant;
    private Participant pt;
    private Championship c;
    private Button addIntegrant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_characteristcs);

        name_participant = (TextView) findViewById(R.id.name_participant);

        Intent i = getIntent();

        pt = (Participant) i.getSerializableExtra("PARTICIPANT");
        c = (Championship) i.getSerializableExtra("CAMPEAO");
        name_participant.setText(pt.getName());

        addIntegrant = (Button) findViewById(R.id.add_integrant_bt);
        if (c.isIndividual()) {
            addIntegrant.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_participant_characteristcs, menu);
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


    public void addIntegrant(View view) {
        Intent i = new Intent(this, AddIntegrantActivity.class);
        startActivity(i);
    }
}
