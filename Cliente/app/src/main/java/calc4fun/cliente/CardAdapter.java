package calc4fun.cliente;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import calc4fun.cliente.DataTypes.DataCardList;
import calc4fun.cliente.DataTypes.DataStringWrapper;


/**
 * Created by vrs on 3/9/15.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context mContext;
    List<DataCardList> list = new ArrayList<>();


    public CardAdapter(Context mContext, List<DataCardList> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItem(position) instanceof DataStringWrapper) {
            holder.dcd = getItem(position);
            holder.cardtitle.setText(((DataStringWrapper) list.get(position)).getData());
        } else
        {
           //TODO: que pasa si no es tipo soportado
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public DataCardList getItem(int i) {
        return list.get(i);
    }





    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView cardtitle;
        DataCardList dcd;

        public ViewHolder(View itemView) {
            super(itemView);
            cardtitle = (TextView) itemView.findViewById(R.id.cardtitle);
        }
    }


}

