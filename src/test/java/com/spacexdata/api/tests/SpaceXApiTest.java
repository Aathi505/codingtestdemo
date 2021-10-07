package com.spacexdata.api.tests;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.driver.base.TestBase;
import com.spacexdata.api.util.APIUtil;
import com.spacexdata.api.util.BaseApiClient;

public class SpaceXApiTest extends TestBase{
	
	BaseApiClient apiClient = new BaseApiClient();
	public static String apiUrl;
	public static String response;
	
	@BeforeClass
	public void setup() {
		apiUrl = prop.getProperty("spacex_latest_launch_api");
		response = apiClient.getResponse(apiUrl);
	}
	
	@Test(priority = 1)
	@Parameters("statusCode")
	public void verifyResponseCode(String statusCode) {
		String status = String.valueOf(apiClient.getStatusCode());
		Reporter.log("Actual Status: "+status);
		assertEquals(statusCode, status);
		
	}
	
	@Test(priority = 2)
	@Parameters("rocket")
	public void verifyRockets(String rocket) {
		String actualRocket = APIUtil.readJsonPathValue(response, "$.rocket");
		Reporter.log("Actual Rocket: "+actualRocket);
		assertEquals(actualRocket, rocket);
		
	}
	
	@Test(priority = 3)
	@Parameters("ships")
	public void verifyShips(String ships) {
		String actualShips = APIUtil.readJsonPath(response, "$.ships");
		List<String> expectedshipList = Arrays.asList(ships.split(","));
		List<String> actualshipList = APIUtil.streamJSONArrayAsList(actualShips);
		Reporter.log("Actual ship: "+actualshipList);
		assertTrue(expectedshipList.equals(actualshipList), "Expected ShipList matches with Actual");
		
	}
	
	@AfterClass
	public void closeConnection() {
		try {
			apiClient.httpClient.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
