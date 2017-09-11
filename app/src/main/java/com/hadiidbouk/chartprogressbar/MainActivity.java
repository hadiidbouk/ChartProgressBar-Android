package com.hadiidbouk.chartprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ArrayList<BarData> dataList = new ArrayList<>();

		BarData data = new BarData();
		data.setBarTitle("sep");
		data.setBarValue(3.4f);

		dataList.add(data);

		data = new BarData();
		data.setBarTitle("oct");
		data.setBarValue(8f);

		dataList.add(data);

		data = new BarData();
		data.setBarTitle("nov");
		data.setBarValue(1.8f);

		dataList.add(data);

		data = new BarData();
		data.setBarTitle("dec");
		data.setBarValue(7.3f);

		dataList.add(data);

		data = new BarData();
		data.setBarTitle("jan");
		data.setBarValue(6.2f);

		dataList.add(data);

		data = new BarData();
		data.setBarTitle("feb");
		data.setBarValue(3.3f);

		dataList.add(data);

		ChartProgressBar chart = (ChartProgressBar) findViewById(R.id.ProgressBarChart);

		ChartProgressBar.Builder chartBuilder = new ChartProgressBar.Builder();
		chartBuilder
			.setContext(this)
			.setChart(chart)
			.setBarWidth(16)
			.setBarHeight(170)
			.setDataList(dataList)
			.setMaxValue(10)
			.setBarRadius(45)
			.setEmptyBarColor(R.color.empty)
			.setProgressBarColor(R.color.progress)
			.build();


	}
}
