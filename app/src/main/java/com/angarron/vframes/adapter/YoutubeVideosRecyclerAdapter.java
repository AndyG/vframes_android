package com.angarron.vframes.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.angarron.vframes.R;
import com.angarron.vframes.data.videos.YoutubeVideo;
import com.angarron.vframes.data.videos.YoutubeVideosModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YoutubeVideosRecyclerAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_VIDEO = 1;


    private List<Object> displayList;
    private IVideoSelectedListener videoSelectedListener;

    public YoutubeVideosRecyclerAdapter(YoutubeVideosModel videosModel, IVideoSelectedListener listener) {
        this.videoSelectedListener = listener;
        setupDisplayList(videosModel);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header, parent, false);
                return new HeaderItemViewHolder(v);
            case VIEW_TYPE_VIDEO:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
                return new VideoItemViewHolder(v);
            default:
                throw new RuntimeException("unable to find ViewHolder for view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoItemViewHolder) {
            setupVideoItemViewHolder((VideoItemViewHolder) holder, position);
            holder.itemView.setOnClickListener(new VideoOnClickListener(position));
        } else if (holder instanceof HeaderItemViewHolder) {
            HeaderItemViewHolder headerItemViewHolder = (HeaderItemViewHolder) holder;
            headerItemViewHolder.label.setText((String) displayList.get(position));
            if (position == 0) {
                headerItemViewHolder.setTopMargin(15);
            } else {
                headerItemViewHolder.setTopMargin(50);
            }
        }
    }

    private void setupVideoItemViewHolder(VideoItemViewHolder holder, int position) {
        YoutubeVideo video = (YoutubeVideo) displayList.get(position);
        holder.setupView(video);
    }

    @Override
    public int getItemCount() {
        return displayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (displayList.get(position) instanceof String) {
            return VIEW_TYPE_HEADER;
        } else {
            return VIEW_TYPE_VIDEO;
        }
    }

    private void setupDisplayList(YoutubeVideosModel youtubeVideosModel) {
        displayList = new ArrayList<>();
        if (youtubeVideosModel != null) {
            for (Map.Entry<String, List<YoutubeVideo>> videoCategory : youtubeVideosModel.getVideos().entrySet()) {

                displayList.add(videoCategory.getKey());

                for (YoutubeVideo video : videoCategory.getValue()) {
                    displayList.add(video);
                }
            }
        }
    }

    private class HeaderItemViewHolder extends RecyclerView.ViewHolder {

        private View rowContainer;
        private TextView label;

        private HeaderItemViewHolder(View v) {
            super(v);
            rowContainer = v;
            label = (TextView) v.findViewById(R.id.label);
        }

        private void setTopMargin(int topMarginPx) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) rowContainer.getLayoutParams();
            params.setMargins(0, topMarginPx, 0, 0);
            rowContainer.setLayoutParams(params);
        }
    }

    private class VideoItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView videoPreview;
        private ProgressBar videoPreviewProgressBar;

        private TextView label;
        private TextView author;
        private TextView description;

        private VideoItemViewHolder(View v) {
            super(v);

            videoPreview = (ImageView) v.findViewById(R.id.video_preview);
            videoPreviewProgressBar = (ProgressBar) v.findViewById(R.id.video_preview_progress_bar);

            label = (TextView) v.findViewById(R.id.video_title_textview);
            author = (TextView) v.findViewById(R.id.video_author_textview);
            description = (TextView) v.findViewById(R.id.video_description_textview);
        }

        private void setupView(YoutubeVideo video) {
            description.setText(video.getDescription());
            author.setText(video.getAuthor());
            label.setText(video.getTitle());

            DownloadImageTask downloadImageTask = new DownloadImageTask(videoPreview, videoPreviewProgressBar);
            downloadImageTask.execute(video.getThumbnailUrl());
        }
    }

    private class VideoOnClickListener implements View.OnClickListener {

        private int position;

        public VideoOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            YoutubeVideo clickedVideo = (YoutubeVideo) displayList.get(position);
            String videoUrl = "https://youtube.com/watch?v=" + clickedVideo.getId();
            videoSelectedListener.onVideoSelected(videoUrl);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        ProgressBar progressBar;

        public DownloadImageTask(ImageView bmImage, ProgressBar progressBar) {
            this.bmImage = bmImage;
            this.progressBar = progressBar;
        }

        protected Bitmap doInBackground(String... urls) {

            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            progressBar.setVisibility(View.INVISIBLE);
            bmImage.setVisibility(View.VISIBLE);
        }
    }

    public interface IVideoSelectedListener {
        void onVideoSelected(String videoUrl);
    }
}
