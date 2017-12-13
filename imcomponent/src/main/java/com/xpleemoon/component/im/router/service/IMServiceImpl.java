package com.xpleemoon.component.im.router.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xpleemoon.common.router.component.im.service.IMService;
import com.xpleemoon.component.im.router.path.PathConstants;

@Route(path = PathConstants.PATH_SERVICE_IM)
public class IMServiceImpl implements IMService {
    @Override
    public void init(Context context) {

    }

    @Override
    public void startIM() {
        ARouter.getInstance().build(PathConstants.PATH_VIEW_IM).navigation();
    }
}
