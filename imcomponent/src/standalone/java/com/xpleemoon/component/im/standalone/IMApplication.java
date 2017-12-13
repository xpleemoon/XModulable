package com.xpleemoon.component.im.standalone;

import com.xpleemoon.common.app.BaseCommonApplication;
import com.xpleemoon.component.im.BuildConfig;

public class IMApplication extends BaseCommonApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initRouterAndComponent(BuildConfig.DEBUG);
    }
}
