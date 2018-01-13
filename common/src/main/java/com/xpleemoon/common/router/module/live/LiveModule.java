package com.xpleemoon.common.router.module.live;

import com.xpleemoon.common.router.module.BaseModule;
import com.xpleemoon.common.router.module.live.service.LiveService;

public abstract class LiveModule extends BaseModule {

    public abstract LiveService getLiveService();
}
