package com.xpleemoon.common.router.module.im.service;

import android.support.v4.app.Fragment;

import com.xpleemoon.common.router.module.BaseService;

public interface IMService extends BaseService {
    Fragment createIMEntranceFragment();

    void startIM();
}
