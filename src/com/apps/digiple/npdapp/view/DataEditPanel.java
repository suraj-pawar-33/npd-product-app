package com.apps.digiple.npdapp.view;

import java.sql.ResultSet;
import java.util.HashMap;

import com.apps.digiple.npdapp.dbdriver.CustLogger;
import com.apps.digiple.npdapp.dbdriver.DBHelper;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public abstract class DataEditPanel extends GridPane {

	protected int objectKey;
	private boolean isUpdateView = true;
	private GridPane saveBtnPane;
	private GridPane updateBtnPane;
	private Dialog<ButtonType> infoAlert;
	private Alert errAlert;
	private Alert confAlert;
	public TablePanel tablePanel;

	public DataEditPanel(TablePanel tablePanel) {
		this.tablePanel = tablePanel;
		setMinSize(500, 300);
		setPrefSize(2000, 1800);
		infoAlert = new Alert(AlertType.INFORMATION);
		errAlert = new Alert(AlertType.ERROR);
		confAlert = new Alert(AlertType.CONFIRMATION);
		setVgap(20);
		setHgap(20);
		setPadding(new Insets(20.0));
		setAlignment(Pos.CENTER_LEFT);
		loadSaveBtn();
		loadUpdateBtn();
		loadDeleteBtn();
		saveBtnPane = getSaveBtnPane();
		updateBtnPane = getUpdateBtnPane();
		showSaveView();
		setStyle("-fx-background-color: BEIGE;");
		startLoaderThread();

	}

	private void startLoaderThread() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					loadDropdownList();					
				} catch (Exception e) {
					CustLogger.info("ERROR : loading dropdown list > " + this.getClass().getSimpleName() + " : " + e.getLocalizedMessage());
				}
			}
		});
		t.start();
	}

	private GridPane getSaveBtnPane() {
		GridPane pane = new GridPane();
		pane.setHgap(10);
		pane.add(loadSaveBtn(), 0, 1);
		pane.add(loadClearBtn(), 1, 1);
		return pane;
	}
	
	private GridPane getUpdateBtnPane() {
		GridPane pane = new GridPane();
		pane.setHgap(10);
		pane.add(loadUpdateBtn(), 0, 1);
		pane.add(loadClearBtn(), 1, 1);
		pane.add(loadDeleteBtn(), 2, 1);
		return pane;
	}

	public int getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(int objectKey) {
		this.objectKey = objectKey;
	}

	public void showUpdateView() {
		if (!isUpdateView) {
			getChildren().remove(saveBtnPane);
			add(updateBtnPane, 0, 2, 2, 1);
			isUpdateView = true;
		}
	}

	public void showSaveView() {
		if (isUpdateView) {
			getChildren().remove(updateBtnPane);
			add(saveBtnPane,0, 2, 2, 1);
			isUpdateView = false;
		}
	}

	private Button loadSaveBtn() {
		Button btn = CustButton.getCustButton("Save", "save");
		btn.setOnMouseClicked(saveEvent());
		return btn;
	}

	private Button loadUpdateBtn() {
		Button btn = CustButton.getCustButton("Update", "update");
		btn.setOnMouseClicked(updateEvent());
		return btn;
	}

	private Button loadDeleteBtn() {
		Button btn = CustButton.getCustButton("Delete", "delete");
		btn.setBackground(CustFontColor.BUTTON_RED.getBackGround());
		btn.setOnMouseClicked(deleteEvent());
		return btn;
	}
	
	private Button loadClearBtn() {
		Button btn = CustButton.getCustButton("Clear", "clear");
		btn.setOnMouseClicked(clearEvent());
		return btn;
	}

	protected abstract void loadDropdownList();
	
	protected abstract EventHandler<MouseEvent> saveEvent();

	protected abstract EventHandler<MouseEvent> updateEvent();

	protected abstract EventHandler<MouseEvent> deleteEvent();
	
	protected EventHandler<MouseEvent> clearEvent() {
		return new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent arg0) {
				clearAllText();
			}
		};
	}

	protected abstract void clearAllText();

	public void showErrAlert(String title, String message) {
		errAlert.setTitle(title);
		errAlert.setHeaderText(null);
		errAlert.setContentText(message);
		if (errAlert.getOwner() != null) {
			errAlert.initOwner(this.getScene().getWindow());
		}
		errAlert.show();
	}
	
	public void showInfoAlert(String title, String message) {
		infoAlert.setTitle(title);
		infoAlert.setHeaderText(null);
		infoAlert.setContentText(message);
		if (infoAlert.getOwner() != null) {
			infoAlert.initOwner(this.getScene().getWindow());
		}
		infoAlert.show();
	}
	
	public void showConfAlert(String title, String message) {
		confAlert.setTitle(title);
		confAlert.setHeaderText(null);
		confAlert.setContentText(message);
		if (confAlert.getOwner() != null) {
			confAlert.initOwner(this.getScene().getWindow());
		}
		confAlert.show();
	}

	protected ChoiceBox<String> getChoiceBox() {
		ChoiceBox<String> choiceBox = new ChoiceBox<String>();
		choiceBox.setPrefSize(300, 30);
		choiceBox.setMinSize(100, 20);
		return choiceBox;
	}

	protected TextField getTextField() {
		TextField textField = new TextField();
		textField.setPrefSize(300, 30);
		textField.setMinSize(100, 20);
		return textField;
	}
	
	protected DatePicker getDatePicker() {
		DatePicker datePiceker = new DatePicker();
		datePiceker.setPrefSize(300, 30);
		datePiceker.setMinSize(100, 20);
		return datePiceker;
	}
	
	protected static HashMap<String, Integer> getStatusMap(){

		HashMap<String, Integer> map = new HashMap<>();
		try {
			ResultSet rs = DBHelper.getStatusList();
			while (rs.next()) {
				map.put(rs.getString(2), rs.getInt(1));
			}
			return map;
		} catch (Exception e) {
			map.put(e.getLocalizedMessage(), 0);
		}
		return map;
		
	}

	protected static HashMap<String, Integer> getOrderTypeMap(){
		HashMap<String, Integer> map = new HashMap<>();
		try {
			ResultSet rs = DBHelper.getOrderType();
			while (rs.next()) {
				map.put( rs.getString(2), rs.getInt(1));
			} 
		} catch (Exception e) {
			map.put(e.getLocalizedMessage(), 0);
		}
		return map;
	}

	protected static HashMap<String, Integer> getBankMap(){
		HashMap<String, Integer> map = new HashMap<>();
		try {
			ResultSet rs = DBHelper.getBankNameList();
			while (rs.next()) {
				map.put( rs.getString(2) + " - " + rs.getString(3) , rs.getInt(1));
			}
		} catch (Exception e) {
			map.put(e.getLocalizedMessage(), 0);
		}
		return map;
	}
	
	protected static HashMap<String, Integer> getProTypeMap(){
		HashMap<String, Integer> map = new HashMap<>();
		try {
			ResultSet rs = DBHelper.getProductType();
			while (rs.next()) {
				map.put( rs.getString(2), rs.getInt(1));
			}
		} catch (Exception e) {
			map.put(e.getLocalizedMessage(), 0);
		}
		return map;
	}
}
