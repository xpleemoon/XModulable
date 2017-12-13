package com.xpleemoon.common.router.component.main;

import com.xpleemoon.common.router.component.BaseComponent;
import com.xpleemoon.common.router.component.main.service.MainService;

public abstract class MainComponent extends BaseComponent {

    public abstract MainService getMainService();
}
