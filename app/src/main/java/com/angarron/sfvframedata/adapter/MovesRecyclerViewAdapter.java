package com.angarron.sfvframedata.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angarron.sfvframedata.R;

import java.util.List;

/**
 * Created by andy on 12/20/15
 */
public class MovesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<IMoveListItem> moveListItems;

    public MovesRecyclerViewAdapter(List<IMoveListItem> moveListItems) {
        this.moveListItems = moveListItems;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.moves_list_item, parent, false);
        return new MoveItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return moveListItems.size();
    }

    private class MoveItemViewHolder extends RecyclerView.ViewHolder {
        public MoveItemViewHolder(View v) {
            super(v);
        }
    }
}
