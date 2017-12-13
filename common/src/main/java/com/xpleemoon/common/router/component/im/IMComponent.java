package com.xpleemoon.common.router.component.im;

import com.xpleemoon.common.router.component.BaseComponent;
import com.xpleemoon.common.router.component.im.service.IMDaoService;
import com.xpleemoon.common.router.component.im.service.IMService;

public abstract class IMComponent extends BaseComponent {

    public abstract IMService getIMService();

    public abstract IMDaoService getIMDaoService();
}
