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

    private static final String VFRAMES_NOTES_DIR = "VFramesNotes";

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
        File generalNoteFile = getGeneralNoteFile(characterID);
        Log.d("findme", "trying to open file: " + generalNoteFile.getAbsolutePath());
        openDocFile(generalNoteFile);
    }

    private File getGeneralNoteFile(CharacterID characterID) {

        File externalStorageDirectory = getContext().getFilesDir();

        if (externalStorageDirectory == null || !externalStorageDirectory.exists()) {
            Log.d("findme", "external storage directory doesn't exist");
        }
        File vFramesNotesDirectory = new File(externalStorageDirectory, VFRAMES_NOTES_DIR);
        File characterNotesDirectory = new File(vFramesNotesDirectory, getCharacterNameForFile(characterID));

        //Ensure that the directory where this file lives actually exists.

        if (!characterNotesDirectory.exists()) {
            Log.d("findme", "attempting to create: " + characterNotesDirectory.getAbsolutePath());
            if (characterNotesDirectory.mkdirs()) {
                Log.d("findme", "created directory");
            } else {
                Log.d("findme", "failed to created directory");
            }
        } else {
            Log.d("findme", "directory already existed: " + characterNotesDirectory.getAbsolutePath());
        }

        File file = new File(characterNotesDirectory, "General2.rtf");
        if (!file.exists()) {
            initializeEmptyFile(file);
        }

        return file;
    }

    private String getCharacterNameForFile(CharacterID characterID) {
        String characterDisplayName = CharacterResourceUtil.getCharacterDisplayName(getContext(), characterID);
        return characterDisplayName.replace(",", "");
    }

    @Override
    public void onMatchupNoteSelected(CharacterID firstCharacter, CharacterID secondCharacter) {
        File matchupNoteFile = getMatchupNoteFile(firstCharacter, secondCharacter);
        Log.d("findme", "trying to open file: " + matchupNoteFile.getAbsolutePath());
        openDocFile(matchupNoteFile);
    }

    private File getMatchupNoteFile(CharacterID firstCharacter, CharacterID secondCharacter) {
        return null;
    }

    private void openDocFile(File file) {
        Uri contentUriToShare = FileProvider.getUriForFile(getContext(),
                "com.agarron.vframes.notesprovider", file);

        Activity activity = getActivity();
        String mime = activity.getContentResolver().getType(contentUriToShare);

        Log.d("findme", "mime: " + mime);

        // Open file with user selected app
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(contentUriToShare, mime);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

        List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            Log.d("findme", "granting permissions to: " + packageName);
            activity.grantUriPermission(packageName, contentUriToShare, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        startActivity(intent);

        try {
            activity.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(activity, "There is no application available to open your notes file. Please download a text editor.", Toast.LENGTH_LONG).show();
        }
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

    private void loadRawFile(File file) {
        Context context = getContext();
        Resources resources = getResources();
        int identifier = resources.getIdentifier("empty_doc", "raw", context.getPackageName());
        InputStream inputStream = resources.openRawResource(identifier);

        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(inputStream, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(writer.toString().getBytes());
            Log.d("findme", "wrote file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
