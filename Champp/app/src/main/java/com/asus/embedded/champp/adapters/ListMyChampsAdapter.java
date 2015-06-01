package com.asus.embedded.champp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.asus.embedded.champp.R;
import com.asus.embedded.champp.model.Championship;

import java.util.ArrayList;
import java.util.List;

public class ListMyChampsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Championship> itens;

    public ListMyChampsAdapter(Context context, List<Championship> itens) {
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
    public Championship getItem(int position) {
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
        Championship item = itens.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.item_layout, null);

        //atraves do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.text)).setText(item.getName());

        // Aqui agnt pode colocar a imagem pra indicar tipo a modalidade
        //((ImageView) view.findViewById(R.id.imagemview)).setImageResource(item.getIconeRid());

        ((Button) view.findViewById(R.id.btnDelete)).setTag(position);

        return view;
    }


    public void removeItem(int positionToRemove){
        itens.remove(positionToRemove);
        notifyDataSetChanged();
    }

    public void updateItens(List<Championship> itens) {
        this.itens = itens;
        notifyDataSetChanged();
    }
}
