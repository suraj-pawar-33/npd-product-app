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
import com.apps.digiple.npdapp.models.ItemModel;
import com.apps.digiple.npdapp.models.ProductModel;
import com.apps.digiple.npdapp.view.DataEditPanel;
import com.apps.digiple.npdapp.view.TablePanel;
import com.apps.digiple.npdapp.view.product.ProductDataEditPanel;
import com.apps.digiple.npdapp.view.product.ProductTablePanel;
import com.mysql.cj.util.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class ProductDataProcessor extends AbstractDataProcessor {

	private ObservableList<ProductModel> data = FXCollections.observableArrayList();
	protected int objectKey;
	

	public ProductDataProcessor() {
		this.tablePanel = new ProductTablePanel(data, getMouseEventHandler());
		this.editPanel = new ProductDataEditPanel(this.tablePanel);
	}

	@Override
	public void loadRow(ItemModel row) {
		loadTextFields((ProductModel) row);
	}

	public void loadDBData() {
		data.clear();
		try {
			ResultSet rs = DBHelper.selectProductQuery();
			while (rs.next()) {
				ProductModel product = new ProductModel(Integer.valueOf(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				product.setProductCost(rs.getString(6));
				product.setProSubCost(Integer.valueOf(rs.getString(7)));
				data.add(product);	
			}
			
		} catch (Exception e) {
			CustLogger.error(e.getLocalizedMessage());
		}
	}

	public TablePanel getTablePanel() {
		return tablePanel;
	}

	public void setTablePanel(TablePanel tablePanel) {
		this.tablePanel = (ProductTablePanel) tablePanel;
	}

	public DataEditPanel getEditPanel() {
		return editPanel;
	}

	public void setEditPanel(DataEditPanel editPanel) {
		this.editPanel = (ProductDataEditPanel) editPanel;
	}

	private void loadTextFields(ProductModel productModel) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(productModel.getClass(), Object.class);
			PropertyDescriptor[] property = beanInfo.getPropertyDescriptors();
			Map<String, String> properties = BeanUtils.describe(productModel);
			editPanel.setObjectKey(productModel.getProductKey());
			editPanel.showUpdateView();
			for (int i = 0; i < property.length; i++) {
				if (StringUtils.isNullOrEmpty(properties.get(property[i].getDisplayName()))) {
					continue;
				}
				switch (property[i].getDisplayName()) {
				case ProductModel.PRODUCT:
					((ProductDataEditPanel) editPanel).setNameText(properties.get(property[i].getDisplayName()));
					break;
				case ProductModel.SHORT:
					((ProductDataEditPanel) editPanel).setShortNameText(properties.get(property[i].getDisplayName()));
					break;
				case ProductModel.DETAILS:
					((ProductDataEditPanel) editPanel).setProDetailText(properties.get(property[i].getDisplayName()));
					break;
				case ProductModel.PRO_TYPE:
					((ProductDataEditPanel) editPanel).setProType(properties.get(property[i].getDisplayName()));
					break;
				case ProductModel.COST:
					((ProductDataEditPanel) editPanel).setProCostText(properties.get(property[i].getDisplayName()));
					break;
				case ProductModel.SUB_COST:
					((ProductDataEditPanel) editPanel).setProSubCostText(properties.get(property[i].getDisplayName()));
					break;
				default:
					break;
				}
			}
			
		} catch (IntrospectionException | IllegalAccessException | InvocationTargetException |
			NoSuchMethodException e) {
			CustLogger.error(e.getLocalizedMessage());
		}
	}

	@Override
	public ObservableList<ProductModel> getData() {
		return this.data;
	}

	public GridPane getPanel() {
		GridPane pane = new GridPane();
		pane.add(editPanel, 0, 0);
		pane.add(tablePanel, 0, 1);
		return pane;
	}
}
