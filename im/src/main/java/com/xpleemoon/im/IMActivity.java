package com.xpleemoon.im;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.module.ModuleName;
import com.xpleemoon.common.router.module.im.IMModule;
import com.xpleemoon.xmodulable.annotation.InjectXModule;
import com.xpleemoon.im.router.path.PathConstants;

@Route(path = PathConstants.PATH_VIEW_IM)
public class IMActivity extends BaseCommonActivity {
    @InjectXModule(name = ModuleName.IM)
    IMModule imModule;
    private EditText mContractEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_activity_im);
        mContractEdt = (EditText) findViewById(R.id.contract_edt);
        mContractEdt.setText(imModule.getIMDaoService().getContact());
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.update_contract) {
            imModule.getIMDaoService().updateContact(mContractEdt.getText().toString());
            Toast.makeText(getApplication(), "联系人更新成功", Toast.LENGTH_SHORT).show();
        }
    }
}
