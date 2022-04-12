package com.apps.digiple.npdapp.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TextField;

public class ProductModel extends ItemModel{


	public static final String PRODUCT = "productName";
	public static final String DETAILS = "productDetail";
	public static final String SHORT = "shortName";
	public static final String SUB_COST = "proSubCost";
	public static final String COST = "productCost";
	public static final String KEY = "productKey";
	public static final String PRO_TYPE = "productType";
	public static final String QUANTITY = "qntyEntry";
	
	private final SimpleStringProperty productName;
	private final SimpleStringProperty productType;
	private final SimpleStringProperty productDetail;
	private final SimpleStringProperty shortName;
	private SimpleStringProperty productCost = new SimpleStringProperty("0");
	private SimpleIntegerProperty proSubCost = new SimpleIntegerProperty(0);
	private int proTypeKey;
	private SimpleStringProperty qntyEntry = new SimpleStringProperty("0");

	public ProductModel(int productKey, String productName, String productType, String productDetail, String shortName) {
		super(productKey);
		this.productName = new SimpleStringProperty(productName);
		this.productType = new SimpleStringProperty(productType);
		this.productDetail = new SimpleStringProperty(productDetail);
		this.shortName = new SimpleStringProperty(shortName);
	}

	public int getProductKey() {
		return key.get();
	}

	public void setProductKey(int longValue) {
		key.set(longValue);
	}

	public String getProductName() {
		return productName.get();
	}

	public void setProductName(String string) {
		productName.set(string);
	}

	public String getProductDetail() {
		return productDetail.get();
	}

	public void setProductDetail(String string) {
		productDetail.set(string);
	}

	public String getShortName() {
		return shortName.get();
	}

	public void setShortName(String string) {
		shortName.set(string);
	}

	public SimpleStringProperty getProductCostPrty() {
		return productCost;
	}
	
	public String getProductCost() {
		return productCost.get();
	}

	public void setProductCost(String productCost) {
		this.productCost.set(productCost);
	}

	public int getProSubCost() {
		return proSubCost.intValue();
	}

	public void setProSubCost(int proSubCost) {
		this.proSubCost.set(proSubCost);
	}

	public int getProTypeKey() {
		return proTypeKey;
	}

	public void setProTypeKey(int proTypeKey) {
		this.proTypeKey = proTypeKey;
	}

	public String getProductType() {
		return productType.get();
	}

	public SimpleStringProperty getQntyEntry() {
		return qntyEntry;
	}

	public void setQntyEntry(String qntyEntry) {
		this.qntyEntry.set(qntyEntry);
	}

	


}
