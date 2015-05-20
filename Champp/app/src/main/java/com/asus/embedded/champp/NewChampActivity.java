package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.ExceededCharacterException;
import com.asus.embedded.champp.model.SameNameException;

import java.lang.String;


public class NewChampActivity extends ActionBarActivity {
    private EditText nameEt, modalEt;
    private RadioButton individualRb, cupRb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_champ);

        nameEt = (EditText) findViewById(R.id.name_et);
        modalEt = (EditText) findViewById(R.id.modal_et);
        individualRb = (RadioButton) findViewById(R.id.radio_champ_individual);
        cupRb = (RadioButton) findViewById(R.id.radio_champ_cup);
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
        String modalCp = modalEt.getText().toString();
        boolean indivCp = individualRb.isChecked();
        boolean cupCp = cupRb.isChecked();

        try {
            ChampionshipController.getInstance().createChampionship(nameCp, modalCp, indivCp, cupCp);
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
