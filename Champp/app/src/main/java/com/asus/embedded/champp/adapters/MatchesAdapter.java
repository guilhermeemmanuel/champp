package com.asus.embedded.champp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.asus.embedded.champp.R;
import com.asus.embedded.champp.model.Match;
import com.asus.embedded.champp.model.Participant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Guilherme-PC on 01/05/2015.
 */
public class MatchesAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Match> itens;

    public MatchesAdapter(Context context, List<Match> itens) {
        //Itens que preencheram o listview
        this.itens = itens;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    //Retorna a quantidade de Itens
    @Override
    public int getCount() {
        return itens.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */
    public Match getItem(int position) {
        return itens.get(position);
    }

    /**
     * Sem implementação
     *
     * @param position
     * @return
     */
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        //Pega o item de acordo com a posção.
        Match item = itens.get(position);

        Log.i("Array", Arrays.asList(itens).toString());
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.matches_layout, null);

        //atraves do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.round_tv)).setText(item.getRound());
        ((TextView) view.findViewById(R.id.number_tv)).setText("#" + item.getNumber());
        ((TextView) view.findViewById(R.id.home_team_tv)).setText(item.getHome().getName());
        ((TextView) view.findViewById(R.id.visitant_team_tv)).setText(item.getVisitant().getName());
        ((Button) view.findViewById(R.id.set_score_bt)).setTag(position);

        if (item.isFinished()) {
            ((TextView) view.findViewById(R.id.home_team_score_tv)).setText(" " + item.getHomeScore() + " ");
            ((TextView) view.findViewById(R.id.visitant_team_score_tv)).setText(" " + item.getVisitantScore() + " ");

            ((EditText) view.findViewById(R.id.home_team_score_et)).setVisibility(View.GONE);
            ((EditText) view.findViewById(R.id.visitant_team_score_et)).setVisibility(View.GONE);
            ((Button) view.findViewById(R.id.set_score_bt)).setVisibility(View.INVISIBLE);

        }

        //((TextView) view.findViewById(R.id.match_tv)).setText(item.toString());

        // Aqui agnt pode colocar a imagem pra indicar tipo a modalidade
        //((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());


        return view;
    }


    public void setScore(View view) {

    }


    public void updateItens(List<Match> itens) {
        this.itens = itens;
        notifyDataSetChanged();
    }

}
