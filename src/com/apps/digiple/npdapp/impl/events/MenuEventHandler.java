package com.apps.digiple.npdapp.impl.events;

import com.apps.digiple.npdapp.dbdriver.CustLogger;
import com.apps.digiple.npdapp.view.CustButton;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class MenuEventHandler implements EventHandler<MouseEvent> {

	@Override
	public void handle(MouseEvent event) {
		if (event.getButton().name().equals(CustButton.BANK.getBtnName())) {

			CustLogger.info(event.getButton().name());
		}
	}

}
