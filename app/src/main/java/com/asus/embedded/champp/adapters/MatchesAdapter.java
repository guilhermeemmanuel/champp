package com.asus.embedded.champp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.asus.embedded.champp.R;
import com.asus.embedded.champp.listeners.MatchListener;
import com.asus.embedded.champp.model.Match;

import java.util.ArrayList;
import java.util.List;

public class MatchesAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Match> items;
    private boolean isCup;

    private List<MatchListener> listeners;

    public MatchesAdapter(Context context, List<Match> items, boolean isCup) {
        //Itens que preencheram o listview
        this.items = items;
        listeners = new ArrayList();
        this.isCup = isCup;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
    }

    public void addListener(MatchListener listener) {
        this.listeners.add(listener);
    }

    //Retorna a quantidade de Itens
    @Override
    public int getCount() {
        return items.size();
    }

    /**
     * Retorna o item de acordo com a posicao dele na tela.
     *
     * @param position
     * @return
     */
    public Match getItem(int position) {
        return items.get(position);
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
        final Match item = items.get(position);

//        Log.i("Array", Arrays.asList(items).toString());
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

        holder.set = ((ImageButton) view.findViewById(R.id.set_score_bt));

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
            if (isCup && !item.isHomeWin() && item.getHomeScore() == item.getVisitantScore()) {
                ((TextView) view.findViewById(R.id.home_team_score_tv)).setText(" " + item.getHomeScore() + " (" + item.getHomePenalty() +") ");
                ((TextView) view.findViewById(R.id.visitant_team_score_tv)).setText(" (" + item.getVisPenalty() +") " + item.getVisitantScore() + " ");
            }
            else {
                ((TextView) view.findViewById(R.id.home_team_score_tv)).setText(" " + item.getHomeScore() + " ");
                ((TextView) view.findViewById(R.id.visitant_team_score_tv)).setText(" " + item.getVisitantScore() + " ");
            }
            holder.home.setVisibility(View.GONE);
            holder.vis.setVisibility(View.GONE);

            ((ImageButton) view.findViewById(R.id.set_score_bt)).setVisibility(View.INVISIBLE);

        }




        //((TextView) view.findViewById(R.id.match_tv)).setText(item.toString());


        return view;
    }

    static class Holder {
        EditText home;
        EditText vis;
        ImageButton set;
        TextView number;
    }


    public void setScore(View view) {

    }


    public void updateItems(List<Match> items) {
        this.items = items;
        notifyDataSetChanged();
    }

}
