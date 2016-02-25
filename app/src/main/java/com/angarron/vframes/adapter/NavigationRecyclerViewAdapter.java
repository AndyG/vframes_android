package com.angarron.vframes.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angarron.vframes.R;

public class NavigationRecyclerViewAdapter extends RecyclerView.Adapter {

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("findme", "returning an item");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_recycler_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("findme", "binding an item");

        if (position == 0) {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.label.setText("hello");
        } else {
            ItemViewHolder viewHolder = (ItemViewHolder) holder;
            viewHolder.label.setText("world");
        }
    }

    @Override
    public int getItemCount() {
        Log.d("findme", "2 item2");
        return 2;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView label;

        public ItemViewHolder(View v) {
            super(v);
            label = (TextView) v.findViewById(R.id.label);
        }
    }
}
