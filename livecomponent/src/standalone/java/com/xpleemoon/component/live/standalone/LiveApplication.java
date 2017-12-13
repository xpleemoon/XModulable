package com.xpleemoon.component.live.standalone;

import com.xpleemoon.common.app.BaseCommonApplication;
import com.xpleemoon.component.live.BuildConfig;
import com.xpleemoon.component.live.router.LiveComponentImpl;

public class LiveApplication extends BaseCommonApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initRouterAndComponent(BuildConfig.DEBUG);
    }
}
