package com.angarron.vframes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.angarron.vframes.R;
import com.angarron.vframes.resource_resolution.StringResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import data.model.input.InputElement;
import data.model.move.IMoveListMove;
import data.model.move.MoveCategory;

/**
 * Created by andy on 12/20/15
 */
public class MovesRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 1;
    private static final int VIEW_TYPE_MOVE = 2;

    //This is the order in which moves will be displayed in the move list UI.
    //Categories which are missing for a particular character will not be displayed.
    private static MoveCategory[] categoriesOrder = {
            MoveCategory.UNIQUE_MOVES,
            MoveCategory.SPECIALS,
            MoveCategory.VSKILL,
            MoveCategory.VTRIGGER,
            MoveCategory.VREVERSAL,
            MoveCategory.CRITICAL_ARTS,
            MoveCategory.THROWS
    };

    private Context context;
    private List<Object> displayList = new ArrayList<>();

    public MovesRecyclerViewAdapter(Context context, Map<MoveCategory, List<IMoveListMove>> moves) {
        this.context = context;
        setupDisplayList(moves);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.moves_list_header, parent, false);
                return new HeaderItemViewHolder(v);
            case VIEW_TYPE_MOVE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.moves_list_item, parent, false);
                return new MoveItemViewHolder(v);
            default:
                throw new RuntimeException("unable to find ViewHolder for view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MoveItemViewHolder) {
            setupMoveItemViewHolder((MoveItemViewHolder) holder, position);
        } else if (holder instanceof HeaderItemViewHolder) {
            HeaderItemViewHolder headerItemViewHolder = (HeaderItemViewHolder) holder;
            MoveCategory moveCategory = (MoveCategory) displayList.get(position);
            headerItemViewHolder.label.setText(getHeaderString(moveCategory));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (displayList.get(position) instanceof MoveCategory) {
            return VIEW_TYPE_HEADER;
        } else if (displayList.get(position) instanceof IMoveListMove) {
            return VIEW_TYPE_MOVE;
        } else {
            throw new RuntimeException("could not resolve Object to category: " + displayList.get(position).getClass().getSimpleName());
        }
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    private void setupDisplayList(Map<MoveCategory, List<IMoveListMove>> moves) {
        for (MoveCategory category : categoriesOrder) {
            if (moves.containsKey(category) && !moves.get(category).isEmpty()) {
                displayList.add(category);
                for (IMoveListMove move : moves.get(category)) {
                    displayList.add(move);
                }
            }
        }
    }

    private void setupMoveItemViewHolder(MoveItemViewHolder moveItemViewHolder, int position) {
        IMoveListMove move = (IMoveListMove) displayList.get(position);
        moveItemViewHolder.label.setText(move.getNameId());

        List<InputElement> input = move.getInput();
        moveItemViewHolder.input.removeAllViews();
        for (InputElement inputElement : input) {
            moveItemViewHolder.input.addView(getViewForInputElement(inputElement));
        }

        if (!TextUtils.isEmpty(move.getPretextId())) {
            moveItemViewHolder.pretext.setText(StringResolver.getStringId(move.getPretextId()));
            moveItemViewHolder.pretext.setVisibility(View.VISIBLE);
        } else {
            moveItemViewHolder.pretext.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(move.getPosttextId())) {
            moveItemViewHolder.posttext.setText(StringResolver.getStringId(move.getPosttextId()));
            moveItemViewHolder.posttext.setVisibility(View.VISIBLE);
        } else {
            moveItemViewHolder.posttext.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(move.getDescriptionId())) {
            moveItemViewHolder.description.setText(StringResolver.getStringId(move.getDescriptionId()));
            moveItemViewHolder.description.setVisibility(View.VISIBLE);
        } else {
            moveItemViewHolder.description.setVisibility(View.GONE);
        }
    }

    private View getViewForInputElement(InputElement inputElement) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setAdjustViewBounds(true);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, dpToPixels(30), 1);
        layoutParams.setMargins(dpToPixels(3), 0, dpToPixels(3), 0);
        imageView.setLayoutParams(layoutParams);

        switch(inputElement) {
            case HK:
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.input_icon_heavy_kick));
                break;
            case LP:
            case MP:
            case HP:
            case LK:
            case MK:
            case PUNCH:
            case KICK:
            case ALL_PUNCHES:
            case ALL_KICKS:
            case UP:
            case UP_FORWARD:
            case FORWARD:
            case DOWN_FORWARD:
            case DOWN:
            case DOWN_BACK:
            case BACK:
            case UP_BACK:
            case QCF:
            case QCB:
            case SRK:
            case SRK_BACK:
            case HCF:
            case HCB:
            case CHARGE_BACK:
            case CHARGE_DOWN:
            case RELEASE_FORWARD:
            case RELEASE_UP:
            case SPD:
            case PLUS:
            case ARROW:
            case OR:
            case NO_INPUT:
            default:
                //TODO: make this put in a question mark icon
                imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.input_icon_unknown));
        }
        return imageView;
    }

    private String getHeaderString(MoveCategory moveCategory) {
        switch (moveCategory) {
            case NORMALS:
                return context.getString(R.string.normals_header);
            case SPECIALS:
                return context.getString(R.string.specials_header);
            case VSKILL:
                return context.getString(R.string.vskill_header);
            case VTRIGGER:
                return context.getString(R.string.vtrigger_header);
            case VREVERSAL:
                return context.getString(R.string.vreversal_header);
            case CRITICAL_ARTS:
                return context.getString(R.string.critical_arts_header);
            case UNIQUE_MOVES:
                return context.getString(R.string.unique_attacks_header);
            case THROWS:
                return context.getString(R.string.throws_header);
            default:
                throw new RuntimeException("Could not resolve header for category: " + moveCategory);
        }
    }

    private class HeaderItemViewHolder extends RecyclerView.ViewHolder {

        private TextView label;

        public HeaderItemViewHolder(View v) {
            super(v);
            label = (TextView) v.findViewById(R.id.label);
        }
    }

    private class MoveItemViewHolder extends RecyclerView.ViewHolder {

        private TextView label;
        private TextView pretext;
        private LinearLayout input;
        private TextView posttext;
        private TextView description;

        public MoveItemViewHolder(View v) {
            super(v);
            label = (TextView) v.findViewById(R.id.name);
            input = (LinearLayout) v.findViewById(R.id.input_container);
            pretext = (TextView) v.findViewById(R.id.pretext);
            posttext = (TextView) v.findViewById(R.id.posttext);
            description = (TextView) v.findViewById(R.id.description);
        }
    }

    //Stolen from http://stackoverflow.com/a/5960030
    private int dpToPixels(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

}
