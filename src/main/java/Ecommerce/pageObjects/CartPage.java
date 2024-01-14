package Ecommerce.pageObjects;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import Utils.MobileGestures;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class CartPage extends MobileGestures {
	
	AppiumDriver driver;
	public CartPage(AppiumDriver driver2) {
		super(driver2);
		this.driver = (AndroidDriver) driver2;
		PageFactory.initElements(new AppiumFieldDecorator(driver2), this);
	}
	
	// Locators
	@AndroidFindBy(id= "com.androidsample.generalstore:id/productPrice")
	private List<WebElement> productPrices;
	
	@AndroidFindBy(id= "com.androidsample.generalstore:id/totalAmountLbl")
	public WebElement totalPrice;
	
	@AndroidFindBy(id= "com.androidsample.generalstore:id/termsButton")
	private WebElement termsLink;
	
	@AndroidFindBy(id= "com.androidsample.generalstore:id/alertTitle")
	private WebElement alertTitle;
	
	@AndroidFindBy(id= "android:id/button1")
	private WebElement closeButton;
	
	@AndroidFindBy(xpath = "//android.widget.CheckBox")
	private WebElement agreementCheckbox;
	
	@AndroidFindBy(id= "com.androidsample.generalstore:id/btnProceed")
	private WebElement proceedButton;
	
	// Actions
	public int getShoesCount() {
		return productPrices.size();
	}
	

	public String getShoesPrice(int index) {
		return productPrices.get(index).getText();
	}
	
	public Double getTotalPrice() {
		return Double.parseDouble((totalPrice.getText()).substring(2));
	}
	
	public Double getActualTotalPrice() {
		int shoesCount = this.getShoesCount();
		double actualTotalSum =0;
		for(int i=0;i<shoesCount;i++) {
			String amountString = this.getShoesPrice(i);
			Double productPrice = Double.parseDouble(amountString.substring(1));
			actualTotalSum = actualTotalSum + productPrice;
		}
		return actualTotalSum;
	}
	
	public void longPressOnTermsLink() {
		longPressOnElement(termsLink);
	}
	
	public String getAlertTitle() {
		return alertTitle.getText();
	}
	
	public void clickOnCloseButton() {
		closeButton.click();
	}
	
	public void clickCheckBox() {
		agreementCheckbox.click();
	}
	
	public void clickOnProceedButton() {
		proceedButton.click();
	}
	
}
