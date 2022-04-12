package com.apps.digiple.npdapp.models;

import java.util.Iterator;
import java.util.List;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

public class OrderModel extends ItemModel{


	public static final String KEY = "orderKey";
	public static final String DATE = "placedDate";
	public static final String BILL_NUM = "billNumber";
	public static final String BANK = "bankname";
	public static final String AMOUNT = "totalAmount";
	public static final String STATUS = "status";
	public static final String TYPE = "orderType";
	
	private SimpleStringProperty placedDate;
	private SimpleIntegerProperty billNumber;
	private SimpleStringProperty orderType;
	private SimpleStringProperty bankname;
	private SimpleIntegerProperty totalAmount;
	private SimpleStringProperty status;
	private int bankKey;
	private int orderTypeKey;
	private int statusKey;

	private List<Order2ProductModel> products = FXCollections.observableArrayList();

	public OrderModel(int orderKey, String localDate, int billNumber, String bankname, int totalAmount, String status) {
		super(orderKey);
		this.placedDate = new SimpleStringProperty(localDate);
		this.billNumber = new SimpleIntegerProperty(billNumber);
		this.orderType = new SimpleStringProperty("-");
		this.bankname = new SimpleStringProperty(bankname);
		this.totalAmount = new SimpleIntegerProperty(totalAmount);
		this.status = new SimpleStringProperty(status);
	}

	public int getBankKey() {
		return bankKey;
	}

	public void setBankKey(int bankKey) {
		this.bankKey = bankKey;
	}

	public int getOrderTypeKey() {
		return orderTypeKey;
	}

	public void setOrderTypeKey(int orderTypeKey) {
		this.orderTypeKey = orderTypeKey;
	}

	public int getStatusKey() {
		return statusKey;
	}

	public void setStatusKey(int statusKey) {
		this.statusKey = statusKey;
	}

	public int getOrderKey() {
		return key.intValue();
	}

	public String getPlacedDate() {
		return placedDate.get();
	}

	public int getBillNumber() {
		return billNumber.get();
	}
	
	public void setBillNumber(int billNumber) {
		this.billNumber.set(billNumber);
	}

	public String getBankname() {
		return bankname.get();
	}

	public int getTotalAmount() {
		return totalAmount.intValue();
	}

	public String getStatus() {
		return status.get();
	}

	public void setOrderKey(int orderKey) {
		this.key.set(orderKey);
	}

	public void setPlacedDate(String placedDate) {
		this.placedDate.set(placedDate);
	}


	public void setBankname(String bankname) {
		this.bankname.set(bankname);
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount.set(totalAmount);
	}

	public void setStatus(String status) {
		this.status.set(status);
	}

	public List<Order2ProductModel> getProducts() {
		return products;
	}

	public void addProduct(Order2ProductModel product) {
		this.products.add(product);
	}

	public void addAllProduct(List<Order2ProductModel> selectedProducts) {
		for (Iterator<Order2ProductModel> iterator = selectedProducts.iterator(); iterator.hasNext();) {
			Order2ProductModel order2ProductModel = (Order2ProductModel) iterator.next();
			addProduct(order2ProductModel);
		}
		
	}

	public String getOrderType() {
		return orderType.get();
	}

	public void setOrderType(String orderType) {
		this.orderType.set(orderType);
	}
	
	

}
