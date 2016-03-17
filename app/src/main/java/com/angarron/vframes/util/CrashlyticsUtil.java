package com.angarron.vframes.util;

import com.angarron.vframes.BuildConfig;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;

public class CrashlyticsUtil {

    private static final String VIEWED_NOTES_EVENT = "Viewed Notes";
    private static final String DID_SAVE_NOTES_PROPERTY = "Did Save";

    public static void sendViewedNotesEvent(boolean didSave) {
        CustomEvent viewedNotesEvent = new CustomEvent(VIEWED_NOTES_EVENT);
        viewedNotesEvent.putCustomAttribute(DID_SAVE_NOTES_PROPERTY, String.valueOf(didSave));

        if (!BuildConfig.DEBUG) {
            Answers.getInstance().logCustom(viewedNotesEvent);
        }
    }
}
