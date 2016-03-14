package com.angarron.vframes.ui.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.angarron.vframes.R;
import com.angarron.vframes.adapter.NotesRecyclerViewAdapter;
import com.angarron.vframes.ui.activity.NotesActivity;
import com.angarron.vframes.util.CharacterResourceUtil;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

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
        File matchupNoteFile = getMatchupNoteFile(firstCharacter, secondCharacter);
        Log.d("findme", "trying to open file: " + matchupNoteFile.getAbsolutePath());
    }

    private File getMatchupNoteFile(CharacterID firstCharacter, CharacterID secondCharacter) {
        return null;
    }

    private void initializeEmptyFile(File file) {
        Log.d("findme", "initializing local file");
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
//                    loadRawFile(file);
                    Log.d("findme", "wrote local file");
                } else {
                    Log.d("findme", "failed to write local file");
                }
            } else {
                Log.d("findme", "file already existed");
            }

        } catch (IOException e) {
            Log.d("findme", e.getMessage());
            throw new RuntimeException("could not initialize local file");
        }
    }

}
