package com.apps.digiple.npdapp.view;

import com.mysql.cj.util.StringUtils;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class AddressDataEditPanel extends  GridPane{

	private TextField line1Text;
	private TextField line2Text;
	private TextField line3Text;
	private ChoiceBox<String> cityChoiceBox;
	private TextField zipText;
	private ChoiceBox<String> stateChoiceBox;
	private ChoiceBox<String> conChoiceBox;
	private TextField otherText;
	private int objectKey;
	public AddressDataEditPanel() {
		setMinSize(500, 500);
		setPrefSize(1800, 1000);
		
		setVgap(20);
		setHgap(40);

		setAlignment(Pos.CENTER);
		setStyle("-fx-background-color: BEIGE;");
		add(getAddressPane(), 0, 0);
	}
	
	public TextField getLine1Text() {
		return line1Text;
	}

	public TextField getLine2Text() {
		return line2Text;
	}

	public TextField getLine3Text() {
		return line3Text;
	}

	public void setCityChoiceBox(String choice) {
		if (!StringUtils.isNullOrEmpty(choice)) {
			cityChoiceBox.getSelectionModel().select(choice);
		}
	}
	
	public String getSelectedCity() {
		return cityChoiceBox.getSelectionModel().getSelectedItem();
	}
	
	public boolean isCitySelected() {
		return !cityChoiceBox.getSelectionModel().isEmpty();
	}

	public TextField getZipText() {
		return zipText;
	}

	public void setStateChoiceBox(String choice) {
		if (!StringUtils.isNullOrEmpty(choice)) {
			stateChoiceBox.getSelectionModel().select(choice);
		}
	}
	
	public String getSelectedState() {
		return stateChoiceBox.getSelectionModel().getSelectedItem();
	}
	
	public boolean isStateSelected() {
		return !stateChoiceBox.getSelectionModel().isEmpty();
	}

	public void setConChoiceBox(String choice) {
		if (!StringUtils.isNullOrEmpty(choice)) {
			conChoiceBox.getSelectionModel().select(choice);
		}
	}
	
	public String getSelectedCountry() {
		return conChoiceBox.getSelectionModel().getSelectedItem();
	}

	public TextField getOtherText() {
		return otherText;
	}

	public GridPane getAddressPane() {
		GridPane addressPanel = new GridPane();
		addressPanel.setVgap(5);
		addressPanel.setHgap(5);

		Text addressLabel = CustLabel.ADDRESS.getBoldLebel();

		Text line1Label = CustLabel.LINE1.getLebel();
		line1Text = getTextField();

		Text line2Label = CustLabel.LINE2.getLebel();
		line2Text = getTextField();

		Text line3Label = CustLabel.LINE3.getLebel();
		line3Text = getTextField();

		Text CityLabel = CustLabel.CITY.getLebel();
		cityChoiceBox = getChoiceBox();
		cityChoiceBox.getItems().addAll("Aurangabad", "Ahamadanagar", "Chennai", "Delhi", "Mumbai", "Pune");

		Text zipLabel = CustLabel.ZIP.getLebel();
		zipText = getTextField();

		Text StateLabel = CustLabel.STATE.getLebel();
		stateChoiceBox = getChoiceBox();
		stateChoiceBox.getItems().addAll("Maharashtra", "Panjab", "Goa", "Gujarat", "UP", "MP", "Rajastan");

		Text ConLabel = CustLabel.CONT.getLebel();
		conChoiceBox = getChoiceBox();
		conChoiceBox.getItems().addAll("India", "Germany", "UK", "US");

		Text otherLabel = CustLabel.OTHER.getLebel();
		otherText = getTextField();

		addressPanel.add(addressLabel, 3, 0);

		addressPanel.add(line1Label, 2, 1);
		addressPanel.add(line1Text, 3, 1);

		addressPanel.add(line2Label, 2, 2);
		addressPanel.add(line2Text, 3, 2);

		addressPanel.add(line3Label, 2, 3);
		addressPanel.add(line3Text, 3, 3);

		addressPanel.add(CityLabel, 2, 4);
		addressPanel.add(cityChoiceBox, 3, 4);

		addressPanel.add(zipLabel, 2, 5);
		addressPanel.add(zipText, 3, 5);

		addressPanel.add(StateLabel, 2, 6);
		addressPanel.add(stateChoiceBox, 3, 6);

		addressPanel.add(ConLabel, 2, 7);
		addressPanel.add(conChoiceBox, 3, 7);

		addressPanel.add(otherLabel, 2, 8);
		addressPanel.add(otherText, 3, 8);

		return addressPanel;
	}

	AddressDataEditPanel getGridPane() {
		return this;
	}

	protected ChoiceBox<String> getChoiceBox() {
		ChoiceBox<String> choiceBox = new ChoiceBox<String>();
		choiceBox.setPrefSize(300, 30);
		choiceBox.setMinSize(50, 20);
		return choiceBox;
	}

	protected TextField getTextField() {
		TextField textField = new TextField();
		textField.setPrefSize(300, 30);
		textField.setMinSize(50, 20);
		return textField;
	}

	public ChoiceBox<String> getCityChoiceBox() {
		return cityChoiceBox;
	}
	
	public ChoiceBox<String> getStateChoiceBox() {
		return stateChoiceBox;
	}
	
	public ChoiceBox<String> getConChoiceBox() {
		return conChoiceBox;
	}

	public void clearAllText() {
		line1Text.clear();
		line2Text.clear();
		line3Text.clear();
		cityChoiceBox.getSelectionModel().clearSelection();
		zipText.clear();
		stateChoiceBox.getSelectionModel().clearSelection();
		conChoiceBox.getSelectionModel().clearSelection();
		otherText.clear();
	}

	public int getObjectKey() {
		return objectKey;
	}

	public void setObjectKey(int objectKey) {
		this.objectKey = objectKey;
	}


}
