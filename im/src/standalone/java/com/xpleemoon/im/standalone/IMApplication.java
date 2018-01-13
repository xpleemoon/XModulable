package com.xpleemoon.im.standalone;

import com.xpleemoon.common.app.BaseCommonApplication;
import com.xpleemoon.im.BuildConfig;

public class IMApplication extends BaseCommonApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initRouterAndModule(BuildConfig.DEBUG);
    }
}
