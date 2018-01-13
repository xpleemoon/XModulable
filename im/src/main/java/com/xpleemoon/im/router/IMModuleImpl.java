package com.xpleemoon.im.router;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xpleemoon.common.router.module.ModuleName;
import com.xpleemoon.common.router.module.im.IMModule;
import com.xpleemoon.common.router.module.im.service.IMDaoService;
import com.xpleemoon.common.router.module.im.service.IMService;
import com.xpleemoon.xmodulable.annotation.XModule;

@XModule(name = ModuleName.IM)
public class IMModuleImpl extends IMModule {
    @Autowired
    IMService imService;
    @Autowired
    IMDaoService imDaoService;

    @Override
    public IMService getIMService() {
        return imService;
    }

    @Override
    public IMDaoService getIMDaoService() {
        return imDaoService;
    }
}
