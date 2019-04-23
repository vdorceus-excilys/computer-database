package com.excilys.training.util.log;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LogTesting {
	
	private static Logger logger = LogManager.getLogger(LogTesting.class);
	 
    public static void main(String[] args) {
        logger.debug("===================Debug log message");
        logger.info("====================Info log message");
        logger.error("====================Error log message");
    }

}
