package com.photo.viewpagerandfragments;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class CustomView extends View {

	int width ;
	int height ;
	public CustomView(Context context) {
		super(context);
	}

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		width = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.EXACTLY);
		height = MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.EXACTLY);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);


	}
}
