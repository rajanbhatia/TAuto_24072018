import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class ZInputText {				//Enter text in text boxes
	
	public void textboxXpathEnterText(String xpath, String text, WebDriver driver)   
	{
		driver.findElement(By.xpath(xpath)).clear();
		driver.findElement(By.xpath(xpath)).sendKeys(text);     // enter text xpath
	}
	public void textboxIdEnterText(String id, String text, WebDriver driver)   
	{
		driver.findElement(By.id(id)).clear();
		driver.findElement(By.id(id)).sendKeys(text);     // enter text id
	}
	public void textboxCssSelectorEnterText(String css, String text, WebDriver driver)   
	{
		driver.findElement(By.cssSelector(css)).clear();
		driver.findElement(By.cssSelector(css)).sendKeys(text);    // enter text css
	}
	public void textboxNameEnterText(String name, String text, WebDriver driver)   
	{
		driver.findElement(By.name(name)).clear();
		driver.findElement(By.name(name)).sendKeys(text);     // enter text name
	}

}
