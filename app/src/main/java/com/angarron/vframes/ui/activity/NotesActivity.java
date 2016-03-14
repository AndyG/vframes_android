package com.angarron.vframes.ui.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.angarron.vframes.R;
import com.angarron.vframes.util.CharacterResourceUtil;

import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import data.model.CharacterID;

/**
 * Created by andy on 3/13/16
 */
public class NotesActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String INTENT_EXTRA_NOTES_TYPE = "INTENT_EXTRA_NOTES_TYPE";
    public static final String NOTES_TYPE_CHARACTER_GENERAL = "NOTES_TYPE_CHARACTER_GENERAL";
    public static final String NOTES_TYPE_MATCHUP = "NOTES_TYPE_MATCHUP";

    public static final String INTENT_EXTRA_CHARACTER = "INTENT_EXTRA_CHARACTER";

    private static final String VFRAMES_NOTES_DIR = "VFramesNotes";

    EditText editText;

    File fileToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity_layout);

        setupToolbar();

        View boldButton = findViewById(R.id.bold_button);
        View italicsButton = findViewById(R.id.italics_button);
        editText = (EditText) findViewById(R.id.notes_edit_text);

        boldButton.setOnClickListener(this);
        italicsButton.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent.hasExtra(INTENT_EXTRA_NOTES_TYPE)) {
            String notesType = intent.getStringExtra(INTENT_EXTRA_NOTES_TYPE);
            switch (notesType) {
                case NOTES_TYPE_CHARACTER_GENERAL:
                    prepareGeneralNotes();
                    break;
                case NOTES_TYPE_MATCHUP:
                    prepareMatchupNotes();
                    break;
                default:
                    throw new RuntimeException("invalid notes type: " + notesType);
            }
        }
    }

    private void prepareMatchupNotes() {

    }

    private void prepareGeneralNotes() {
        CharacterID characterID = (CharacterID) getIntent().getSerializableExtra(INTENT_EXTRA_CHARACTER);
        String characterDisplayName = CharacterResourceUtil.getCharacterDisplayName(this, characterID);
        setTitle(characterDisplayName + " General Notes");

        fileToEdit = getGeneralNoteFile(characterID);
        loadFileToEditText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                supportFinishAfterTransition();
                return true;
            case R.id.action_save:
                saveFile();
                return true;
            default:
                throw new RuntimeException("invalid menu item clicked");
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void loadFileToEditText() {
        try {
            String fileContents = FileUtils.readFileToString(fileToEdit);
            Spanned displayText = Html.fromHtml(fileContents);
            editText.setText(displayText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeEmptyFile(File file) {
        Log.d("findme", "initializing local file");
        try {
            if (!file.exists()) {
                if (file.createNewFile()) {
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bold_button:
                boldText();
                break;
            case R.id.italics_button:
                italicizeText();
                break;
        }
    }

    private void boldText() {
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        applyStyleSpanToSelection(boldSpan);
    }

    private void italicizeText() {
        StyleSpan italicsSpan = new StyleSpan(Typeface.ITALIC);
        applyStyleSpanToSelection(italicsSpan);
    }

    private void applyStyleSpanToSelection(StyleSpan styleSpan) {
        int selectionStart = editText.getSelectionStart();
        int selectionEnd = editText.getSelectionEnd();

        if (selectionStart > selectionEnd) {
            int temp = selectionEnd;
            selectionEnd = selectionStart;
            selectionStart = temp;
        }

        if (selectionEnd > selectionStart) {

            Spannable str = editText.getText();

            StyleSpan[] spansInSelection = str.getSpans(selectionStart, selectionEnd, StyleSpan.class);

            // If the selected text-part already has this style on it, then
            // we need to remove it

            boolean removed = false;
            for (StyleSpan spanInSelection : spansInSelection) {
                Log.d("findme", "inspecting span with style: " + spanInSelection.getStyle());
                if (spanInSelection.getStyle() == styleSpan.getStyle()) {
                    str.removeSpan(spanInSelection);
                    removed = true;
                } else {
                    Log.d("findme", "span with style: " + spanInSelection.getStyle() + " did not match style: " + styleSpan.getStyle());
                }
            }

            //Otherwise, add the style
            if (!removed) {
                str.setSpan(styleSpan, selectionStart, selectionEnd, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
    }

    private void saveFile() {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(fileToEdit);
            BufferedWriter bufferWritter = new BufferedWriter(fileWriter);
            Spanned text = editText.getText();
            String htmlText = Html.toHtml(text);
            bufferWritter.write(htmlText);
            bufferWritter.close();
            Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getGeneralNoteFile(CharacterID characterID) {

        File externalStorageDirectory = getFilesDir();

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

        File file = new File(characterNotesDirectory, "General.txt");
        if (!file.exists()) {
            initializeEmptyFile(file);
        }

        return file;
    }

    private String getCharacterNameForFile(CharacterID characterID) {
        String characterDisplayName = CharacterResourceUtil.getCharacterDisplayName(this, characterID);
        return characterDisplayName.replace(",", "");
    }
}
