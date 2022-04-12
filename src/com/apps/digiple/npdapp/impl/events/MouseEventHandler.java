package com.apps.digiple.npdapp.impl.events;

import com.apps.digiple.npdapp.view.ContentPanel;
import com.apps.digiple.npdapp.view.CustButton;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class MouseEventHandler implements EventHandler<MouseEvent>{

	private ContentPanel contentPanel;

	public MouseEventHandler(ContentPanel contentPanel) {
		this.contentPanel = contentPanel;
	}

	@Override
	public void handle(MouseEvent mouseEvent) {
		Button btn = (Button) mouseEvent.getSource();
		CustButton.unSelectAll();
		if (btn.getId().equals(CustButton.BANK.getBtnName())) {
			CustButton.BANK.setSelected();
			contentPanel.showBanksPane();
		} else if (btn.getId().equals(CustButton.ORDER.getBtnName())) {
			CustButton.ORDER.setSelected();
			contentPanel.showOrdersPane();
		} else if (btn.getId().equals(CustButton.SUBSCRIPTION.getBtnName())) {
			CustButton.SUBSCRIPTION.setSelected();
			contentPanel.showSubPane();
		} else if (btn.getId().equals(CustButton.CONTACT.getBtnName())) {
			CustButton.CONTACT.setSelected();
			contentPanel.showContactsPane();
		} else if (btn.getId().equals(CustButton.PRODUCT.getBtnName())) {
			CustButton.PRODUCT.setSelected();
			contentPanel.showProductsPane();
		}
		
	}

	

}
