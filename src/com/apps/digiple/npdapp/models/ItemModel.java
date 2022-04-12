package com.apps.digiple.npdapp.models;

import javafx.beans.property.SimpleIntegerProperty;

public class ItemModel {

	protected SimpleIntegerProperty key;

	public ItemModel(int key) {
		this.key = new SimpleIntegerProperty(key);
	}
	
	public int getKey() {
		return this.key.get();
	}

	public void setKey(int key) {
		this.key.set(key);
	}
}
