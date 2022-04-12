package com.apps.digiple.npdapp.view.bank;

import com.apps.digiple.npdapp.dbdriver.CustLogger;
import com.apps.digiple.npdapp.dbdriver.DBHelper;
import com.apps.digiple.npdapp.models.AddressModel;
import com.apps.digiple.npdapp.models.BankModel;
import com.apps.digiple.npdapp.view.AddressDataEditPanel;
import com.apps.digiple.npdapp.view.CustLabel;
import com.apps.digiple.npdapp.view.DataEditPanel;
import com.apps.digiple.npdapp.view.TablePanel;
import com.mysql.cj.util.StringUtils;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class BankDataEditPanel extends DataEditPanel {
	private TextField nameText;
	private TextField brNameText;
	private TextField shortNameText;
	private AddressDataEditPanel addEdit;
	
	public BankDataEditPanel(AddressDataEditPanel addEdit, TablePanel tablePanel) {
		super(tablePanel);
		this.addEdit = addEdit;
		add(getBankPane(), 0, 0);
		add(addEdit.getAddressPane(), 1, 0);
	}

	public String getNameText() {
		return nameText.getText();
	}
	
	public String getBrNameText() {
		return brNameText.getText();
	}

	public String getShortNameText() {
		return shortNameText.getText();
	}
	
	public void setNameText(String value) {
		nameText.setText(value);
	}
	

	public void setBrNameText(String value) {
		brNameText.setText(value);
	}

	public void setShortNameText(String value) {
		shortNameText.setText(value);
	}

	
	
	protected void clearAllText(){
		nameText.clear();
		brNameText.clear();
		shortNameText.clear();
		addEdit.clearAllText();
		showSaveView();
	}
	
	private GridPane getBankPane() {
		GridPane bankPanel = new GridPane();
		bankPanel.setVgap(5);
		bankPanel.setHgap(5);
		
		Text bankLabel = CustLabel.BANK.getBoldLebel();
		
		Text nameLabel = CustLabel.BK_NAME.getLebel();
		nameText = getTextField();

		Text branchLable = CustLabel.BRANCH.getLebel();
		brNameText = getTextField();

		Text shortNameLabel = CustLabel.BK_SHNAME.getLebel();
		shortNameText = getTextField();
		
		// Arranging all the nodes in the grid
		bankPanel.add(bankLabel, 1, 0);
		
		bankPanel.add(nameLabel, 0, 1);
		bankPanel.add(nameText, 1, 1);

		bankPanel.add(branchLable, 0, 2);
		bankPanel.add(brNameText, 1, 2);

		bankPanel.add(shortNameLabel, 0, 3);
		bankPanel.add(shortNameText, 1, 3);
		return bankPanel;		
	}

	@Override
	protected EventHandler<MouseEvent> updateEvent() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (notValidAddress()) {
					showErrAlert("Data Error!",
							"Address > Line 1, city, zip and state is required");
					return;
				} else {
					try {
						DBHelper.updateBankQuery(getCurrentBank());
						tablePanel.updateRow(getCurrentBank());
//						DBHelper.saveBankAddressQuery(getCurrentAddress(), getObjectKey());
					} catch (Exception e) {
						showErrAlert("Error", "Bank data is not updated.\nReason : " + e.getLocalizedMessage());
						CustLogger.error("Bank data is not updated. KEY:" + getObjectKey() + " Reason : " + e.getLocalizedMessage());
						return;
					}
					showConfAlert("Success",
							"Bank data is updated " + getObjectKey());
					CustLogger.info("Bank data is updated KEY:" + getObjectKey());
				}
			}
		};
	}



	@Override
	protected EventHandler<MouseEvent> saveEvent(){
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				if (notValidBankData()) {
					showErrAlert("Data Error!", "Bank > Name, Short Name, branch Name is required");
					return;
				} else if (notValidAddress()) {
					showErrAlert("Data Error!",
							"Address > Line 1, city, zip and state is required");
					return;
				} else {
					int bankKey = 0;
					try {
						bankKey = DBHelper.saveBankQuery(getCurrentBank());
						tablePanel.addNewRow(bankKey, getCurrentBank());
					} catch (Exception e) {
						showErrAlert("Error", "Bank is not saved.//n Reason : " + e.getLocalizedMessage());
						CustLogger.error("Bank is not saved.  KEY:" + bankKey + " Reason : " + e.getLocalizedMessage());
					}
					showConfAlert("Success",
							"New bank is Added " + bankKey);
					CustLogger.info("New bank is Added KEY:" + bankKey);
				}
			}
		};
	}

	

	protected boolean notValidBankData() {
		return StringUtils.isNullOrEmpty(nameText.getText()) || StringUtils.isNullOrEmpty(brNameText.getText())
				|| StringUtils.isNullOrEmpty(shortNameText.getText());
	}

	protected boolean notValidAddress() {
		return StringUtils.isNullOrEmpty(addEdit.getLine1Text().getText())
				|| !StringUtils.isStrictlyNumeric(addEdit.getZipText().getText()) || !addEdit.isCitySelected()
				|| !addEdit.isStateSelected();
	}
	
	protected BankModel getCurrentBank() {
		return new BankModel(getObjectKey(), nameText.getText(), shortNameText.getText(), brNameText.getText(), getCurrentAddress());
	}
	
	protected AddressModel getCurrentAddress() {
		 AddressModel address = new AddressModel(addEdit.getObjectKey(), getObjectKey());
		address.setLine1(addEdit.getLine1Text().getText());
		address.setLine2(addEdit.getLine2Text().getText());
		address.setLine3(addEdit.getLine3Text().getText());
		address.setCity(addEdit.getSelectedCity());
		address.setZip(Integer.parseInt(addEdit.getZipText().getText()));
		address.setState(addEdit.getSelectedState());
		address.setCountry(addEdit.getSelectedCountry());
		address.setOther(addEdit.getOtherText().getText());
		return address;
	}

	@Override
	protected EventHandler<MouseEvent> deleteEvent() {
		return new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {

				try {
					DBHelper.deleteBankQuery(getObjectKey());
				} catch (Exception e) {
					showErrAlert("Error", "Bank is not deleted.//n Reason : " + e.getLocalizedMessage());
					CustLogger.error("Bank is not deleted. KEY:" + getObjectKey()+ ", Reason : " + e.getLocalizedMessage());
				}
				showConfAlert("Success", "Bank dats is deleted " + getObjectKey());
				CustLogger.info("Bank dats is deleted KEY:" + getObjectKey());
				clearAllText();
				tablePanel.deleteRow();
			}

		};
	}

	@Override
	protected void loadDropdownList() {
	}
	
}
