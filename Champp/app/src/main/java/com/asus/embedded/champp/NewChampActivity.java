package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.ExceededCharacterException;
import com.asus.embedded.champp.model.SameNameException;

import java.lang.String;


public class NewChampActivity extends ActionBarActivity {
    private EditText nameEt;
    private RadioButton individualRb, cupRb;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_champ);

        nameEt = (EditText) findViewById(R.id.name_et);
        individualRb = (RadioButton) findViewById(R.id.radio_champ_individual);
        cupRb = (RadioButton) findViewById(R.id.radio_champ_cup);
        spinner = (Spinner) findViewById(R.id.modal_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.modal_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }

    public void onItemSelected(AdapterView<?> parent, View view,
                              int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_champ, menu);
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

    public void createChamp(View view){
        String nameCp = nameEt.getText().toString();
        String modalCp = spinner.getSelectedItem().toString();
        boolean indivCp = individualRb.isChecked();
        boolean cupCp = cupRb.isChecked();

        try {
            ChampionshipController.getInstance(getApplicationContext()).createChampionship(nameCp, modalCp, indivCp, cupCp);
            Intent intent = new Intent();
            setResult(1, intent);
            finish();
        } catch (EmptyFieldException e) {
            Toast.makeText(this,R.string.fieldEmpty, Toast.LENGTH_SHORT).show();
        } catch (SameNameException e) {
            Toast.makeText(this,R.string.sameChamp, Toast.LENGTH_SHORT).show();
        } catch (ExceededCharacterException e) {
            Toast.makeText(this,R.string.charExceeded,Toast.LENGTH_LONG).show();
        }
    }
}
