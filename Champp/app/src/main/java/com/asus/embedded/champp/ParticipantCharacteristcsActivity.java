package com.asus.embedded.champp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.embedded.champp.adapters.IntegrantsAdapter;
import com.asus.embedded.champp.adapters.ParticipantsAdapter;
import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.Championship;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.ExceededCharacterException;
import com.asus.embedded.champp.model.Integrant;
import com.asus.embedded.champp.model.Participant;
import com.asus.embedded.champp.model.SameNameException;

import java.util.List;


public class ParticipantCharacteristcsActivity extends ActionBarActivity {

    private TextView name_participant;
    private Participant pt;
    private Championship c;
    private Button addIntegrant;
    private ListView integrantsLv;
    private IntegrantsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_characteristcs);

        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        Intent i = getIntent();

        pt = (Participant) i.getSerializableExtra("PARTICIPANT");
        c = (Championship) i.getSerializableExtra("CAMPEAO");

        integrantsLv = (ListView) findViewById(R.id.integrants_list_view);

        setTitle(pt.getName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();

        List<Integrant> integrants = pt.getIntegrants();
        adapter = new IntegrantsAdapter(this, integrants);
        integrantsLv.setAdapter(adapter);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem register = menu.findItem(R.id.action_add_integrant);
        if(c.isIndividual()){
            register.setVisible(false);
        }else{
            register.setVisible(true);
        }
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        getMenuInflater().inflate(R.menu.menu_participant_characteristcs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_add_integrant:
                Intent intent = new Intent(this, AddIntegrantActivity.class);
                intent.putExtra("PARTICIPANT", pt);
                startActivityForResult(intent, 1);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteItem(final View v) {
        if(!c.isStarted()){
            // 1. Instantiate an AlertDialog.Builder with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.deleteParticipanteDialog)
                    .setTitle(R.string.btnDelete);
            // 3. Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    sureDeleteItem(v);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            builder.show();

        }else {
            Toast.makeText(this, R.string.champStarted, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void sureDeleteItem(View v){
        adapter.removeItem((Integer) v.getTag());
        Toast.makeText(this,R.string.participantDeleted,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1){
            if(resultCode == 1) {
                String name = data.getStringExtra("NEW_INTEGRANT");
                try {
                    this.pt = ChampionshipController.getInstance(getApplicationContext()).addIntegrant(c.getName(), pt, name);
                    Toast.makeText(this, R.string.integrantCreated, Toast.LENGTH_SHORT).show();
                }  catch (EmptyFieldException e) {
                    Toast.makeText(this,R.string.fieldEmpty, Toast.LENGTH_SHORT).show();
                } catch (ExceededCharacterException e) {
                    Toast.makeText(this,R.string.charExceeded,Toast.LENGTH_SHORT).show();
                } catch (SameNameException e) {
                    Toast.makeText(this,R.string.sameName, Toast.LENGTH_SHORT).show();
                }
            }
        }

    }
}
