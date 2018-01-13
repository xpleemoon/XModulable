package com.xpleemoon.xmodulable.api.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * 组件相关的缓存工具
 *
 * @author xpleemoon
 */
public class CacheUtils {
    private static final String NAME = "xmodulable_cache";
    /**
     * 组件加载器集合的缓存key
     */
    static final String KEY_MODULE_LOADER_SET = "ModuleLoaderSet";
    /**
     * 缓存的apk版本名key
     */
    static final String KEY_LAST_VERSION_NAME = "lastVersionName";
    /**
     * apk缓存的版本号key
     */
    static final String KEY_LAST_VERSION_CODE = "lastVersionCode";

    static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    /**
     * 获取缓存的组件加载器集合
     *
     * @param context
     * @return
     */
    public static Set<String> getModuleLoaderSet(Context context) {
        return getPrefs(context).getStringSet(KEY_MODULE_LOADER_SET, null);
    }

    /**
     * 更新缓存的组件加载器集合
     *
     * @param context
     * @param componentLoaderSet
     */
    public static void updateModuleLoaderSet(Context context, Set<String> componentLoaderSet) {
        getPrefs(context).edit().putStringSet(KEY_MODULE_LOADER_SET, componentLoaderSet).apply();
    }
}
