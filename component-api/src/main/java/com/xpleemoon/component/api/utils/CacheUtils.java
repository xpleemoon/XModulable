package com.xpleemoon.component.api.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by xplee on 2017/12/13.
 */

public class CacheUtils {
    static final String KEY_COMPONENT_LOADER_SET = "componentLoaderSet";
    static final String KEY_LAST_VERSION_NAME = "lastVersionName";
    static final String KEY_LAST_VERSION_CODE = "lastVersionCode";

    static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("COMPONENT_CACHE", Context.MODE_PRIVATE);
    }

    public static Set<String> getComponentLoaderSet(Context context) {
        return getPrefs(context).getStringSet(KEY_COMPONENT_LOADER_SET, null);
    }

    public static void updateComponentLoaderSet(Context context, Set<String> componentLoaderSet) {
        getPrefs(context).edit().putStringSet(KEY_COMPONENT_LOADER_SET, componentLoaderSet).apply();
    }
}
