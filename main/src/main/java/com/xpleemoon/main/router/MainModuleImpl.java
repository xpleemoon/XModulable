package com.xpleemoon.main.router;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xpleemoon.common.router.module.ModuleName;
import com.xpleemoon.common.router.module.main.MainModule;
import com.xpleemoon.common.router.module.main.service.MainService;
import com.xpleemoon.xmodulable.annotation.XModule;
import com.xpleemoon.main.router.path.PathConstants;

@XModule(name = ModuleName.MAIN)
public class MainModuleImpl extends MainModule {
    MainService mainService;

    @Override
    public MainService getMainService() {
        return mainService != null ? mainService : (mainService = (MainService) ARouter.getInstance().build(PathConstants.PATH_SERVICE_MAIN).navigation());
    }
}
