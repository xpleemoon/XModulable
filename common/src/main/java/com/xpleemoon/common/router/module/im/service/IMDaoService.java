package com.xpleemoon.common.router.module.im.service;

import com.xpleemoon.common.router.module.BaseService;

public interface IMDaoService extends BaseService {
    String getContact();

    void updateContact(String name);
}
