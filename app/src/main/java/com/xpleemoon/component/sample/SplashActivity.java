package com.xpleemoon.component.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.component.ComponentName;
import com.xpleemoon.common.router.component.main.MainComponent;
import com.xpleemoon.common.router.component.im.IMComponent;
import com.xpleemoon.common.router.component.live.LiveComponent;
import com.xpleemoon.component.annotations.InjectComponent;
import com.xpleemoon.component.api.ComponentManager;
import com.xpleemoon.component.api.exception.UnknownComponentException;

public class SplashActivity extends BaseCommonActivity {
    @InjectComponent(name = ComponentName.MAIN)
    MainComponent mMainComponent;
    @InjectComponent(name = ComponentName.IM)
    IMComponent mIMComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_main:
                if (mMainComponent == null) {
                    Toast.makeText(getApplicationContext(), "Main组件未知", Toast.LENGTH_SHORT).show();
                    break;
                }
                mMainComponent.getMainService().startMainActivity();
                break;
            case R.id.start_live:
                try {
                    // 手动查找组件
                    LiveComponent liveComponent = (LiveComponent) ComponentManager.getInstance().getComponent(ComponentName.LIVE);
                    liveComponent.getLiveService().startLive();
                } catch (UnknownComponentException e) {
                    Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.start_IM:
                if (mIMComponent == null) {// 组件独立运行，会导致app壳找不到对应的组件，从而引起依赖注入失败
                    Toast.makeText(getApplicationContext(), "IM组件未知", Toast.LENGTH_SHORT).show();
                    break;
                }
                mIMComponent.getIMService().startIM();
                break;
        }
    }
}
