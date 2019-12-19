package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class Scenario3_Page {
	
	WebDriver driver;
	public By slider = By.xpath("//*[@id=\"content\"]/div/div/input");
	public By value = By.id("range");
	
	public Scenario3_Page(WebDriver driver) {
		this.driver=driver;
	}
	
	public void setSliderValueRight(String value) {
		while(!getSliderValue().equals(value)) {
			driver.findElement(slider).sendKeys(Keys.ARROW_RIGHT);
		}
	}
	
	public void setSliderValueLeft(String value) {
		while(!getSliderValue().equals(value)) {
			driver.findElement(slider).sendKeys(Keys.ARROW_LEFT);
		}
	}
	
	public Object getSliderValue(){
		return driver.findElement(value).getText();
	}

}
