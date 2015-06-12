package com.asus.embedded.champp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.asus.embedded.champp.R;
import com.asus.embedded.champp.model.Integrant;

import java.util.List;

public class IntegrantsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Integrant> items;

    public IntegrantsAdapter(Context context, List<Integrant> items) {
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
    public Integrant getItem(int position) {
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
        Integrant item = items.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.integrants_layout, null);

        //atraves do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.integrant_name_tv)).setText(item.getName());

        ((Button) view.findViewById(R.id.integrant_delete_bt)).setTag(position);
        return view;
    }


    public void removeItem(int positionToRemove){
        items.remove(positionToRemove);
        notifyDataSetChanged();
    }

    public void updateItems(List<Integrant> items) {
        this.items = items;
        notifyDataSetChanged();
    }


}
