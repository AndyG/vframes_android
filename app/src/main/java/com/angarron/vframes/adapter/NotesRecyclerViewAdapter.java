package com.angarron.vframes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angarron.vframes.R;
import com.angarron.vframes.application.VFramesApplication;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import data.model.CharacterID;
import data.model.move.IMoveListEntry;
import data.model.move.MoveCategory;

/**
 * Created by Lennon on 2/8/2016.
 */
public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String INTENT_EXTRA_TARGET_CHARACTER = "INTENT_EXTRA_TARGET_CHARACTER";

    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_NOTE = 2;
    private final Context context;
    private CharacterID targetCharacter;
    private ArrayList<CharacterID> characterList;

    public NotesRecyclerViewAdapter(Context context, CharacterID targetCharacter) {
        this.context = context;
        this.targetCharacter = targetCharacter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        setupCharacterList();
        View v;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header, parent, false);
                return new HeaderItemViewHolder(v);
            case VIEW_TYPE_NOTE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list_item, parent, false);
                return new NoteItemViewHolder(v);
            default:
                throw new RuntimeException("unable to find ViewHolder for view type: " + viewType);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof NoteItemViewHolder) {
            setupNoteItemViewHolder((NoteItemViewHolder) holder, position);
        } else if (holder instanceof HeaderItemViewHolder) {
            if (position == 0) {
                ((HeaderItemViewHolder) holder).label.setText("General");
            }
            else if (position == 3) {
                ((HeaderItemViewHolder) holder).label.setText(context.getString(getNameResource(targetCharacter)) + " matchups");
            }
        }
    }

    private void setupNoteItemViewHolder(NoteItemViewHolder holder, int position) {
        if(position > 3) {
            holder.label.setText(String.valueOf(context.getString(getNameResource(targetCharacter)) + " vs. " + context.getString(getNameResource(characterList.get(position - 4)))));
        } else if (position == 1) {
            holder.label.setText("As " + context.getString(getNameResource(targetCharacter)));
        } else if (position == 2) {
            holder.label.setText("vs " + context.getString(getNameResource(targetCharacter)));
        } else {
            holder.label.setText(String.valueOf(position));
        }
    }

    private void setupCharacterList() {
        this.characterList = new ArrayList<CharacterID>();
        // Add all characters
        for (CharacterID c : CharacterID.values()) {
            characterList.add(c);
        }
        // Sort alphabetically
        Collections.sort(characterList, new Comparator<CharacterID>() {
            @Override
            public int compare(CharacterID lhs, CharacterID rhs) {
                return context.getString(getNameResource(lhs)).compareTo(context.getString(getNameResource(rhs)));
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 || position == 3) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_NOTE;
        }

        /*
        if (displayList.get(position) instanceof MoveCategory) {
            return VIEW_TYPE_HEADER;
        } else if (displayList.get(position) instanceof IMoveListEntry) {
            return VIEW_TYPE_MOVE;
        } else {
            throw new RuntimeException("could not resolve Object to category: " + displayList.get(position).getClass().getSimpleName());
        }
        */
    }

    @Override
    public int getItemCount() {
        return 20;
        //displayList.size();
    }
/*
    private void setupDisplayList(Map<MoveCategory, List<IMoveListEntry>> moves) {
        for (MoveCategory category : categoriesOrder) {
            if (moves.containsKey(category) && !moves.get(category).isEmpty()) {
                displayList.add(category);
                for (IMoveListEntry move : moves.get(category)) {
                    displayList.add(move);
                }
            }
        }
    }
*/
    private class HeaderItemViewHolder extends RecyclerView.ViewHolder {

        private TextView label;

        public HeaderItemViewHolder(View v) {
            super(v);
            label = (TextView) v.findViewById(R.id.label);
        }
    }

    private class NoteItemViewHolder extends RecyclerView.ViewHolder {
        private TextView label;

        public NoteItemViewHolder(View v) {
            super(v);
            label = (TextView) v.findViewById(R.id.label);
        }
    }

    private int getNameResource(CharacterID characterID) {
        switch(characterID) {
            case RYU:
                return R.string.ryu_name;
            case CHUN:
                return R.string.chun_name;
            case DICTATOR:
                return R.string.dictator_name;
            case BIRDIE:
                return R.string.birdie_name;
            case NASH:
                return R.string.nash_name;
            case CAMMY:
                return R.string.cammy_name;
            case KEN:
                return R.string.ken_name;
            case MIKA:
                return R.string.mika_name;
            case NECALLI:
                return R.string.necalli_name;
            case CLAW:
                return R.string.claw_name;
            case RASHID:
                return R.string.rashid_name;
            case KARIN:
                return R.string.karin_name;
            case LAURA:
                return R.string.laura_name;
            case DHALSIM:
                return R.string.dhalsim_name;
            case ZANGIEF:
                return R.string.zangief_name;
            case FANG:
                return R.string.fang_name;
            default:
                throw new RuntimeException("unable to resolve character name: " + targetCharacter);
        }
    }


}
