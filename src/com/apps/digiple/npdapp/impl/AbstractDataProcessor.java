package com.apps.digiple.npdapp.impl;

import com.apps.digiple.npdapp.models.BankModel;
import com.apps.digiple.npdapp.models.ItemModel;
import com.apps.digiple.npdapp.view.DataEditPanel;
import com.apps.digiple.npdapp.view.TablePanel;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public abstract class AbstractDataProcessor {

	protected TablePanel tablePanel;
	protected DataEditPanel editPanel;
	
	public abstract TablePanel getTablePanel();
	public abstract void setTablePanel(TablePanel tablePanel);
	public abstract DataEditPanel getEditPanel();
	public abstract void setEditPanel(DataEditPanel editPanel);
	
	public abstract ObservableList<?> getData();

	protected EventHandler<MouseEvent> getMouseEventHandler() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if ( "DataTable".equals(((Node) arg0.getSource()).getId()) && tablePanel.isSelected()) {
					loadRow(tablePanel.getSelectedItem());
				} else if ("ReloadBtn".equals(((Node) arg0.getSource()).getId())) {
					loadDBData();
					tablePanel.refresh();
				}
			}
		};
	}
	
	public abstract void loadRow(ItemModel selectedItem) ;
	public void refreshView(){
		loadDBData();
		tablePanel.refresh();
	}
	public abstract void loadDBData() ;


}
