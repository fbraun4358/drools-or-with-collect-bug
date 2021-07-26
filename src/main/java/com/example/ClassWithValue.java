package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassWithValue {

	public static final String DOUBLE = "double";
	public static final double DOUBLE_VALUE = 5.5;
	public static final String INTEGER = "integer";
	public static final int INTEGER_VALUE = 3;
	
	private String value;
	private List<Double> doubleValues = new ArrayList<>(Arrays.asList(0.0));
	private List<Integer> intValues = new ArrayList<>(Arrays.asList(0));

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Double> getDoubleValues() {
		return doubleValues;
	}

	public void setDoubleValue(double doubleValue) {
		this.doubleValues.set(0, doubleValue);
	}

	public void addDoubleValue(double doubleValue) {
		this.doubleValues.add(doubleValue);
	}

	public List<Integer> getIntValues() {
		return intValues;
	}

	public void setIntValue(int intValue) {
		this.intValues.set(0, intValue);
	}

	public void addIntValue(int intValue) {
		this.intValues.add(intValue);
	}
}
