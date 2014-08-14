package com.testSuite.stevenramzel.tabhostpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class TabHostPagerFragment extends Fragment implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    private FragmentTabHost mTabHost;
    private List<String> mTabNames;
    private ViewPager mViewPager;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TabHostPagerFragment newInstance(int sectionNumber) {
        TabHostPagerFragment fragment = new TabHostPagerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TabHostPagerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // R.layout.fragment_tabs_pager contains the layout as specified in your question
        View rootView = inflater.inflate(R.layout.fragment_tab_host_pager, container, false);

        // Initialise the tab-host
        mTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
        mViewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        // Initialise this list somewhere with the content that should be displayed
        mTabNames = new ArrayList<String>();
        mTabNames.add("Tab 1");
        mTabNames.add("Tab 2");
        // Give along the name - you can use this to hand over an ID for example
        Bundle b = new Bundle();
        String tabName = mTabNames.get(0);
        b.putString("TAB_ITEM_NAME", tabName);

        // Add a tab to the tabHost
        mTabHost.addTab(mTabHost.newTabSpec(tabName).setIndicator("Tab 1"), Fragment.class, b);
        tabName = mTabNames.get(1);
        b.putString("TAB_ITEM_NAME", tabName);

        // Add a tab to the tabHost
        mTabHost.addTab(mTabHost.newTabSpec(tabName).setIndicator("Tab 2"), Fragment.class, b);

        mViewPager.setAdapter(new WizardPagerAdapter(getChildFragmentManager()));
        mTabHost.setOnTabChangedListener(this);
        mViewPager.setOnPageChangeListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((TabHostPagerActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mTabHost.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onTabChanged(String s) {
        mViewPager.setCurrentItem(mTabNames.indexOf(s));
    }

    class WizardPagerAdapter extends FragmentStatePagerAdapter {
        FragmentManager mFragmentManager;

        public WizardPagerAdapter(FragmentManager fm) {
            super(fm);
            mFragmentManager = fm;
        }

        @Override
        public Fragment getItem(int position) {
            return new ContentFragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}