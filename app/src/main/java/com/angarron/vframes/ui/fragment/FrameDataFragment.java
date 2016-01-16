package com.angarron.vframes.ui.fragment;

import android.app.Activity;
import android.content.Context;
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

import data.model.character.FrameData;

public class FrameDataFragment extends Fragment {

    private RecyclerView frameDataRecyclerView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        IFrameDataFragmentHost frameDataFragmentHost = (IFrameDataFragmentHost) context;
        frameDataFragmentHost.registerFrameDataFragment(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        IFrameDataFragmentHost frameDataFragmentHost = (IFrameDataFragmentHost) getActivity();
        frameDataFragmentHost.unregisterFrameDataFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Activity hostActivity = getActivity();
        IFrameDataFragmentHost frameDataFragmentHost = (IFrameDataFragmentHost) hostActivity;
        FrameData frameData = frameDataFragmentHost.getFrameData();

        if (frameData != null) {
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.frame_data_layout, container, false);

            frameDataRecyclerView = (RecyclerView) linearLayout.findViewById(R.id.frames_recycler_view);
            frameDataRecyclerView.setLayoutManager(new LinearLayoutManager(hostActivity));
            frameDataRecyclerView.setAdapter(new FrameDataRecyclerViewAdapter(getContext(), frameData));
            return linearLayout;
        } else {
            return inflater.inflate(R.layout.frame_data_upcoming, container, false);
        }
    }

    public void updateFrameData(FrameData frameData) {
        frameDataRecyclerView.setAdapter(new FrameDataRecyclerViewAdapter(getContext(), frameData));
    }

    public interface IFrameDataFragmentHost {
        void registerFrameDataFragment(FrameDataFragment frameDataFragment);
        void unregisterFrameDataFragment();
        FrameData getFrameData();
    }
}
