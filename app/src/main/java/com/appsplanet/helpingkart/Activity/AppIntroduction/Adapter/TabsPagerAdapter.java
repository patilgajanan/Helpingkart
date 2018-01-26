package com.appsplanet.helpingkart.Activity.AppIntroduction.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.appsplanet.helpingkart.Activity.AppIntroduction.Fragment.AppIntroduction1;
import com.appsplanet.helpingkart.Activity.AppIntroduction.Fragment.AppIntroduction2;
import com.appsplanet.helpingkart.Activity.AppIntroduction.Fragment.AppIntroduction3;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			return new AppIntroduction1();
		case 1:
			return new AppIntroduction2();
		case 2:
			return new AppIntroduction3();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}

}
