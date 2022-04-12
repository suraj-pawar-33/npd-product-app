package com.apps.digiple.npdapp.view;

import javafx.scene.text.Text;

public enum CustLabel {
	PRODUCT("Product"), 
	BANK("Bank"), 
	ADDRESS("Address"), 
	PRO_NAME("Product Name"), 
	BK_NAME("Bank Name"),
	BRANCH("Branch Name"), 
	BK_SHNAME("Short Name"),  
	PRO_SHNAME("Short Name"),
	DETAIL("Details"), 
	COST("Cost"), 
	SUBS_COST("Subscription Cost"), LINE1("Line 1"), 
	OTHER("Other"), CONT("Country"), STATE("State"), ZIP("Zip Code"), 
	CITY("City"), LINE3("Line 3"), LINE2("Line 2"), PRO_TYPE("Product Type"), 
	ORDER("Order"), 
	PLACED_DATE("Placed Date"), BILL_NUM("Bill Number"), ORDER_TYPE("Order Type"), 
	BANK_NAME("Bank Name"), TOTAL_AMOUNT("Total Amount"), STATUS("Status");
	
	private Text lebel;
	private Text boldLebel;
	private String lblText;

	private CustLabel(String lblText) {
		this.lblText = lblText;
		this.lebel = createLabel();
		this.boldLebel = createBoldLabel();
	}

	public Text createLabel() {
		Text text = new Text(lblText);
		text.setStrokeWidth(5);
		text.setFont(CustFontColor.getNormalFont());
		return text; 
	}
	public Text createBoldLabel() {
		Text text = new Text(lblText);
		text.setStrokeWidth(5);
		text.setFont(CustFontColor.getBoldFont());
		return text; 
	}

	public Text getLebel() {
		return lebel;
	}
	
	public Text getBoldLebel() {
		return boldLebel;
	}
	
	public static Text createLabel(String str) {
		Text text = new Text(str);
		text.setStrokeWidth(5);
		text.setFont(CustFontColor.getNormalFont());
		return text; 
	}

}
