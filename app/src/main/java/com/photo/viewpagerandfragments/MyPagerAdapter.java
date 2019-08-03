package com.photo.viewpagerandfragments;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		if (position == 0){
			return new FirstFragment();
		} else if (position == 1){
			return new SecondFragment();
		} else {
			return new ThirdFragment();
		}
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Nullable
	@Override
	public CharSequence getPageTitle(int position) {
		if (position == 0){
			return "First";
		} else if (position == 1){
			return "Second";
		} else {
			return "Third";
		}
	}
}
