package com.apps.digiple.npdapp.view;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public enum CustFontColor {
	BEIGE(Color.BEIGE), BLACK(Color.BLACK), WHITE(Color.WHITE), BUTTON_BG(Color.rgb(50, 72, 168))
	, MENU_BG(Color.rgb(90, 50, 168)), BUTTON_H_BG(Color.rgb(63, 92, 217)), BUTTON_RED(Color.RED);
	
	private Color color;
	CustFontColor( Color color){
		this.color = color;
	}
	public Background getBackGround(){
		return new Background(getBackGroundFill());
	}
	
	public BackgroundFill getBackGroundFill(){
		return new BackgroundFill(this.color, CornerRadii.EMPTY, Insets.EMPTY);
	}
	
	public Paint getPaint(){
		return getBackGroundFill().getFill();
	}
	
	public static Font getNormalFont() {
		return Font.font("", FontWeight.NORMAL, 15);
	}
	
	public static Font getBoldFont() {
		return Font.font("", FontWeight.BOLD, 15);
	}
	
}
