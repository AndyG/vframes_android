package com.angarron.vframes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angarron.vframes.R;
import com.angarron.vframes.util.CharacterResourceUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import data.model.CharacterID;

/**
 * Created by Lennon on 2/8/2016.
 */
public class NotesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_NOTE = 2;

    private final Context context;
    private CharacterID targetCharacter;
    private INotesSelectionListener listener;

    private List<CharacterID> characterList;


    public NotesRecyclerViewAdapter(Context context, CharacterID targetCharacter, INotesSelectionListener listener) {
        this.context = context;
        this.targetCharacter = targetCharacter;
        this.listener = listener;
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
            setupHeader((HeaderItemViewHolder)holder, position);
        }
    }

    private void setupHeader(HeaderItemViewHolder holder, int position) {
        if (position == 0) {
            holder.label.setText(R.string.notes_general);
        } else if (position == 2) {
            holder.label.setText(R.string.matchups);
        }
    }

    private void setupNoteItemViewHolder(NoteItemViewHolder holder, int position) {
        if (position == 1) {
            //General Notes Item

            holder.itemView.setOnClickListener(new GeneralNotesClickListener());

            String characterDisplayName = getCharacterDisplayName();
            String noteTitle = context.getString(R.string.general_notes_format, characterDisplayName);
            holder.label.setText(noteTitle);
        } else {
            //Matchup Notes Item
            CharacterID opponentCharactedId = characterList.get(position - 3);

            holder.itemView.setOnClickListener(new MatchupNotesClickListener(opponentCharactedId));

            String opponentDisplayName = CharacterResourceUtil.getCharacterDisplayName(context, opponentCharactedId);
            String noteTitle = context.getString(R.string.matchup_notes_format, opponentDisplayName);
            holder.label.setText(noteTitle);
        }
    }

    private void setupCharacterList() {
        this.characterList = new ArrayList<>();
        // Add all characters
        Collections.addAll(characterList, CharacterID.values());

        // Sort alphabetically
        Collections.sort(characterList, new Comparator<CharacterID>() {
            @Override
            public int compare(CharacterID lhs, CharacterID rhs) {
                String firstCharacterName = CharacterResourceUtil.getCharacterDisplayName(context, lhs);
                String secondCharacterName = CharacterResourceUtil.getCharacterDisplayName(context, rhs);
                return firstCharacterName.compareTo(secondCharacterName);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 || position == 2) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_NOTE;
        }
    }

    @Override
    public int getItemCount() {
        return 3 + CharacterID.values().length;
    }

    private String getCharacterDisplayName() {
        return CharacterResourceUtil.getCharacterDisplayName(context, targetCharacter);
    }

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

    private class GeneralNotesClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //general notes clicked
            Log.d("findme", "clicked general notes for : " + targetCharacter.toString());
            listener.onGeneralNoteSelected(targetCharacter);
        }
    }

    private class MatchupNotesClickListener implements View.OnClickListener {

        private CharacterID opponentCharacterId;

        public MatchupNotesClickListener(CharacterID opponentCharactedId) {
            this.opponentCharacterId = opponentCharactedId;
        }

        @Override
        public void onClick(View view) {
            //matchup clicked
            Log.d("findme", "clicked matchup notes for : " + targetCharacter.toString() + " vs " + opponentCharacterId.toString());
            listener.onMatchupNoteSelected(targetCharacter, opponentCharacterId);
        }
    }

    public interface INotesSelectionListener {
        void onGeneralNoteSelected(CharacterID characterID);
        void onMatchupNoteSelected(CharacterID firstCharacter, CharacterID secondCharacter);
    }
}
