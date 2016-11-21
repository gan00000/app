package com.efun.platform.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;

import com.efun.game.tw.R;
import com.lsjwzh.widget.recyclerviewpager.FragmentStatePagerAdapter;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.lsjwzh.widget.recyclerviewpager.TabLayoutSupport;

import java.util.LinkedHashMap;

public class PlatAppActivity extends PlatBaseActivity {
    protected RecyclerViewPager mRecyclerView;
    private FragmentsAdapter mAdapter;
    protected Toolbar titileToolbar;
    protected TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.efun_plat_app);

        titileToolbar = (Toolbar) findViewById(R.id.plat_app_toolbar);
        setSupportActionBar(titileToolbar);
        initViewPager();
        initTabLayout();

    }


    private void initTabLayout() {
        //给TabLayout增加Tab, 并关联ViewPager
        tabLayout = (TabLayout) findViewById(R.id.plat_app_tabs);
        TabLayoutSupport.setupWithViewPager(tabLayout, mRecyclerView, mAdapter);
    }

    protected void initViewPager() {
        mRecyclerView = (RecyclerViewPager) findViewById(R.id.plat_app_rec_viewpager);
        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layout);
        mAdapter = new FragmentsAdapter(getSupportFragmentManager());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLongClickable(true);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(50, mRecyclerView.getAdapter().getItemCount()));
        mRecyclerView.addOnPageChangedListener(new RecyclerViewPager.OnPageChangedListener() {
            @Override
            public void OnPageChanged(int oldPosition, int newPosition) {
                Log.d("test", "oldPosition:" + oldPosition + " newPosition:" + newPosition);
            }
        });

    }


    class FragmentsAdapter extends FragmentStatePagerAdapter implements TabLayoutSupport.ViewPagerTabLayoutAdapter {
        LinkedHashMap<Integer, Fragment> mFragmentCache = new LinkedHashMap<>();

        public FragmentsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position, Fragment.SavedState savedState) {
            Fragment f = mFragmentCache.containsKey(position) ? mFragmentCache.get(position) : null
                    ;
            Log.e("test", "getItem:" + position + " from cache" + mFragmentCache.containsKey(position));
            if (savedState == null || f.getArguments() == null) {
                Bundle bundle = new Bundle();
                bundle.putInt("index", position);
                f.setArguments(bundle);
                Log.e("test", "setArguments:" + position);
            } else if (!mFragmentCache.containsKey(position)) {
                f.setInitialSavedState(savedState);
                Log.e("test", "setInitialSavedState:" + position);
            }
            mFragmentCache.put(position, f);
            return f;
        }

        @Override
        public void onDestroyItem(int position, Fragment fragment) {
            // onDestroyItem
            while (mFragmentCache.size() > 5) {
                Object[] keys = mFragmentCache.keySet().toArray();
                mFragmentCache.remove(keys[0]);
            }
        }

        @Override
        public String getPageTitle(int position) {
            return "item-" + position;
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }
}
