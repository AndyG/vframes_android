package com.angarron.vframes.ui.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.angarron.vframes.R;
import com.angarron.vframes.adapter.FrameDataRecyclerViewAdapter;
import com.angarron.vframes.application.VFramesApplication;
import com.angarron.vframes.util.CharacterResourceUtil;

import data.model.CharacterID;
import data.model.IDataModel;
import data.model.character.FrameData;
import data.model.character.SFCharacter;

public class FrameDataFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    public static final String CHARACTER_ID = "CHARACTER_ID";

    private static final String ALTERNATE_FRAME_DATA_SELECTED = "ALTERNATE_FRAME_DATA_SELECTED";

    private FrameData frameData;

    private Switch alternateFrameDataSwitch;
    private RecyclerView frameDataRecyclerView;
    private boolean showingAlternateFrameData = false;

    CharacterID characterId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();

        if (bundle != null && bundle.containsKey(CHARACTER_ID)) {
            characterId = (CharacterID) bundle.getSerializable(CHARACTER_ID);
        } else {
            throw new RuntimeException("no character id for FrameDataFragment");
        }

        frameData = getFrameData();
        if(frameData != null) {
            return setupRecyclerView(inflater, container);
        } else {
            return inflater.inflate(R.layout.frame_data_upcoming, container, false);
        }
    }

    private View setupRecyclerView(LayoutInflater inflater, ViewGroup container) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.frame_data_layout, container, false);

        alternateFrameDataSwitch = (Switch) linearLayout.findViewById(R.id.alternate_frame_data_switch);
        alternateFrameDataSwitch.setOnCheckedChangeListener(this);
        setSwitchState();

        ColorDrawable color = CharacterResourceUtil.getCharacterPrimaryColorDrawable(getContext(), characterId);
        linearLayout.findViewById(R.id.frame_data_header_layout).setBackgroundColor(color.getColor());

        frameDataRecyclerView = (RecyclerView) linearLayout.findViewById(R.id.frames_recycler_view);
        frameDataRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        frameDataRecyclerView.setAdapter(new FrameDataRecyclerViewAdapter(getContext(), frameData));
        return linearLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ALTERNATE_FRAME_DATA_SELECTED, showingAlternateFrameData);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (frameData != null) {
            if (savedInstanceState != null) {
                showingAlternateFrameData = savedInstanceState.getBoolean(ALTERNATE_FRAME_DATA_SELECTED, false);
                setShowAlternateFrameData(showingAlternateFrameData);
            }
        }

    }

    public void setShowAlternateFrameData(boolean showAlternateFrameData) {
        this.showingAlternateFrameData = showAlternateFrameData;
        FrameDataRecyclerViewAdapter frameDataRecyclerViewAdapter = (FrameDataRecyclerViewAdapter) frameDataRecyclerView.getAdapter();
        frameDataRecyclerViewAdapter.setShowAlternate(showAlternateFrameData);
        setSwitchState();
    }

    private void setSwitchState() {
        if (frameData.hasAlternateFrameData()) {
            if (showingAlternateFrameData) {
                alternateFrameDataSwitch.setChecked(true);
                if (characterId == CharacterID.CLAW) {
                    alternateFrameDataSwitch.setText("Claw Off Frame Data");
                } else {
                    alternateFrameDataSwitch.setText("V-Trigger Frame Data");
                }
            } else {
                alternateFrameDataSwitch.setChecked(false);
                if (characterId == CharacterID.CLAW) {
                    alternateFrameDataSwitch.setText("Claw On Frame Data");
                } else {
                    alternateFrameDataSwitch.setText("Standard Frame Data");
                }
            }
            alternateFrameDataSwitch.setVisibility(View.VISIBLE);
        } else {
            alternateFrameDataSwitch.setVisibility(View.GONE);
        }
    }

    private FrameData getFrameData() {
        VFramesApplication application = (VFramesApplication) getContext().getApplicationContext();
        IDataModel dataModel = application.getDataModel();
        SFCharacter targetCharacterModel = dataModel.getCharactersModel().getCharacter(characterId);
        return targetCharacterModel.getFrameData();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        setShowAlternateFrameData(b);
    }
}
