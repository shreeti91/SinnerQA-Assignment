package sinnerSchrader;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import junit.framework.Assert;
import pageObjects.Scenario1_Page;
import pageObjects.Scenario2_Page;
import pageObjects.Scenario3_Page;
import pageObjects.Scenario4_Page;
import utils.Commons;

public class AppTest {

	Scenario1_Page objScen1;
	Scenario2_Page objScen2;
	Scenario3_Page objScen3;
	Scenario4_Page objScen4;
	

	@BeforeTest
	public void SetUp() {
		System.setProperty("webdriver.chrome.driver", Commons.chromeDriverPath + "chromedriver.exe");
		Commons.objDriver = new ChromeDriver();
		Commons.objWait = new WebDriverWait(Commons.objDriver, 10);

		Commons.objWait = new WebDriverWait(Commons.objDriver, 10);
		objScen1 = new Scenario1_Page();
		objScen2 = new Scenario2_Page();
		objScen3 = new Scenario3_Page(Commons.objDriver);
		objScen4 = new Scenario4_Page();

	}

	@Test(dataProvider = "Scenario 1 TestData", priority = 1)
	public void testScenario1(String url) throws MalformedURLException, IOException {
		int ActualBrokenImagecount = 0;
		int ExpectedBrokenImageCount = 2;
		Commons.objDriver.manage().window().maximize();
		Commons.objDriver.get(url);
		List<WebElement> imgList = Commons.objDriver.findElements(objScen1.images);
		for (int i = 0; i < imgList.size(); i++) {
			HttpURLConnection connection = (HttpURLConnection) new URL(imgList.get(i).getAttribute("src"))
					.openConnection();
			connection.connect();
			String Response = connection.getResponseMessage();
			connection.disconnect();
			System.out.println(imgList.get(i).getAttribute("src") + ":" + Response);
			if (Response.equalsIgnoreCase("Not Found")) {
				ActualBrokenImagecount = ActualBrokenImagecount + 1;
			}
		}
		Assert.assertEquals(ExpectedBrokenImageCount, ActualBrokenImagecount);
	}

	@DataProvider(name = "Scenario 1 TestData")
	public Object[][] getDataScenario_1() {
		return new Object[][] { { "https://the-internet.herokuapp.com/broken_images" } };
	}

	@Test(dataProvider = "Scenario 2 TestData", priority = 2)
	public void testScenario2(String url, String userName, String password) {
		Commons.objDriver.manage().window().maximize();
		String final_url = "http://" + userName + ":" + password + "@" + url.substring(7);
		Commons.objDriver.get(final_url);
		String expected = "Congratulations! You must have the proper credentials.";
		String actual = Commons.objDriver.findElement(objScen2.successMessage).getText();
		Assert.assertEquals(expected, actual);
	}

	@DataProvider(name = "Scenario 2 TestData")
	public Object[][] getDataScenario_2() {
		return new Object[][] { { "http://the-internet.herokuapp.com/basic_auth", "admin", "admin" } };
	}

	@Test(dataProvider = "Scenario 3 TestData", priority = 3)
	public void testScenario3(String url) {
		Commons.objDriver.manage().window().maximize();
		Commons.objDriver.get(url);
		WebElement slider = Commons.objDriver.findElement(objScen3.slider);

		// Move slider to max and assert the number
		String maxValue = "5";
		slider.click();
		objScen3.setSliderValueRight(maxValue);
		assertEquals(objScen3.getSliderValue(), maxValue, "Slider value is incorrect");

		// Move slider to min value and check the number
		String minValue = "0";
		slider.click();
		objScen3.setSliderValueLeft(minValue);
		System.out.println("Scenario : 3 - Actual Min Value: " + objScen3.getSliderValue());
		assertEquals(objScen3.getSliderValue(), minValue, "Slider value is incorrect");

	}

	@DataProvider(name = "Scenario 3 TestData")
	public Object[][] getScenario_3() {
		return new Object[][] { { "https://the-internet.herokuapp.com/horizontal_slider" } };
	}

	/*
	 * Scenario 4 https://the-internet.herokuapp.com/hovers Hover on the pictures
	 * and assert the details like User
	 */

	@Test(dataProvider = "Scenario 4 TestData", priority = 4)
	public void testScenario4(String url) {
		Commons.objDriver.manage().window().maximize();
		Commons.objDriver.get(url);

		Actions objAction = new Actions(Commons.objDriver);
		WebElement imgs = Commons.objDriver.findElement(objScen4.img);
		String expected = "name: user1";
		objAction.moveToElement(imgs).click().build().perform();
		WebElement userNameActual = imgs.findElement(objScen4.userDetails);
		String actual = userNameActual.getText();
		System.out.println("Expected=====" + expected);
		System.out.println("Actual=====" + actual);
		Assert.assertEquals(expected, actual);

	}

	@DataProvider(name = "Scenario 4 TestData")
	public Object[][] getScenario_4() {
		return new Object[][] { { "https://the-internet.herokuapp.com/hovers" } };
	}

	/*
	 * @AfterTest public void TearDown() {
	 * 
	 * Commons.objDriver.close(); }
	 */
}
