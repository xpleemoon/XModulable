package com.xpleemoon.main;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xpleemoon.common.app.BaseCommonActivity;
import com.xpleemoon.common.router.module.ModuleName;
import com.xpleemoon.common.router.module.im.IMModule;
import com.xpleemoon.common.router.module.live.LiveModule;
import com.xpleemoon.main.router.path.PathConstants;
import com.xpleemoon.xmodulable.annotation.InjectXModule;

import java.util.LinkedHashMap;
import java.util.Map;

@Route(path = PathConstants.PATH_VIEW_MAIN)
public class MainActivity extends BaseCommonActivity {
    @InjectXModule(name = ModuleName.LIVE)
    LiveModule liveModule;
    @InjectXModule(name = ModuleName.IM)
    IMModule imModule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(false); // 设置导航按钮无效
        getSupportActionBar().setDisplayHomeAsUpEnabled(false); // 不显示导航按钮
        getSupportActionBar().setDisplayShowTitleEnabled(true); // 显示标题

        TabLayout tabLayout = findViewById(R.id.main_tab_layout);
        ViewPager viewPager = findViewById(R.id.main_pager);
        LinkedHashMap<String, Fragment> fragments = new LinkedHashMap<>(3);
        fragments.put("main", new MainFragment());
        if (liveModule != null) {
            fragments.put("live", liveModule.getLiveService().createLiveEntranceFragment());
        }
        if (imModule != null) {
            fragments.put("im", imModule.getIMService().createIMEntranceFragment());
        }
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
    }

    private static class MainPagerAdapter extends FragmentStatePagerAdapter {
        private LinkedHashMap<String, Fragment> fragments;

        public MainPagerAdapter(FragmentManager fm, LinkedHashMap<String, Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        private Map.Entry<String, Fragment> getEntry(int position) {
            int index = 0;
            for (Map.Entry<String, Fragment> entry : fragments.entrySet()) {
                if (index++ == position) {
                    return entry;
                }
            }
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            Map.Entry<String, Fragment> entry = getEntry(position);
            return entry != null ? entry.getValue() : null;
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Map.Entry<String, Fragment> entry = getEntry(position);
            return entry != null ? entry.getKey() : null;
        }
    }

}
