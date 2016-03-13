package com.angarron.vframes.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.angarron.vframes.R;
import com.angarron.vframes.adapter.FrameDataRecyclerViewAdapter;
import com.angarron.vframes.adapter.NotesRecyclerViewAdapter;

import java.util.List;
import java.util.Map;

import data.model.CharacterID;
import data.model.character.FrameData;
import data.model.move.IMoveListEntry;
import data.model.move.MoveCategory;

/**
 * Created by Lennon on 2/8/2016.
 */
public class NotesFragment extends Fragment {
    private static final String ALTERNATE_FRAME_DATA_SELECTED = "ALTERNATE_FRAME_DATA_SELECTED";
    private RecyclerView frameDataRecyclerView;
    private boolean showingAlternateFrameData = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Activity hostActivity = getActivity();

        RecyclerView notesRecyclerView = (RecyclerView) inflater.inflate(R.layout.notes_fragment_layout, container, false);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(hostActivity));
        notesRecyclerView.setAdapter(new NotesRecyclerViewAdapter(getContext(), ((INotesFragmentHost) hostActivity).getTargetCharacter()));

        return notesRecyclerView;

    }

    public interface INotesFragmentHost {
        CharacterID getTargetCharacter();
    }
}
