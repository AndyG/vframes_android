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

    private static final int CHARACTERS_POSITION = 1;
    private static final int STREAMS_POSITION = 3;
    private static final int GUIDES_POSITION = 4;
    private static final int TOURNAMENT_MATCHES_POSITION = 5;

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
        if (position == CHARACTERS_POSITION) {
            holder.label.setText(context.getString(R.string.character_data));
            holder.icon.setImageResource(R.drawable.ic_action_group);
        } else if (position == STREAMS_POSITION) {
            holder.label.setText(context.getString(R.string.live_streams));
            holder.icon.setImageResource(R.drawable.ic_action_play);
        } else if (position == GUIDES_POSITION) {
            holder.label.setText(context.getString(R.string.street_fighter_v_guides));
            holder.icon.setImageResource(R.drawable.ic_action_view_as_list);
        } else if (position == TOURNAMENT_MATCHES_POSITION) {
            holder.label.setText(context.getString(R.string.recent_tournament_matches));
            holder.icon.setImageResource(R.drawable.ic_action_gamepad);
        }
        holder.itemView.setOnClickListener(new ItemClickListener(position));
    }

    private void setupHeader(HeaderViewHolder holder, int position) {
        if (position == 0) {
            holder.label.setText(context.getString(R.string.references));
        } else if (position == 2) {
            holder.label.setText(context.getString(R.string.content));
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
        return 6;
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
            switch (position) {
                case CHARACTERS_POSITION:
                    listener.onCharacterDataClicked();
                    break;
                case STREAMS_POSITION:
                    listener.onStreamersClicked();
                    break;
                case GUIDES_POSITION:
                    listener.onGuideVideosClicked();
                    break;
                case TOURNAMENT_MATCHES_POSITION:
                    listener.onTournamentVideosClicked();
                    break;
            }
        }
    }

    public interface IMenuClickListener {
        void onCharacterDataClicked();
        void onStreamersClicked();
        void onTournamentVideosClicked();
        void onGuideVideosClicked();
    }
}
