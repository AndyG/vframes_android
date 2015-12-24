package com.angarron.sfvframedata.resource_resolution;

import com.angarron.sfvframedata.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andy on 12/23/15
 */
public class StringResolver {

    private static Map<String, Integer> stringIdMap;

    static {
        stringIdMap = new HashMap<>();
        stringIdMap.put("id_karin_tenko_posttext", R.string.karin_tenko_posttext);
        stringIdMap.put("id_yashagaeshi_pretext", R.string.karin_yashagaeshi_pretext);
    }

    public static int getStringId(String key) {
        if (stringIdMap.containsKey(key)) {
            return stringIdMap.get(key);
        } else {
            throw new RuntimeException("could not find key: " + key);
        }
    }
}
