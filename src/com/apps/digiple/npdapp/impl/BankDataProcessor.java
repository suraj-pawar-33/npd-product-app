package com.apps.digiple.npdapp.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.apps.digiple.npdapp.dbdriver.CustLogger;
import com.apps.digiple.npdapp.dbdriver.DBHelper;
import com.apps.digiple.npdapp.models.AddressModel;
import com.apps.digiple.npdapp.models.BankModel;
import com.apps.digiple.npdapp.models.ItemModel;
import com.apps.digiple.npdapp.view.DataEditPanel;
import com.apps.digiple.npdapp.view.TablePanel;
import com.apps.digiple.npdapp.view.bank.BankDataEditPanel;
import com.apps.digiple.npdapp.view.bank.BankTablePanel;
import com.mysql.cj.util.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BankDataProcessor extends AbstractDataProcessor {
	private ObservableList<BankModel> data = FXCollections.observableArrayList();;
	protected int objectKey;
	AddressDataProcessor addressProcessor = new AddressDataProcessor();

	public BankDataProcessor() {
		this.tablePanel = new BankTablePanel(data, getMouseEventHandler());
		this.editPanel = new BankDataEditPanel(addressProcessor.getEditPanel(), this.tablePanel);
	}


	@Override
	public void loadRow(ItemModel row) {
		loadTextFields((BankModel) row);
	}

	public void loadDBData() {
		data.clear();
		try {
			ResultSet rs = DBHelper.selectBankQuery();
			while (rs.next()) {
				AddressModel address = new AddressModel(Integer.valueOf(rs.getString(13)), Integer.valueOf(rs.getString(1)));
				address.setLine1(rs.getString(5));
				address.setLine2(rs.getString(6));
				address.setLine3(rs.getString(7));
				address.setCity(rs.getString(8));
				address.setZip(Integer.parseInt(rs.getString(9) != null ? rs.getString(9) : "0"));
				address.setState(rs.getString(10));
				address.setCountry(rs.getString(11));
				address.setOther(rs.getString(12));
				data.add(new BankModel(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), address));
			}
		} catch (Exception e) {
			CustLogger.error(e.getLocalizedMessage());
		}
	}

	public TablePanel getTablePanel() {
		return tablePanel;
	}

	public void setTablePanel(TablePanel tablePanel) {
		this.tablePanel = (BankTablePanel) tablePanel;
	}

	public DataEditPanel getEditPanel() {
		return editPanel;
	}

	public void setEditPanel(DataEditPanel editPanel) {
		this.editPanel = (BankDataEditPanel) editPanel;
	}

	private void loadTextFields(BankModel bankModel) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(bankModel.getClass(), Object.class);
			PropertyDescriptor[] property = beanInfo.getPropertyDescriptors();
			Map<String, String> properties = BeanUtils.describe(bankModel);
			editPanel.setObjectKey(bankModel.getBankKey());
			editPanel.showUpdateView();
			addressProcessor.loadAddresTextFields(bankModel.address());
			for (int i = 0; i < property.length; i++) {
				if (StringUtils.isNullOrEmpty(properties.get(property[i].getDisplayName()))) {
					continue;
				}
				switch (property[i].getDisplayName()) {
				case BankModel.BANK:
					((BankDataEditPanel) editPanel).setNameText(properties.get(property[i].getDisplayName()));
					break;
				case BankModel.SHORTNAME:
					((BankDataEditPanel) editPanel).setShortNameText(properties.get(property[i].getDisplayName()));
					break;
				case BankModel.BRANCH:
					((BankDataEditPanel) editPanel).setBrNameText(properties.get(property[i].getDisplayName()));
					break;
				default:
					break;
				}
			}

		} catch (IntrospectionException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

	@Override
	public ObservableList<BankModel> getData() {
		return this.data;
	}

	public GridPane getPanel() {
		GridPane pane = new GridPane();
		pane.add(editPanel, 0, 0);
		pane.add(tablePanel, 0, 1);
		return pane;
	}


	


}
