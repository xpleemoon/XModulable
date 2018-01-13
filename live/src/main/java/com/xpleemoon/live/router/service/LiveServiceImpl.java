package com.xpleemoon.live.router.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xpleemoon.common.router.module.live.service.LiveService;
import com.xpleemoon.live.router.path.PathConstants;

@Route(path = PathConstants.PATH_SERVICE_LIVE)
public class LiveServiceImpl implements LiveService {
    @Override
    public void init(Context context) {

    }

    @Override
    public void startLive() {
        ARouter.getInstance().build(PathConstants.PATH_VIEW_LIVE).navigation();
    }
}
