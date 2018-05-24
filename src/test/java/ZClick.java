import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ZClick {						//Class to click on the objects (buttons, radio, checkboxes,...)

	
	protected void click(WebDriver driver ,WebElement element) { 
        try { 
            if (element.isDisplayed() && element.isEnabled()) { 
                element.click(); 
            } 
            else 
            { 
            	Thread.sleep(2); 
            	(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(element)); 
                element.click(); 
            } 
        } catch (NoSuchElementException f) { 
  //          logger.error("\nElement not visible in screen : " + f.getMessage()); 
            throw new ElementNotVisibleException("Element not displayed in webpage."); 
        } catch (WebDriverException e) { 
            try { 
            	Thread.sleep(2); 
                element.click(); 
                 
            } catch (Exception d) { 
     //           logger.error("click(element) failed with error : " + d.getMessage()); 
            } 
        } catch (Exception g) { 
   //         logger.error("click(element) failed with error : " + g.getMessage()); 
        }
     //   return 
	}
	
	public void clickIdObject(String id,WebDriver driver)   
	{
		//((JavascriptExecutor)driver).executeScript("arguments[0].cli‌​ck()", driver.findElement(By.id(id)));
		driver.findElement(By.id(id)).click();     // click id
		//driver.findElement(By.id(id)).sendKeys(Keys.ENTER);     // click id
	}
	public void clickCssSelectorObject(String css,WebDriver driver)   
	{
		driver.findElement(By.id(css)).click();     // click css
	}
	public void clickNameObject(String name,WebDriver driver)   
	{
		driver.findElement(By.id(name)).click();     // click name
	}

}
