package com.apps.digiple.npdapp.view.nodes;

import com.apps.digiple.npdapp.view.CustFontColor;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ButtonFactory {

	public static Button getPaneResizeBtn(String name) {
		Button btn = new Button("<<");
		btn.setId(name);
		btn.setBackground(CustFontColor.BUTTON_BG.getBackGround());
		btn.setFont(CustFontColor.getNormalFont());
		btn.setTextFill(CustFontColor.WHITE.getPaint());
		btn.setBorder(null);
		btn.setPrefHeight(40);
		btn.setPrefWidth(40);
		btn.setMinWidth(20);
		btn.setPadding(new Insets(2));
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
