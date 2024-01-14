package Ecommerce.pageObjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import Utils.MobileGestures;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class LaunchPage extends MobileGestures {
	
	AppiumDriver driver;
	public LaunchPage(AppiumDriver driver2) {
		super(driver2);
		this.driver = (AndroidDriver) driver2;
		PageFactory.initElements(new AppiumFieldDecorator(driver2), this);
	}
	
	// Locators
	@AndroidFindBy(id= "com.androidsample.generalstore:id/spinnerCountry")
	private WebElement countryDropdown;
	
	@AndroidFindBy(xpath= "//android.widget.EditText")
	private WebElement nameField;
	
	@AndroidFindBy(xpath= "//android.widget.RadioButton[@text='Female']")
	private WebElement femaleRadioButton;
	
	@AndroidFindBy(xpath= "//android.widget.RadioButton[@text='Male']")
	private WebElement maleRadioButton;
	
	@AndroidFindBy(id= "com.androidsample.generalstore:id/btnLetsShop")
	private WebElement letsShopButton;
	
	@AndroidFindBy(xpath= "(//android.widget.Toast)[1]")
	private WebElement errorToast;
		
	
	// Actions
	public void addUsername(String userName) {
		nameField.sendKeys(userName);
		((AndroidDriver)driver).hideKeyboard();
	}
	
	public void selectCountry(String countryName) {
		countryDropdown.click();
		scrollForTheText(countryName).click();
	}
	
	public void selectGender(String gender) {
		if(gender.equalsIgnoreCase("Female")) {
			femaleRadioButton.click();	
		}else {
			maleRadioButton.click();
		}
	}
	
	public void clickOnLetsShopButton(){
		letsShopButton.click();
	}
	
	public String getErrorToastMessage() {
		return errorToast.getAttribute("name");
	}	
}
