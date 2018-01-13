package com.xpleemoon.main.standalone;

import com.xpleemoon.common.app.BaseCommonApplication;
import com.xpleemoon.main.BuildConfig;

public class MainApplication extends BaseCommonApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initRouterAndModule(BuildConfig.DEBUG);
    }
}
