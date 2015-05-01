package com.asus.embedded.champp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.asus.embedded.champp.R;
import com.asus.embedded.champp.model.Match;
import com.asus.embedded.champp.model.Participant;

import java.util.ArrayList;
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
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.matches_layout, null);

        //atraves do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.match_tv)).setText(item.toString());

        // Aqui agnt pode colocar a imagem pra indicar tipo a modalidade
        //((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());

        //((Button) view.findViewById(R.id.participant_delete_bt)).setTag(position);

        return view;
    }




    public void updateItens(ArrayList<Match> itens) {
        this.itens = itens;
        notifyDataSetChanged();
    }

}
