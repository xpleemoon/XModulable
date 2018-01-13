package com.xpleemoon.common.router.module.im;

import com.xpleemoon.common.router.module.BaseModule;
import com.xpleemoon.common.router.module.im.service.IMDaoService;
import com.xpleemoon.common.router.module.im.service.IMService;

public abstract class IMModule extends BaseModule {

    public abstract IMService getIMService();

    public abstract IMDaoService getIMDaoService();
}
