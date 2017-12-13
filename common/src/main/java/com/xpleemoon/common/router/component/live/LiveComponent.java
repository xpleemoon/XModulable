package com.xpleemoon.common.router.component.live;

import com.xpleemoon.common.router.component.BaseComponent;
import com.xpleemoon.common.router.component.live.service.LiveService;

public abstract class LiveComponent extends BaseComponent {

    public abstract LiveService getLiveService();
}
