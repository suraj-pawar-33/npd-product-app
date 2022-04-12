package com.apps.digiple.npdapp.view.product;

import java.util.Iterator;

import com.apps.digiple.npdapp.models.ItemModel;
import com.apps.digiple.npdapp.models.ProductModel;
import com.apps.digiple.npdapp.view.TablePanel;
import com.mysql.cj.util.StringUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ProductTablePanel extends TablePanel {

	
	public ProductTablePanel(ObservableList<ProductModel> data, EventHandler<MouseEvent> eventHandler) {
		super(eventHandler, new TableView<ProductModel>());
		this.data =  data;      
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void loadColumns() {
 
        table.setEditable(true);
 
        TableColumn<ProductModel, Long> keyCol = getTableColumn("Product Key", ProductModel.KEY, Long.class, ProductModel.class);
        TableColumn<ProductModel, String> proNameCol = getTableColumn("Product Name", ProductModel.PRODUCT, String.class, ProductModel.class);
        TableColumn<ProductModel, String> proTypeCol = getTableColumn("Product Type", ProductModel.PRO_TYPE, String.class, ProductModel.class);
        TableColumn<ProductModel, String> shNameCol = getTableColumn("Short Name", ProductModel.SHORT, String.class, ProductModel.class);
        TableColumn<ProductModel, String> detailCol = getTableColumn("Details",ProductModel.DETAILS, String.class, ProductModel.class);
        TableColumn<ProductModel, Long> costCol = getTableColumn("Cost", ProductModel.COST, Long.class, ProductModel.class);
        TableColumn<ProductModel, Long> subCostCol = getTableColumn("Subscription", ProductModel.SUB_COST, Long.class, ProductModel.class);
        
        ((TableView<ProductModel>) table).setItems((ObservableList<ProductModel>) data);
        ((TableView<ProductModel>) table).getColumns().addAll(keyCol, proNameCol, shNameCol, proTypeCol, detailCol, costCol, subCostCol);
        table.setPrefSize(1800, 1800);
        table.setMinSize(500, 300);
 
	}
	
	@Override
	protected ObservableList<ProductModel> loadFilteredData(String str) {
		ObservableList<ProductModel> newData = FXCollections.observableArrayList();
		if (StringUtils.isNullOrEmpty(str) || StringUtils.isEmptyOrWhitespaceOnly(str)) {
			newData.addAll((ObservableList<ProductModel>) data);
			return newData;
		}
		if (StringUtils.isStrictlyNumeric(str)) {
			for (Iterator<? extends ItemModel> iterator = data.iterator(); iterator.hasNext();) {
				ProductModel productModel = (ProductModel) iterator.next();
				if ((!StringUtils.isNullOrEmpty(productModel.getProductCost()) 
						&& productModel.getProductCost().contains(str))
						|| String.valueOf(productModel.getProSubCost()).contains(str)
						|| String.valueOf(productModel.getProductKey()).contains(str) ) {
					newData.add(productModel);
				}
			}
		} else {
			for (Iterator<? extends ItemModel> iterator = data.iterator(); iterator.hasNext();) {
				ProductModel productModel = (ProductModel) iterator.next();
				String lowerStr = str.toLowerCase();
				if ((!StringUtils.isNullOrEmpty(productModel.getProductName()) 
						&& productModel.getProductName().toLowerCase().contains(lowerStr))
						|| (!StringUtils.isNullOrEmpty(productModel.getShortName())
						&& productModel.getShortName().toLowerCase().contains(lowerStr))
						|| (!StringUtils.isNullOrEmpty(productModel.getProductType())
						&& productModel.getProductType().toLowerCase().contains(lowerStr))
						|| (!StringUtils.isNullOrEmpty(productModel.getProductDetail())
						&& productModel.getProductDetail().toLowerCase().contains(lowerStr))) {
					newData.add(productModel);
				}
			}
		}
		return newData;
	}
}
