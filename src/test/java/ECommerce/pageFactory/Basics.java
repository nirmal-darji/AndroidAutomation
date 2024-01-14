package ECommerce.pageFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import Ecommerce.pageObjects.CartPage;
import Ecommerce.pageObjects.LaunchPage;
import Ecommerce.pageObjects.ProductsPage;
import Ecommerce.pageObjects.WebviewPage;
import Utils.ImplicitWaits;
import Utils.MobileGestures;
import Utils.SwitchContext;
import Utils.TestDataConversion;

public class Basics extends BaseSetup {
	private LaunchPage launchPage;
	private MobileGestures gestures;
	private ProductsPage productsPage;
	private ImplicitWaits implicitWait;
	private CartPage cartPage;
	private WebviewPage webviewPage;
	private SwitchContext switchContext;
	private TestDataConversion testDataConversion;

	// Instantiate pages with the driver before all test methods
	@BeforeClass (alwaysRun = true)
    public void instantiatePages() { 
        launchPage = new LaunchPage(driver); 
        gestures = new MobileGestures(driver);
        productsPage = new ProductsPage(driver);
        implicitWait = new ImplicitWaits(driver);
        cartPage = new CartPage(driver);
        webviewPage = new WebviewPage(driver);
        switchContext = new SwitchContext(driver);
        testDataConversion = new TestDataConversion(driver);
    }
	
	// Opens the App at Launch Page before executing each method of test 
	@BeforeMethod (alwaysRun = true)
	public void bringAppToLaunchScreen() throws InterruptedException {
		String packageName = "com.androidsample.generalstore";
		String activityName = "com.androidsample.generalstore.MainActivity";
		gestures.startActivity(packageName,activityName);
	}
	
	// Parameterization by sending Array Objects directly
	@DataProvider
	public Object[][] getTestData(){
		return new Object[][] {{"N Darji", "Male", "India"}, {"R Darji", "Female", "Germany"}};
	}

	// Parameterization by sending JSON file which contains Array Objects
	@DataProvider
	public Object[][] getTestDataFromJSONFile() throws IOException{
		String jsonFilePath = System.getProperty("user.dir")+"\\src\\test\\java\\TestData\\formTestData.json";
		List<HashMap<String, String>> jsonData = testDataConversion.getJsonData(jsonFilePath);
		return new Object[][] {{jsonData.get(0)},{jsonData.get(1)}};
	}
	
	@Test(dataProvider = "getTestData", groups = {"Smoke"})
	public void FillForm(String name, String gender, String country) throws InterruptedException {
		launchPage.addUsername(name);
		launchPage.selectCountry(country);
		launchPage.selectGender(gender);
		launchPage.clickOnLetsShopButton();
	}
	
	@Parameters({"URL"})
	@Test(groups = {"Smoke", "Tag1"})
	public void CheckErrorToastMessage(String testURL) throws InterruptedException {
		Thread.sleep(5000);
		System.out.println(testURL);     // Value coming from Parameters.xml testNG file
		launchPage.clickOnLetsShopButton();		
		String actualToastMessage = launchPage.getErrorToastMessage();
		String extpectedToastMessage = "Please enter your name";
		Assert.assertEquals(actualToastMessage, extpectedToastMessage);
	}
	
	@Test(dataProvider = "getTestDataFromJSONFile")
	public void SelectShoes(HashMap<String, String> formData) throws InterruptedException {	
		this.FillForm(formData.get("name"),formData.get("gender"),formData.get("country"));
		
		String expectedProductName = "Jordan 6 Rings";
		gestures.scrollForTheText(expectedProductName).click();
		
		productsPage.addRequiredProductToTheCart(expectedProductName);
		productsPage.clickOnCartIcon();
		implicitWait.waitUntilCartPageIsLoaded();
		
		String actualProductName = productsPage.getShoesNameOnCartPage();
		Assert.assertEquals(actualProductName, expectedProductName);
	}

	@Test(dataProvider = "getTestData")
	public void VerifyTotalOnCartPage(String name, String gender, String country) throws InterruptedException {		
		this.FillForm(name,gender,country);
	
		productsPage.addVisibleProductsToTheCart();
		productsPage.clickOnCartIcon();
		implicitWait.waitUntilCartPageIsLoaded();
		
		// On Cart Page verify total
		double actualTotalSum = cartPage.getActualTotalPrice();
		double expectedTotalSum = cartPage.getTotalPrice();
		Assert.assertEquals(actualTotalSum, expectedTotalSum);
	}	
	
	@Test(dataProvider = "getTestData")
	public void VerifyTermsOnCartPage(String name, String gender, String country) throws InterruptedException {	
		this.VerifyTotalOnCartPage(name,gender,country);
		
		cartPage.longPressOnTermsLink();
		String actualTermsAlertTitle = cartPage.getAlertTitle();
		Assert.assertEquals(actualTermsAlertTitle, "Terms Of Conditions");
		cartPage.clickOnCloseButton();
	}

	@Test(dataProvider = "getTestData")
	public void CheckHybridWebView(String name, String gender, String country) throws InterruptedException {	
		this.VerifyTotalOnCartPage(name,gender,country);
		
		// Proceed to WebView
		cartPage.clickCheckBox();
		cartPage.clickOnProceedButton();
		Thread.sleep(5000);
		
		// Hybrid App- WebView
		switchContext.getContexts();
		
		// Switched context to Webview
		switchContext.switchContextToMWeb();
		
		// Handle Google Terms
		webviewPage.clickOnLanguageIcon();
		webviewPage.selectEnglishUKLanguage();
		webviewPage.acceptGoogleTerms();
		webviewPage.searchText("Virat Kohli");

		// Switched context back to Native App
		switchContext.switchContextToNativeApp();
	}
}
