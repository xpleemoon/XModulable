package com.xpleemoon.component.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.component.ComponentName;
import com.xpleemoon.common.router.component.main.MainComponent;
import com.xpleemoon.common.router.component.im.IMComponent;
import com.xpleemoon.common.router.component.live.LiveComponent;
import com.xpleemoon.component.api.ComponentManager;

public class SplashActivity extends BaseCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_main:
                try {
                    MainComponent mainComponent = (MainComponent) ComponentManager.getInstance().getComponent(ComponentName.MAIN);
                    mainComponent.getMainService().startMainActivity();
                } catch (IllegalAccessException e) {
                    Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.start_live:
                try {
                    LiveComponent liveComponent = (LiveComponent) ComponentManager.getInstance().getComponent(ComponentName.LIVE);
                    liveComponent.getLiveService().startLive();
                } catch (IllegalAccessException e) {
                    Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.start_IM:
                try {
                    IMComponent imComponent = (IMComponent) ComponentManager.getInstance().getComponent(ComponentName.IM);
                    imComponent.getIMService().startIM();
                } catch (IllegalAccessException e) {
                    Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
