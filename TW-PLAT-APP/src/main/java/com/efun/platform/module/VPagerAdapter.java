package com.efun.platform.module;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

/**
 * V pager adapter
 * 
 * @author Jesse
 * 
 */
public class VPagerAdapter extends PagerAdapter {
	private ViewGroup[] groups;
	private String[] titles;

	public VPagerAdapter(ViewGroup[] groups) {
		this.groups = groups;
	}
	
	public void setTitles(String[] titles){
		this.titles = titles;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		container.addView(groups[position], LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		return groups[position];
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView(groups[position]);
	}

	@Override
	public int getCount() {
		return groups.length;
	}

	@Override
	public int getItemPosition(Object object) {
		return super.getItemPosition(object);
	}

	@Override
	public boolean isViewFromObject(View view, Object obj) {
		return view == obj;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return titles[position % titles.length];
	}
}