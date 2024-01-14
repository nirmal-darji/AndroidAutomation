package Utils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.io.*;
import org.apache.commons.io.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class TestDataConversion {
	
	AppiumDriver driver;
	
	public TestDataConversion(AppiumDriver driver2) {
		this.driver = (AndroidDriver) driver2;
	}
	
	public List<HashMap<String,String>> getJsonData(String filePath) throws IOException {
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String,String>> data = mapper.readValue(jsonContent, 
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}
	
}
