package com.angarron.vframes.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angarron.vframes.R;
import com.angarron.vframes.adapter.NotesRecyclerViewAdapter;
import com.angarron.vframes.ui.activity.NotesActivity;

import data.model.CharacterID;

/**
 * Created by Lennon on 2/8/2016.
 */
public class NotesFragment extends Fragment implements NotesRecyclerViewAdapter.INotesSelectionListener {

    public static final String CHARACTER_ID = "CHARACTER_ID";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        RecyclerView notesRecyclerView = (RecyclerView) inflater.inflate(R.layout.notes_fragment_layout, container, false);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        notesRecyclerView.setAdapter(new NotesRecyclerViewAdapter(getContext(), getCharacterIdFromArguments(), this));

        return notesRecyclerView;
    }

    private CharacterID getCharacterIdFromArguments() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(CHARACTER_ID)) {
            return (CharacterID) bundle.getSerializable(CHARACTER_ID);
        } else {
            throw new RuntimeException("no character id for FrameDataFragment");
        }
    }

    @Override
    public void onGeneralNoteSelected(CharacterID characterID) {
        Intent intent = new Intent(getActivity(), NotesActivity.class);
        intent.putExtra(NotesActivity.INTENT_EXTRA_NOTES_TYPE, NotesActivity.NOTES_TYPE_CHARACTER_GENERAL);
        intent.putExtra(NotesActivity.INTENT_EXTRA_CHARACTER, characterID);
        getActivity().startActivity(intent);
    }

    @Override
    public void onMatchupNoteSelected(CharacterID firstCharacter, CharacterID secondCharacter) {
        Intent intent = new Intent(getActivity(), NotesActivity.class);
        intent.putExtra(NotesActivity.INTENT_EXTRA_NOTES_TYPE, NotesActivity.NOTES_TYPE_MATCHUP);
        intent.putExtra(NotesActivity.INTENT_EXTRA_CHARACTER, firstCharacter);
        intent.putExtra(NotesActivity.INTENT_EXTRA_SECOND_CHARACTER, secondCharacter);
        getActivity().startActivity(intent);
    }

}
