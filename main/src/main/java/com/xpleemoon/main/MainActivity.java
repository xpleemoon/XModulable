package com.xpleemoon.main;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.module.ModuleName;
import com.xpleemoon.common.router.module.im.IMModule;
import com.xpleemoon.common.router.module.live.LiveModule;
import com.xpleemoon.xmodulable.annotation.InjectXModule;
import com.xpleemoon.main.router.path.PathConstants;

@Route(path = PathConstants.PATH_VIEW_MAIN)
public class MainActivity extends BaseCommonActivity {
    @InjectXModule(name = ModuleName.LIVE)
    LiveModule liveModule;
    @InjectXModule(name = ModuleName.IM)
    IMModule imModule;
    private EditText mContractEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);
        mContractEdt = (EditText) findViewById(R.id.im_contract_edt);
        if (imModule != null) {
            mContractEdt.setText(imModule.getIMDaoService().getContact());
        } else {
            Toast.makeText(getApplicationContext(), "IM组件未知", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.start_live) {
            if (liveModule == null) {
                Toast.makeText(getApplicationContext(), "Live组件未知", Toast.LENGTH_SHORT).show();
                return;
            }
            liveModule.getLiveService().startLive();
        } else if (id == R.id.update_im_contract) {
            if (imModule == null) {
                Toast.makeText(getApplicationContext(), "IM组件未知", Toast.LENGTH_SHORT).show();
                return;
            }
            imModule.getIMDaoService().updateContact(mContractEdt.getText().toString());
            Toast.makeText(getApplication(), "IM组件中的联系人更新成功", Toast.LENGTH_SHORT).show();
        }
    }
}
