package com.example;

import java.util.ArrayList;
import java.util.List;

public class ClassWithValue {

	public static final double DOUBLE_VALUE = 5.5;
	
	private List<Double> doubleValues = new ArrayList<>();

	public List<Double> getDoubleValues() {
		return doubleValues;
	}

	public void setDoubleValue(double doubleValue) {

		this.doubleValues.clear();
		this.doubleValues.add(doubleValue);
	}

	public void addDoubleValue(double doubleValue) {
		this.doubleValues.add(doubleValue);
	}
}
