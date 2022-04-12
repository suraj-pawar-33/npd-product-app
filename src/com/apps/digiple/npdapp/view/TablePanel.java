package com.apps.digiple.npdapp.view;

import java.util.Iterator;

import com.apps.digiple.npdapp.models.BankModel;
import com.apps.digiple.npdapp.models.ItemModel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class TablePanel extends GridPane {
	protected TableView<? extends ItemModel> table;
	protected ObservableList<? extends ItemModel> data ;
	protected ObservableList<? extends ItemModel> filteredData = FXCollections.observableArrayList();
	private TextField field;
	private Button btn;
	
	public TablePanel(EventHandler<MouseEvent> eventHandler, TableView<? extends ItemModel> tableView) {
		setMinSize(500, 300);
		setPrefSize(2000, 1800);
		setAlignment(Pos.CENTER);
		setVgap(5);
		setHgap(5);
		table = tableView;
		table.setId("DataTable");
        table.setOnMouseClicked(eventHandler);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.btn = createReloadBtn(eventHandler);
		add(getFilterPanel(), 0, 0);
		add(getVbox(), 0, 1);
	}

	private GridPane getFilterPanel() {
		GridPane pane = new GridPane();
		pane.setHgap(5);
		pane.setPadding(new Insets(0, 0, 0, 10));
		pane.add(createSearchBox(), 0, 0);
		pane.add(btn, 1, 0);
		return pane;
	}
	
	protected void filterData (String str) {
		filteredData.clear();
		filteredData = loadFilteredData(str);
		refreshFildata();
	}
	
	public void disableSearchField(){
		field.disableProperty().set(true);
	}
	
	public void enableSearchField(){
		field.disableProperty().set(false);
	}
	
	private TextField createSearchBox() {
		field = new TextField();
		field.setPrefSize(300, 30);
		field.setMinSize(50, 25);
		field.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> obs, String oldValue, String newValue) {
				filterData(newValue);
			}

		});
		return field;
	}
	
	private Button createReloadBtn(EventHandler<? super MouseEvent> eventHandler) {
		Button button = CustButton.getCustButton("", "ReloadBtn");
		button.setShape(new Circle(15));
		button.setPrefSize(20, 20);
		button.setMinSize(10, 10);
		button.setOnMouseClicked(eventHandler);
		return button;
	}

	protected VBox getVbox() {
		final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(0, 10, 10, 10));
        loadColumns();
        vbox.getChildren().addAll(table);
		return vbox;
	}

	@SuppressWarnings("unchecked")
	protected void loadColumns() {
		// Dummy columns
		table.setEditable(true);
		 
        TableColumn<ItemModel, Long> keyCol = getTableColumn("Item Key", "bankKey", Long.class, ItemModel.class);

        ((TableView<ItemModel>) table).getColumns().addAll(keyCol);
        table.setPrefSize(1800, 1800);
        table.setMinSize(500, 300);
	}

	public boolean isSelected() {
		return !table.getSelectionModel().isEmpty();
	}
	
	public int getSelectedIndex() {
		return table.getSelectionModel().getSelectedIndex();
	}
	
	public ItemModel getSelectedItem() {
		return table.getSelectionModel().getSelectedItem();
	}

	protected <K, T> TableColumn<K, T> getTableColumn(String string, String string2, Class<T> clazz, Class<K> clazz2) {
		TableColumn<K, T> col = new TableColumn<K, T>(string);
        col.setMinWidth(100);
        col.setResizable(true);
        col.setEditable(true);
        col.setCellValueFactory(
                new PropertyValueFactory<K, T>(string2));
		return col;
	}
	
	protected void showAlert(AlertType alertType, String title, String message) {
		Alert alert = new Alert(alertType);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.initOwner(this.getScene().getWindow());
		alert.show();
	}
	

	@SuppressWarnings("unchecked")
	public void refresh() {
		((TableView<ItemModel>) table).setItems((ObservableList<ItemModel>) data);
	}
	
	@SuppressWarnings("unchecked")
	public void refreshFildata() {
		((TableView<ItemModel>) table).setItems((ObservableList<ItemModel>) filteredData);
	}
	
	@SuppressWarnings("unchecked")
	public void setPlaceHolder(String msg) {
		((TableView<ItemModel>) table).setPlaceholder(CustLabel.createLabel(msg));
	}

	@SuppressWarnings("unchecked")
	protected ObservableList<? extends ItemModel> loadFilteredData(String str) {
		return (ObservableList<ItemModel>) filteredData;
	}

	@SuppressWarnings("unchecked")
	public void addNewRow(int key, ItemModel model) {
		model.setKey(key);
		((TableView<ItemModel>) table).getItems().add(0,  model);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteRow() {
		((TableView<ItemModel>) table).getItems().remove(((TableView<ItemModel>) table).getSelectionModel().getSelectedIndex());
	}
	
	@SuppressWarnings("unchecked")
	public void updateRow(ItemModel model) {
		ObservableList<ItemModel> items = ((TableView<ItemModel>) table).getItems();
		for (Iterator<?> iterator = items.iterator(); iterator.hasNext();) {
			ItemModel itemModel = (ItemModel) iterator.next();
			if (itemModel.getKey() == model.getKey()) {
				((TableView<ItemModel>) table).getItems().remove(itemModel);
				((TableView<ItemModel>) table).getItems().add(0,  model);
				return;
			}
		}
	}
}
