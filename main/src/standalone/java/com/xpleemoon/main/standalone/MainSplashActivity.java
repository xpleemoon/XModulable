package com.xpleemoon.main.standalone;

import android.os.Bundle;
import android.view.View;

import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.module.ModuleName;
import com.xpleemoon.common.router.module.main.MainModule;
import com.xpleemoon.main.R;
import com.xpleemoon.xmodulable.annotation.InjectXModule;

public class MainSplashActivity extends BaseCommonActivity {
    @InjectXModule(name = ModuleName.MAIN)
    MainModule mainModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_splash);
    }

    public void startEntrance(View view) {
        mainModule.getMainService().startMainActivity();
    }
}
