package Ecommerce.pageObjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import Utils.MobileGestures;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class WebviewPage extends MobileGestures {
	
	AppiumDriver driver;
	public WebviewPage(AppiumDriver driver2) {
		super(driver2);
		this.driver = (AndroidDriver) driver2;
		PageFactory.initElements(new AppiumFieldDecorator(driver2), this);
	}
	
	// Locators	
	@FindBy(xpath = "//div[@class='FYXSad']")
	public WebElement languageSelectionIcon;
	
	@FindBy(xpath= "(//li[contains(text(),'‪English (United Kingdom)‬')])[1]")
	private WebElement englishUK;
	
	@FindBy(xpath= "(//div[contains(text(),'Read more')])[1]")
	private WebElement readMoreButton;
	
	@FindBy(xpath= "//div[normalize-space()='Accept all']")
	private WebElement acceptAllButton;
	
	@FindBy(name = "q")
	private WebElement searchBox;
	
	
	// Actions	
	public void clickOnLanguageIcon() {
		languageSelectionIcon.click();
	}
	
	public void selectEnglishUKLanguage() {
		englishUK.click();
	}
	
	public void clickOnReadMoreButton() {
		readMoreButton.click();
	}
	
	public Boolean isReadMoreButtonVisible() {
		return readMoreButton.isDisplayed();
	}
	
	public void clickOnAcceptAllButton() {
		acceptAllButton.click();
	}
	
	public Boolean isAcceptAllButtonVisible() {
		return acceptAllButton.isDisplayed();
	}
	
	public void acceptGoogleTerms() {
		int maxAttempts = 5;
        int attempt = 0;
        while (attempt < maxAttempts) {
            try {
                // Check if the element is visible
                if (this.isReadMoreButtonVisible()) {
                	this.clickOnReadMoreButton();
                	attempt++;
                }else if (this.isAcceptAllButtonVisible()) {
                	this.clickOnAcceptAllButton();
                	break;
                }else {
                	break;
                }
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}
	
	public void searchText(String query) {
		searchBox.sendKeys(query);
		searchBox.sendKeys(Keys.ENTER);
		((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
	}
	
}
