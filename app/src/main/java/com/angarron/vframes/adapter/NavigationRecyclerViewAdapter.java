package com.angarron.vframes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.angarron.vframes.R;

public class NavigationRecyclerViewAdapter extends RecyclerView.Adapter {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private IMenuClickListener listener;
    private Context context;

    public NavigationRecyclerViewAdapter(IMenuClickListener listener, Context context) {
        this.listener = listener;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case TYPE_HEADER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_recycler_header, parent, false);
                return new HeaderViewHolder(v);
            case TYPE_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_recycler_item, parent, false);
                return new ItemViewHolder(v);
        }

        throw new RuntimeException("could not resolve appropriate view for type: " + viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0 || position == 2) {
            setupHeader((HeaderViewHolder) holder, position);
        } else {
            setupMenuItem((ItemViewHolder) holder, position);
        }
    }

    private void setupMenuItem(ItemViewHolder holder, int position) {
        if (position == 1) {
            holder.label.setText(context.getString(R.string.character_data));
            holder.icon.setImageResource(R.drawable.ic_action_group);
        } else if (position == 3) {
            holder.label.setText(context.getString(R.string.live_streams));
            holder.icon.setImageResource(R.drawable.ic_action_play);
        }
        holder.itemView.setOnClickListener(new ItemClickListener(position));
    }

    private void setupHeader(HeaderViewHolder holder, int position) {
        if (position == 0) {
            holder.label.setText(context.getString(R.string.references));
        } else if (position == 2) {
            holder.label.setText(context.getString(R.string.community));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == 2) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView label;
        private ImageView icon;

        public ItemViewHolder(View v) {
            super(v);
            label = (TextView) v.findViewById(R.id.label);
            icon = (ImageView) v.findViewById(R.id.navigation_icon);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private TextView label;

        public HeaderViewHolder(View v) {
            super(v);
            label = (TextView) v.findViewById(R.id.label);
        }
    }

    private class ItemClickListener implements View.OnClickListener {

        int position;

        public ItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            if (position == 1) {
                listener.onCharacterDataClicked();
            } else {
                listener.onStreamersClicked();
            }
        }
    }

    public interface IMenuClickListener {
        void onCharacterDataClicked();
        void onStreamersClicked();
    }
}
