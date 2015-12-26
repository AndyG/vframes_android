package com.angarron.vframes.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.angarron.vframes.R;

/**
 * Created by andy on 12/26/15
 */
public class FeedbackUtil {
    public static void sendFeedback(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:" + Uri.encode(activity.getString(R.string.feedback_email_address)) +
                "?subject=" + Uri.encode(activity.getString(R.string.feedback_email_subject));
        Uri uri = Uri.parse(uriText);
        intent.setData(uri);
        activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.send_feedback)));
    }
}
