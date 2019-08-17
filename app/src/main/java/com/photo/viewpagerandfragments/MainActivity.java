package com.photo.viewpagerandfragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

	public static final String PREFS_NAME = "my_pref_name";

	private ViewPager pager;
	private SharedPreferences preferences;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

		int pageIndex = preferences.getInt("page_index", 0);

		pager = findViewById(R.id.pager);

		MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());

		pager.setAdapter(adapter);

		pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				Log.d("pager", "onPageScrolled position = "
						+ position
						+ " positionOffset = "
						+ positionOffset
						+ " positionOffsetPixels = "
						+ positionOffsetPixels);
			}

			@Override
			public void onPageSelected(int position) {
				Log.i("pager", "onPageSelected position = "
						+ position);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				Log.w("pager", "onPageScrollStateChanged position = "
						+ state);
			}
		});

		CustomTabLayout tabLayout = findViewById(R.id.tab_layout);

		tabLayout.attachToViewPager(pager);


		pager.setCurrentItem(pageIndex);

	}

	@Override
	protected void onDestroy() {
		Log.i("pager", "onDestroy index = " + pager.getCurrentItem());
		preferences.edit().putInt("page_index", pager.getCurrentItem()).apply();
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		Log.i("pager", "onStop");
		super.onStop();
	}

}
