package com.apps.digiple.npdapp.view;

import com.apps.digiple.npdapp.impl.events.MouseEventHandler;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class MainPanel extends GridPane {

	private MenuPanel menuPanel = new MenuPanel();
	private SmallMenuPanel smallMPanel = new SmallMenuPanel();
	private ContentPanel contentPanel = new ContentPanel();

	public MainPanel() {

		setMinSize(500, 200);
		setPrefSize(1100, 650);
		setMaxSize(1500, 900);

		setAlignment(Pos.TOP_LEFT);

		initialise();

		add(menuPanel, 0, 0);
		add(contentPanel, 1, 0);

	}

	private void initialise() {
		MouseEventHandler eventHandler = new MouseEventHandler(contentPanel);

		CustButton.BANK.getButton().setOnMouseClicked(eventHandler);
		CustButton.ORDER.getButton().setOnMouseClicked(eventHandler);
		CustButton.SUBSCRIPTION.getButton().setOnMouseClicked(eventHandler);
		CustButton.CONTACT.getButton().setOnMouseClicked(eventHandler);
		CustButton.PRODUCT.getButton().setOnMouseClicked(eventHandler);
		
		menuPanel.getLeftSlidBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				getChildren().remove(menuPanel);
				add(smallMPanel, 0, 0);
			}
		});
		
		smallMPanel.getRightSlidBtn().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				getChildren().remove(smallMPanel);
				add(menuPanel, 0, 0);
			}
		});
	}
}
