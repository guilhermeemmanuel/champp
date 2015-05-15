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
import android.widget.Toast;

import com.asus.embedded.champp.R;
import com.asus.embedded.champp.listeners.MatchListener;
import com.asus.embedded.champp.model.Match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Guilherme-PC on 01/05/2015.
 */
public class MatchesAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Match> itens;

    private List<MatchListener> listeners;

    public MatchesAdapter(Context context, List<Match> itens) {
        //Itens que preencheram o listview
        this.itens = itens;
        listeners = new ArrayList<>();
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    public void addListener(MatchListener listener) {
        this.listeners.add(listener);
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


        ((TextView) view.findViewById(R.id.home_team_tv)).setText(item.getHome().getName());
        ((TextView) view.findViewById(R.id.visitant_team_tv)).setText(item.getVisitant().getName());
        final Holder holder = new Holder();

        holder.number = (TextView) view.findViewById(R.id.number_tv);

        holder.number.setText("" + item.getNumber());
        holder.home = ((EditText) view.findViewById(R.id.home_team_score_et));
        holder.vis = ((EditText) view.findViewById(R.id.visitant_team_score_et));

        holder.set = ((Button) view.findViewById(R.id.set_score_bt));
        //bt.setTag(position);
        holder.set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hScore = holder.home.getText().toString();
                String vScore = holder.vis.getText().toString();
                String num = holder.number.getText().toString();

                try{
                    int homeScore = Integer.parseInt(hScore);
                    int visitantScore = Integer.parseInt(vScore);
                    int matchNumber = Integer.parseInt(num);
                    for (MatchListener listener : listeners) {
                        listener.setScore(matchNumber, homeScore, visitantScore);
                    }

                } catch(Exception ex) {
                    Toast.makeText(view.getContext(), holder.home.getText().toString(), Toast.LENGTH_LONG).show();
                }


            }
        });

        if (item.isFinished()) {
            ((TextView) view.findViewById(R.id.home_team_score_tv)).setText(" " + item.getHomeScore() + " ");
            ((TextView) view.findViewById(R.id.visitant_team_score_tv)).setText(" " + item.getVisitantScore() + " ");

            holder.home.setVisibility(View.GONE);
            holder.vis.setVisibility(View.GONE);

            ((Button) view.findViewById(R.id.set_score_bt)).setVisibility(View.INVISIBLE);

        }

        //((TextView) view.findViewById(R.id.match_tv)).setText(item.toString());

        // Aqui agnt pode colocar a imagem pra indicar tipo a modalidade
        //((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());


        return view;
    }

    static class Holder {
        EditText home;
        EditText vis;
        Button set;
        TextView number;
    }


    public void setScore(View view) {

    }


    public void updateItens(List<Match> itens) {
        this.itens = itens;
        notifyDataSetChanged();
    }

}
