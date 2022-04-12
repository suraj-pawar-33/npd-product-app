package com.apps.digiple.npdapp.models;

import javafx.beans.property.SimpleIntegerProperty;

public class Order2ProductModel extends ItemModel {

	private SimpleIntegerProperty orderKey;
	private SimpleIntegerProperty productKey;
	private SimpleIntegerProperty quantity;
	private SimpleIntegerProperty amount;

	public Order2ProductModel(int key, int orderKey, int productKey, int quantity, int amount) {
		super(key);
		this.orderKey = new SimpleIntegerProperty(orderKey);
		this.productKey = new SimpleIntegerProperty(productKey);
		this.quantity = new SimpleIntegerProperty(quantity);
		this.amount = new SimpleIntegerProperty(amount);
	}

	public int getOrderKey() {
		return orderKey.get();
	}

	public void setOrderKey(int orderKey) {
		this.orderKey.set(orderKey);
	}

	public int getProductKey() {
		return productKey.get();
	}

	public void setProductKey(int productKey) {
		this.productKey.set(productKey);
	}

	public int getQuantity() {
		return quantity.get();
	}

	public void setQuantity(int quantity) {
		this.quantity.set(quantity);
	}
	
	public void setQuantityValue(String quantity) {
		this.quantity.set(Integer.valueOf(quantity));
	}

	public int getAmount() {
		return amount.get();
	}

	public void setAmount(int amount) {
		this.amount.set(amount);
	}

	public void setAmountValue(String productCost) {
		this.amount.set(Integer.valueOf(productCost));
	}

}
