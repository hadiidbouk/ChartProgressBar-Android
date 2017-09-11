package com.hadiidbouk.library;

public class BarData {

	private String barTitle;
	private float barValue;

	public String getBarTitle() {
		return barTitle;
	}

	public void setBarTitle(String barTitle) {
		this.barTitle = barTitle;
	}

	public float getBarValue() {
		return barValue;
	}

	public void setBarValue(float barValue) {
		this.barValue = barValue;
	}

	public BarData(String barTitle, float barValue) {
		this.barTitle = barTitle;
		this.barValue = barValue;
	}

	public BarData() {
	}
}
