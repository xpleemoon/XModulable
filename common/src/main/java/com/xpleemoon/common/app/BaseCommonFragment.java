package com.xpleemoon.common.app;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xpleemoon.basiclib.BaseFragment;

/**
 * 公共业务基础fragment
 * <ul>
 * <li>{@link #onCreate(Bundle)}内部调用了{@link ARouter#inject(Object)}用于IOC</li>
 * </ul>
 *
 * @author xpleemoon
 */
public class BaseCommonFragment extends BaseFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
    }
}
