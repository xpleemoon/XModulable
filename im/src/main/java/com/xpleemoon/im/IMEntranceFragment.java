package com.xpleemoon.im;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xpleemoon.common.app.BaseCommonFragment;
import com.xpleemoon.common.router.module.ModuleName;
import com.xpleemoon.common.router.module.im.IMModule;
import com.xpleemoon.xmodulable.annotation.InjectXModule;

/**
 * @author xpleemoon
 */
public class IMEntranceFragment extends BaseCommonFragment {
    @InjectXModule(name = ModuleName.IM)
    IMModule imModule;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.im_fragment_entrance, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.im_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imModule.getIMService().startIM();
            }
        });
    }
}
