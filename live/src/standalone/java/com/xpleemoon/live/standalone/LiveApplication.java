package com.xpleemoon.live.standalone;

import com.xpleemoon.common.app.BaseCommonApplication;
import com.xpleemoon.live.BuildConfig;

public class LiveApplication extends BaseCommonApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initRouterAndModule(BuildConfig.DEBUG);
    }
}
