package com.apps.digiple.npdapp.view.orders;

import java.util.Iterator;

import com.apps.digiple.npdapp.models.ItemModel;
import com.apps.digiple.npdapp.models.OrderModel;
import com.apps.digiple.npdapp.view.TablePanel;
import com.mysql.cj.util.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class OrderTablePanel extends TablePanel {
	
	public OrderTablePanel(ObservableList<OrderModel> data, EventHandler<MouseEvent> eventHandler) {
		super(eventHandler, new TableView<OrderModel>());
		this.data = data;
	}
	@Override
	protected ObservableList<OrderModel> loadFilteredData(String str) {
		ObservableList<OrderModel> newData = FXCollections.observableArrayList();
		if (StringUtils.isNullOrEmpty(str) || StringUtils.isEmptyOrWhitespaceOnly(str)) {
			newData.addAll((ObservableList<OrderModel>) data);
			return newData;
		}
		if (StringUtils.isStrictlyNumeric(str)) {
			for (Iterator<? extends ItemModel> iterator = data.iterator(); iterator.hasNext();) {
				OrderModel orderModel = (OrderModel) iterator.next();
				if ((orderModel.getBillNumber() != 0
						&& String.valueOf(orderModel.getBillNumber()).contains(str))
						|| (!StringUtils.isNullOrEmpty(orderModel.getPlacedDate())
						&& orderModel.getPlacedDate().contains(str))
						|| String.valueOf(orderModel.getOrderKey()).contains(str) ) {
					newData.add(orderModel);
				}
			}
		} else {
			for (Iterator<? extends ItemModel> iterator = data.iterator(); iterator.hasNext();) {
				OrderModel orderModel = (OrderModel) iterator.next();
				String lowerStr = str.toLowerCase();
				if ((!StringUtils.isNullOrEmpty(orderModel.getBankname()) 
						&& orderModel.getBankname().toLowerCase().contains(lowerStr))
						|| (!StringUtils.isNullOrEmpty(orderModel.getStatus())
						&& orderModel.getStatus().toLowerCase().contains(lowerStr))) {
					newData.add(orderModel);
				}
			}
		}
		return newData;
	}

	@SuppressWarnings("unchecked")
	protected void loadColumns() {

		table.setEditable(true);

		TableColumn<OrderModel, Long> keyCol = getTableColumn("Order Key", OrderModel.KEY, Long.class,
				OrderModel.class);
		TableColumn<OrderModel, String> billNumCol = getTableColumn("Bill Number", OrderModel.BILL_NUM, String.class,
				OrderModel.class);
		TableColumn<OrderModel, String> orderDateCol = getTableColumn("Order Date", OrderModel.DATE, String.class,
				OrderModel.class);
		TableColumn<OrderModel, String> bankNameCol = getTableColumn("Bank Name", OrderModel.BANK, String.class,
				OrderModel.class);
		TableColumn<OrderModel, String> typeCol = getTableColumn("Order Type", OrderModel.TYPE, String.class,
				OrderModel.class);
		TableColumn<OrderModel, Long> amountCol = getTableColumn("Total Amount", OrderModel.AMOUNT, Long.class,
				OrderModel.class);
		TableColumn<OrderModel, String> statusCol = getTableColumn("Status", OrderModel.STATUS, String.class,
				OrderModel.class);

		((TableView<OrderModel>) table).setItems((ObservableList<OrderModel>) data);
		((TableView<OrderModel>) table).getColumns().addAll(keyCol, billNumCol, orderDateCol, bankNameCol, typeCol,
				amountCol, statusCol);
		table.setPrefSize(1800, 1800);
		table.setMinSize(500, 300);

	}

}
