package com.hadiidbouk.chartprogressbar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hadiidbouk.charts.BarData;
import com.hadiidbouk.charts.ChartProgressBar;
import com.hadiidbouk.charts.OnBarClickedListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnBarClickedListener {

	private ChartProgressBar mChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ArrayList<BarData> dataList = new ArrayList<>();

		BarData data = new BarData("Sep", 3.4f, "3.4€", 10f);
		dataList.add(data);

		data = new BarData("Oct", 8.0f, "8.0€", 20f);
		dataList.add(data);

		data = new BarData("Nov", 1.8f, "1.8€", 40f);
		dataList.add(data);

		data = new BarData("Dec", 7.3f, "7.3€", 100f);
		dataList.add(data);

		data = new BarData("Jan", 6.2f, "6.2€", 25f);
		dataList.add(data);

		data = new BarData("Feb", 3.3f, "3.3€", 20f);
		dataList.add(data);

		mChart = (ChartProgressBar) findViewById(R.id.ChartProgressBar);

		mChart.setDataList(dataList);
		mChart.build();
		mChart.setOnBarClickedListener(this);
		mChart.disableBar(dataList.size() - 1);
	}

	public void BtnPressed(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.BtnClear :
				mChart.removeBarValues();
				break;
			case R.id.BtnReset :
				mChart.resetBarValues();
				break;
			case R.id.BtnClearClick :
				mChart.removeClickedBar();
				break;
		}
	}

	@Override public void onBarClicked(int index) {
		Toast.makeText(this, String.valueOf(index), Toast.LENGTH_SHORT).show();
	}
}
