package com.xpleemoon.component.im.router;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.xpleemoon.common.router.component.ComponentName;
import com.xpleemoon.common.router.component.im.IMComponent;
import com.xpleemoon.common.router.component.im.service.IMDaoService;
import com.xpleemoon.common.router.component.im.service.IMService;
import com.xpleemoon.component.annotations.Component;

@Component(name = ComponentName.IM)
public class IMComponentImpl extends IMComponent {
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
