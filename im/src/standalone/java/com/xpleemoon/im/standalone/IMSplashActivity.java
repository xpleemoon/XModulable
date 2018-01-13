package com.xpleemoon.im.standalone;

import android.os.Bundle;
import android.view.View;

import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.module.ModuleName;
import com.xpleemoon.common.router.module.im.IMModule;
import com.xpleemoon.im.R;
import com.xpleemoon.xmodulable.annotation.InjectXModule;

public class IMSplashActivity extends BaseCommonActivity {
    @InjectXModule(name = ModuleName.IM)
    IMModule imModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_splash);
    }

    public void startEntrance(View view) {
        imModule.getIMService().startIM();
    }
}
