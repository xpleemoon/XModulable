package com.xpleemoon.component.sample;

import com.xpleemoon.common.app.BaseCommonApplication;

public class SampleApplication extends BaseCommonApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        initRouterAndComponent(BuildConfig.DEBUG);
    }
}
