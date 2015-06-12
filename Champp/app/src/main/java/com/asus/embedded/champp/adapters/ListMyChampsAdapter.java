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
import com.asus.embedded.champp.model.Championship;

import java.util.List;

public class ListMyChampsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Championship> items;
    private Context myContext;

    public ListMyChampsAdapter(Context context, List<Championship> items) {
        //Itens que preencheram o listview
        this.items = items;
        //responsavel por pegar o Layout do item.
        mInflater = LayoutInflater.from(context);
        myContext = context;
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
    public Championship getItem(int position) {
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
        Championship item = items.get(position);
        //infla o layout para podermos preencher os dados
        view = mInflater.inflate(R.layout.item_layout, null);

        //atraves do layout pego pelo LayoutInflater, pegamos cada id relacionado
        //ao item e definimos as informações.
        ((TextView) view.findViewById(R.id.champ_name)).setText(item.getName());

        ((ImageView) view.findViewById(R.id.icon_champ)).setImageResource(getIdImage(item));

        ((Button) view.findViewById(R.id.delete_champ_bt)).setTag(position);

        return view;
    }

    private int getIdImage(Championship item){
        final String BASKETBALL = myContext.getResources().getString(R.string.basketball);
        final String FOOTBALL = myContext.getResources().getString(R.string.football);
        final String FUTSAL = myContext.getResources().getString(R.string.futsal);
        final String HANDBALL = myContext.getResources().getString(R.string.handball);
        final String TENNIS = myContext.getResources().getString(R.string.tennis);
        final String VOLLEY = myContext.getResources().getString(R.string.volley);

        if(item.getModal().equals(BASKETBALL)){
            return R.mipmap.lbasketball;
        }else if(item.getModal().equals(FOOTBALL)){
            return R.mipmap.lfootball;
        }else if(item.getModal().equals(FUTSAL)){
            return R.mipmap.lfootball;
        }else if(item.getModal().equals(HANDBALL)){
            return R.mipmap.lhandball;
        }else if(item.getModal().equals(TENNIS)){
            return R.mipmap.ltennis;
        }else if(item.getModal().equals(VOLLEY)){
            return R.mipmap.lvolley;
        }
        return -1;
    }


    public void removeItem(int positionToRemove){
        items.remove(positionToRemove);
        notifyDataSetChanged();
    }

    public void updateItems(List<Championship> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
