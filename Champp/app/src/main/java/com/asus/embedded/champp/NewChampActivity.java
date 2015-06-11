package com.asus.embedded.champp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.embedded.champp.controller.ChampionshipController;
import com.asus.embedded.champp.model.EmptyFieldException;
import com.asus.embedded.champp.model.ExceededCharacterException;
import com.asus.embedded.champp.model.SameNameException;

import java.lang.String;


public class NewChampActivity extends ActionBarActivity {
    private final int BASKETBALL = R.string.basketball;
    private final int FOOTBALL = R.string.football;
    private final int FUTSAL = R.string.futsal;
    private final int HANDBALL = R.string.handball;
    private final int TENNIS = R.string.tennis;
    private final int VOLLEY = R.string.volley;

    private EditText nameEt;
    private RadioButton individualRb, cupRb;
    private LinearLayout modalLayout, compLayout;
    private ImageView modalIcon;
    private int modal;
    private TextView modalityText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_champ);

        nameEt = (EditText) findViewById(R.id.name_et);
        modalityText = (TextView) findViewById(R.id.modality_tv);
        individualRb = (RadioButton) findViewById(R.id.radio_champ_individual);
        modalLayout = (LinearLayout) findViewById(R.id.modality_layout);
        compLayout = (LinearLayout) findViewById(R.id.competition_layout);
        cupRb = (RadioButton) findViewById(R.id.radio_champ_cup);
        modalIcon = (ImageView) findViewById(R.id.modal_icon);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                              int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent i = getIntent();
        modal = (int) i.getSerializableExtra("MODAL");
        modalityText.setText(getResources().getString(modal));

        switch(modal){
            case BASKETBALL:
                modalIcon.setImageResource(R.mipmap.basketball);
                individualRb.setEnabled(false);
                modalLayout.setVisibility(View.GONE);
                break;
            case FOOTBALL:
                individualRb.setEnabled(false);
                modalLayout.setVisibility(View.GONE);
                modalIcon.setImageResource(R.mipmap.football);
                break;
            case FUTSAL:
                individualRb.setEnabled(false);
                modalLayout.setVisibility(View.GONE);
                modalIcon.setImageResource(R.mipmap.futsal);
                break;
            case HANDBALL:
                individualRb.setEnabled(false);
                modalLayout.setVisibility(View.GONE);
                modalIcon.setImageResource(R.mipmap.handball);
                break;
            case TENNIS:
                compLayout.setVisibility(View.GONE);
                modalIcon.setImageResource(R.mipmap.tennis);
                break;
            case VOLLEY:
                individualRb.setEnabled(false);
                modalLayout.setVisibility(View.GONE);
                compLayout.setVisibility(View.GONE);
                modalIcon.setImageResource(R.mipmap.volley);
                break;
        }

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
        String modalCp = getResources().getString(modal);
        boolean indivCp = individualRb.isChecked();
        boolean cupCp = cupRb.isChecked();

        try {
            ChampionshipController.getInstance(getApplicationContext()).createChampionship(nameCp, modalCp, indivCp, cupCp);
            Intent intent = new Intent(this, MainActivity.class);
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
