package com.apps.digiple.npdapp.view.orders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

import com.apps.digiple.npdapp.dbdriver.CustLogger;
import com.apps.digiple.npdapp.dbdriver.DBHelper;
import com.apps.digiple.npdapp.models.OrderModel;
import com.apps.digiple.npdapp.view.CustLabel;
import com.apps.digiple.npdapp.view.DataEditPanel;
import com.apps.digiple.npdapp.view.TablePanel;
import com.mysql.cj.util.StringUtils;

import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class OrderDataEditPanel extends DataEditPanel {
	private DatePicker orderPlacedDate;
	private TextField billNumberText;
	private DatePicker orderPaidDate;
	private ChoiceBox<String> bankNameText;
	private ChoiceBox<String> orderTypeText;
	private TextField totalAmountText;
	private ChoiceBox<String> statusText;
	private ProSelectionPanel proSelPanel;
	private HashMap<String, Integer> bankMap = new HashMap<>();
	private HashMap<String, Integer> orderTypeMap = new HashMap<>();
	private HashMap<String, Integer> statusMap = new HashMap<>();

	public OrderDataEditPanel(ProSelectionPanel proSelPanel, TablePanel tablePanel) {
		super(tablePanel);
		this.proSelPanel = proSelPanel;
		add(getBankPane(), 0, 0);
		add(proSelPanel, 1, 0, 1, 2);
	}

	public LocalDate getOrderDate() {
		return orderPlacedDate.getValue();
	}

	public void setOrderDate(String value) {
		orderPlacedDate.setValue(LocalDate.parse(value));
	}
	
	public int getBillNumber() {
		return Integer.valueOf(billNumberText.getText());
	}

	public void setBillNumber(String value) {
		billNumberText.setText(value == null ? "0" : value);
	}

	public LocalDate getOrderPaidDate() {
		return orderPaidDate.getValue();
	}

	public void setOrderPaidDate(String value) {
		this.orderPaidDate.setValue(LocalDate.parse(value));
	}

	public String getBankName() {
		return bankNameText.getSelectionModel().getSelectedItem();
	}

	public int getSelectedBankKey() {
		return bankMap.get(getBankName());
	}

	private String[] getBankNameList() {
		String[] arr = bankMap.keySet().toArray(new String[0]);
		Arrays.sort(arr);
		return arr;
	}

	public void setBankName(String value) {
		this.bankNameText.getSelectionModel().select(value);
	}

	public String getOrderType() {
		return orderTypeText.getSelectionModel().getSelectedItem();
	}

	public void setOrderType(String value) {
		this.orderTypeText.getSelectionModel().select(value);
	}

	public int getSelectedOTypeKey() {
		return orderTypeMap.get(getOrderType());
	}

	private String[] getOrderTypeList() {
		return orderTypeMap.keySet().toArray(new String[0]);
	}

	public String getTotalAmount() {
		return totalAmountText.getText();
	}

	public void setTotalAmount(String value) {
		this.totalAmountText.setText(value);
	}

	public String getStatus() {
		return statusText.getSelectionModel().getSelectedItem();
	}

	public void setStatus(String value) {
		this.statusText.getSelectionModel().select(value);
	}

	public int getSelectedStatusKey() {
		return statusMap.get(getStatus());
	}

	private String[] getStatusList() {
		return statusMap.keySet().toArray(new String[0]);
	}

	private GridPane getBankPane() {
		GridPane productPanel = new GridPane();
		productPanel.setVgap(5);
		productPanel.setHgap(5);

		// Text orderLabel = CustLabel.ORDER.getBoldLebel();

		Text placedDate = CustLabel.PLACED_DATE.getLebel();
		orderPlacedDate = getDatePicker();

		Text billNumberlbl = CustLabel.BILL_NUM.getLebel();
		billNumberText = getTextField();

		Text orderType = CustLabel.ORDER_TYPE.getLebel();
		orderTypeText = getChoiceBox();

		Text bank = CustLabel.BANK_NAME.getLebel();
		bankNameText = getChoiceBox();

		Text totalAmount = CustLabel.TOTAL_AMOUNT.getLebel();
		totalAmountText = getTextField();
		totalAmountText.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				setTotalAmount(String.valueOf(proSelPanel.getTotalQuantity()));
			}
		});

		Text status = CustLabel.STATUS.getLebel();
		statusText = getChoiceBox();

		// Arranging all the nodes in the grid
		// productPanel.add(orderLabel, 1, 0);

		productPanel.add(billNumberlbl , 0, 1);
		productPanel.add(billNumberText , 1, 1);

		productPanel.add(placedDate, 0, 2);
		productPanel.add(orderPlacedDate, 1, 2);

		productPanel.add(orderType, 0, 3);
		productPanel.add(orderTypeText, 1, 3);

		productPanel.add(bank, 0, 4);
		productPanel.add(bankNameText, 1, 4);

		productPanel.add(totalAmount, 0, 5);
		productPanel.add(totalAmountText, 1, 5);

		productPanel.add(status, 0, 6);
		productPanel.add(statusText, 1, 6);

		return productPanel;
	}

	@Override
	protected EventHandler<MouseEvent> saveEvent() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (StringUtils.isNullOrEmpty(getBankName()) || StringUtils.isNullOrEmpty(getOrderType())
						|| getOrderDate() == null
						|| !StringUtils.isStrictlyNumeric(getTotalAmount()) || StringUtils.isNullOrEmpty(getStatus())) {
					showErrAlert("Data Error!",
							"Bank Name, Type is required and Total amount need to be numeric value");
					return;
				} else {
					OrderModel order = new OrderModel(0, getOrderDate().toString(), getBillNumber(),
							getBankName(), Integer.valueOf(getTotalAmount()), getStatus());
					order.setBankKey(getSelectedBankKey());
					order.addAllProduct(proSelPanel.getSelectedProducts());
					order.setStatusKey(getSelectedStatusKey());
					order.setOrderTypeKey(getSelectedOTypeKey());
					try {
						int orderKey = DBHelper.saveOrderQuery(order);
						tablePanel.addNewRow(orderKey, order);
					} catch (Exception e) {
						showErrAlert("Error", "Order is not saved.\nReason : " + e.getLocalizedMessage());
						CustLogger.error(
								"Order is not saved. KEY:" + getBillNumber() + " Reason : " + e.getLocalizedMessage());
						return;
					}
					showConfAlert("Data Saved", "Order is saved " + getBillNumber());
					CustLogger.info("Order is saved. KEY:" + getBillNumber());
				}
			}
		};
	}

	@Override
	protected EventHandler<MouseEvent> updateEvent() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (StringUtils.isNullOrEmpty(getBankName()) || StringUtils.isNullOrEmpty(getOrderType())
						|| !StringUtils.isStrictlyNumeric(getTotalAmount())) {
					showErrAlert("Data Error!",
							"Bank Name, Type is required and Total amount need to be numeric value");
					return;
				} else {
					OrderModel order = new OrderModel(getObjectKey(), getOrderDate().toString(),
							getBillNumber(), getBankName(), Integer.valueOf(getTotalAmount()),
							getStatus());
					order.setBankKey(getSelectedBankKey());
					order.addAllProduct(proSelPanel.getSelectedProducts());
					order.setStatusKey(getSelectedStatusKey());
					order.setOrderTypeKey(getSelectedOTypeKey());
					order.setOrderType(getOrderType());
					try {
						DBHelper.updateOrderQuery(order);
						tablePanel.updateRow(order);
					} catch (Exception e) {
						showErrAlert("Error", "Order is not update.\nReason : " + e.getLocalizedMessage());
						CustLogger.error(
								"Order is Not updated. KEY:" + getBillNumber() + " Reason : " + e.getLocalizedMessage());
						return;
					}
					showConfAlert("Data Updated", "Order is saved " + getBillNumber());
					CustLogger.info("Order is saved. KEY:" + getBillNumber());
				}
			}
		};
	}

	@Override
	protected EventHandler<MouseEvent> deleteEvent() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {

				try {
					DBHelper.deleteOrderQuery(getObjectKey());
				} catch (Exception e) {
					showErrAlert("Error", "Order is not Deleted.\nReason : " + e.getLocalizedMessage());
					CustLogger.error(
							"Order is not Deleted. KEY:" + getBillNumber() + "Reason : " + e.getLocalizedMessage());
					return;
				}
				showConfAlert("Data Updated", "Order is Deleted " + getBillNumber());
				CustLogger.info("Order is Deleted. KEY:" + getBillNumber());
				clearAllText();
				tablePanel.deleteRow();
			}

		};
	}

	@Override
	protected void clearAllText() {
		orderPlacedDate.setValue(LocalDate.now());
		billNumberText.clear();
		bankNameText.getSelectionModel().clearSelection();
		orderTypeText.getSelectionModel().clearSelection();
		totalAmountText.clear();
		statusText.getSelectionModel().clearSelection();
		proSelPanel.clearQnts();
		showSaveView();
	}

	@Override
	protected void loadDropdownList() {
		this.bankMap = getBankMap();
		this.orderTypeMap = getOrderTypeMap();
		this.statusMap = getStatusMap();

		orderTypeText.getItems().addAll(getOrderTypeList());
		bankNameText.getItems().addAll(getBankNameList());
		statusText.getItems().addAll(getStatusList());
	}

}
