package com.xpleemoon.component.main;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.component.ComponentName;
import com.xpleemoon.common.router.component.im.IMComponent;
import com.xpleemoon.common.router.component.im.service.IMDaoService;
import com.xpleemoon.common.router.component.live.LiveComponent;
import com.xpleemoon.component.api.ComponentManager;
import com.xpleemoon.component.main.router.path.PathConstants;

@Route(path = PathConstants.PATH_VIEW_MAIN)
public class MainActivity extends BaseCommonActivity {
    private EditText mContractEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maincomponent_activity_main);
        mContractEdt = (EditText) findViewById(R.id.im_contract_edt);
        try {
            IMComponent imComponent = (IMComponent) ComponentManager.getInstance().getComponent(ComponentName.IM);
            IMDaoService imDaoService = imComponent.getIMDaoService();
            mContractEdt.setText(imDaoService.getContact());
        } catch (IllegalAccessException e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.start_live) {
            try {
                LiveComponent liveComponent = (LiveComponent) ComponentManager.getInstance().getComponent(ComponentName.LIVE);
                liveComponent.getLiveService().startLive();
            } catch (IllegalAccessException e) {
                Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.update_im_contract) {
            try {
                IMComponent imComponent = (IMComponent) ComponentManager.getInstance().getComponent(ComponentName.IM);
                imComponent.getIMDaoService().updateContact(mContractEdt.getText().toString());
                Toast.makeText(getApplication(), "IM组件中的联系人更新成功", Toast.LENGTH_SHORT).show();
            } catch (IllegalAccessException e) {
                Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
