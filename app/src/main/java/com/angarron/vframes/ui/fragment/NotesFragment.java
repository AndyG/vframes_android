package com.angarron.vframes.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angarron.vframes.R;
import com.angarron.vframes.ui.activity.NotesActivity;
import com.angarron.vframes.util.CharacterResourceUtil;

import data.model.CharacterID;

public class NotesFragment extends Fragment implements View.OnClickListener {

    public static final String CHARACTER_ID = "CHARACTER_ID";

    private CharacterID characterId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        characterId = getCharacterIdFromArguments();

        View notesLayout = inflater.inflate(R.layout.notes_fragment_layout, container, false);
        CardView generalNotesCard = (CardView) notesLayout.findViewById(R.id.general_notes_card);
        generalNotesCard.setOnClickListener(this);
        setupGeneralNotesCard(generalNotesCard);

        GridLayout matchupSelectorGrid = (GridLayout) notesLayout.findViewById(R.id.character_select_gridlayout);
        setupMatchupClickListeners(matchupSelectorGrid);
        return notesLayout;
    }

    private void setupMatchupClickListeners(GridLayout matchupSelectorGrid) {
        CharacterCardClickListener characterCardClickListener = new CharacterCardClickListener();
        for (int i = 0; i < matchupSelectorGrid.getChildCount(); i++) {
            matchupSelectorGrid.getChildAt(i).setOnClickListener(characterCardClickListener);
        }
    }

    private void setupGeneralNotesCard(CardView generalNotesCard) {
        TextView generalNotesText = (TextView) generalNotesCard.findViewById(R.id.general_notes_text);
        String characterDisplayName = CharacterResourceUtil.getCharacterDisplayName(getContext(), characterId);
        generalNotesText.setText(getString(R.string.general_notes_text_format, characterDisplayName));
    }

    private CharacterID getCharacterIdFromArguments() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(CHARACTER_ID)) {
            return (CharacterID) bundle.getSerializable(CHARACTER_ID);
        } else {
            throw new RuntimeException("no character id for FrameDataFragment");
        }
    }

   private void onGeneralNoteSelected() {
        Intent intent = new Intent(getActivity(), NotesActivity.class);
        intent.putExtra(NotesActivity.INTENT_EXTRA_NOTES_TYPE, NotesActivity.NOTES_TYPE_CHARACTER_GENERAL);
        intent.putExtra(NotesActivity.INTENT_EXTRA_CHARACTER, characterId);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.stay_still);
    }

    private void onMatchupNoteSelected(CharacterID secondCharacter) {
        Intent intent = new Intent(getActivity(), NotesActivity.class);
        intent.putExtra(NotesActivity.INTENT_EXTRA_NOTES_TYPE, NotesActivity.NOTES_TYPE_MATCHUP);
        intent.putExtra(NotesActivity.INTENT_EXTRA_CHARACTER, characterId);
        intent.putExtra(NotesActivity.INTENT_EXTRA_SECOND_CHARACTER, secondCharacter);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_up, R.anim.stay_still);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.general_notes_card:
                onGeneralNoteSelected();
        }
    }

    private class CharacterCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            CharacterID clickedCharacter = getClickedCharacter(v.getId());
            onMatchupNoteSelected(clickedCharacter);
        }
    }

    private CharacterID getClickedCharacter(int viewId) {
        switch (viewId) {
            case R.id.birdie_card:
                return CharacterID.BIRDIE;
            case R.id.cammy_card:
                return CharacterID.CAMMY;
            case R.id.ryu_card:
                return CharacterID.RYU;
            case R.id.chun_card:
                return CharacterID.CHUN;
            case R.id.dictator_card:
                return CharacterID.DICTATOR;
            case R.id.nash_card:
                return CharacterID.NASH;
            case R.id.fang_card:
                return CharacterID.FANG;
            case R.id.laura_card:
                return CharacterID.LAURA;
            case R.id.karin_card:
                return CharacterID.KARIN;
            case R.id.mika_card:
                return CharacterID.MIKA;
            case R.id.zangief_card:
                return CharacterID.ZANGIEF;
            case R.id.necalli_card:
                return CharacterID.NECALLI;
            case R.id.claw_card:
                return CharacterID.CLAW;
            case R.id.dhalsim_card:
                return CharacterID.DHALSIM;
            case R.id.ken_card:
                return CharacterID.KEN;
            case R.id.rashid_card:
                return CharacterID.RASHID;
            default:
                throw new RuntimeException("clicked invalid character card");
        }
    }
}
