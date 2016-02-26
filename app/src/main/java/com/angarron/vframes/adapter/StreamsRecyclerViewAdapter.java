package com.angarron.vframes.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.angarron.vframes.R;
import com.angarron.vframes.data.twitch.TwitchStream;

import java.io.InputStream;
import java.util.List;

public class StreamsRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<TwitchStream> twitchStreams;
    private Context context;
    private IStreamClickListener listener;

    public StreamsRecyclerViewAdapter(List<TwitchStream> twitchStreams, Context context, IStreamClickListener listener) {
        this.twitchStreams = twitchStreams;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stream_layout, parent, false);
        return new StreamViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        TwitchStream stream = twitchStreams.get(position);
        setupStreamViewHolder((StreamViewHolder) holder, stream, position);
    }

    private void setupStreamViewHolder(StreamViewHolder holder, TwitchStream stream, int position) {
        holder.streamName.setText(stream.getChannelDisplayName());

        String viewerCountText = String.format(context.getString(R.string.viewers_format), String.valueOf(stream.getViewerCount()));
        holder.viewerCount.setText(viewerCountText);

        String streamStatus = stream.getStatus();
        holder.streamStatus.setText(streamStatus != null ? streamStatus : "");

        if (!TextUtils.isEmpty(stream.getPreviewUrl())) {
            ImageView streamPreview = holder.streamLogo;
            ProgressBar loadingSpinner = holder.progressBar;
            streamPreview.setVisibility(View.INVISIBLE);
            loadingSpinner.setVisibility(View.VISIBLE);
            DownloadImageTask downloadImageTask = new DownloadImageTask(holder.streamLogo, holder.progressBar);
            downloadImageTask.execute(stream.getPreviewUrl());
        }

        holder.itemView.setOnClickListener(new StreamOnClickListener(position));
    }

    @Override
    public int getItemCount() {
        return twitchStreams.size();
    }

    private class StreamViewHolder extends RecyclerView.ViewHolder {

        private TextView streamName;
        private TextView streamStatus;
        private TextView viewerCount;
        private ImageView streamLogo;
        private ProgressBar progressBar;

        public StreamViewHolder(View v) {
            super(v);
            streamName = (TextView) v.findViewById(R.id.stream_name_textview);
            streamStatus = (TextView) v.findViewById(R.id.stream_status);
            viewerCount = (TextView) v.findViewById(R.id.stream_viewers_count);
            streamLogo = (ImageView) v.findViewById(R.id.stream_logo);
            progressBar = (ProgressBar) v.findViewById(R.id.stream_preview_progress_bar);
        }
    }

    private class StreamOnClickListener implements View.OnClickListener {

        private int position;

        public StreamOnClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            TwitchStream clickedStream = twitchStreams.get(position);
            String streamUrl = clickedStream.getChannelUrl();
            listener.onStreamClicked(streamUrl);
        }
    }

    public interface IStreamClickListener {
        void onStreamClicked(String url);
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
}
