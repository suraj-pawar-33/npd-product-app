package com.apps.digiple.npdapp.view;

import com.apps.digiple.npdapp.view.nodes.ButtonFactory;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public class SmallMenuPanel extends AnchorPane {

	protected Button rightSlidBtn;
	
	public SmallMenuPanel() {
		setMinSize(50, 500);
		setPrefSize(60, 1200);
		
		loadResizeBtn();
		
		AnchorPane.setTopAnchor(rightSlidBtn, 10.0);
		AnchorPane.setRightAnchor(rightSlidBtn, 5.0);
		
		getChildren().addAll(rightSlidBtn);
		
		// Setting the back ground color
		setBackground(CustFontColor.MENU_BG.getBackGround());
	}

	private void loadResizeBtn() {
		rightSlidBtn = ButtonFactory.getPaneResizeBtn("menuRbtn");
		rightSlidBtn.setText(">>");
	}
	
	public Button getRightSlidBtn() {
		return rightSlidBtn;
	}




}
