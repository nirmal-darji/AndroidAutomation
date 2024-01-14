package Ecommerce.pageObjects;

import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import Utils.MobileGestures;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProductsPage extends MobileGestures {
	
	AppiumDriver driver;
	public ProductsPage(AppiumDriver driver2) {
		super(driver2);
		this.driver = (AndroidDriver) driver2;
		PageFactory.initElements(new AppiumFieldDecorator(driver2), this);
	}
	
	// Locators
	@AndroidFindBy(id= "com.androidsample.generalstore:id/productName")
	private List<WebElement> productName;
	
	@AndroidFindBy(id= "com.androidsample.generalstore:id/productName")
	public WebElement productNameOnCartPage;
	
	@AndroidFindBy(id= "com.androidsample.generalstore:id/productAddCart")
	private List<WebElement> addToCartButtons;
	
	@AndroidFindBy(id= "com.androidsample.generalstore:id/appbar_btn_cart")
	private WebElement cartButton;
	
	@AndroidFindBy(id= "com.androidsample.generalstore:id/btnLetsShop")
	private WebElement letsShopButton;
	
	
	// Actions
	public int getShoesCount() {
		return productName.size();
	}
	
	public void addVisibleProductsToTheCart() {
		int productCount = this.getShoesCount();
		for (int i=0;i<productCount;i++) {
			this.clickAddToCartButton(i);
		}
	}
	
	public void addRequiredProductToTheCart(String expectedProductName) {
		int productCount = this.getShoesCount();
		// Select a product from the list of similar kind of elements
		for (int i=0;i<productCount;i++) {
			String productName = this.getShoesName(i);
			if(productName.equalsIgnoreCase(expectedProductName)) {
				this.clickAddToCartButton(i);
				break;
			}
		}
	}
	
	public String getShoesName(int index) {
		return productName.get(index).getText();
	}
	
	public void clickAddToCartButton(int index) {
		addToCartButtons.get(index).click();
	}
	
	public void clickOnCartIcon() {
		cartButton.click();
	}
	
	public String getShoesNameOnCartPage() {
		return productNameOnCartPage.getText();
	}
	
	
}
