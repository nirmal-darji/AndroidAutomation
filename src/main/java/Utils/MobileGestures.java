package Utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class MobileGestures {
	AppiumDriver driver;
	
	public MobileGestures(AppiumDriver driver2) {
		this.driver = (AndroidDriver) driver2;
	}
		
	public WebElement scrollForTheText(String searchedText) {
		return driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + searchedText + "\"));"));
	}
	
	public void longPressOnElement (WebElement element) {
		((JavascriptExecutor) driver).executeScript("mobile: longClickGesture", 
				ImmutableMap.of("elementId", ((RemoteWebElement) element).getId(),
				"duration", 3000));
	}
	
	public void startActivity (String packageName, String activityName) throws InterruptedException {
		String fullActivityName = packageName+"/"+activityName;
		System.out.println(fullActivityName);
		((JavascriptExecutor) driver).executeScript("mobile: startActivity", 
				ImmutableMap.of("intent", fullActivityName
        ));
		Thread.sleep(2000);
	}
	
	// Grabs the screenshot and then place it under reports folder
	public String getScreenshot(String testcaseName) throws IOException{
		File sourceFile = driver.getScreenshotAs(OutputType.FILE);
		String destinationFile = System.getProperty("user.dir")+"\\reports\\"+testcaseName+".png";
		FileUtils.copyFile(sourceFile, new File(destinationFile));
		return destinationFile;
	}
}