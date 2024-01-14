package Utils;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class ImplicitWaits {
	AppiumDriver driver;
	
	public ImplicitWaits(AppiumDriver driver2) {
		this.driver = (AndroidDriver) driver2;
	}
	
	public void waitUntilCartPageIsLoaded() {
		By pageTitle = By.id("com.androidsample.generalstore:id/toolbar_title");  
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.refreshed(ExpectedConditions.textToBePresentInElementLocated(pageTitle, "Cart")));  
	}

	
}
