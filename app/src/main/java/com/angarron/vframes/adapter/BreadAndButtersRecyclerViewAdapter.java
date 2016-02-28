package com.angarron.vframes.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angarron.vframes.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.model.character.bnb.BreadAndButterCombo;
import data.model.character.bnb.BreadAndButterModel;

public class BreadAndButtersRecyclerViewAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_BNB = 1;

    private List<Object> displayList;

    public BreadAndButtersRecyclerViewAdapter(BreadAndButterModel breadAndButterModel) {
        setupDisplayList(breadAndButterModel);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header, parent, false);
                return new HeaderItemViewHolder(v);
            case VIEW_TYPE_BNB:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bnb_item, parent, false);
                return new BnBItemViewHolder(v);
            default:
                throw new RuntimeException("unable to find ViewHolder for view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof BnBItemViewHolder) {
            setupBnBItemViewHolder((BnBItemViewHolder) holder, position);
        } else if (holder instanceof HeaderItemViewHolder) {
            HeaderItemViewHolder headerItemViewHolder = (HeaderItemViewHolder) holder;
            headerItemViewHolder.label.setText((String) displayList.get(position));
            if (position == 0) {
                headerItemViewHolder.setTopMargin(15);
            } else {
                headerItemViewHolder.setTopMargin(50);
            }
        }
    }

    private void setupBnBItemViewHolder(BnBItemViewHolder holder, int position) {
        BreadAndButterCombo combo = (BreadAndButterCombo) displayList.get(position);
        holder.setupView(combo);
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (displayList.get(position) instanceof String) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_BNB;
        }
    }

    private void setupDisplayList(BreadAndButterModel breadAndButterModel) {
        displayList = new ArrayList<>();
        for (Map.Entry<String, List<BreadAndButterCombo>> bnbCategory : breadAndButterModel.getBreadAndButters().entrySet()) {

            displayList.add(bnbCategory.getKey());

            for (BreadAndButterCombo combo : bnbCategory.getValue()) {
                displayList.add(combo);
            }
        }
    }



    private class HeaderItemViewHolder extends RecyclerView.ViewHolder {

        private View rowContainer;
        private TextView label;

        private HeaderItemViewHolder(View v) {
            super(v);
            rowContainer = v;
            label = (TextView) v.findViewById(R.id.label);
        }

        private void setTopMargin(int topMarginPx) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) rowContainer.getLayoutParams();
            params.setMargins(0, topMarginPx, 0, 0);
            rowContainer.setLayoutParams(params);
        }
    }


    private class BnBItemViewHolder extends RecyclerView.ViewHolder {

        private TextView label;
        private TextView inputs;
        private TextView description;

        private BnBItemViewHolder(View v) {
            super(v);
            label = (TextView) v.findViewById(R.id.bnb_label);
            inputs = (TextView) v.findViewById(R.id.bnb_inputs);
            description = (TextView) v.findViewById(R.id.bnb_description);
        }

        private void setupView(BreadAndButterCombo combo) {
            label.setText(combo.getLabel());
            inputs.setText(combo.getInputs());
            description.setText(combo.getDescription());
        }
    }
}
