# ChartProgressBar

Draw a chart with progress bar style - the ios version [here](https://github.com/hadiidbouk/ChartProgressBar-iOS)

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
compile 'com.github.hadiidbouk:ChartProgressBar:1.0.2'
```

## Usage

1. Add `ChartProgressBar` to your layout :
	
Don't forget to add the namespace on the root tag ```xmlns:hd="http://schemas.android.com/apk/lib/com.hadiidbouk.charts"```
```xml
<com.hadiidbouk.charts.ChartProgressBar
		android:id="@+id/ChartProgressBar"
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:layout_centerInParent="true"
		android:gravity="center"
		hd:hdBarCanBeClick="true"
		hd:hdBarHeight="170dp"
		hd:hdBarWidth="16dp"
		hd:hdBarRadius="10dp"
		hd:hdMaxValue="10"
		hd:hdEmptyColor="@color/empty"
		hd:hdProgressColor="@color/progress"
		hd:hdProgressClickColor="@color/progress_click"
		hd:hdPinBackgroundColor="@color/pin_background"
		hd:hdPinTextColor="@color/pin_text"
		hd:hdPinPadding="3dp"
		hd:hdBarTitleColor="@color/bar_title_color"
		hd:hdBarTitleTxtSize="12sp"
		hd:hdPinTxtSize="17sp"
		hd:hdPinMarginTop="10dp"/>
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
