package com.xpleemoon.component.live;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.component.live.router.path.PathConstants;

@Route(path = PathConstants.PATH_VIEW_LIVE)
public class LiveActivity extends BaseCommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.livecomponent_activity_live);
    }
}
