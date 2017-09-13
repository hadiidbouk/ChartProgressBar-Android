package com.hadiidbouk.charts;

public class BarData {

	private String barTitle;
	private float barValue;
	private String pinText;

	public String getPinText() {
		return pinText;
	}

	public void setPinText(String pinText) {
		this.pinText = pinText;
	}

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

	public BarData(String barTitle, float barValue, String pinText) {
		this.barTitle = barTitle;
		this.barValue = barValue;
		this.pinText = pinText;
	}

	public BarData() {
	}
}
