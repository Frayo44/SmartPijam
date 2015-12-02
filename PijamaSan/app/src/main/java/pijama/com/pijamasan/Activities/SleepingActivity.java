package pijama.com.pijamasan.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import pijama.com.pijamasan.Fragments.DataFragment;
import pijama.com.pijamasan.Fragments.SleepingFragment;
import pijama.com.pijamasan.R;

/**
 * Created by Yoav on 11/30/2015.
 */
public class SleepingActivity extends ActionBarActivity {

    Toolbar toolbar;
    private ViewPager pager;
    FragmentPagerItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleeping_actvity);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setTitleTextColor(Color.WHITE);

        adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add(R.string.data, DataFragment.class)
                .add(R.string.sleeping, SleepingFragment.class)
                .create());

        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(pager);

        pager.setCurrentItem(1);




    }
}
