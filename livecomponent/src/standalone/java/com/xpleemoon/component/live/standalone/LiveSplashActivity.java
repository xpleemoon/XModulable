package com.xpleemoon.component.live.standalone;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.component.ComponentName;
import com.xpleemoon.common.router.component.live.LiveComponent;
import com.xpleemoon.component.api.ComponentManager;
import com.xpleemoon.component.api.exception.UnknownComponentException;
import com.xpleemoon.component.live.R;

public class LiveSplashActivity extends BaseCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livecomponent_activity_splash);
    }

    public void startEntrance(View view) {
        try {
            LiveComponent liveComponent = (LiveComponent) ComponentManager.getInstance().getComponent(ComponentName.LIVE);
            liveComponent.getLiveService().startLive();
        } catch (UnknownComponentException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
