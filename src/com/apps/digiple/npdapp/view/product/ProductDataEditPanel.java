package com.apps.digiple.npdapp.view.product;

import java.util.HashMap;

import com.apps.digiple.npdapp.dbdriver.DBHelper;
import com.apps.digiple.npdapp.models.ProductModel;
import com.apps.digiple.npdapp.view.CustLabel;
import com.apps.digiple.npdapp.view.DataEditPanel;
import com.apps.digiple.npdapp.view.TablePanel;
import com.mysql.cj.util.StringUtils;

import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class ProductDataEditPanel extends DataEditPanel {
	private TextField proNameText;
	private TextField proDetailText;
	private TextField shortNameText;
	private TextField proCostText;
	private TextField proSubCostText;
	private ChoiceBox<String> proTypeBox;
	private HashMap<String, Integer> proTypeMap = new HashMap<>();


	public ProductDataEditPanel(TablePanel tablePanel) {
		super(tablePanel);
		add(getProductPane(), 0, 0);
	}
	
	public String getNameText() {
		return proNameText.getText();
	}

	public void setNameText(String value) {
		this.proNameText.setText(value);
	}

	public String getProCostText() {
		return proCostText.getText();
	}

	public void setProCostText(String value) {
		this.proCostText.setText(value);
	}

	public String getShortNameText() {
		return shortNameText.getText();
	}

	public void setShortNameText(String value) {
		this.shortNameText.setText(value);
	}


	private GridPane getProductPane() {
		GridPane productPanel = new GridPane();
		productPanel.setVgap(5);
		productPanel.setHgap(5);

		Text productLabel = CustLabel.PRODUCT.getBoldLebel();

		Text proNameLabel = CustLabel.PRO_NAME.getLebel();
		proNameText = getTextField();

		Text shortNameLabel = CustLabel.PRO_SHNAME.getLebel();
		shortNameText = getTextField();

		Text detailLable = CustLabel.DETAIL.getLebel();
		proDetailText = getTextField();

		Text costLable = CustLabel.COST.getLebel();
		proCostText = getTextField();

		Text subscrLable = CustLabel.SUBS_COST.getLebel();
		proSubCostText = getTextField();

		Text proTypeLable = CustLabel.PRO_TYPE.getLebel();
		proTypeBox = getChoiceBox();

		// Arranging all the nodes in the grid
		productPanel.add(productLabel, 1, 0);

		productPanel.add(proNameLabel, 0, 1);
		productPanel.add(proNameText, 1, 1);

		productPanel.add(shortNameLabel, 0, 2);
		productPanel.add(shortNameText, 1, 2);

		productPanel.add(proTypeLable, 0, 3);
		productPanel.add(proTypeBox, 1, 3);

		productPanel.add(detailLable, 0, 4);
		productPanel.add(proDetailText, 1, 4);

		productPanel.add(costLable, 0, 5);
		productPanel.add(proCostText, 1, 5);

		productPanel.add(subscrLable, 0, 6);
		productPanel.add(proSubCostText, 1, 6);

		return productPanel;
	}

	

	@Override
	protected EventHandler<MouseEvent> saveEvent() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (StringUtils.isNullOrEmpty(getNameText()) || StringUtils.isNullOrEmpty(getProType())
						|| !StringUtils.isStrictlyNumeric(getProCostText())
						|| !StringUtils.isStrictlyNumeric(getProSubCostText())) {
					showErrAlert("Data Error!",
							"Product Name, Type is required and Cost, Subscription Cost need to be numeric value");
					return;
				} else {
					ProductModel product = new ProductModel(0, getNameText(), getProType(), getProDetailText(),
							getShortNameText());
					product.setProductCost(getProCostText());
					product.setProSubCost(Integer.valueOf(getProSubCostText()));
					product.setProTypeKey(getSelectedPTypeKey());
					try {
						int productKey = DBHelper.saveProductQuery(product);
						tablePanel.addNewRow(productKey, product);
					} catch (Exception e) {
						showErrAlert("Error", "Product data not saved.\nReason : " + e.getLocalizedMessage());
						return;
					}
					showConfAlert("Data Saved", "Product is saved " + getNameText());
					
				}
			}
		};
	}

	public String getProSubCostText() {
		return proSubCostText.getText();
	}

	public void setProSubCostText(String value) {
		this.proSubCostText.setText(value);
	}

	public String getProDetailText() {
		return proDetailText.getText();
	}

	public void setProDetailText(String value) {
		this.proDetailText.setText(value);
		;
	}

	public String getProType() {
		return proTypeBox.getSelectionModel().getSelectedItem();
	}

	public void setProType(String value) {
		this.proTypeBox.getSelectionModel().select(value);
	}
	

	public int getSelectedPTypeKey() {
		return proTypeMap.get(getProType());
	}

	private String[] getProTypeList() {
		return proTypeMap.keySet().toArray(new String[0]);
	}

	@Override
	protected EventHandler<MouseEvent> updateEvent() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (StringUtils.isNullOrEmpty(getNameText()) || StringUtils.isNullOrEmpty(getProType())
						|| !StringUtils.isStrictlyNumeric(getProCostText())
						|| !StringUtils.isStrictlyNumeric(getProSubCostText())) {
					showErrAlert("Data Error!",
							"Product Name, Type is required and Cost, Subscription Cost need to be numeric value");
					return;
				} else {
					ProductModel product = new ProductModel(getObjectKey(), getNameText(), getProType(), getProDetailText(),
							getShortNameText());
					product.setProductCost(getProCostText());
					product.setProSubCost(Integer.valueOf(getProSubCostText()));
					product.setProTypeKey(getSelectedPTypeKey());
					try {
						DBHelper.updateProductQuery(product);
						tablePanel.updateRow(product);
					} catch (Exception e) {
						showErrAlert("Error", "Product data not updated.\nReason : " + e.getLocalizedMessage());
						return;
					}
					showConfAlert("Success", "Product is updated " + getNameText());
					
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
					DBHelper.deleteProductQuery(getObjectKey());
				} catch (Exception e) {
					showErrAlert("Error", "Product data not deleted.\nReason : " + e.getLocalizedMessage());
					return;
				}
				showConfAlert("Success", "Product is deleted " + getNameText());
				clearAllText();
				tablePanel.deleteRow();
			}

		};
	}
	
	protected void loadDropdownList(){
		this.proTypeMap = getProTypeMap();
		proTypeBox.getItems().addAll(getProTypeList());
	}

	@Override
	protected void clearAllText() {
		proNameText.clear();
		proDetailText.clear();
		shortNameText.clear();
		proCostText.clear();
		proSubCostText.clear();
		proTypeBox.getSelectionModel().clearSelection();
		showSaveView();
	}

}
