package com.xpleemoon.im.router.service;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xpleemoon.common.router.module.im.service.IMDaoService;
import com.xpleemoon.im.repo.IMDataRepo;
import com.xpleemoon.im.router.path.PathConstants;

@Route(path = PathConstants.PATH_SERVICE_DAO)
public class IMDaoServiceImpl implements IMDaoService {
    private Context mCtx;

    @Override
    public String getContact() {
        return IMDataRepo.getInstance().getContract(mCtx);
    }

    @Override
    public void updateContact(String name) {
        IMDataRepo.getInstance().updateContract(mCtx, name);
    }

    @Override
    public void init(Context context) {
        mCtx = context;
    }
}
