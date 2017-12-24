package com.xpleemoon.component.im;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.component.ComponentName;
import com.xpleemoon.common.router.component.im.IMComponent;
import com.xpleemoon.component.annotations.InjectComponent;
import com.xpleemoon.component.im.router.path.PathConstants;

@Route(path = PathConstants.PATH_VIEW_IM)
public class IMActivity extends BaseCommonActivity {
    @InjectComponent(name = ComponentName.IM)
    IMComponent mIMComponent;
    private EditText mContractEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imcomponent_activity_im);
        mContractEdt = (EditText) findViewById(R.id.contract_edt);
        if (mIMComponent != null) {
            mContractEdt.setText(mIMComponent.getIMDaoService().getContact());
        } else {
            Toast.makeText(getApplicationContext(), "IM组件未知", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.update_contract) {
            if (mIMComponent == null) {
                Toast.makeText(getApplicationContext(), "IM组件未知", Toast.LENGTH_SHORT).show();
                return;
            }
            mIMComponent.getIMDaoService().updateContact(mContractEdt.getText().toString());
            Toast.makeText(getApplication(), "联系人更新成功", Toast.LENGTH_SHORT).show();
        }
    }
}
