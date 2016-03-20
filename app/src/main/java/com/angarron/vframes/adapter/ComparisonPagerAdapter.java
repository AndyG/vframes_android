package com.angarron.vframes.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.angarron.vframes.R;
import com.angarron.vframes.ui.fragment.FrameDataFragment;
import com.angarron.vframes.ui.fragment.MovePunisherFragment;
import com.angarron.vframes.util.CharacterResourceUtil;

import data.model.CharacterID;

public class ComparisonPagerAdapter extends FragmentStatePagerAdapter {

    private static final int FIRST_FRAME_DATA_POSITION = 0;
    private static final int SECOND_FRAME_DATA_POSITION = 1;
    private static final int MOVE_PUNISHER_POSITION = 2;

    private Context context;
    private CharacterID firstCharacter;
    private CharacterID secondCharacter;

    public ComparisonPagerAdapter(Context context, FragmentManager fragmentManager, CharacterID firstCharacterId, CharacterID secondCharacterId) {
        super(fragmentManager);
        this.context = context;
        this.firstCharacter = firstCharacterId;
        this.secondCharacter = secondCharacterId;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case FIRST_FRAME_DATA_POSITION:
                return createFrameDataFragment(firstCharacter);
            case SECOND_FRAME_DATA_POSITION:
                return createFrameDataFragment(secondCharacter);
            case MOVE_PUNISHER_POSITION:
                return new MovePunisherFragment();
            default:
                throw new RuntimeException("could not find fragment for position: " + position);
        }
    }

    private Fragment createFrameDataFragment(CharacterID characterID) {
        FrameDataFragment frameDataFragment = new FrameDataFragment();
        frameDataFragment.setArguments(createFrameDataArguments(characterID));
        return frameDataFragment;
    }

    private Bundle createFrameDataArguments(CharacterID characterID) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(FrameDataFragment.CHARACTER_ID, characterID);
        return bundle;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case FIRST_FRAME_DATA_POSITION:
                String firstCharacterDisplayName = CharacterResourceUtil.getCharacterDisplayName(context, firstCharacter);
                return context.getString(R.string.comparison_frame_data_format, firstCharacterDisplayName);
            case SECOND_FRAME_DATA_POSITION:
                String secondCharacterDisplayName = CharacterResourceUtil.getCharacterDisplayName(context, secondCharacter);
                return context.getString(R.string.comparison_frame_data_format, secondCharacterDisplayName);
            case MOVE_PUNISHER_POSITION:
                return context.getString(R.string.move_punisher);
            default:
                throw new RuntimeException("invalid position: " + position);
        }
    }
}
