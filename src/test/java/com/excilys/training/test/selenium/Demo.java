package com.excilys.training.test.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Demo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver","/home/excilys/Projects/cdb/src/test/resources/chromedriver");
		WebDriver  driver = new ChromeDriver();
		driver.get("http://localhost:8080/cdb/list-computer");
		driver.manage().window().maximize();
		String cTitle = driver.getTitle();
		String eTitle = "Computer Database";
		if(cTitle.equalsIgnoreCase(eTitle)) {
			System.out.println("Selenium Demo successful");
		}else {
			System.out.println("Selenium Demo unsuccessful");
		}

	}

}
