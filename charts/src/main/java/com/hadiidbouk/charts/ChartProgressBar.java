package com.hadiidbouk.charts;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ChartProgressBar extends FrameLayout {

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
	private int mPinPaddingTop;
	private int mPinPaddingBottom;
	private int mPinPaddingEnd;
	private int mPinPaddingStart;
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
	private int mPinMarginBottom;
	private int mPinMarginEnd;
	private int mPinMarginStart;
	private int mPinDrawable;
	private ArrayList<TextView> pins = new ArrayList<>();
	private int mBarTitleMarginTop;
	private int mBarTitleSelectedColor;
	private int mProgressDisableColor;
	private OnBarClickedListener listener;
	private boolean mBarCanBeToggle;

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
		mProgressDisableColor = typedArray.getResourceId(R.styleable.ChartProgressBar_hdProgressDisableColor, ContextCompat.getColor(mContext, android.R.color.darker_gray));
		mBarTitleSelectedColor = typedArray.getResourceId(R.styleable.ChartProgressBar_hdBarTitleSelectedColor, ContextCompat.getColor(mContext, R.color.progress_click));
		mPinTextColor = typedArray.getResourceId(R.styleable.ChartProgressBar_hdPinTextColor, ContextCompat.getColor(mContext, R.color.pin_text));
		mPinBackgroundColor = typedArray.getResourceId(R.styleable.ChartProgressBar_hdPinBackgroundColor, ContextCompat.getColor(mContext, R.color.pin_background));
		mPinPaddingTop = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdPinPaddingTop, 3);
		mPinPaddingBottom = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdPinPaddingBottom, 3);
		mPinPaddingEnd = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdPinPaddingEnd, 3);
		mPinPaddingStart = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdPinPaddingStart, 3);
		isBarCanBeClick = typedArray.getBoolean(R.styleable.ChartProgressBar_hdBarCanBeClick, false);
		mBarTitleColor = typedArray.getResourceId(R.styleable.ChartProgressBar_hdBarTitleColor, ContextCompat.getColor(mContext, R.color.bar_title_color));
		mMaxValue = typedArray.getFloat(R.styleable.ChartProgressBar_hdMaxValue, 1f);
		mBarTitleTxtSize = typedArray.getDimension(R.styleable.ChartProgressBar_hdBarTitleTxtSize, 14);
		mPinTxtSize = typedArray.getDimension(R.styleable.ChartProgressBar_hdPinTxtSize, 14);
		mPinMarginTop = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdPinMarginTop, 0);
		mPinMarginBottom = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdPinMarginBottom, 0);
		mPinMarginEnd = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdPinMarginEnd, 0);
		mPinMarginStart = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdPinMarginStart, 0);
		mBarTitleMarginTop = typedArray.getDimensionPixelSize(R.styleable.ChartProgressBar_hdBarTitleMarginTop, 0);
		mPinDrawable = typedArray.getResourceId(R.styleable.ChartProgressBar_hdPinDrawable, 0);
		mBarCanBeToggle = typedArray.getBoolean(R.styleable.ChartProgressBar_hdBarCanBeToggle, false);
		typedArray.recycle();
	}


	public void setDataList(ArrayList<BarData> dataList) {
		mDataList = dataList;
	}


	public void setOnBarClickedListener(OnBarClickedListener listener) {
		this.listener = listener;
	}

	public void build() {

		removeAllViews();

		LinearLayout linearLayout = new LinearLayout(mContext);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			LayoutParams.MATCH_PARENT,
			LayoutParams.MATCH_PARENT
		);

		Drawable d = getResources().getDrawable(mPinDrawable);
		int h = d.getIntrinsicHeight();

		if (mPinMarginBottom != 0)
			h += mPinMarginBottom / 2;

		linearLayout.setPadding(0, h, 0, 0);
		linearLayout.setLayoutParams(params);

		addView(linearLayout);
		int i = 0;
		for (BarData data : mDataList) {
			int barValue = (int) (data.getBarValue() * 100);
			FrameLayout bar = getBar(data.getBarTitle(), barValue, i);
			linearLayout.addView(bar);
			i++;
		}

		getViewTreeObserver().addOnGlobalLayoutListener(
			new ViewTreeObserver.OnGlobalLayoutListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void onGlobalLayout() {
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
						getViewTreeObserver()
							.removeOnGlobalLayoutListener(this);
					} else {
						getViewTreeObserver()
							.removeGlobalOnLayoutListener(this);
					}
					setPins();
				}
			});
	}

	private FrameLayout getBar(final String title, final int value, final int index) {

		int maxValue = (int) (mMaxValue * 100);

		LinearLayout linearLayout = new LinearLayout(mContext);
		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
			LayoutParams.MATCH_PARENT,
			LayoutParams.MATCH_PARENT
		);

		params.gravity = Gravity.CENTER;
		linearLayout.setLayoutParams(params);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
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

		if (progressLayer != null) {
			progressLayer.setColor(ContextCompat.getColor(mContext, mProgressColor));
			progressLayer.setCornerRadius(mBarRadius);
		}


		linearLayout.addView(bar);

		//Adding txt below bar
		TextView txtBar = new TextView(mContext);
		LayoutParams txtParams = new LayoutParams(
			LayoutParams.WRAP_CONTENT,
			LayoutParams.WRAP_CONTENT
		);

		txtBar.setTextSize(getSP(mBarTitleTxtSize));
		txtBar.setText(title);
		txtBar.setGravity(Gravity.CENTER);
		txtBar.setTextColor(ContextCompat.getColor(mContext, mBarTitleColor));
		txtBar.setPadding(0, mBarTitleMarginTop, 0, 0);

		txtBar.setLayoutParams(txtParams);

		linearLayout.addView(txtBar);

		FrameLayout rootFrameLayout = new FrameLayout(mContext);
		LinearLayout.LayoutParams rootParams = new LinearLayout.LayoutParams(
			0,
			LayoutParams.MATCH_PARENT,
			1f
		);

		rootParams.gravity = Gravity.CENTER;


		//rootParams.setMargins(0, h, 0, h);
		rootFrameLayout.setLayoutParams(rootParams);


		//Adding bar + title
		rootFrameLayout.addView(linearLayout);

		if (isBarCanBeClick)
			rootFrameLayout.setOnClickListener(barClickListener);

		rootFrameLayout.setTag(index);
		return rootFrameLayout;
	}

	private void setPins() {

		pins.clear();
		
		int maxValue = (int) (mMaxValue * 100);

		int childCount = getChildCount();

		LinearLayout linearLayout = null;

		for (int i = 0; i < childCount; i++) {
			View view = getChildAt(i);

			if (view instanceof LinearLayout) {
				linearLayout = (LinearLayout) view;
				break;
			}
		}

		if (linearLayout != null) {
			childCount = linearLayout.getChildCount();

			for (int i = 0; i < childCount; i++) {
				View view = linearLayout.getChildAt(i);

				BarData data = mDataList.get(i);

				int value = (int) (data.getBarValue() * 100);
				String pinTxt = data.getPinText();

				FrameLayout barFrame = (FrameLayout) view;
				int frameCount = barFrame.getChildCount();

				for (int j = 0; j < frameCount; j++) {
					View v = barFrame.getChildAt(j);

					if (v instanceof LinearLayout) {

						int count = ((LinearLayout) v).getChildCount();

						for (int k = 0; k < count; k++) {

							if (((LinearLayout) v).getChildAt(k) instanceof Bar) {
								Bar bar = (Bar) ((LinearLayout) v).getChildAt(k);

								// Adding value Txt when click on a bar
								TextView pinTxtView = new TextView(mContext);
								FrameLayout.LayoutParams valueParams = new FrameLayout.LayoutParams(
									ViewGroup.LayoutParams.WRAP_CONTENT,
									ViewGroup.LayoutParams.WRAP_CONTENT
								);

								int pinDrawableId = mPinDrawable != 0 ? mPinDrawable : R.drawable.pin_shape;
								pinTxtView.setBackgroundResource(pinDrawableId);


								pinTxtView.setPadding(mPinPaddingStart, mPinPaddingTop, mPinPaddingEnd, mPinPaddingBottom);


								pinTxtView.setTextColor(ContextCompat.getColor(mContext, mPinTextColor));

								Rect bounds = new Rect();
								pinTxtView.setText(pinTxt);
								pinTxtView.setMaxLines(1);
								pinTxtView.setTextSize(getSP(mPinTxtSize));
								pinTxtView.setGravity(Gravity.CENTER);

								Paint textPaint = pinTxtView.getPaint();
								textPaint.getTextBounds(pinTxt, 0, pinTxt.length(), bounds);
								int pinBackgroundHeight = bounds.height();


								int pinBackgroundWidth = bounds.width();

								int x = (int) (view.getX() - pinBackgroundWidth / 2 + view.getMeasuredWidth() / 2);
								int y = (int) (view.getY());

								int pinPosition = mBarHeight - ((value * mBarHeight) / maxValue) - pinBackgroundHeight / 2;

								pinTxtView.setLayoutParams(valueParams);

								pinTxtView.setX(x + mPinMarginStart - mPinMarginEnd);
								pinTxtView.setY(y + pinPosition + mPinMarginTop - mPinMarginBottom);

								addView(pinTxtView);

								pins.add(pinTxtView);

								pinTxtView.setVisibility(View.INVISIBLE);
								pinTxtView.setTag(i);
							}
						}
					}
				}
			}
		}
	}

	public void setMaxValue(float mMaxValue) {
		this.mMaxValue = mMaxValue;
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


			if (oldFrameLayout == frameLayout && mBarCanBeToggle) {
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

			if (listener != null)
				listener.onBarClicked((int) frameLayout.getTag());

		}
	};

	private void clickBarOn(FrameLayout frameLayout) {

		pins.get((int) frameLayout.getTag()).setVisibility(View.VISIBLE);

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
				if (mPinBackgroundColor != 0) {
					if (progressLayer != null) {
						progressLayer.setColor(ContextCompat.getColor(mContext, mProgressClickColor));
					}

				} else {
					if (progressLayer != null) {
						progressLayer.setColor(ContextCompat.getColor(mContext, android.R.color.holo_green_dark));
					}
				}

				if (mBarTitleSelectedColor > 0) {
					titleTxtView.setTextColor(ContextCompat.getColor(mContext, mBarTitleSelectedColor));
				} else {
					titleTxtView.setTextColor(ContextCompat.getColor(mContext, android.R.color.holo_green_dark));
				}

			}
		}
	}

	private void clickBarOff(FrameLayout frameLayout) {

		pins.get((int) frameLayout.getTag()).setVisibility(View.INVISIBLE);


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
				if (progressLayer != null) {
					progressLayer.setColor(ContextCompat.getColor(mContext, mProgressColor));
				}
				titleTxtView.setTextColor(ContextCompat.getColor(mContext, mBarTitleColor));
			}
		}
	}


	public ArrayList<BarData> getData() {
		return mDataList;
	}

	public void removeBarValues() {

		if (oldFrameLayout != null)
			removeClickedBar();

		final int barsCount = ((LinearLayout) this.getChildAt(0)).getChildCount();

		for (int i = 0; i < barsCount; i++) {

			FrameLayout rootFrame = (FrameLayout) ((LinearLayout) this.getChildAt(0)).getChildAt(i);
			int rootChildCount = rootFrame.getChildCount();

			for (int j = 0; j < rootChildCount; j++) {

				View childView = rootFrame.getChildAt(j);

				if (childView instanceof LinearLayout) {
					//bar
					LinearLayout barContainerLinear = ((LinearLayout) childView);
					int barContainerCount = barContainerLinear.getChildCount();

					for (int k = 0; k < barContainerCount; k++) {

						View view = barContainerLinear.getChildAt(k);

						if (view instanceof Bar) {
							BarAnimation anim = new BarAnimation(((Bar) view), (int) (mDataList.get(i).getBarValue() * 100), 0);
							anim.setDuration(250);
							((Bar) view).startAnimation(anim);
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

		final int barsCount = ((LinearLayout) this.getChildAt(0)).getChildCount();

		for (int i = 0; i < barsCount; i++) {

			FrameLayout rootFrame = (FrameLayout) ((LinearLayout) this.getChildAt(0)).getChildAt(i);
			int rootChildCount = rootFrame.getChildCount();

			for (int j = 0; j < rootChildCount; j++) {

				View childView = rootFrame.getChildAt(j);

				if (childView instanceof LinearLayout) {
					//bar
					LinearLayout barContainerLinear = ((LinearLayout) childView);
					int barContainerCount = barContainerLinear.getChildCount();

					for (int k = 0; k < barContainerCount; k++) {

						View view = barContainerLinear.getChildAt(k);

						if (view instanceof Bar) {
							BarAnimation anim = new BarAnimation(((Bar) view), 0, (int) (mDataList.get(i).getBarValue() * 100));
							anim.setDuration(250);
							((Bar) view).startAnimation(anim);
						}
					}
				}


			}
		}
		isBarsEmpty = false;
	}

	public void disableBar(int index) {

		final int barsCount = ((LinearLayout) this.getChildAt(0)).getChildCount();

		for (int i = 0; i < barsCount; i++) {

			FrameLayout rootFrame = (FrameLayout) ((LinearLayout) this.getChildAt(0)).getChildAt(i);

			int rootChildCount = rootFrame.getChildCount();

			for (int j = 0; j < rootChildCount; j++) {

				if ((int) rootFrame.getTag() != index)
					continue;

				rootFrame.setEnabled(false);
				rootFrame.setClickable(false);

				View childView = rootFrame.getChildAt(j);
				if (childView instanceof LinearLayout) {
					//bar
					LinearLayout barContainerLinear = ((LinearLayout) childView);
					int barContainerCount = barContainerLinear.getChildCount();

					for (int k = 0; k < barContainerCount; k++) {

						View view = barContainerLinear.getChildAt(k);

						if (view instanceof Bar) {

							Bar bar = (Bar) view;

							LayerDrawable layerDrawable = (LayerDrawable) bar.getProgressDrawable();
							layerDrawable.mutate();

							ScaleDrawable scaleDrawable = (ScaleDrawable) layerDrawable.getDrawable(1);

							GradientDrawable progressLayer = (GradientDrawable) scaleDrawable.getDrawable();

							if (progressLayer != null) {

								if (mProgressDisableColor > 0)
									progressLayer.setColor(ContextCompat.getColor(mContext, mProgressDisableColor));
								else
									progressLayer.setColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
							}
						} else {
							TextView titleTxtView = (TextView) view;
							if (mProgressDisableColor > 0)
								titleTxtView.setTextColor(ContextCompat.getColor(mContext, mProgressDisableColor));
							else
								titleTxtView.setTextColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
						}
					}
				}
			}
		}
	}


	public void enableBar(int index) {

		final int barsCount = ((LinearLayout) this.getChildAt(0)).getChildCount();

		for (int i = 0; i < barsCount; i++) {

			FrameLayout rootFrame = (FrameLayout) ((LinearLayout) this.getChildAt(0)).getChildAt(i);

			int rootChildCount = rootFrame.getChildCount();

			for (int j = 0; j < rootChildCount; j++) {

				if ((int) rootFrame.getTag() != index)
					continue;

				rootFrame.setEnabled(true);
				rootFrame.setClickable(true);

				View childView = rootFrame.getChildAt(j);
				if (childView instanceof LinearLayout) {
					//bar
					LinearLayout barContainerLinear = ((LinearLayout) childView);
					int barContainerCount = barContainerLinear.getChildCount();

					for (int k = 0; k < barContainerCount; k++) {

						View view = barContainerLinear.getChildAt(k);

						if (view instanceof Bar) {

							Bar bar = (Bar) view;

							LayerDrawable layerDrawable = (LayerDrawable) bar.getProgressDrawable();
							layerDrawable.mutate();

							ScaleDrawable scaleDrawable = (ScaleDrawable) layerDrawable.getDrawable(1);

							GradientDrawable progressLayer = (GradientDrawable) scaleDrawable.getDrawable();

							if (progressLayer != null) {

								if (mProgressColor > 0)
									progressLayer.setColor(ContextCompat.getColor(mContext, mProgressColor));
								else
									progressLayer.setColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
							}
						} else {
							TextView titleTxtView = (TextView) view;
							if (mProgressDisableColor > 0)
								titleTxtView.setTextColor(ContextCompat.getColor(mContext, mBarTitleColor));
							else
								titleTxtView.setTextColor(ContextCompat.getColor(mContext, android.R.color.darker_gray));
						}
					}
				}
			}
		}
	}

	public void selectBar(int index) {

		final int barsCount = ((LinearLayout) this.getChildAt(0)).getChildCount();

		for (int i = 0; i < barsCount; i++) {

			FrameLayout rootFrame = (FrameLayout) ((LinearLayout) this.getChildAt(0)).getChildAt(i);

			int rootChildCount = rootFrame.getChildCount();

			for (int j = 0; j < rootChildCount; j++) {

				if ((int) rootFrame.getTag() != index)
					continue;

				if (oldFrameLayout != null)
					clickBarOff(oldFrameLayout);

				clickBarOn(rootFrame);
				oldFrameLayout = rootFrame;
			}
		}
	}

	public void deselectBar(int index) {
		final int barsCount = ((LinearLayout) this.getChildAt(0)).getChildCount();

		for (int i = 0; i < barsCount; i++) {

			FrameLayout rootFrame = (FrameLayout) ((LinearLayout) this.getChildAt(0)).getChildAt(i);

			int rootChildCount = rootFrame.getChildCount();

			for (int j = 0; j < rootChildCount; j++) {

				if ((int) rootFrame.getTag() != index)
					continue;

				clickBarOff(rootFrame);
			}
		}
	}
}
