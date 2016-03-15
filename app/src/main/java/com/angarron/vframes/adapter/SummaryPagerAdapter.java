package com.angarron.vframes.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.angarron.vframes.R;
import com.angarron.vframes.ui.fragment.BreadAndButterFragment;
import com.angarron.vframes.ui.fragment.FrameDataFragment;
import com.angarron.vframes.ui.fragment.MoveListFragment;
import com.angarron.vframes.ui.fragment.NotesFragment;
import com.angarron.vframes.ui.fragment.RecommendedVideosFragment;

import data.model.CharacterID;

public class SummaryPagerAdapter extends FragmentStatePagerAdapter {

    private static final int FRAME_DATA_POSITION = 0;
    private static final int MOVE_LIST_POSITION = 1;
    private static final int BNBS_POSITION = 2;
    private static final int RECOMMENDED_VIDEOS_POSITION = 3;
    private static final int NOTES_POSITION = 4;

    private Context context;
    private CharacterID characterId;

    public SummaryPagerAdapter(Context context, FragmentManager fm, CharacterID characterId) {
        super(fm);
        this.context = context;
        this.characterId = characterId;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case MOVE_LIST_POSITION:
                return new MoveListFragment();
            case FRAME_DATA_POSITION:
                FrameDataFragment frameDataFragment = new FrameDataFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(FrameDataFragment.CHARACTER_ID, characterId);
                frameDataFragment.setArguments(bundle);
                return frameDataFragment;
            case BNBS_POSITION:
                return new BreadAndButterFragment();
            case RECOMMENDED_VIDEOS_POSITION:
                return new RecommendedVideosFragment();
            case NOTES_POSITION:
                return new NotesFragment();
            default:
                throw new RuntimeException("invalid position: " + position);
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case MOVE_LIST_POSITION:
                return context.getString(R.string.moves_list_title);
            case FRAME_DATA_POSITION:
                return context.getString(R.string.frame_data_title);
            case NOTES_POSITION:
                return context.getString(R.string.notes_title);
            case RECOMMENDED_VIDEOS_POSITION:
                return context.getString(R.string.recommended_videos_title);
            case BNBS_POSITION:
                return context.getString(R.string.bnb_page_title);
            default:
                throw new RuntimeException("invalid position: " + position);
        }
    }


}
