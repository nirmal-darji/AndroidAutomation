package ECommerce.pageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class BaseSetup {
	AppiumDriver driver;
	AppiumDriverLocalService service;
	
	@BeforeClass (alwaysRun = true)
	public void AppiumConfiguration() throws IOException {
		
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\Utils\\GlobalProperties.properties");
		prop.load(fis);
		String ipAddress = prop.getProperty("ipAddress");
		String port = prop.getProperty("port");
		// Take device name from maven command and if its not provided then take it from prop file
		String deviceName = System.getProperty("deviceName")!=null ? System.getProperty("deviceName") : prop.getProperty("deviceName");
		System.out.println("deviceName: "+deviceName);
		
		// Start Appium Server programmatically
		service = new AppiumServiceBuilder()
				.withAppiumJS(new File("C:\\Users\\NirmalDarji\\AppData\\Roaming\\npm\\node_modules\\appium\\build\\lib\\main.js"))
				.withIPAddress(ipAddress)
				.usingPort(Integer.parseInt(port))
				.build();
		service.start();
		
		// Set Device Capabilities
		UiAutomator2Options options = new UiAutomator2Options();
		options.setChromedriverExecutable(System.getProperty("user.dir")+"\\src\\test\\java\\resources\\chromedriver.exe");
		options.setDeviceName(deviceName);
		options.setApp(System.getProperty("user.dir")+"\\src\\test\\java\\resources\\General-Store.apk");	
	
		// Launch Driver with local host & device capability
		driver = new AndroidDriver(service.getUrl(), options);
		
		// Set Timeout for element visibility
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}
	
	@AfterClass (alwaysRun = true)
	public void tearDown() {
		driver.quit();
		service.stop();
	}
}