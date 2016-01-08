package com.angarron.vframes.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.angarron.vframes.R;
import com.angarron.vframes.ui.fragment.FrameDataFragment;
import com.angarron.vframes.ui.fragment.MoveListFragment;

public class SummaryPagerAdapter extends FragmentStatePagerAdapter {

    private static final int MOVE_LIST_POSITION = 0;
    private static final int FRAME_DATA_POSITION = 1;

    private Context context;

    public SummaryPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case MOVE_LIST_POSITION:
                return new MoveListFragment();
            case FRAME_DATA_POSITION:
                return new FrameDataFragment();
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
            default:
                throw new RuntimeException("invalid position: " + position);
        }
    }


}
