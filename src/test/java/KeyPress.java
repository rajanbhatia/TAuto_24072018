
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class KeyPress  // Class to press keyboard keys at different objects
{
	
/**	public void keyXpath(WebElement xpath, String key, WebDriver driver)
	{
		switch (key)
		{
		case "Enter": 	xpath.sendKeys(Keys.ENTER); //String Format should be like "Keys.RETURN"	 
		break;
		case "Return": 	xpath.sendKeys(Keys.RETURN); //String Format should be like "Keys.RETURN"	 
		break;
		case "Tab": 	xpath.sendKeys(Keys.TAB); //String Format should be like "Keys.RETURN"	 
		break;
		case "Escape": 	xpath.sendKeys(Keys.ESCAPE); //String Format should be like "Keys.RETURN"	 
		break;
		}
	 
	}**/

	public void keyId(WebElement id, String key, WebDriver driver)
	{
		switch (key.toUpperCase())
		{
		case "ENTER":   id.sendKeys(Keys.ENTER); //String Format should be like "Keys.RETURN"
	    //selenium.keyPressNative("10"); // Enter //	 
		break;
		case "RETURN": 	id.sendKeys(Keys.RETURN); //String Format should be like "Keys.RETURN"	 
		break;
		case "TAB": 	id.sendKeys(Keys.TAB); //String Format should be like "Keys.RETURN"	 
		break;	
		case "ESCAPE": 	id.sendKeys(Keys.ESCAPE); //String Format should be like "Keys.RETURN"	 
		break;
		case "DOWNARROW": 	id.sendKeys(Keys.ARROW_DOWN); //String Format should be like "Keys.RETURN"	 
		break;
		case "UPARROW": 	id.sendKeys(Keys.ARROW_UP); //String Format should be like "Keys.RETURN"	 
		break;
		case "LEFTARROW": 	id.sendKeys(Keys.ARROW_LEFT); //String Format should be like "Keys.RETURN"	 
		break;
		case "RIGHTARROW": 	id.sendKeys(Keys.ARROW_RIGHT); //String Format should be like "Keys.RETURN"	 
		break;
		}	 	 
	}
	/**
	public void keyName(WebElement name, String key, WebDriver driver)
	{	
		switch (key)
		{
		case "Enter": 	name.sendKeys(Keys.ENTER); //String Format should be like "Keys.RETURN"	 
		break;
		case "Return": 	name.sendKeys(Keys.RETURN); //String Format should be like "Keys.RETURN"	 
		break;
		case "Tab": 	name.sendKeys(Keys.TAB); //String Format should be like "Keys.RETURN"	 
		break;	
		case "Escape": 	name.sendKeys(Keys.ESCAPE); //String Format should be like "Keys.RETURN"	 
		break;
		}	 	 
	}
	
	public void keyCssSelector(WebElement css, String key, WebDriver driver)
	{	
		switch (key)
		{
		case "Enter": 	css.sendKeys(Keys.ENTER); //String Format should be like "Keys.RETURN"	 
		break;
		case "Return": 	css.sendKeys(Keys.RETURN); //String Format should be like "Keys.RETURN"	 
		break;
		case "Tab": 	css.sendKeys(Keys.TAB); //String Format should be like "Keys.RETURN"	 
		break;	
		case "Escape": 	css.sendKeys(Keys.ESCAPE); //String Format should be like "Keys.RETURN"	 
		break;
		}	 
		
	}**/
	
 
	
	
	
	
}
