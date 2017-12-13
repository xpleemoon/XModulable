package com.xpleemoon.common.router.component.im.service;

import com.xpleemoon.common.router.component.BaseService;

public interface IMDaoService extends BaseService {
    String getContact();

    void updateContact(String name);
}
