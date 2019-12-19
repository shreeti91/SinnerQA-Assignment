package utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.Commons;

public class BaseUtils {
	public WebDriverWait objWait = new WebDriverWait(Commons.objDriver,10);
	public Actions actions = new Actions(Commons.objDriver);
	
	public void wait_presenceOfElementLocated(By locator) {
		Commons.objWait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	public void wait_visibilityOfElementLocated(By locator) {
		Commons.objWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	public void wait_elementToBeClickable(By locator) {
		Commons.objWait.until(ExpectedConditions.elementToBeClickable(locator));
	}
	
	public boolean locator_isDisplayed_onPage(By locator) {
		boolean rc;
		rc = Commons.objDriver.findElement(locator).isDisplayed();
		return rc;
	}
	
	public void sendKeys_onTextField(By locator, String text) {
		Commons.objDriver.findElement(locator).sendKeys(text);
	}
	
	public void click_onLocator(By locator) {
		Commons.objDriver.findElement(locator).click();
	}
	
	public void enter_details_using_Action_class(By locator, String text) {
		actions.moveToElement(Commons.objDriver.findElement(locator)).click().sendKeys(text).build().perform();
	}
	
	public void press_pageDown() {
		actions.sendKeys(Keys.PAGE_DOWN).build().perform();
	}
	
	public String getText(By locator) {
		String message;
		message = Commons.objDriver.findElement(locator).getText();
		return message;
	}
	
	public  void takeSnapShot(WebDriver driver,String fileWithPath) throws Exception {
		
		TakesScreenshot scrShot = ((TakesScreenshot)driver);
		File scrFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		FileUtils.copyFile(scrFile,DestFile);
	}
}
