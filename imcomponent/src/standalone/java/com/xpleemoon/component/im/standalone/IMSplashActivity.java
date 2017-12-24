package com.xpleemoon.component.im.standalone;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.component.ComponentName;
import com.xpleemoon.common.router.component.im.IMComponent;
import com.xpleemoon.component.api.ComponentManager;
import com.xpleemoon.component.api.exception.UnknownComponentException;
import com.xpleemoon.component.im.R;

public class IMSplashActivity extends BaseCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imcomponent_activity_splash);
    }

    public void startEntrance(View view) {
        try {
            IMComponent imComponent = (IMComponent) ComponentManager.getInstance().getComponent(ComponentName.IM);
            imComponent.getIMService().startIM();
        } catch (UnknownComponentException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
