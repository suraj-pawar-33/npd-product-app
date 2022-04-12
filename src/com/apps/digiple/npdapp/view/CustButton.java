package com.apps.digiple.npdapp.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public enum CustButton {

	BANK("bank", "Bank"), ORDER("order", "Order"), SUBSCRIPTION("sub", "Subscriptions"), CONTACT("contacts",
			"Contacts"), PRODUCT("product", "Products"), SAVE("save", "Save");
	private String btnName;
	private String btnText;
	private Button button;
	private boolean isSelected;

	private CustButton(String btnName, String btnText) {
		this.setBtnName(btnName);
		this.setBtnText(btnText);
		this.setButton(getMenuButton());
	}

	public String getBtnName() {
		return btnName;
	}

	private void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getBtnText() {
		return btnText;
	}

	private void setBtnText(String btnText) {
		this.btnText = btnText;
	}

	private Button getMenuButton() {
		Button btn = new Button(getBtnText());
		btn.setId(getBtnName());
		btn.setBackground(CustFontColor.BUTTON_BG.getBackGround());
		btn.setFont(CustFontColor.getNormalFont());
		btn.setTextFill(CustFontColor.WHITE.getPaint());
		btn.setBorder(null);
		btn.setPrefHeight(30);
		btn.setPrefWidth(1000);
		btn.setMaxWidth(600);
		btn.setOnMouseEntered(getMouseEnteredHandler());
		btn.setOnMouseExited(getMouseExitedHandler());
		return btn;
	}

	private EventHandler<? super MouseEvent> getMouseExitedHandler() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getTarget().equals(getButton()) && !isSelected()) {
					getButton().setFont(CustFontColor.getNormalFont());
					getButton().setBackground(CustFontColor.BUTTON_BG.getBackGround());
				}
			}
		};
	}

	private EventHandler<? super MouseEvent> getMouseEnteredHandler() {
		return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getTarget().equals(getButton()) && !isSelected()) {
					getButton().setFont(CustFontColor.getBoldFont());
					getButton().setBackground(CustFontColor.BUTTON_H_BG.getBackGround());
				}
			}
		};
	}

	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public void setSelected() {
		this.button.setTextFill(CustFontColor.BLACK.getPaint());
		this.button.setFont(CustFontColor.getBoldFont());
		this.setSelected(true);
		this.button.setBackground(new Background(new BackgroundFill(Color.BEIGE, CornerRadii.EMPTY, Insets.EMPTY)));

	}

	public void setUnselected() {
		this.button.setTextFill(CustFontColor.WHITE.getPaint());
		this.button.setFont(CustFontColor.getNormalFont());
		this.setSelected(false);
		getButton().setBackground(
				new Background(new BackgroundFill(Color.rgb(50, 72, 168), CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public static void unSelectAll() {
		CustButton.BANK.setUnselected();
		CustButton.ORDER.setUnselected();
		CustButton.CONTACT.setUnselected();
		CustButton.PRODUCT.setUnselected();
		CustButton.SUBSCRIPTION.setUnselected();
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public static Button getCustButton(String text, String id) {
		Button btn = new Button(text);
		btn.setId(id);
		btn.setBackground(CustFontColor.BUTTON_BG.getBackGround());
		btn.setFont(CustFontColor.getNormalFont());
		btn.setTextFill(CustFontColor.WHITE.getPaint());
		btn.setBorder(null);
		btn.setPrefHeight(30);
		btn.setPrefWidth(150);
		btn.setMinWidth(20);
		btn.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
					btn.setFont(CustFontColor.getBoldFont());
					btn.setBackground(CustFontColor.BUTTON_H_BG.getBackGround());
			}
		});
		btn.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
					btn.setFont(CustFontColor.getNormalFont());
					btn.setBackground(CustFontColor.BUTTON_BG.getBackGround());
			}
		});
		return btn;
	}

}
