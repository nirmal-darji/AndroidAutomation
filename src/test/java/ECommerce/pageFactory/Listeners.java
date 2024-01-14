package ECommerce.pageFactory;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.appium.java_client.AppiumDriver;

public class Listeners extends BaseSetup implements ITestListener {

	ExtentTest test;
	ExtentReports extent = ExtentReporterNG.getReporterObject();
	
    public void onTestStart(ITestResult result) {					
       test = extent.createTest(result.getMethod().getMethodName()); 				
    }	
    	
    public void onTestSuccess(ITestResult result) {					
        test.log(Status.PASS, "Test is successful!");				
    }
    
    public void onTestFailure(ITestResult result) {					
    	System.out.println("***** Error "+result.getName()+" test has failed *****");
    	test.log(Status.FAIL, result.getThrowable());
    	String methodName= result.getName().toString().trim();
    	Object currentClass = result.getInstance();
    	AppiumDriver driver = ((Basics) currentClass).driver;
    	try {
    		String destinationFile = takeScreenShot(methodName, driver);
        	test.addScreenCaptureFromPath(destinationFile); 
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public String takeScreenShot(String methodName, AppiumDriver driver) throws IOException {
   	 	File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
   	 	String destinationFile = System.getProperty("user.dir")+"\\reports\\screenshots\\"+methodName+".png";
        //The below method will save the screenshot with test method name 
        try {
			FileUtils.copyFile(sourceFile, new File(destinationFile));
			System.out.println("***Placed screenshot in "+destinationFile+" ***");
		} catch (IOException e) {
				e.printStackTrace();
		}
		return destinationFile;
   }
    
    public void onFinish(ITestContext arg0) {					
    	extent.flush();			        		
    }	
}
