package com.hadiidbouk.library;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.util.ArrayList;

public class ProgressBarChart extends LinearLayout{

	private double mMaxValue;
	private int mBarWidth;
	private int mBarHeight;
	private int mEmptyColor;
	private int mFillColor;
	private int mBarRadius;
	private ArrayList<Double> mDataList;

	public ProgressBarChart(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public ProgressBarChart(Context context,
							ArrayList<Double> dataList,
							int barWidth,
							int barHeight,
							double maxValue,
							int emptyColor,
							int fillColor,
							int barRadius
	) {
		super(context);
		this.mMaxValue = maxValue;
		this.mBarWidth = barWidth;
		this.mBarHeight = barHeight;
		this.mEmptyColor = emptyColor;
		this.mFillColor = fillColor;
		this.mBarRadius = barRadius;
		this.mDataList = dataList;
	}

	public static class Builder {

		private ArrayList<Double> mDataList;
		private double mMaxValue;
		private int mBarWidth;
		private int mBarHeight;
		private int mEmptyColor;
		private int mFillColor;
		private int mBarRadius;
		private Context mContext;

		public Builder setContext(Context context) {
			mContext = context;
			return this;
		}
		public Builder setDataList(ArrayList<Double> dataList) {
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

		public Builder setMaxValue(double maxValue) {
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

		public ProgressBarChart build() {
			return new ProgressBarChart(
				mContext,
				mDataList,
				mBarWidth,
				mBarHeight,
				mMaxValue,
				mEmptyColor,
				mFillColor,
				mBarRadius
			);
		}
	}

}
