# ChartProgressBar

Draw a chart with progress bar style - the ios version [here](https://github.com/hadiidbouk/ChartProgressBar-iOS)

![](https://i.imgur.com/bMB49fa.png)

![](https://i.imgur.com/bcb3jti.png)


## Installation

Add `jitpack` to your build.gradle (project) : 

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add the dependency to your build.gradle (app) :

```
 compile 'com.github.hadiidbouk:ChartProgressBar-Android:2.0.6'
```

## Usage

1. Add `ChartProgressBar` to your layout :
	
```xml
<com.hadiidbouk.charts.ChartProgressBar
		android:id="@+id/ChartProgressBar"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:layout_centerInParent="true"
		android:gravity="center"
		app:hdBarCanBeClick="true"
		app:hdBarHeight="170dp"
		app:hdBarWidth="7dp"
		app:hdBarRadius="10dp"
		app:hdMaxValue="10"
		app:hdEmptyColor="@color/empty"
		app:hdProgressColor="@color/progress"
		app:hdProgressClickColor="@color/progress_click"
		app:hdPinBackgroundColor="@color/pin_background"
		app:hdPinTextColor="@color/pin_text"
		app:hdPinPaddingBottom="5dp"
		app:hdBarTitleColor="@color/bar_title_color"
		app:hdBarTitleTxtSize="12sp"
		app:hdPinTxtSize="17sp"
		app:hdPinMarginTop="10dp"
		app:hdPinMarginBottom="55dp"
		app:hdPinMarginEnd="22dp"
		app:hdBarTitleMarginTop="9dp"
		app:hdPinDrawable="@drawable/ic_pin"
		app:hdProgressDisableColor="@color/progress_disable"
		app:hdBarTitleSelectedColor="@color/bar_title_selected_color"/>
```

2. Add your Data to the chart :

```java
		ArrayList<BarData> dataList = new ArrayList<>();

		BarData data = new BarData("Sep", 3.4f, "3.4€");
		dataList.add(data);

		data = new BarData("Oct", 8f, "8€");
		dataList.add(data);

		data = new BarData("Nov", 1.8f, "1.8€");
		dataList.add(data);

		data = new BarData("Dec", 7.3f, "7.3€");
		dataList.add(data);

		data = new BarData("Jan", 6.2f, "6.2€");
		dataList.add(data);

		data = new BarData("Feb", 3.3f, "3.3€");
		dataList.add(data);

		mChart = (ChartProgressBar) findViewById(R.id.ChartProgressBar);

		mChart.setDataList(dataList);
		mChart.build();
```

## Useful methods

1. `mChart.removeBarValues()` : Remove values of all progress bars in the chart.

2. `mChart.resetBarValues()` : Set values to the chart ( it may used after `removeBarValues()`) .

3. `mChart.removeClickedBar()` : Unselect the clicked bar.

4. `isBarsEmpty()` : Check if bars values are empty.

5. `setMaxValue(float maxValue)` : Setting bars max value programmatically .

6. ` enableBar(int index)` : Enable a bar

7. `disableBar(int index)` : Disable a bar

8. `setOnBarClickedListener(OnBarClickedListener listener)` : listening for click bar event

9. `selectBar(int index)` : to select a bar like a click

10. `deselectBar(int index)` : to deselect a bar
