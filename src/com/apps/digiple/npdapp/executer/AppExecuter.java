package com.apps.digiple.npdapp.executer;

import com.apps.digiple.npdapp.dbdriver.CustLogger;
import com.apps.digiple.npdapp.dbdriver.DBHelper;
import com.apps.digiple.npdapp.view.AppWindow;

public class AppExecuter {
	public static void main(String args[]) {
		// TODO : use constants
		String hostName = "";
		String userName = "";
		String pass = "";
		if (args != null && args.length == 3 ) {
			hostName = args[0];
			userName = args[1];
			pass = args[2];
		}
		CustLogger.main(null);
		CustLogger.info("NPD APP Started---------------------------->");
		DBHelper.createConnection(hostName, userName, pass);
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
