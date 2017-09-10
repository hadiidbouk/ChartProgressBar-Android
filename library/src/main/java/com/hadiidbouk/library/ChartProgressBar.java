package com.hadiidbouk.library;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.LinearLayout;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.ArrayList;

public class ChartProgressBar extends LinearLayout {

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


	public static class Builder {

		private ChartProgressBar mChart;
		private ArrayList<Float> mDataList;
		private float mMaxValue;
		private int mBarWidth;
		private int mBarHeight;
		private int mEmptyColor;
		private int mFillColor;
		private int mBarRadius;
		private Context mContext;
		private DisplayMetrics mMetrics;
		private int mBarMargins;

		public Builder setContext(Context context) {
			mContext = context;
			return this;
		}

		public Builder setChart(ChartProgressBar chart) {
			mChart = chart;
			return this;
		}

		public Builder setDataList(ArrayList<Float> dataList) {
			mDataList = dataList;
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

		public Builder setBarMargins(int barMargins) {
			mBarMargins = barMargins;
			return this;
		}
		public Builder setMaxValue(float maxValue) {
			mMaxValue = maxValue;
			return this;
		}

		public Builder setEmptyColor(int emptyColor) {
			mEmptyColor = emptyColor;
			return this;
		}

		public Builder setFillColor(int fillColor) {
			mFillColor = fillColor;
			return this;
		}

		public Builder setBarRadius(int barRadius) {
			mBarRadius = barRadius;
			return this;
		}

		public void build() {

			mMetrics = Resources.getSystem().getDisplayMetrics();


			for (float value : mDataList) {
				RoundCornerProgressBar bar = getBar(value);
				mChart.addView(bar);
			}
		}

		private RoundCornerProgressBar getBar(float value) {
			RoundCornerProgressBar bar = new RoundCornerProgressBar(mContext, null);
			bar.setProgress(value);
			bar.setProgressColor(ContextCompat.getColor(mContext, mFillColor));
			bar.setProgressBackgroundColor(ContextCompat.getColor(mContext, mEmptyColor));
			bar.setRotation(90);
			bar.setMax(mMaxValue);
			bar.setRadius(mBarRadius);
			bar.setReverse(true);

			LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(
				getDPI(mBarHeight),
				getDPI(mBarWidth)
			);

			progressParams.setMargins(-getDPI(mBarHeight) + getDPI(mBarMargins),0,0,0);

			bar.setLayoutParams(progressParams);
			return bar;
		}

		private int getDPI(int size){
			return (size * mMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
		}
	}


}
