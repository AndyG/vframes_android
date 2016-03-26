package com.angarron.vframes.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.angarron.vframes.R;
import com.angarron.vframes.adapter.BreadAndButtersRecyclerViewAdapter;
import com.angarron.vframes.application.VFramesApplication;
import com.angarron.vframes.util.CharacterResourceUtil;

import data.model.CharacterID;
import data.model.IDataModel;
import data.model.character.SFCharacter;
import data.model.character.bnb.BreadAndButterModel;

public class BreadAndButterFragment extends Fragment {

    public static final String CHARACTER_ID = "CHARACTER_ID";
    private CharacterID characterId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity hostActivity = getActivity();

        characterId = getCharacterIdFromArguments();

        BreadAndButterModel breadAndButterModel = getBreadAndButterModel();

        if (breadAndButterModel != null) {
            View view = inflater.inflate(R.layout.fragment_bread_and_butters, container, false);

            RecyclerView breadAndButtersRecyclerView = (RecyclerView) view.findViewById(R.id.bnbs_recycler_view);
            breadAndButtersRecyclerView.setLayoutManager(new LinearLayoutManager(hostActivity));
            breadAndButtersRecyclerView.setAdapter(new BreadAndButtersRecyclerViewAdapter(breadAndButterModel));

            return view;
        } else {
            View view = inflater.inflate(R.layout.bnbs_not_available, container, false);
            String characterDisplayName = CharacterResourceUtil.getCharacterDisplayName(hostActivity, characterId);
            String text = getString(R.string.bnbs_not_available, characterDisplayName);

            TextView bnbsNotAvailableLabel = (TextView) view.findViewById(R.id.bnbs_not_available_label);
            bnbsNotAvailableLabel.setText(text);

            return view;
        }
    }

    private BreadAndButterModel getBreadAndButterModel() {
        VFramesApplication application = (VFramesApplication) getContext().getApplicationContext();
        IDataModel dataModel = application.getDataModel();
        SFCharacter targetCharacterModel = dataModel.getCharactersModel().getCharacter(characterId);
        return targetCharacterModel.getBreadAndButters();
    }

    private CharacterID getCharacterIdFromArguments() {
        Bundle bundle = getArguments();
        if (bundle != null && bundle.containsKey(CHARACTER_ID)) {
            return (CharacterID) bundle.getSerializable(CHARACTER_ID);
        } else {
            throw new RuntimeException("no character id for FrameDataFragment");
        }
    }
}
