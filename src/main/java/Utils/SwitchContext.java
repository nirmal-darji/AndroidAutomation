package Utils;

import java.util.Set;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class SwitchContext {
	
	AppiumDriver driver;
	
	public SwitchContext(AppiumDriver driver2) {
		this.driver = (AndroidDriver) driver2;
	}
	
	public void getContexts() {
		Set<String> contexts = ((AndroidDriver) driver).getContextHandles();
		for (String contextName : contexts) {
			System.out.println(contextName);
		}
	}
	
	public void switchContextToMWeb() {
		((AndroidDriver) driver).context("WEBVIEW_com.androidsample.generalstore");
	}
	
	public void switchContextToNativeApp() {
		((AndroidDriver) driver).context("NATIVE_APP");
	}
}
