package com.apps.digiple.npdapp.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.apps.digiple.npdapp.dbdriver.CustLogger;
import com.apps.digiple.npdapp.models.AddressModel;
import com.apps.digiple.npdapp.models.BankModel;
import com.apps.digiple.npdapp.view.AddressDataEditPanel;
import com.mysql.cj.util.StringUtils;

public class AddressDataProcessor {
	protected AddressDataEditPanel editPanel;
	
	public AddressDataProcessor() {
		this.editPanel = new AddressDataEditPanel();
	}
	
	public AddressDataEditPanel getEditPanel() {
		return editPanel;
		
	}

	protected void loadAddresTextFields(AddressModel addressModel) {
		crearFields();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(addressModel.getClass(), Object.class);
			PropertyDescriptor[] property = beanInfo.getPropertyDescriptors();
			Map<String, String> properties = BeanUtils.describe(addressModel);
			editPanel.setObjectKey(addressModel.getKey());
			for (int i = 0; i < property.length; i++) {
				if (StringUtils.isNullOrEmpty(properties.get(property[i].getDisplayName()))) {
					continue;
				}
				switch (property[i].getDisplayName()) {
				case AddressModel.LINE1:
					editPanel.getLine1Text().setText(properties.get(property[i].getDisplayName()));
					break;
				case AddressModel.LINE2:
					editPanel.getLine2Text().setText(properties.get(property[i].getDisplayName()));
					break;
				case AddressModel.LINE3:
					editPanel.getLine3Text().setText(properties.get(property[i].getDisplayName()));
					break;
				case AddressModel.CITY:
					editPanel.setCityChoiceBox(properties.get(property[i].getDisplayName()));
					break;
				case AddressModel.ZIP:
					editPanel.getZipText().setText(properties.get(property[i].getDisplayName()));
					break;
				case AddressModel.STATE:
					editPanel.setStateChoiceBox(properties.get(property[i].getDisplayName()));
					break;
				case AddressModel.COUNTRY:
					editPanel.setConChoiceBox(properties.get(property[i].getDisplayName()));
					break;
				case AddressModel.OTHER:
					editPanel.getOtherText().setText(properties.get(property[i].getDisplayName()));
					break;
				default:
					break;
				}
				
			}
		} catch (IntrospectionException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			CustLogger.error(e.getLocalizedMessage());
		}
	}

	private void crearFields() {
		editPanel.getLine1Text().clear();
		editPanel.getLine2Text().clear();
		editPanel.getLine3Text().clear();
		editPanel.getCityChoiceBox().getSelectionModel().clearSelection();
		editPanel.getZipText().clear();
		editPanel.getStateChoiceBox().getSelectionModel().clearSelection();
		editPanel.getConChoiceBox().getSelectionModel().clearSelection();
		editPanel.getOtherText().clear();
	}

}
