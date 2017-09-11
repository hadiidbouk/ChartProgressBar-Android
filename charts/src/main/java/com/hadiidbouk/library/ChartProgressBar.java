package com.hadiidbouk.library;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
		private ArrayList<BarData> mDataList;
		private float mMaxValue;
		private int mBarWidth;
		private int mBarHeight;
		private int mEmptyColor;
		private int mProgressColor;
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

		public Builder setDataList(ArrayList<BarData> dataList) {
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

			mMetrics = Resources.getSystem().getDisplayMetrics();


			for (BarData data : mDataList) {
				LinearLayout bar = getBar(data.getBarTitle(), data.getBarValue());
				mChart.addView(bar);
			}
		}

		private LinearLayout getBar(final String title, final float value) {

			LinearLayout linearLayout = new LinearLayout(mContext);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				0,
				LayoutParams.WRAP_CONTENT,
				1f
			);

			linearLayout.setLayoutParams(params);
			linearLayout.setOrientation(VERTICAL);
			linearLayout.setGravity(Gravity.CENTER);

			//Adding bar
			Bar bar = new Bar(mContext, null, android.R.attr.progressBarStyleHorizontal);
			bar.setProgress((int) value);
			bar.setVisibility(View.VISIBLE);
			bar.setIndeterminate(false);

			bar.setMax((int) mMaxValue);

			bar.setProgressDrawable(ContextCompat.getDrawable(mContext, R.drawable.progress_bar_shape));

			LinearLayout.LayoutParams progressParams = new LinearLayout.LayoutParams(
				getDPI(mBarWidth),
				getDPI(mBarHeight)
			);

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
			progressLayer.setCornerRadii(new float[]{mBarRadius, mBarRadius, mBarRadius, mBarRadius,
				mBarRadius, mBarRadius, mBarRadius, mBarRadius});


			linearLayout.addView(bar);

			//Adding txt below bar
			TextView txtBar = new TextView(mContext);
			LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT
			);
			txtParams.setMargins(0,

				getDPI(15), 0, 0);
			txtBar.setTextSize(14);
			txtBar.setText(title);
			txtBar.setGravity(Gravity.CENTER);
			linearLayout.addView(txtBar);
			return linearLayout;
		}

		private int getDPI(int size) {
			return (size * mMetrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
		}


	}


}
