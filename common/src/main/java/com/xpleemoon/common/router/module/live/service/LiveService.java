package com.xpleemoon.common.router.module.live.service;

import android.support.v4.app.Fragment;

import com.xpleemoon.common.router.module.BaseService;

public interface LiveService extends BaseService {
    Fragment createLiveEntranceFragment();

    void startLive();
}
