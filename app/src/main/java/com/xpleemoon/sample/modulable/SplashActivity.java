package com.xpleemoon.sample.modulable;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.module.ModuleName;
import com.xpleemoon.common.router.module.live.LiveModule;
import com.xpleemoon.common.router.module.main.MainModule;
import com.xpleemoon.common.router.module.im.IMModule;
import com.xpleemoon.xmodulable.annotation.InjectXModule;

public class SplashActivity extends BaseCommonActivity {
    @InjectXModule(name = ModuleName.MAIN)
    MainModule mainModule;
    @InjectXModule(name = ModuleName.IM)
    IMModule imModule;
    @InjectXModule(name = ModuleName.LIVE)
    LiveModule liveModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_main:
                if (mainModule == null) {
                    Toast.makeText(getApplicationContext(), "Main组件未知", Toast.LENGTH_SHORT).show();
                    break;
                }
                mainModule.getMainService().startMainActivity();
                break;
            case R.id.start_live:
                if (liveModule == null) {
                    Toast.makeText(getApplicationContext(), "Live组件未知", Toast.LENGTH_SHORT).show();
                    break;
                }
                liveModule.getLiveService().startLive();
                break;
            case R.id.start_IM:
                if (imModule == null) {// 组件独立运行，会导致app壳找不到对应的组件，从而引起依赖注入失败
                    Toast.makeText(getApplicationContext(), "IM组件未知", Toast.LENGTH_SHORT).show();
                    break;
                }
                imModule.getIMService().startIM();
                break;
        }
    }
}
