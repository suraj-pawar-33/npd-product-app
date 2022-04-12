package com.apps.digiple.npdapp.view.bank;

import java.util.Iterator;

import com.apps.digiple.npdapp.models.BankModel;
import com.apps.digiple.npdapp.models.ItemModel;
import com.apps.digiple.npdapp.view.TablePanel;
import com.mysql.cj.util.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class BankTablePanel extends TablePanel {

	
	public BankTablePanel(ObservableList<BankModel> data, EventHandler<MouseEvent> eventHandler) {
		super(eventHandler, new TableView<BankModel>());
		this.data =  data;
	}

	@SuppressWarnings("unchecked")
	protected void loadColumns() {
 
        table.setEditable(true);
 
        TableColumn<BankModel, Long> keyCol = getTableColumn("Bank Key", "bankKey", Long.class, BankModel.class);
        TableColumn<BankModel, String> bankNameCol = getTableColumn("Bank Name", "bankName", String.class, BankModel.class);
        TableColumn<BankModel, String> shNameCol = getTableColumn("Short Name", "shortName", String.class, BankModel.class);
        TableColumn<BankModel, String> brNameCol = getTableColumn("Branch Name","branchName", String.class, BankModel.class);
        TableColumn<BankModel, String> addressCol = getTableColumn("Address", "address", String.class, BankModel.class);

        ((TableView<BankModel>) table).setItems((ObservableList<BankModel>) data);
        ((TableView<BankModel>) table).getColumns().addAll(keyCol, bankNameCol, shNameCol, brNameCol, addressCol);
        table.setPrefSize(1800, 1800);
        table.setMinSize(500, 300);
	}
	
	@Override
	protected ObservableList<BankModel> loadFilteredData(String str) {
		ObservableList<BankModel> newData = FXCollections.observableArrayList();
		if (StringUtils.isNullOrEmpty(str) || StringUtils.isEmptyOrWhitespaceOnly(str)) {
			newData.addAll((ObservableList<BankModel>) data);
			return newData;
		}
		if (StringUtils.isStrictlyNumeric(str)) {
			for (Iterator<? extends ItemModel> iterator = data.iterator(); iterator.hasNext();) {
				BankModel bankModel = (BankModel) iterator.next();
				if (String.valueOf(bankModel.address().getZip()).contains(str)
						|| String.valueOf(bankModel.getBankKey()).contains(str) ) {
					newData.add(bankModel);
				}
			}
		} else {
			for (Iterator<? extends ItemModel> iterator = data.iterator(); iterator.hasNext();) {
				BankModel bankModel = (BankModel) iterator.next();
				String lowerStr = str.toLowerCase();
				if ((!StringUtils.isNullOrEmpty(bankModel.getBankName()) 
						&& bankModel.getBankName().toLowerCase().contains(lowerStr))
						|| (!StringUtils.isNullOrEmpty(bankModel.getBranchName())
						&& bankModel.getBranchName().toLowerCase().contains(lowerStr))
						|| (!StringUtils.isNullOrEmpty(bankModel.getShortName())
						&& bankModel.getShortName().toLowerCase().contains(lowerStr))
						|| (!StringUtils.isNullOrEmpty(bankModel.getAddress())
						&& bankModel.getAddress().toLowerCase().contains(lowerStr))
						 ) {
					newData.add(bankModel);
				}
			}
		}
		return newData;
	}
	

}
