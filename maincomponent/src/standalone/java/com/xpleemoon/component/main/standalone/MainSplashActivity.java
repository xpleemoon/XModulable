package com.xpleemoon.component.main.standalone;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.component.ComponentName;
import com.xpleemoon.common.router.component.main.MainComponent;
import com.xpleemoon.component.api.ComponentManager;
import com.xpleemoon.component.api.exception.UnknownComponentException;
import com.xpleemoon.component.main.R;

public class MainSplashActivity extends BaseCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maincomponent_activity_splash);
    }

    public void startEntrance(View view) {
        try {
            MainComponent mainComponent = (MainComponent) ComponentManager.getInstance().getComponent(ComponentName.MAIN);
            mainComponent.getMainService().startMainActivity();
        } catch (UnknownComponentException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
