package com.xpleemoon.common.router.module.main;

import com.xpleemoon.common.router.module.BaseModule;
import com.xpleemoon.common.router.module.main.service.MainService;

public abstract class MainModule extends BaseModule {

    public abstract MainService getMainService();
}
