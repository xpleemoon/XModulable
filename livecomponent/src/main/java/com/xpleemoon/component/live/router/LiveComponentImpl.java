package com.xpleemoon.component.live.router;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xpleemoon.common.router.component.ComponentName;
import com.xpleemoon.common.router.component.live.LiveComponent;
import com.xpleemoon.common.router.component.live.service.LiveService;
import com.xpleemoon.component.annotations.Component;

@Component(name = ComponentName.LIVE)
public class LiveComponentImpl extends LiveComponent {
    @Autowired
    LiveService liveService;

    @Override
    public LiveService getLiveService() {
        return liveService;
    }
}
