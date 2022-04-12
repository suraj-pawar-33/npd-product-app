package com.apps.digiple.npdapp.executer;

import com.apps.digiple.npdapp.dbdriver.CustLogger;
import com.apps.digiple.npdapp.dbdriver.DBHelper;
import com.apps.digiple.npdapp.view.AppWindow;

public class AppExecuter {
	public static void main(String args[]) {
		CustLogger.main(null);
		CustLogger.info("NPD APP Started---------------------------->");
		DBHelper.createConnection();
		try {
			new AppWindow().launchApp(args);
		} catch (Exception e) {
			CustLogger.error("AppExecuter > " + e.getLocalizedMessage());
		}
		DBHelper.closeCon();
		CustLogger.info("NPD APP Closed ---------------------------->");
		CustLogger.save();
	}
}
