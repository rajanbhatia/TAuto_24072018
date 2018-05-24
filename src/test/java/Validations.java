import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class Validations 		//Class to test all the assertEqual validations, including getAttribute("values")
{   
	// e.g. captions
		public void validateCaptionByXpath(WebElement xpath, String validationtext, WebDriver driver)    // xpath validation
		{
			assertEquals(xpath.getText(),validationtext);
		}
		public void validateCaptionById(WebElement id, String validationtext,WebDriver driver)   //id validation
		{
			assertEquals(id.getText(),validationtext);
		}
		public void validateCaptionByName(WebElement name, String validationtext,WebDriver driver)   //name validation
		{
			assertEquals(name.getText(),validationtext);
		}
		public void validateCaptionByCssSelector(WebElement css, String validationtext,WebDriver driver)   //css validation
		{
			assertEquals(css.getText(),validationtext);
		}
		
		//Attribute Values (e.g. textboxes)
		public void validateTextboxValue(WebElement element, String validationtext,WebDriver driver)    // xpath validation
		{
			assertEquals(element.getAttribute("value"),validationtext);
		}
	/**	public void validateTextboxValueById(WebElement id, String validationtext,WebDriver driver)   //id validation
		{
			//assertEquals(driver.findElement(By.id(id)).getAttribute("value"),validationtext);
			assertEquals(id.getAttribute("value"),validationtext);
		}
		public void validateTextboxValueByName(WebElement name, String validationtext,WebDriver driver)   //name validation
		{
			assertEquals(name.getAttribute("value"),validationtext);
		}
		public void validateTextboxValueByCssSelector(WebElement css, String validationtext,WebDriver driver)   //css validation
		{
			assertEquals(css.getAttribute("value"),validationtext);		
		}
		**/
		//Dropdown
		public void validateDropdownValueByXpath(WebElement xpath, String validationtext,WebDriver driver)    // xpath validation
		{
			 assertEquals(new Select(xpath).getFirstSelectedOption().getText(), validationtext);	  // To get the selected dropdown value		
		}
		public void validateDropdownValueById(WebElement id, String validationtext,WebDriver driver)   //id validation
		{
			assertEquals(new Select(id).getFirstSelectedOption().getText(), validationtext);	  // To get the selected dropdown value
		}
		public void validateDropdownValueByName(WebElement name, String validationtext,WebDriver driver)   //name validation
		{
			assertEquals(new Select(name).getFirstSelectedOption().getText(), validationtext);	  // To get the selected dropdown value
		}
		public void validateDropdownValueByCssSelector(WebElement css, String validationtext,WebDriver driver)   //css validation
		{
			assertEquals(new Select(css).getFirstSelectedOption().getText(), validationtext);	  // To get the selected dropdown value
		}
	
	
	
	

}
