package com.hadiidbouk.charts;

import android.content.Context;
import android.content.res.Resources;
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

	private static ArrayList<BarData> mDataList;

	public ChartProgressBar(Context context) {
		super(context);
		setGravity(Gravity.CENTER);
	}

	public ChartProgressBar(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		setGravity(Gravity.CENTER);
	}

	public ChartProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setGravity(Gravity.CENTER);
	}

	public static void setData(ArrayList<BarData> dataList) {
		mDataList = dataList;
	}

	public static class Builder {

		private ChartProgressBar mChart;
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
		private DisplayMetrics mMetrics;
		private FrameLayout oldFrameLayout;
		private boolean isBarCanBeClick;

		public Builder setContext(Context context) {
			mContext = context;
			return this;
		}

		public Builder setChart(ChartProgressBar chart) {
			mChart = chart;
			return this;
		}

		public Builder setDataList(ArrayList<BarData> dataList) {
			setData(dataList);
			return this;
		}

		public Builder setBarWidth(int barWidth) {
			mBarWidth = barWidth;
			return this;
		}

		public Builder setBarHeight(int barHeight) {
			mBarHeight = barHeight;
			return this;
		}


		public Builder setPinTextColor(int pinTextColor) {
			mPinTextColor = pinTextColor;
			return this;
		}


		public Builder setPinBackgroundColor(int pinBackgroundColor) {
			mPinBackgroundColor = pinBackgroundColor;
			return this;
		}

		public Builder setProgressClickColor(int color) {
			mProgressClickColor = color;
			return this;
		}

		public Builder setPinPadding(int pinPadding) {
			mPinPadding = pinPadding;
			return this;
		}


		public Builder setMaxValue(float maxValue) {
			mMaxValue = maxValue;
			return this;
		}

		public Builder setBarCanBeClick(boolean isBarCanBeClick) {
			this.isBarCanBeClick = isBarCanBeClick;
			return this;
		}

		public Builder setEmptyBarColor(int emptyColor) {
			mEmptyColor = emptyColor;
			return this;
		}

		public Builder setProgressBarColor(int progressColor) {
			mProgressColor = progressColor;
			return this;
		}

		public Builder setBarRadius(int barRadius) {
			mBarRadius = barRadius;
			return this;
		}

		public void build() {

			mChart.removeAllViews();

			mMetrics = Resources.getSystem().getDisplayMetrics();


			for (BarData data : mDataList) {

				int value = (int) (data.getBarValue() * 100);
				FrameLayout bar = getBar(data.getBarTitle(), value, data.getPinText());
				mChart.addView(bar);
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
				getDPI(mBarWidth),
				getDPI(mBarHeight)
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
//			progressLayer.setCornerRadii(new float[]{mBarRadius, mBarRadius, mBarRadius, mBarRadius,
//				mBarRadius, mBarRadius, mBarRadius, mBarRadius});


			linearLayout.addView(bar);

			//Adding txt below bar
			TextView txtBar = new TextView(mContext);
			LayoutParams txtParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			);
			txtParams.setMargins(0, getDPI(15), 0, 0);
			txtBar.setTextSize(14);
			txtBar.setText(title);
			txtBar.setGravity(Gravity.CENTER);
			txtBar.setTextColor(ContextCompat.getColor(mContext, mProgressColor));

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
			pinTxtView.setPadding(padding *2, padding, padding *2, padding * 2);

			if (mPinPadding != 0) {
				int pinPadding = getDPI(mPinPadding);
				pinTxtView.setPadding(pinPadding*2, pinPadding, pinPadding*2, pinPadding * 2);
			}

			Rect bounds = new Rect();
			Paint textPaint = pinTxtView.getPaint();
			textPaint.getTextBounds(pinTxt, 0, pinTxt.length(), bounds);
			int pinBackgroundHeight = bounds.height();

			int margin = ((value * mBarHeight) / maxValue) + pinBackgroundHeight / 2 - 4;

			valueParams.setMargins(0, 0, 0, getDPI(margin));
			pinTxtView.setLayoutParams(valueParams);


			pinTxtView.setText(pinTxt);
			pinTxtView.setVisibility(INVISIBLE);
			pinTxtView.setMaxLines(1);

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

		private FrameLayout.OnClickListener barClickListener = new FrameLayout.OnClickListener() {
			@Override
			public void onClick(View view) {

				FrameLayout frameLayout = (FrameLayout) view;

				if (oldFrameLayout != null)
					clickBarOff(oldFrameLayout);

				if (oldFrameLayout != frameLayout)
					clickBarOn(frameLayout);

				oldFrameLayout = frameLayout;

			}
		};

		private void clickBarOn(FrameLayout frameLayout) {
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
					titleTxtView.setTextColor(ContextCompat.getColor(mContext, mProgressColor));
				} else {
					TextView valueTxtView = (TextView) childView;
					valueTxtView.setVisibility(INVISIBLE);
				}
			}
		}
	}


	public ArrayList<BarData> getData() {
		return mDataList;
	}

	public void removeBarValues() {

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
	}

	public void resetBarClick() {

		final int barsCount = this.getChildCount();

		for (int i = 0; i < barsCount; i++)
		{
			FrameLayout rootFrame = (FrameLayout) this.getChildAt(i);

		}


	}
	public void resetBarValues() {

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
							((Bar) view).setProgress((int) mDataList.get(i).getBarValue());

						}
					}
				}


			}
		}
	}



}
