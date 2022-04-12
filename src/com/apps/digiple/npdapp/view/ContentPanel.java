package com.apps.digiple.npdapp.view;

import com.apps.digiple.npdapp.dbdriver.CustLogger;
import com.apps.digiple.npdapp.impl.AbstractDataProcessor;
import com.apps.digiple.npdapp.impl.BankDataProcessor;
import com.apps.digiple.npdapp.impl.OrderDataProcessor;
import com.apps.digiple.npdapp.impl.ProductDataProcessor;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class ContentPanel extends StackPane {

	protected static final String REF_VIEW = "Refreshing View > ";
	private BankDataProcessor bankProcessor = null;
	private ProductDataProcessor productProcessor = null;
	private OrderDataProcessor orderProcessor = null;

	public ContentPanel() {

		// Setting size for the pane
		setMinSize(500, 500);
		setPrefSize(2000, 1000);
		setAlignment(Pos.CENTER);
		setBackground(CustFontColor.BEIGE.getBackGround());
	}

	public void showBanksPane() {

		if (bankProcessor == null) {
			this.bankProcessor = new BankDataProcessor();
			add(bankProcessor.getPanel());
			loadDataInTables(bankProcessor);
		} else {
			getChildren().clear();
			add(bankProcessor.getPanel());
		}
	}

	public void add(GridPane editPanel) {
		getChildren().add(editPanel);
	}

	public void clear() {
		getChildren().clear();
	}

	public void showOrdersPane() {
		if (orderProcessor == null) {
			this.orderProcessor = new OrderDataProcessor();
			add(orderProcessor.getPanel());
			loadDataInTables(orderProcessor);
		} else {
			getChildren().clear();
			add(orderProcessor.getPanel());
		}
	}

	public void showSubPane() {
		getChildren().clear();
	}

	public void showContactsPane() {
		getChildren().clear();
	}

	public void showProductsPane() {
		if (productProcessor == null) {
			this.productProcessor = new ProductDataProcessor();
			add(productProcessor.getPanel());
			loadDataInTables(productProcessor);
		} else {
			getChildren().clear();
			add(productProcessor.getPanel());
		}
	}

	private void loadDataInTables(AbstractDataProcessor abstractDataProcessor) {

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				CustLogger.info(REF_VIEW + abstractDataProcessor.getClass().getSimpleName());
				try {
					abstractDataProcessor.refreshView();
				} catch (Exception e) {
					CustLogger.info(REF_VIEW + abstractDataProcessor.getClass().getSimpleName() + " : " + e.getLocalizedMessage());
				}
			}
		}, REF_VIEW + abstractDataProcessor.getClass());
		t.start();

	}

}
