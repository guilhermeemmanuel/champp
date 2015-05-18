package com.asus.embedded.champp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.asus.embedded.champp.R;
import com.asus.embedded.champp.model.Participant;

import java.util.ArrayList;
import java.util.List;

/**
 * CREATED BY VINICIUS
 */
public class RankingAdapter extends BaseAdapter {


    private LayoutInflater mInflater;
    private List<Participant> itens;

    public RankingAdapter(Context context, List<Participant> itens) {
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
    public Participant getItem(int position) {
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
        Participant participant = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.ranking_adapter_item, null);

        TextView tvParticipantName = (TextView) view.findViewById(R.id.tv_team_name);
        TextView tvParticipantScore = (TextView) view.findViewById(R.id.tv_team_score);

        tvParticipantName.setText(participant.getName());
        tvParticipantScore.setText(participant.getPontuacao() + "");

        return view;
    }


    public void removeItem(int positionToRemove){
        itens.remove(positionToRemove);
        notifyDataSetChanged();
    }

    public void updateItens(ArrayList<Participant> itens) {
        this.itens = itens;
        notifyDataSetChanged();
    }


}
