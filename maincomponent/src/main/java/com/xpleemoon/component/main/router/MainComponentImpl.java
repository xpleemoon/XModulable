package com.xpleemoon.component.main.router;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xpleemoon.common.router.component.ComponentName;
import com.xpleemoon.common.router.component.main.MainComponent;
import com.xpleemoon.common.router.component.main.service.MainService;
import com.xpleemoon.component.annotations.Component;
import com.xpleemoon.component.main.router.path.PathConstants;

@Component(name = ComponentName.MAIN)
public class MainComponentImpl extends MainComponent {
    MainService mainService;

    @Override
    public MainService getMainService() {
        return mainService != null ? mainService : (mainService = (MainService) ARouter.getInstance().build(PathConstants.PATH_SERVICE_MAIN).navigation());
    }
}
