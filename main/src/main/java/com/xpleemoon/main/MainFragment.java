package com.xpleemoon.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.xpleemoon.common.app.BaseCommonFragment;
import com.xpleemoon.common.router.module.ModuleName;
import com.xpleemoon.common.router.module.im.IMModule;
import com.xpleemoon.common.router.module.live.LiveModule;
import com.xpleemoon.xmodulable.annotation.InjectXModule;

/**
 * @author xpleemoon
 */
public class MainFragment extends BaseCommonFragment implements View.OnClickListener {
    @InjectXModule(name = ModuleName.LIVE)
    LiveModule liveModule;
    @InjectXModule(name = ModuleName.IM)
    IMModule imModule;
    private EditText mContractEdt;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.start_live).setOnClickListener(this);
        view.findViewById(R.id.update_im_contract).setOnClickListener(this);
        mContractEdt = (EditText) view.findViewById(R.id.im_contract_edt);
        if (imModule != null) {
            mContractEdt.setText(imModule.getIMDaoService().getContact());
        } else {
            Toast.makeText(getContext(), "IM组件未知", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.start_live) {
            if (liveModule == null) {
                Toast.makeText(getContext(), "Live组件未知", Toast.LENGTH_SHORT).show();
                return;
            }
            liveModule.getLiveService().startLive();
        } else if (id == R.id.update_im_contract) {
            if (imModule == null) {
                Toast.makeText(getContext(), "IM组件未知", Toast.LENGTH_SHORT).show();
                return;
            }
            imModule.getIMDaoService().updateContact(mContractEdt.getText().toString());
            Toast.makeText(getContext(), "IM组件中的联系人更新成功", Toast.LENGTH_SHORT).show();
        }
    }
}
