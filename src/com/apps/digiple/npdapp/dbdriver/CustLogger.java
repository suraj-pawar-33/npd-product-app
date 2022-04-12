package com.apps.digiple.npdapp.dbdriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CustLogger{
	/** Logger */
	private static final Log LOGGER = LogFactory.getLog(CustLogger.class);
	private static final String ERROR = "ERROR";
	private static final String INFO =  "INFO ";
	private static final String WARN =  "WARN ";
	private static PrintWriter fileWriter;

	public static void main(String[] args) {
		String path = "C:\\NpdLogs";
		String className = "NpdAppLogs";
		String filename = getFileName(className);
		boolean isCreated = createLogFile(path, filename);
		if (!isCreated) {
			LOGGER.info("Error in File creation attempt");
		} else {
			LOGGER.info("Log file created : " + path + "\\" + filename);
		}

	}


	/**
	 * Creates a log file
	 * @param path 
	 * @param className 
	 * @return
	 */
	public static boolean createLogFile(String path, String fileName) {

		try {
			File logFile = new File(path + "\\" + fileName);
			File logDir = new File(path);
			if (logDir.exists() || logDir.mkdir()) {
				if (logFile.exists() || logFile.createNewFile()) {
					setFileWriter(logFile);
					return true;
				}
			}
			
		} catch (IOException e) {
			LOGGER.error(e + " Error in log file creation.");
		}
		return false;
	}
	/**
	 * Creates a buffered file writer
	 * @param file
	 */
	public static void setFileWriter(File file) {
		try {
			 FileWriter fw = new FileWriter(file, true);
			fileWriter = new PrintWriter(new BufferedWriter(fw));
		} catch (IOException e) {
			LOGGER.error(e + " Error in writing to file " + file.getName() + " for equipment import.");
		}
	}
	/**
	 * Saves the log file
	 */
	public static void save(){
		fileWriter.close();
		LOGGER.info("File Saved");
	}
	
	/**
	 * Creates a unique file name
	 * @param className
	 * @return
	 */
	public static String getFileName(String className) {
		SimpleDateFormat datetime = new SimpleDateFormat("dd-MM-yy");
		String dateTimeStr = datetime.format(new Date());
		return className + "_" + dateTimeStr + "-imp.log";
	}
	
	public static void error(String msg) {
		writeToFile(msg, ERROR);
	}
	
	public static void info(String msg) {
		writeToFile(msg, INFO);
	}

	public static void warning(String msg) {
		writeToFile(msg, WARN);
	}
	
	/**
	 * Formats the message with the time and the type of message
	 * @param msg
	 * @param type
	 */
	private static void writeToFile(String msg, String type) {
		StringBuilder errorMsg = new StringBuilder();
		SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss,SSS");
		errorMsg.append(datetime.format(new Date()));
		errorMsg.append(" ");
		errorMsg.append(type);
		errorMsg.append(" : ");
		errorMsg.append(msg);
		
		fileWriter.println(errorMsg.toString());
	}




}
