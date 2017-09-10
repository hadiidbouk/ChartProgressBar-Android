package com.hadiidbouk.chartprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;

import com.hadiidbouk.library.ChartProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ArrayList<Float> dataList = new ArrayList<>();
		dataList.add(3.4f);
		dataList.add(8f);
		dataList.add(1.8f);
		dataList.add(7.3f);
		dataList.add(6.2f);
		dataList.add(3.3f);
		dataList.add(5f);
		dataList.add(9f);

		ChartProgressBar chart = (ChartProgressBar) findViewById(R.id.ProgressBarChart);

		ChartProgressBar.Builder chartBuilder = new ChartProgressBar.Builder();
		chartBuilder
			.setContext(this)
			.setChart(chart)
			.setBarWidth(12)
			.setBarHeight(100)
			.setDataList(dataList)
			.setMaxValue(10)
			.setBarMargins(20)
			.setBarRadius(45)
			.setEmptyColor(R.color.empty)
			.setFillColor(R.color.fill)
			.build();




	}
}
