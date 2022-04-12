package com.apps.digiple.npdapp.view;

import com.apps.digiple.npdapp.view.nodes.ButtonFactory;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Scale;

public class MenuPanel extends AnchorPane {

	private GridPane pane;

	protected Button leftSlidBtn;

	Scale scale = new Scale();

	public MenuPanel() {
		super();
		setMinSize(200, 500);
		setPrefSize(300, 1000);

		loadButtonPane();

		loadResizeBtn();
		
		AnchorPane.setTopAnchor(leftSlidBtn, 10.0);
		AnchorPane.setRightAnchor(leftSlidBtn, 10.0);
		
		AnchorPane.setLeftAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
		AnchorPane.setTopAnchor(pane, 200.0);

		getChildren().addAll(leftSlidBtn, pane);

		// Setting the back ground color
		setBackground(CustFontColor.MENU_BG.getBackGround());

	}

	private void loadButtonPane() {
		pane = new GridPane();
		
		pane.add(CustButton.ORDER.getButton(), 0, 0);
		pane.add(CustButton.SUBSCRIPTION.getButton(), 0, 1);
		pane.add(CustButton.BANK.getButton(), 0, 2);
		pane.add(CustButton.CONTACT.getButton(), 0, 3);
		pane.add(CustButton.PRODUCT.getButton(), 0, 4);
	}

	private void loadResizeBtn() {
		leftSlidBtn = ButtonFactory.getPaneResizeBtn("menuLbtn");
	}

	public Button getLeftSlidBtn() {
		return leftSlidBtn;
	}

}
