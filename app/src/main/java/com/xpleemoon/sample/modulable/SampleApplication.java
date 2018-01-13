package com.xpleemoon.sample.modulable;

import com.xpleemoon.common.app.BaseCommonApplication;

public class SampleApplication extends BaseCommonApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        initRouterAndModule(BuildConfig.DEBUG);
    }
}
