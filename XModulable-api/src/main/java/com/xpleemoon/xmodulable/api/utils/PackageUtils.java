package com.xpleemoon.xmodulable.api.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


/**
 * Android package utils
 *
 * @author zhilong <a href="mailto:zhilong.liu@aliyun.com">Contact me.</a>
 * @version 1.0
 * @since 2017/8/8 下午8:19
 */
public class PackageUtils {

    private static String NEW_VERSION_NAME;
    private static int NEW_VERSION_CODE;

    public static boolean isNewVersion(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (null != packageInfo) {
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;

            SharedPreferences sp = CacheUtils.getPrefs(context);
            if (!versionName.equals(sp.getString(CacheUtils.KEY_LAST_VERSION_NAME, null))
                    || versionCode != sp.getInt(CacheUtils.KEY_LAST_VERSION_CODE, -1)) {
                // new version
                NEW_VERSION_NAME = versionName;
                NEW_VERSION_CODE = versionCode;

                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static void updateVersion(Context context) {
        if (!android.text.TextUtils.isEmpty(NEW_VERSION_NAME) && NEW_VERSION_CODE != 0) {
            SharedPreferences sp = CacheUtils.getPrefs(context);
            sp.edit()
                    .putString(CacheUtils.KEY_LAST_VERSION_NAME, NEW_VERSION_NAME)
                    .putInt(CacheUtils.KEY_LAST_VERSION_CODE, NEW_VERSION_CODE)
                    .apply();
        }
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return packageInfo;
    }
}
