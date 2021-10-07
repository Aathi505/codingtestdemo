package com.qa.driver.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	
	
	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream ip = new FileInputStream(System.getProperty("user.dir")+ "/src/test/resources"
					+ "/config/runconfig.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void initializeDriver() {
		String browserName = prop.getProperty("browser").toString();
		String currentDirectory = System.getProperty("user.dir");
		
		if(browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", currentDirectory+"/src/test/resources/driver/chromedriver.exe");
			driver = new ChromeDriver();	
			driver.get(prop.getProperty("url"));
			driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		}else {
			System.out.println("Browser Not Supported");
		}
		
		
	}
}
