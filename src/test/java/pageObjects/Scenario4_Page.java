package pageObjects;

import org.openqa.selenium.By;

public class Scenario4_Page {
	public By img = By.xpath("//*[@class='figure']//following::img");
	public By userDetails = By.xpath("//*[@class='figcaption']/child::h5");
	//public By userDetails = By.xpath("//*[@class='figure']/div/h5");
}
