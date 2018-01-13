package com.xpleemoon.live.standalone;

import android.os.Bundle;
import android.view.View;

import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.module.ModuleName;
import com.xpleemoon.common.router.module.live.LiveModule;
import com.xpleemoon.live.R;
import com.xpleemoon.xmodulable.annotation.InjectXModule;

public class LiveSplashActivity extends BaseCommonActivity {
    @InjectXModule(name = ModuleName.LIVE)
    LiveModule liveModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_activity_splash);
    }

    public void startEntrance(View view) {
        liveModule.getLiveService().startLive();
    }
}
