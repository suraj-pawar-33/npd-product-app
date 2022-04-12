package com.apps.digiple.npdapp.models;

import com.mysql.cj.util.StringUtils;

public class AddressModel extends ItemModel{

	public static final String LINE1 = "line1";
	public static final String LINE2 = "line2";
	public static final String LINE3 = "line3";

	public static final String CITY = "city";
	public static final String ZIP = "zip";
	public static final String STATE = "state";
	

	public static final String COUNTRY = "country";
	public static final String OTHER = "other";
	
	private int bankKey;
	private String line1;
	private String line2;
	private String line3;

	private String city;
	private int zip;
	private String state;
	private String country;
	private String other;
	
	public AddressModel(int addressKey, int bankKey) {
		super(addressKey);
		this.setBankKey(bankKey);
	}


	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getLine3() {
		return line3;
	}

	public void setLine3(String line3) {
		this.line3 = line3;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getZip() {
		return zip;
	}

	public void setZip(int zip) {
		this.zip = zip;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}
	
	public String getAddressString(){
		StringBuilder address = new StringBuilder();
		String str = ", ";
		if (!StringUtils.isNullOrEmpty(getLine1())) {
			address.append(getLine1());
			address.append(str);
		}
		if (!StringUtils.isNullOrEmpty(getLine1())) {
			address.append(getLine1());
			address.append(str);
		}
		if (!StringUtils.isNullOrEmpty(getLine2())) {
			address.append(getLine2());
			address.append(str);
		}
		if (!StringUtils.isNullOrEmpty(getLine3())) {
			address.append(getLine3());
			address.append(str);
		}
		if (!StringUtils.isNullOrEmpty(getCity())) {
			address.append(getCity());
			address.append(str);
		}
		if (getZip() != 0) {
			address.append(getZip());
			address.append(str);
		}
		if (!StringUtils.isNullOrEmpty(getState())) {
			address.append(getState());
			address.append(str);
		}
		if (!StringUtils.isNullOrEmpty(getCountry())) {
			address.append(getCountry());
			address.append(str);
		}
		if (!StringUtils.isNullOrEmpty(getOther())) {
			address.append(getOther());
		}
		return address.toString();			
	}


	public int getBankKey() {
		return bankKey;
	}


	public void setBankKey(int bankKey) {
		this.bankKey = bankKey;
	}

}
