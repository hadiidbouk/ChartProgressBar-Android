package com.hadiidbouk.charts;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ChartProgressBar extends LinearLayout {

	private float mMaxValue;
	private int mBarWidth;
	private int mBarHeight;
	private int mEmptyColor;
	private int mProgressColor;
	private int mProgressClickColor;
	private int mBarRadius;
	private Context mContext;
	private int mPinTextColor;
	private int mPinBackgroundColor;
	private int mPinPadding;
	private int mBarTitleColor;
	private float mBarTitleTxtSize;
	private float mPinTxtSize;
	private DisplayMetrics mMetrics;
	private FrameLayout oldFrameLayout;
	private boolean isBarCanBeClick;
	private ArrayList<BarData> mDataList;
	private boolean isOldBarClicked;
	private boolean isBarsEmpty;
	private int mPinMarginTop;

	public ChartProgressBar(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		mContext = context;
		setAttrs(attrs, 0);
		mMetrics = Resources.getSystem().getDisplayMetrics();
	}

	public ChartProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mContext = context;
		setAttrs(attrs, defStyleAttr);
		mMetrics = Resources.getSystem().getDisplayMetrics();
	}

	private void setAttrs(AttributeSet attrs, int defStyleAttr) {
		TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ChartProgressBar, defStyleAttr, 0);
		mBarWidth = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdBarWidth, 0);
		mBarHeight = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdBarHeight, 0);
		mBarRadius = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdBarRadius, 0);
		mEmptyColor = typedArray.getResourceId(R.styleable.ChartProgressBar_hdEmptyColor, ContextCompat.getColor(mContext, R.color.empty));
		mProgressColor = typedArray.getResourceId(R.styleable.ChartProgressBar_hdProgressColor, ContextCompat.getColor(mContext, R.color.progress));
		mProgressClickColor = typedArray.getResourceId(R.styleable.ChartProgressBar_hdProgressClickColor, ContextCompat.getColor(mContext, R.color.progress_click));
		mPinTextColor = typedArray.getResourceId(R.styleable.ChartProgressBar_hdPinTextColor, ContextCompat.getColor(mContext, R.color.pin_text));
		mPinBackgroundColor = typedArray.getResourceId(R.styleable.ChartProgressBar_hdPinBackgroundColor, ContextCompat.getColor(mContext, R.color.pin_background));
		mPinPadding = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdPinPadding, 3);
		isBarCanBeClick = typedArray.getBoolean(R.styleable.ChartProgressBar_hdBarCanBeClick, false);
		mBarTitleColor = typedArray.getResourceId(R.styleable.ChartProgressBar_hdBarTitleColor, ContextCompat.getColor(mContext, R.color.bar_title_color));
		mMaxValue = typedArray.getFloat(R.styleable.ChartProgressBar_hdMaxValue, 1f);
		mBarTitleTxtSize = typedArray.getDimension(R.styleable.ChartProgressBar_hdBarTitleTxtSize, 14);
		mPinTxtSize = typedArray.getDimension(R.styleable.ChartProgressBar_hdPinTxtSize, 14);
		mPinMarginTop = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdPinMarginTop, 0);
		typedArray.recycle();
	}


	public void setDataList(ArrayList<BarData> dataList) {
		mDataList = dataList;
	}


	public void build() {
		for (BarData data : mDataList) {
			int barValue = (int) (data.getBarValue() * 100);
			FrameLayout bar = getBar(data.getBarTitle(), barValue, data.getPinText());
			addView(bar);
		}
	}

	private FrameLayout getBar(final String title, final int value, final String pinTxt) {

		int maxValue = (int) (mMaxValue * 100);

		LinearLayout linearLayout = new LinearLayout(mContext);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
			LayoutParams.MATCH_PARENT,
			LayoutParams.MATCH_PARENT
		);

		params.gravity = Gravity.CENTER;
		linearLayout.setLayoutParams(params);
		linearLayout.setOrientation(VERTICAL);
		linearLayout.setGravity(Gravity.CENTER);

		//Adding bar
		Bar bar = new Bar(mContext, null, android.R.attr.progressBarStyleHorizontal);
		bar.setProgress(value);
		bar.setVisibility(View.VISIBLE);
		bar.setIndeterminate(false);

		bar.setMax(maxValue);

		bar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.progress_bar_shape));

		LayoutParams progressParams = new LayoutParams(
			mBarWidth,
			mBarHeight
		);

		progressParams.gravity = Gravity.CENTER;
		bar.setLayoutParams(progressParams);

		BarAnimation anim = new BarAnimation(bar, 0, value);
		anim.setDuration(250);
		bar.startAnimation(anim);

		LayerDrawable layerDrawable = (LayerDrawable) bar.getProgressDrawable();
		layerDrawable.mutate();

		GradientDrawable emptyLayer = (GradientDrawable) layerDrawable.getDrawable(0);
		ScaleDrawable scaleDrawable = (ScaleDrawable) layerDrawable.getDrawable(1);

		emptyLayer.setColor(ContextCompat.getColor(mContext, mEmptyColor));
		emptyLayer.setCornerRadius(mBarRadius);

		GradientDrawable progressLayer = (GradientDrawable) scaleDrawable.getDrawable();
		assert progressLayer != null;
		progressLayer.setColor(ContextCompat.getColor(mContext, mProgressColor));
		progressLayer.setCornerRadius(mBarRadius);


		linearLayout.addView(bar);

		//Adding txt below bar
		TextView txtBar = new TextView(mContext);
		LayoutParams txtParams = new LayoutParams(
			LayoutParams.WRAP_CONTENT,
			LayoutParams.WRAP_CONTENT
		);
		txtParams.setMargins(0, getDPI(15), 0, 0);
		txtBar.setTextSize(getSP(mBarTitleTxtSize));
		txtBar.setText(title);
		txtBar.setGravity(Gravity.CENTER);
		txtBar.setTextColor(ContextCompat.getColor(mContext, mBarTitleColor));

		linearLayout.addView(txtBar);

		FrameLayout rootFrameLayout = new FrameLayout(mContext);
		LayoutParams rootParams = new LayoutParams(
			0,
			LayoutParams.MATCH_PARENT,
			1f
		);

		rootParams.gravity = Gravity.CENTER;

		rootFrameLayout.setLayoutParams(rootParams);


		//Adding bar + title
		rootFrameLayout.addView(linearLayout);

		// Adding value Txt when click on a bar
		TextView pinTxtView = new TextView(mContext);
		FrameLayout.LayoutParams valueParams = new FrameLayout.LayoutParams(
			ViewGroup.LayoutParams.WRAP_CONTENT,
			ViewGroup.LayoutParams.WRAP_CONTENT
		);

		valueParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
		pinTxtView.setBackgroundResource(R.drawable.pin_shape);

		int padding = getDPI(3);
		pinTxtView.setPadding(padding * 2, padding, padding * 2, padding * 2);

		if (mPinPadding != 0) {
			int pinPadding = mPinPadding;
			pinTxtView.setPadding(pinPadding * 2, pinPadding, pinPadding * 2, pinPadding * 2);
		}

		Rect bounds = new Rect();
		pinTxtView.setText(pinTxt);
		pinTxtView.setMaxLines(1);
		pinTxtView.setTextSize(getSP(mPinTxtSize));
		pinTxtView.setGravity(Gravity.CENTER);

		Paint textPaint = pinTxtView.getPaint();
		textPaint.getTextBounds(pinTxt, 0, pinTxt.length(), bounds);
		int pinBackgroundHeight = bounds.height();

		int margin = ((value * mBarHeight) / maxValue) + getDPI(pinBackgroundHeight / 2) - mPinMarginTop;

		valueParams.setMargins(0, 0, 0, margin);
		pinTxtView.setLayoutParams(valueParams);

		pinTxtView.setVisibility(INVISIBLE);


		pinTxtView.setTextColor(ContextCompat.getColor(mContext, android.R.color.white));

		int color = mPinTextColor;
		if (color != 0)
			pinTxtView.setTextColor(ContextCompat.getColor(mContext, color));

		int backgroundColor = mPinBackgroundColor;
		if (backgroundColor != 0) {

			Drawable drawable = pinTxtView.getBackground();
			PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(
				ContextCompat.getColor(mContext, backgroundColor),
				PorterDuff.Mode.SRC_ATOP
			);

			drawable.setColorFilter(porterDuffColorFilter);
		}


		rootFrameLayout.addView(pinTxtView);

		if (isBarCanBeClick)
			rootFrameLayout.setOnClickListener(barClickListener);


		return rootFrameLayout;
	}


	private int getDPI(int size) {
		return (size * mMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
	}

	private float getSP(float size) {
		return size / mMetrics.scaledDensity;
	}

	public boolean isBarsEmpty() {
		return isBarsEmpty;
	}

	private FrameLayout.OnClickListener barClickListener = new FrameLayout.OnClickListener() {
		@Override
		public void onClick(View view) {

			if (isBarsEmpty)
				return;

			FrameLayout frameLayout = (FrameLayout) view;

			if (oldFrameLayout == frameLayout) {
				if (isOldBarClicked)
					clickBarOff(frameLayout);
				else
					clickBarOn(frameLayout);
			} else {

				if (oldFrameLayout != null)
					clickBarOff(oldFrameLayout);

				clickBarOn(frameLayout);
			}


			oldFrameLayout = frameLayout;

		}
	};

	private void clickBarOn(FrameLayout frameLayout) {

		isOldBarClicked = true;

		int childCount = frameLayout.getChildCount();

		for (int i = 0; i < childCount; i++) {

			View childView = frameLayout.getChildAt(i);
			if (childView instanceof LinearLayout) {

				LinearLayout linearLayout = (LinearLayout) childView;
				Bar bar = (Bar) linearLayout.getChildAt(0);
				TextView titleTxtView = (TextView) linearLayout.getChildAt(1);

				LayerDrawable layerDrawable = (LayerDrawable) bar.getProgressDrawable();
				layerDrawable.mutate();

				ScaleDrawable scaleDrawable = (ScaleDrawable) layerDrawable.getDrawable(1);

				GradientDrawable progressLayer = (GradientDrawable) scaleDrawable.getDrawable();
				assert progressLayer != null;
				if (mPinBackgroundColor != 0) {
					progressLayer.setColor(ContextCompat.getColor(mContext, mProgressClickColor));
					titleTxtView.setTextColor(ContextCompat.getColor(mContext, mProgressClickColor));
				} else {
					progressLayer.setColor(ContextCompat.getColor(mContext, android.R.color.holo_green_dark));
					titleTxtView.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_green_dark));
				}
			} else {
				TextView valueTxtView = (TextView) childView;
				valueTxtView.setVisibility(VISIBLE);
			}
		}


	}

	private void clickBarOff(FrameLayout frameLayout) {

		isOldBarClicked = false;

		int childCount = frameLayout.getChildCount();

		for (int i = 0; i < childCount; i++) {

			View childView = frameLayout.getChildAt(i);
			if (childView instanceof LinearLayout) {

				LinearLayout linearLayout = (LinearLayout) childView;
				Bar bar = (Bar) linearLayout.getChildAt(0);
				TextView titleTxtView = (TextView) linearLayout.getChildAt(1);

				LayerDrawable layerDrawable = (LayerDrawable) bar.getProgressDrawable();
				layerDrawable.mutate();

				ScaleDrawable scaleDrawable = (ScaleDrawable) layerDrawable.getDrawable(1);

				GradientDrawable progressLayer = (GradientDrawable) scaleDrawable.getDrawable();
				assert progressLayer != null;
				progressLayer.setColor(ContextCompat.getColor(mContext, mProgressColor));
				titleTxtView.setTextColor(ContextCompat.getColor(mContext, mBarTitleColor));
			} else {
				TextView valueTxtView = (TextView) childView;
				valueTxtView.setVisibility(INVISIBLE);
			}
		}
	}


	public ArrayList<BarData> getData() {
		return mDataList;
	}

	public void removeBarValues() {

		if (oldFrameLayout != null)
			removeClickedBar();

		final int barsCount = this.getChildCount();

		for (int i = 0; i < barsCount; i++) {

			FrameLayout rootFrame = (FrameLayout) this.getChildAt(i);
			int rootChildCount = rootFrame.getChildCount();

			for (int j = 0; j < rootChildCount; j++) {

				View childView = rootFrame.getChildAt(j);

				if (childView instanceof LinearLayout) {
					//bar
					LinearLayout barContainerLinear = ((LinearLayout) childView);
					int barContainerCount = barContainerLinear.getChildCount();

					for (int k = 0; k < barContainerCount; k++) {

						View view = barContainerLinear.getChildAt(j);

						if (view instanceof Bar) {
							((Bar) view).setProgress(0);
						}
					}
				}
			}


		}
		isBarsEmpty = true;
	}

	public void removeClickedBar() {

		clickBarOff(oldFrameLayout);

	}

	public void resetBarValues() {

		if (oldFrameLayout != null)
			removeClickedBar();

		final int barsCount = this.getChildCount();

		for (int i = 0; i < barsCount; i++) {

			FrameLayout rootFrame = (FrameLayout) this.getChildAt(i);
			int rootChildCount = rootFrame.getChildCount();

			for (int j = 0; j < rootChildCount; j++) {

				View childView = rootFrame.getChildAt(j);

				if (childView instanceof LinearLayout) {
					//bar
					LinearLayout barContainerLinear = ((LinearLayout) childView);
					int barContainerCount = barContainerLinear.getChildCount();

					for (int k = 0; k < barContainerCount; k++) {

						View view = barContainerLinear.getChildAt(j);

						if (view instanceof Bar) {
							((Bar) view).setProgress((int)(mDataList.get(i).getBarValue() * 100));

						}
					}
				}


			}
		}
		isBarsEmpty = false;
	}


}
