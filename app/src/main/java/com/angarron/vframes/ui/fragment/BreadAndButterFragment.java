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

import data.model.character.bnb.BreadAndButterModel;

public class BreadAndButterFragment extends Fragment {


    public interface IBreadAndButterFragmentHost {
        BreadAndButterModel getBreadAndButterModel();
        String getCharacterDisplayName();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity hostActivity = getActivity();
        IBreadAndButterFragmentHost breadAndButterFragmentHost = (IBreadAndButterFragmentHost) hostActivity;
        BreadAndButterModel breadAndButterModel = breadAndButterFragmentHost.getBreadAndButterModel();

        if (breadAndButterModel != null) {
            View view = inflater.inflate(R.layout.fragment_bread_and_butters, container, false);

            RecyclerView breadAndButtersRecyclerView = (RecyclerView) view.findViewById(R.id.bnbs_recycler_view);
            breadAndButtersRecyclerView.setLayoutManager(new LinearLayoutManager(hostActivity));
            breadAndButtersRecyclerView.setAdapter(new BreadAndButtersRecyclerViewAdapter(breadAndButterModel));

            return view;
        } else {
            View view = inflater.inflate(R.layout.bnbs_not_available, container, false);
            String characterDisplayName = breadAndButterFragmentHost.getCharacterDisplayName();
            String text = getString(R.string.bnbs_not_available, characterDisplayName);

            TextView bnbsNotAvailableLabel = (TextView) view.findViewById(R.id.bnbs_not_available_label);
            bnbsNotAvailableLabel.setText(text);

            return view;
        }
    }
}
