package com.xpleemoon.component.im.repo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class IMDataRepo {
    private static final String NAME = "IMData";
    private static final String KEY_CONTRACT = "contract";
    private static volatile IMDataRepo sInstance;

    private IMDataRepo() {
    }

    public static IMDataRepo getInstance() {
        if (sInstance == null) {
            synchronized (IMDataRepo.class) {
                if (sInstance == null) {
                    sInstance = new IMDataRepo();
                }
            }
        }
        return sInstance;
    }

    private SharedPreferences getPreferences(@NonNull Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void updateContract(@NonNull Context context, String name) {
        getPreferences(context).edit().putString(KEY_CONTRACT, name).commit();
    }

    public String getContract(@NonNull Context context) {
        return getPreferences(context).getString(KEY_CONTRACT, "");
    }
}
