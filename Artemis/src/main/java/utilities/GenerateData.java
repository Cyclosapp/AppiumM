package utilities;

import java.util.Locale;

import com.github.javafaker.Faker;

public class GenerateData {
	
	Faker Generate = new Faker(new Locale("en-IND"));
	
	public String getData(String TestData) {
		String iData;
		if (TestData.trim().contains("mock:")) {
			String[] iTestData = TestData.trim().split(":");
			switch(iTestData[1].trim().toLowerCase()){
				case "email":
					iData = Generate.internet().emailAddress();
					break;
				case "name":
					iData = Generate.name().fullName();
					break;
				case "username":
					iData = Generate.name().username();
					break;
				case "mobile":
					iData = Generate.phoneNumber().cellPhone();
					break;
				case "landline":
					iData = Generate.phoneNumber().phoneNumber();
					break;
				case "url":
					iData = Generate.internet().url();
					break;
				default:
					System.out.println("Artemis WARNING: User hasn't specified the mock data catergory which is avaialble, please fix the test data or add the data catergory in the code.");
					iData = TestData;
			}
		}else {
			iData = TestData;
		}
		return iData;	
	}	
}
