package com.xpleemoon.component.main.standalone;

import com.xpleemoon.common.app.BaseCommonApplication;
import com.xpleemoon.component.main.router.MainComponentImpl;
import com.xpleemoon.component.main.BuildConfig;

public class MainApplication extends BaseCommonApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initRouterAndComponent(BuildConfig.DEBUG);
    }
}
