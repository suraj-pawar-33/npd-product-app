package com.apps.digiple.npdapp.view.orders;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.apps.digiple.npdapp.dbdriver.CustLogger;
import com.apps.digiple.npdapp.dbdriver.DBHelper;
import com.apps.digiple.npdapp.models.Order2ProductModel;
import com.apps.digiple.npdapp.models.ProductModel;
import com.apps.digiple.npdapp.table.edit.EditingCell;
import com.apps.digiple.npdapp.view.TablePanel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class ProSelectionPanel extends TablePanel {

	private int orderKey;
	private ObservableList<ProductModel> data = FXCollections.observableArrayList();
	
	public ProSelectionPanel(EventHandler<MouseEvent> eventHandler) {
		super(eventHandler, new TableView<ProductModel>());
		setMinSize(100, 100);
		setPrefSize(500, 300);	
		table.setEditable(true);
		disableSearchField();
	}

	public void setOrderKey(int orderKey) {
		this.orderKey = orderKey;
	}

	public void loadProductData() {
		data.clear();
		try {
			ResultSet rs = DBHelper.selectProductQuery();
			while (rs.next()) {
				ProductModel product = new ProductModel(Integer.valueOf(rs.getString(1)), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
				product.setProductCost(rs.getString(6));
				product.setProSubCost(Integer.valueOf(rs.getString(7)));
				data.add(product);	
			}
		} catch (Exception e) {
			CustLogger.error("ERROR : " + e.getLocalizedMessage());
//			showAlert(AlertType.ERROR, "Error", "Product data not accessible.\n Reason : " + e.getLocalizedMessage());
		}
	}

	public int getOrderKey() {
		return orderKey;
	}

	@SuppressWarnings("unchecked")
	protected void loadColumns() {
		
		Callback<TableColumn<ProductModel, String>, TableCell<ProductModel, String>> cellFactory
        = (TableColumn<ProductModel, String> param) -> new EditingCell();

		TableColumn<ProductModel, String> proNameCol = getTableColumn("Product Name", ProductModel.PRODUCT,
				String.class, ProductModel.class);
		TableColumn<ProductModel, String> costCol = getTableColumn("Cost", ProductModel.COST, String.class,
				ProductModel.class);
		
		costCol.setCellValueFactory(cellData -> cellData.getValue().getProductCostPrty());
		costCol.setCellFactory(cellFactory);
		costCol.setOnEditCommit(
                (TableColumn.CellEditEvent<ProductModel, String> t) -> {
                    ((ProductModel) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setProductCost(t.getNewValue());
                });
		
		TableColumn<ProductModel, String> qntyCol = getTableColumn("Quantity", ProductModel.QUANTITY, String.class,
				ProductModel.class);
		
		qntyCol.setCellValueFactory(cellData -> cellData.getValue().getQntyEntry());
		qntyCol.setCellFactory(cellFactory);
		qntyCol.setOnEditCommit(
                (TableColumn.CellEditEvent<ProductModel, String> t) -> {
                    ((ProductModel) t.getTableView().getItems()
                    .get(t.getTablePosition().getRow()))
                    .setQntyEntry(t.getNewValue());
                });

		((TableView<ProductModel>) table).getColumns().addAll(proNameCol, costCol, qntyCol);
		table.setPrefSize(1800, 1800);
		table.setMinSize(100, 100);
	}

	@SuppressWarnings("unchecked")
	public void setProducts(ObservableList<ProductModel> data) {
		((TableView<ProductModel>) table).setItems(data);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void refresh() {
		((TableView<ProductModel>) table).setItems((ObservableList<ProductModel>) data);
	}

	@SuppressWarnings("unchecked")
	public void selectItems(List<Order2ProductModel> products) {
		HashMap<Integer, Order2ProductModel> orPrMap = new HashMap<>();
		for (Iterator<Order2ProductModel> iterator = products.iterator(); iterator.hasNext();) {
			Order2ProductModel order2ProductModel = (Order2ProductModel) iterator.next();
			orPrMap.put(order2ProductModel.getProductKey(), order2ProductModel);
		}
		
		((TableView<ProductModel>) table).getSelectionModel().clearSelection();
		ObservableList<ProductModel> items = ((TableView<ProductModel>) table).getItems();
		if (items == null || items.isEmpty()) {
			return;
		}
		for (Iterator<ProductModel> iterator = items.iterator(); iterator.hasNext();) {
			ProductModel productModel = iterator.next();
			if (orPrMap.containsKey(productModel.getProductKey())) {
				productModel.setQntyEntry(String.valueOf(orPrMap.get(productModel.getProductKey()).getQuantity()));
				productModel.setProductCost(String.valueOf(orPrMap.get(productModel.getProductKey()).getAmount()));
			} else {
				productModel.setQntyEntry("0");
				productModel.setProductCost("0");
			}
		}
	}


	@SuppressWarnings("unchecked")
	protected List<Order2ProductModel> getSelectedProducts() {
		List<Order2ProductModel> list = new ArrayList<>();
		ObservableList<ProductModel> items = (ObservableList<ProductModel>) table.getItems();
		for (Iterator<ProductModel> iterator = items.iterator(); iterator.hasNext();) {
			ProductModel productModel = (ProductModel) iterator.next();
			if (!productModel.getQntyEntry().get().equals("0")) {
				list.add(new Order2ProductModel(0, getOrderKey(), productModel.getProductKey(), Integer.valueOf(productModel.getQntyEntry().get()), Integer.valueOf(productModel.getProductCost())));
			}
		}
		return list;
	}
	
	public int getTotalQuantity(){
		int qnty = 0;
		List<Order2ProductModel> productList = getSelectedProducts();
		for (Iterator<Order2ProductModel> iterator = productList.iterator(); iterator.hasNext();) {
			Order2ProductModel order2ProductModel = (Order2ProductModel) iterator.next();
			qnty = qnty + (order2ProductModel.getAmount() * order2ProductModel.getQuantity());
		}
		return qnty;		
	}

	public void clearQnts() {
		selectItems(new ArrayList<>());
	}

}
