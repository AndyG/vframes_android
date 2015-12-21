package com.angarron.sfvframedata.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angarron.sfvframedata.R;

import java.util.List;

/**
 * Created by andy on 12/20/15
 */
public class MovesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<IMoveListItem> moveListItems;
    private List<String> categories;

//    public MovesRecyclerViewAdapter(List<IMoveListItem> moveListItems) {
//        this.moveListItems = moveListItems;
//    }

    public MovesRecyclerViewAdapter(List<String> movesAsListItems) {
        this.categories = movesAsListItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.moves_list_item, parent, false);
        return new MoveItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MoveItemViewHolder) {
            String category = categories.get(position);
            MoveItemViewHolder moveItemViewHolder = (MoveItemViewHolder) holder;
            moveItemViewHolder.label.setText(category);
        }
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    private class MoveItemViewHolder extends RecyclerView.ViewHolder {

        private TextView label;

        public MoveItemViewHolder(View v) {
            super(v);
            label = (TextView) v.findViewById(R.id.moves_list_item_textview);
        }
    }
}
