package com.asus.embedded.champp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.asus.embedded.champp.R;
import com.asus.embedded.champp.model.Participant;

import java.util.ArrayList;
import java.util.List;

public class RankingAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Participant> items;

    public RankingAdapter(Context context, List<Participant> items) {
        //Itens que preencheram o listview
        this.items = items;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
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
    public Participant getItem(int position) {
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
        Participant participant = items.get(position);

        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.ranking_adapter_item, null);

        TextView tvParticipantName = (TextView) view.findViewById(R.id.tv_team_name);
        TextView tvParticipantScore = (TextView) view.findViewById(R.id.tv_team_score);
        TextView tvParticipantGames = (TextView) view.findViewById(R.id.tv_number_games);
        TextView tvParticipantNumberWin = (TextView) view.findViewById(R.id.tv_number_win);
        TextView tvParticipantNumberDefeats = (TextView) view.findViewById(R.id.tv_number_defeats);
        TextView tvParticipantDraw = (TextView) view.findViewById(R.id.tv_draw);
        TextView tvParticipantGoalsPro = (TextView) view.findViewById(R.id.tv_goals_pro);
        TextView tvParticipantGoalsAgainst = (TextView) view.findViewById(R.id.tv_goals_against);
        TextView tvParticipantBalance = (TextView) view.findViewById(R.id.tv_balance);

        tvParticipantName.setText(participant.getName());
        tvParticipantScore.setText(participant.getScore() + "|");
        tvParticipantGames.setText(participant.getNumberGames()+ "|");
        tvParticipantNumberWin.setText(participant.getNumberWins()+ "|");
        tvParticipantNumberDefeats.setText(participant.getNumberDefeats()+ "|");
        tvParticipantDraw.setText(participant.getDraw() + "|");
        tvParticipantGoalsPro.setText(participant.getGoalsPro()+ "|");
        tvParticipantGoalsAgainst.setText(participant.getGoalsAgainst()+ "|");
        tvParticipantBalance.setText(participant.getBalance()+ "");

        return view;
    }


    public void removeItem(int positionToRemove){
        items.remove(positionToRemove);
        notifyDataSetChanged();
    }

    public void updateItems(ArrayList<Participant> items) {
        this.items = items;
        notifyDataSetChanged();
    }


}
