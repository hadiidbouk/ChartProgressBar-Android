package com.hadiidbouk.chartprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

	private ChartProgressBar chart;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ArrayList<BarData> dataList = new ArrayList<>();

		BarData data = new BarData("sep",3.4f, "3.4€");
		dataList.add(data);

		data = new BarData("Oct",8f,"8€");
		dataList.add(data);

		data = new BarData("Nov",1.8f,"1.8€");
		dataList.add(data);

		data = new BarData("Dec",7.3f,"7.3€");
		dataList.add(data);

		data = new BarData("Jan",6.2f,"6.2€");
		dataList.add(data);

		data = new BarData("Feb",3.3f,"3.3€");
		dataList.add(data);

		chart = (ChartProgressBar) findViewById(R.id.ProgressBarChart);

		ChartProgressBar.Builder chartBuilder = new ChartProgressBar.Builder();
		chartBuilder
			.setContext(this)
			.setChart(chart)
			.setBarWidth(16)
			.setBarHeight(170)
			.setDataList(dataList)
			.setMaxValue(10)
			.setBarRadius(45)
			.setPinTextColor(R.color.value_bar_text)
			.setPinBackgroundColor(R.color.value_bar_background)
			.setPinPadding(3)
			.setEmptyBarColor(R.color.empty)
			.setProgressBarColor(R.color.progress)
			.build();


	}
}
