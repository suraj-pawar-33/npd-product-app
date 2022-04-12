package com.apps.digiple.npdapp.models;

import javafx.beans.property.SimpleStringProperty;

public class BankModel extends ItemModel{

	public static final String BANK = "bankName";
	public static final String BRANCH = "branchName";
	public static final String SHORTNAME = "shortName";
	
	private final SimpleStringProperty bankName;
	private final SimpleStringProperty branchName;
	private final SimpleStringProperty shortName;
	private final SimpleStringProperty address;

	private AddressModel addressModel;

	public BankModel(int bankKey, String bankName, String shortName, String branchName, AddressModel address) {
		super(bankKey);
		this.bankName = new SimpleStringProperty(bankName);
		this.branchName = new SimpleStringProperty(branchName);
		this.shortName = new SimpleStringProperty(shortName);
		this.address = new SimpleStringProperty(address != null ? address.getAddressString() : "");
		this.addressModel = address;
	}

	public int getBankKey() {
		return key.get();
	}

	public void setBankKey(int longValue) {
		key.set(longValue);
	}

	public String getBankName() {
		return bankName.get();
	}

	public void setBankName(String string) {
		bankName.set(string);
	}

	public String getBranchName() {
		return branchName.get();
	}

	public void setBranchName(String string) {
		branchName.set(string);
	}

	public String getShortName() {
		return shortName.get();
	}

	public void setShortName(String string) {
		shortName.set(string);
	}

	public String getAddress() {
		return address.get();
	}

	public void setAddress(String string) {
		address.set(string);
	}
	
	public AddressModel address() {
		return addressModel;
	}

}
