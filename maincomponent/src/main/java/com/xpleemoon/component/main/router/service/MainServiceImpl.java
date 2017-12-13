package com.xpleemoon.component.main.router.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xpleemoon.common.router.component.main.service.MainService;
import com.xpleemoon.component.main.router.path.PathConstants;

@Route(path = PathConstants.PATH_SERVICE_MAIN)
public class MainServiceImpl implements MainService {
    @Override
    public void init(Context context) {

    }

    @Override
    public void startMainActivity() {
        ARouter.getInstance().build(PathConstants.PATH_VIEW_MAIN).navigation();
    }
}
