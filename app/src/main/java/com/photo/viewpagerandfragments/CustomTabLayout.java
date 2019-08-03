package com.photo.viewpagerandfragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class CustomTabLayout extends LinearLayout {

	private List<TextView> textViews = new ArrayList<>();
	private LinearLayout container;

	private int pageCount;
	private ViewPager pager;
	private ViewPager.OnPageChangeListener pageChangeListener;
	private OnClickListener itemClickListener;


	public CustomTabLayout(Context context) {
		super(context);
		init();
	}

	public CustomTabLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
//		container = new LinearLayout(getContext());
//		container.setOrientation(LinearLayout.HORIZONTAL);
//		container.setGravity(Gravity.CENTER_VERTICAL);
//		addView(container, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//				ViewGroup.LayoutParams.MATCH_PARENT));
		setOrientation(LinearLayout.HORIZONTAL);
//		setGravity(Gravity.BOTTOM);

		pageChangeListener = new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				for (int i = 0; i < textViews.size(); i++) {
					textViews.get(i).setSelected(position == i);
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		};

		itemClickListener = new OnClickListener() {
			@Override
			public void onClick(View view) {
				int index = textViews.indexOf((TextView) view);

				if (pager != null && index >= 0
						&& pager.getAdapter().getCount() > index) {
					pager.setCurrentItem(index, true);
				}
			}
		};
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public void attachToViewPager(ViewPager pager) {
		this.pager = pager;
		pager.addOnPageChangeListener(pageChangeListener);
		initTabs();
	}

	private void initTabs() {
		if (pager == null || pager.getAdapter() == null) {
			return;
		}

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0,
				ViewGroup.LayoutParams.MATCH_PARENT);
		lp.weight = 1;
		textViews.clear();

		for (int i = 0; i < pager.getAdapter().getCount(); i++) {
			TextView tabView = new TextView(getContext());
			tabView.setGravity(Gravity.CENTER);
			tabView.setText(pager.getAdapter().getPageTitle(i));
			tabView.setBackgroundResource(R.drawable.item_selector);
			addView(tabView, lp);
			tabView.setOnClickListener(itemClickListener);
			tabView.setSelected(pager.getCurrentItem() == i);
			textViews.add(tabView);
		}
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}
}
