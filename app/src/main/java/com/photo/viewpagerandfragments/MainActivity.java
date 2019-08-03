package com.photo.viewpagerandfragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

	private ViewPager pager;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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


	}
}
