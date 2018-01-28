package com.xpleemoon.im.router.service;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.xpleemoon.common.router.module.im.service.IMService;
import com.xpleemoon.im.IMEntranceFragment;
import com.xpleemoon.im.router.path.PathConstants;

@Route(path = PathConstants.PATH_SERVICE_IM)
public class IMServiceImpl implements IMService {
    @Override
    public void init(Context context) {

    }

    @Override
    public Fragment createIMEntranceFragment() {
        return new IMEntranceFragment();
    }

    @Override
    public void startIM() {
        ARouter.getInstance().build(PathConstants.PATH_VIEW_IM).navigation();
    }
}
