package com.xpleemoon.live.router;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xpleemoon.common.router.module.ModuleName;
import com.xpleemoon.common.router.module.live.LiveModule;
import com.xpleemoon.common.router.module.live.service.LiveService;
import com.xpleemoon.xmodulable.annotation.XModule;

@XModule(name = ModuleName.LIVE)
public class LiveModuleImpl extends LiveModule {
    @Autowired
    LiveService liveService;

    @Override
    public LiveService getLiveService() {
        return liveService;
    }
}
