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
import com.apps.digiple.npdapp.models.Order2ProductModel;
import com.apps.digiple.npdapp.models.OrderModel;
import com.apps.digiple.npdapp.view.DataEditPanel;
import com.apps.digiple.npdapp.view.TablePanel;
import com.apps.digiple.npdapp.view.orders.OrderDataEditPanel;
import com.apps.digiple.npdapp.view.orders.OrderTablePanel;
import com.apps.digiple.npdapp.view.orders.ProSelectionPanel;
import com.mysql.cj.util.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class OrderDataProcessor extends AbstractDataProcessor {

	private ObservableList<OrderModel> data = FXCollections.observableArrayList();
	protected int objectKey;
	private ProSelectionPanel proSelPanel;

	public OrderDataProcessor() {
		proSelPanel = new ProSelectionPanel(getProSelEventHandler());
		tablePanel = new OrderTablePanel(data, getMouseEventHandler());
		editPanel = new OrderDataEditPanel(proSelPanel, tablePanel);
	}

	private EventHandler<MouseEvent> getProSelEventHandler() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if ("ReloadBtn".equals(((Node) arg0.getSource()).getId())) {
					proSelPanel.loadProductData();
					proSelPanel.refresh();
				}
			}
		};
	}

	@Override
	public void loadRow(ItemModel row) {
		loadTextFields((OrderModel) row);
		proSelPanel.selectItems(((OrderModel) row).getProducts());
	}

	public void loadDBData() {
		data.clear();
		try {
			ResultSet rs = DBHelper.selectOrderQuery();
			int tempOrderKey = 0;
			OrderModel order = null;
			while (rs.next()) {
				int key = rs.getInt(1);
				if (key != tempOrderKey) {
					order = new OrderModel(key, rs.getString(2), Integer.valueOf(rs.getString(3) == null? "0": rs.getString(3)), rs.getString(4) +" - "+ rs.getString(14), rs.getInt(6),
							rs.getString(7));
					order.setOrderTypeKey(rs.getInt(5));
					order.setOrderType(rs.getString(13));
					tempOrderKey = key;
				}
				Order2ProductModel product = new Order2ProductModel(rs.getInt(8), rs.getInt(9), rs.getInt(10),
						rs.getInt(11), rs.getInt(12));
				order.addProduct(product);
				data.add(order);
			}

		} catch (Exception e) {
			CustLogger.error("loadDBData > " + e.getLocalizedMessage());
		}
	}

	public TablePanel getTablePanel() {
		return tablePanel;
	}

	public void setTablePanel(TablePanel tablePanel) {
		this.tablePanel = (OrderTablePanel) tablePanel;
	}

	public DataEditPanel getEditPanel() {
		return editPanel;
	}

	public void setEditPanel(DataEditPanel editPanel) {
		this.editPanel = (OrderDataEditPanel) editPanel;
	}

	@Override
	public void refreshView() {
		proSelPanel.loadProductData();
		proSelPanel.refresh();
		super.refreshView();
	}

	private void loadTextFields(OrderModel orderModel) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(orderModel.getClass(), Object.class);
			PropertyDescriptor[] property = beanInfo.getPropertyDescriptors();
			Map<String, String> properties = BeanUtils.describe(orderModel);
			editPanel.setObjectKey(orderModel.getOrderKey());
			editPanel.showUpdateView();
			for (int i = 0; i < property.length; i++) {
				if (StringUtils.isNullOrEmpty(properties.get(property[i].getDisplayName()))) {
					continue;
				}
				switch (property[i].getDisplayName()) {
				case OrderModel.DATE:
					((OrderDataEditPanel) editPanel).setOrderDate(properties.get(property[i].getDisplayName()));
					break;
				case OrderModel.BILL_NUM:
					((OrderDataEditPanel) editPanel).setBillNumber(properties.get(property[i].getDisplayName()));
					break;
				case OrderModel.TYPE:
					((OrderDataEditPanel) editPanel).setOrderType(properties.get(property[i].getDisplayName()));
					break;
				case OrderModel.BANK:
					((OrderDataEditPanel) editPanel).setBankName(properties.get(property[i].getDisplayName()));
					break;
				case OrderModel.AMOUNT:
					((OrderDataEditPanel) editPanel).setTotalAmount(properties.get(property[i].getDisplayName()));
					break;
				case OrderModel.STATUS:
					((OrderDataEditPanel) editPanel).setStatus(properties.get(property[i].getDisplayName()));
					break;
				default:
					break;
				}
			}

		} catch (IntrospectionException | IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			CustLogger.error("loadTextFields >" + e.getLocalizedMessage());
		}
	}

	@Override
	public ObservableList<OrderModel> getData() {
		return this.data;
	}

	public GridPane getPanel() {
		GridPane pane = new GridPane();
		pane.add(editPanel, 0, 0);
		pane.add(tablePanel, 0, 1);
		return pane;
	}

	public GridPane getSelProductPane() {
		GridPane pane = new GridPane();
		pane.add(editPanel, 0, 0);
		pane.add(tablePanel, 0, 1);
		return pane;
	}
}
