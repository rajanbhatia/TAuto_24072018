
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import io.github.bonigarcia.wdm.FirefoxDriverManager;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.IAnnotationTransformer;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.LogStatus;

public class Master extends BaseClass implements IAnnotationTransformer
{
	Boolean allRowBlank;
	Set<String> beforepopup;
	String winhandlebefore;
	String locatorType, locatorValue;
	WebDriverWait wait;
	WebElement element; // elementID, elementName, elementCssSelector; 
	int counter =0; //to count if 3 objects are not found together in 3 steps than change the Explicit wait time period for that specific test case
	//ExcelDataConfig excelreadpreferences;  //Declared in the Base class
@Test(dataProvider = "TestSteps")//, threadPoolSize=2)		//, invocationCount=invocationcount) //invocationCount set at run time
public void main(String tcid, String tc_desc, String stepid, String step_desc, String page, String object, String testdata, String executeFlag,String a2, String a3,String a4, String a5,String a6, String a7,String a8, String a9, String locatortype, String locatorvalue) //, String result, String error)
{	
	try
	{			
		//logger = ReportScreenshotUtility.report.startTest("Automation Run: Testcase- "+tcid+", Teststep- "+stepid);  //To log every step on the left panel
		exceptionerror=false;	   //ExceptionError flag to capture errors and log to the logger report   
		allRowBlank=false;  //To check the entire row blank
		stepdescription=step_desc;
		objectName = object;
		this.stepid=stepid;
		locatorType = locatortype;
		locatorValue = locatorvalue;
		//this.command=command;
		
		if(executeFlag.equalsIgnoreCase("N")) {	stepdescription="Not Executing";		}   //Do not execute this test case step
		//Auto parameterise test data every time for the textbox enter commands only.
		if (((multipleExecutionsDifferentTestData) && (stepdescription.equalsIgnoreCase("Enter Text"))) || (stepdescription.equalsIgnoreCase("Enter Text (auto parameterise)")))
		{
				Calendar cal = Calendar.getInstance();
				testdata=mData(testdata, cal);
		}
		
		//Check explicitly for every Web Element presence
		if (!locatorType.trim().equals("") && !locatorType.equals(null) && !stepdescription.equalsIgnoreCase("Not Executing") && !stepdescription.equalsIgnoreCase("Validate Object Absence") && !stepdescription.equalsIgnoreCase("Click on Hyperlink") && !stepdescription.equalsIgnoreCase("Click"))  //Adding Click for Microsoft Dynamics CRM only (PAOL) 
		{
			//driver.switchTo().frame("contentIFrame0");
			
			findElement();		/////No need to call first when it's being called before the main operation in all the cases. But now Element Check is not happening in the beginning so do it before counter=0; otherwise it will always be 0	
			counter=0;	//reset to 0, if object is found
			wait = new WebDriverWait(driver, 15); // Reset to 10 seconds explicit wait, if got changed during 3 NotVisible exceptions
			
		}		    
		
		//System.out.println(tcid + " " + tc_desc + " " + stepid + " " + step_desc + " " + command  + " " + locatortype  + " " + locatorvalue + " " + testdata + " " + "\n");
		switch (stepdescription.toUpperCase())
		{
			//case "Browser: Open (specify link under Test Data column)": 
		case "OPEN BROWSER":
			{
				if (logger != null)	  ReportScreenshotUtility.report.endTest(logger);  // close it for second run
				logger = ReportScreenshotUtility.report.startTest("Automation Run: Testcase- "+tcid+" ("+tc_desc+")"); //To log every testcase on the left panel and teststeps on the right.
				//browserSettings(driver, testdata);
				switch(browsername.toUpperCase())
				{
					case "CHROME":			
							System.setProperty("webdriver.chrome.driver", "C:/QAT/drivers/chromedriver.exe");
							driver = new ChromeDriver();							
							break;						
					case "FIREFOX":
							//FirefoxDriverManager.getInstance().setup();
						FirefoxOptions optionsF = new FirefoxOptions();
						optionsF.addPreference("browser.link.open_newwindow.restriction", 0);  //To avoid opening a new window. After Implementation, it is still opening but working with the SaveAttributes command (window driver handles)
						/**
						 * B) browser.link.open_newwindow.restriction - for links in Firefox tabs

									0 = apply the setting under (A (open_newwindow) to ALL new windows (even script windows) <= Try this one
									2 = apply the setting under (A) to normal windows, but NOT to script windows with features (default)
									1 = override the setting under (A) and always use new windows**/
						 
						optionsF.addPreference("browser.link.open_newwindow", 1);
						
						/**(A) browser.link.open_newwindow - for links in Firefox tabs

						3 = divert new window to a new tab (default) <= This should already be set; if it's not, right-click > Reset to restore the default
						2 = allow link to open a new window
						1 = force new window into same tab **/
						
						System.setProperty("webdriver.firefox.bin", "C:\\Users\\bh6877\\AppData\\Local\\Mozilla Firefox\\firefox.exe");
						System.setProperty("webdriver.gecko.driver", "C:/QAT/drivers/geckodriver.exe");  //gecko is required for Selenium 3
						
						
						//System.setProperty("webdriver.gecko.driver", InvokeMaster.sheetDirPath+"/drivers/geckodriver.exe");  //gecko is required for Selenium 3
							//System.setProperty("webdriver.chrome.driver", "C:\\firefoxdriver.exe");
						driver = new FirefoxDriver();
							
					//	FirefoxOptions optionsF = new FirefoxOptions();
					//	optionsF.setCapability("platform", Platform.ANY);
					//	optionsF.setCapability("binary", "C:\\Users\\bh6877\\AppData\\Local\\Mozilla Firefox\\firefox.exe"); //for windows       
							//DesiredCapabilities capability = DesiredCapabilities.firefox();
							//capability.setCapability("platform", Platform.ANY);
							//capability.setCapability("binary", "/ms/dist/fsf/PROJ/firefox/16.0.0/bin/firefox"); //for linux
							//capability.setCapability("binary", "C:\\Users\\bh6877\\AppData\\Local\\Mozilla Firefox\\firefox.exe"); //for windows                
					//		driver = new FirefoxDriver(optionsF); // new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
							
							
							break;						
					case "INTERNET EXPLORER":	// 64-bit types slowly in the textfields so use 32-bit					
							/** DEPRECATED DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer(); // To speed up IE. If not use 32 bit driver
							capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
							capabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
							capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
							//capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
							capabilities.setCapability("IntroduceInstabilityByIgnoringProtectedModeSettings",true);**/
							
							InternetExplorerOptions options = new InternetExplorerOptions();
							//options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
							options.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, true);
							options.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
							options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
							options.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, "accept");
							//options.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
							options.setCapability("javascriptEnabled", true);
							options.setCapability("disable-popup-blocking", true);
							
							//options.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
							options.setCapability("IgnoringProtectedModeSettings",true);
							//System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"/drivers/IEDriverServer.exe");
							System.setProperty("webdriver.ie.driver", "C:/QAT/drivers/IEDriverServer.exe");
							
							driver = new InternetExplorerDriver(options);
							
							break;						
					case "SAFARI": //Deprecated for Windows, only in MAC now						
						/**	System.setProperty("webdriver.safari.driver", "C:\\safaridriver.exe");  //gecko is required for Selenium 3
							driver = new SafariDriver();				
						
							break;**/
					case "CHROME (NON-GUI)":
							//*** Headless ***//
							System.setProperty("webdriver.chrome.driver", "C:/QAT/drivers/chromedriver.exe");
							ChromeOptions chromeOptions = new ChromeOptions();
					        chromeOptions.addArguments("--headless");
					        chromeOptions.addArguments("--disable-gpu");  //disable GPU accelerator abd it doesn't work properly in headless mode           
					        driver = new ChromeDriver(chromeOptions);
							//HTMLUnitDriver
							//driver = new HtmlUnitDriver();				
							//((HtmlUnitDriver)driver).setJavascriptEnabled(true);
					        break;				        
					case "FIREFOX (NON-GUI)":
					        /** Headless **/
							FirefoxDriverManager.getInstance().setup();	
							FirefoxBinary firefoxBinary = new FirefoxBinary();
							firefoxBinary.addCommandLineOptions("--headless");
							//System.setProperty("webdriver.gecko.driver", "C:\\geckodriver.exe");  //gecko is required for Selenium 3
							FirefoxOptions firefoxOptions = new FirefoxOptions();
							firefoxOptions.setBinary(firefoxBinary);
							FirefoxDriver fdriver = new FirefoxDriver(firefoxOptions);
							this.driver=fdriver;				        
							break;
					case "MICROSOFT EDGE":
							System.setProperty("webdriver.edge.driver", "C:/QAT/drivers/MicrosoftWebDriver.exe");  //Get the OS Build number from the Computer (Settings>System>About) and then download the corresponding driver exe
							//System.setProperty("webdriver.edge.driver", "C:\\Program Files (x86)\\Microsoft Web Driver\\MicrosoftWebDriver.exe");
							//DesiredCapabilities capabilities = DesiredCapabilities.edge();
							//driver = new EdgeDriver(capabilities);
							//EdgeDriverManager.getInstance().setup(); if using Maven dependency but it doesn't pick the compatible driver.
							driver = new EdgeDriver();
							break;							
					default:						
							//logger.log(LogStatus.INFO,"Invalid Browser specified."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid Browser type specified.";
							exceptionerror=true;						
				}
				if (testdata.equals("") || testdata.equals(null)) //validate that test data is valid
				{
					errormessage="Invalid URL";
					exceptionerror=true;   
				}
				else
				{
					f.setVisible(false);
					f.dispose();
					if (testdata.startsWith("https://") || testdata.startsWith("http://"))		driver.get(testdata);	
					else								 										driver.get("https://"+testdata); //http link code not added now
					driver.manage().window().maximize();
				//	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // No need for Implicit
					wait = new WebDriverWait(driver, 15); // 20 seconds explicit wait
				}
				break;
			}
					
			//case "Textbox: Enter Text (locator value, test data)": 
		case "ENTER TEXT":
		case "ENTER TEXT (AUTO PARAMETERISE)":
			{	
							
				checkLocParamBlankValues(locatorValue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					try{
						
							findElement();
							element.click();
							element.clear();
							element.sendKeys(testdata);     // enter text
							break;
					}catch (StaleElementReferenceException se)
					{
						 try {	
							 	Thread.sleep(2000);
							 	findElement();
								element.click();
								element.clear();
								element.sendKeys(testdata);     // enter text
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								errormessage = "Error: " + e.getMessage(); 
						        exceptionerror = true;
							}	
							
					}
					
				
				}
				break;
			}
			
		
			
		case "ENTER TEXT (TEXTAREA)":
		{	
			checkLocParamBlankValues(locatorValue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
			if (!exceptionerror)  //Execute it only if the values are valid
			{
				try {	
						findElement();
						Actions actions = new Actions(driver);
						actions.moveToElement(element);
				  		actions.click();
				  		actions.sendKeys(testdata);
				  		Thread.sleep(1000);		//The Actions do not get executed on the page, so adding this Sleep command
				  		actions.build().perform();
				  		
				} catch (StaleElementReferenceException se)
					{
						 try {									
								Actions actions = new Actions(driver);
								findElement();
								actions.moveToElement(element);
						  		actions.click();
						  		actions.sendKeys(testdata);
						  		Thread.sleep(2000);
						  		actions.build().perform();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								errormessage = "Error: " + e.getMessage(); 
						        exceptionerror = true;
							}	
							
					}
			}
			break;
		}
					
			
		case "VALIDATE TEXT IN TEXTBOX": 
		case "VALIDATE TEXT IN TEXTAREA": 
		case "VALIDATE TEXT (CAPTION)": 
		{
				checkLocParamBlankValues(locatorValue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{	
					findElement();
					assertEquals(element.getText(),testdata);				
					
					//assertEquals(element.getAttribute("value"),testdata);
					//validation.validateTextboxValue(element, testdata, driver);
					
		/**			switch (locatortype)
					{
						//case "ID": validation.validateTextboxValueById(locatorvalue, testdata, driver);
						case "ID": validation.validateTextboxValueById(elementId, testdata, driver);
						break;
						case "Xpath": validation.validateTextboxValueByXpath(element, testdata, driver);
						break;
						case "Name": validation.validateTextboxValueByName(elementName, testdata, driver);
						break;
						case "CssSelector": validation.validateTextboxValueByCssSelector(elementCssSelector, testdata, driver);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the textbox to validate text."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the textbox to validate text.";
							exceptionerror=true;						
					} **/					
				}
				break;
		}
			
		case "VALIDATE SUBSET OF TEXT": 
		{
				checkLocParamBlankValues(locatorValue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{	
					findElement();
		
					assertTrue(element.getText().contains(testdata));  // True is the sequence of characters is same 				
					
				}
				break;
		}
			
			/**case "Validate Text (caption)": //COVERED ABOVE
			{
				checkLocParamBlankValues(locatorValue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror) //Execute it only if the values are valid
				{
					findElement();
					assertEquals(element.getText(),testdata);
					//validation.validateCaption(element, testdata, driver);**/
				/**	switch (locatortype)
					{
						case "ID": validation.validateCaptionById(elementId, testdata, driver);
						break;
						case "Xpath": validation.validateCaptionByXpath(element, testdata, driver);
						break;
						case "Name": validation.validateCaptionByName(elementName, testdata, driver);
						break;
						case "CssSelector": validation.validateCaptionByCssSelector(elementCssSelector, testdata, driver);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the caption/text to validate."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the caption/text to validate.";
							exceptionerror=true;   
					}**/					
			//	}
		//		break;
		//	}**/
			case "SELECT TEXT FROM DROPDOWN": 
			{
				checkLocParamBlankValues(locatorValue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror) //Execute it only if the values are valid
				{
					//POAL Specific Block Begins/////////////
						if (object.equals("Org Unit dropdown*"))
						{
							selectDropDownListPOAL(locatorValue, testdata, "/html/body/ul[1]");
						}
						else if (object.equals("Entity dropdown*"))
						{
							selectDropDownListPOAL(locatorValue, testdata, "/html/body/ul[2]");
						}
						else if (object.equals("Vessel dropdown*"))
						{
							selectDropDownListPOAL(locatorValue, testdata, "/html/body/ul[3]");
						}	
						else if (object.equals("Who was Involved dropdown*"))
						{
							selectDropDownListPOAL(locatorValue, testdata, "/html/body/ul[1]");
						}	
					//POAL Specific Block Ends/////////////
						else
						{
							switch (locatorType.toUpperCase())
							{
								case "ID": new Select(driver.findElement(By.id(locatorValue))).selectByVisibleText(testdata);//selectdropdown.selectDropdownValueById(locatorValue, testdata, driver);
								break;
								case "XPATH":	new Select(driver.findElement(By.xpath(locatorValue))).selectByVisibleText(testdata);	
								break;
								case "NAME":  new Select(driver.findElement(By.name(locatorValue))).selectByVisibleText(testdata);
								break;
								case "CSSSELECTOR":  new Select(driver.findElement(By.cssSelector(locatorValue))).selectByVisibleText(testdata);
								break;
								default:
									//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the dropdown to select value."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
									errormessage="Invalid or No Locator type specified for the dropdown to select value.";
									exceptionerror=true;   
								
							}
						}
				}
				break;
			}
			
			case "VALIDATE SELECTED TEXT IN DROPDOWN": 
			{
				checkLocParamBlankValues(locatorValue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					findElement();
					assertEquals(new Select(element).getFirstSelectedOption().getText(), testdata);	  // To get the selected dropdown value		
					//validation.validateDropdownValue(element, testdata, driver);
					/**
					switch (locatortype)
					{
						case "ID": validation.validateDropdownValueById(elementId, testdata, driver);
						break;
						case "Xpath": validation.validateDropdownValueByXpath(element, testdata, driver);
						break;
						case "Name": validation.validateDropdownValueByName(elementName, testdata, driver);
						break;
						case "CssSelector": validation.validateDropdownValueByCssSelector(elementCssSelector, testdata, driver);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified dropdown to validate value."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified dropdown to validate value.";
							exceptionerror=true;   
						
					}					**/
				}
				break;
			}
			case "VALIDATE TEXT OF BUTTON": 
			{
				checkLocParamBlankValues(locatorValue, testdata);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					findElement();
					assertEquals(element.getAttribute("value"),testdata);
					//validation.validateTextboxValueById(element, testdata, driver);  //use the textbox attribute code for button also
					
					/** switch (locatortype)
					{
						case "ID": validation.validateTextboxValueById(elementId, testdata, driver);  //use the textbox attribute code for button also
						break;
						case "Xpath": validation.validateTextboxValueByXpath(element, testdata, driver); //use the textbox attribute code for button also
						break;
						case "Name": validation.validateTextboxValueByName(elementName, testdata, driver); //use the textbox attribute code for button also
						break;
						case "CssSelector": validation.validateTextboxValueByCssSelector(elementCssSelector, testdata, driver); //use the textbox attribute code for button also
						break;
						default:						
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the button to validate text."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the button to validate text.";
							exceptionerror=true; 						
					}	**/				
				}
				break;
			}
			//case "Object: Click (locator value)":
			case "CLICK":			
			case "DOUBLE CLICK":	
			{
				checkLocBlankValue(locatorValue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror) //Execute it only if the values are valid
				{
					try {
						//driver.switchTo().frame("contentIFrame0");		
						
						findElement();
				    	//(new WebDriverWait(driver, 15)).until(ExpectedConditions.elementToBeClickable(element));  //checks if element is visible and clickable 
				       //if (element.isDisplayed() && element.isEnabled()) {
				    ///	//POAL Specific Block Begins/////////////
					/**	if (object.equals("Report Title hyperlink"))
						{					
							Actions actions = new Actions(driver);
                            actions.keyDown(protractor.Key.SHIFT)
                            .click(driver.findElement(By.linkText(testdata)))
                            .keyUp(protractor.Key.SHIFT)
                            .perform();
							//selectSubmittedHazardReport(testdata);
							//driver.findElement(By.linkText(testdata+" - Review Hazard")).click();
						}**/ 
						//////		//POAL Specific Block Ends/////////////
						
						if (stepdescription.equalsIgnoreCase("Double Click"))
						{
							
							element.click();
							Thread.sleep(500);
							//((JavascriptExecutor) driver).executeScript("document.getElementByXpath(//*[text() = 'POA-00435-N9J5']).dispatchEvent(new Event('dblclick'));");
							//WebElement element = driver.findElement(By.xpath(locatorvalue));
							
							Actions dClickAction = new Actions(driver);
							//dClickAction.moveToElement(element);
							
					  		dClickAction.doubleClick();					  		
					  		//Thread.sleep(1000);		//The Actions do not get executed on the page, so adding this Sleep command
					  		dClickAction.build().perform();
					  		
						}
						else
						{
							
				            //element.click();
							Actions sClickActions = new Actions(driver);

							sClickActions.moveToElement(element).click();
							Thread.sleep(1000);
							sClickActions.build().perform();
						}
						
				
				       // } 
				       // else 
				        //{ 
				        //	Thread.sleep(2000); 
				        //	(new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(element)); 
				         //   element.click(); 
				        //} 
				    } catch(StaleElementReferenceException ex)  {  //Stale Element. It may mean element is re-loaded by the Javascript page, so find it again
				    						        
					        try {
								Thread.sleep(2000);
								findElement();
								element.click();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								errormessage = "Error: " + e.getMessage(); 
						        exceptionerror = true;
							}				       
					        
				    } catch (NoSuchElementException f) 
					{ 
					        errormessage = "Element not visible on screen : " + f.getMessage(); 
					        exceptionerror = true;
					        throw new ElementNotVisibleException("Element not displayed in webpage."); 
				    }  catch (WebDriverException e)  
					{ 
					        try {
					        	Thread.sleep(2000);
					        	findElement();				// WebDriver exception in case giving Stale Element error, then find it again 
					            element.click(); 
					             
					        } catch (Exception d) 
					        { 
					        	errormessage = "click(Element) failed with error : " + d.getMessage(); 
					            exceptionerror = true;
					        } 
				    } catch (Exception g) 
						{ 
				    	errormessage = "click(element) failed with error : " + g.getMessage(); 
				        exceptionerror = true;
						} 
				}
				break;
			}
			
			case "CLICK ON HYPERLINK":
			{
				driver.findElement(By.linkText(testdata)).click();
				break;			
			}
			
			case "PRESSKEY (ENTER/RETURN/TAB/ESCAPE)":
			case "PRESSKEY (LEFTARROW/RIGHTARROW/UPARROW/DOWNARROW)":
			case "PRESSKEY (F1/F2/F5/CONTROL+A/SHIFT+TAB/...)":	
			{
				checkLocBlankValue(locatorValue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					findElement();
					keypress.keyId(element, testdata, driver);
					
					/** switch (locatortype)
					{
						case "ID": keypress.keyId(elementId, testdata, driver);
						break;
						case "Xpath": keypress.keyXpath(element, testdata, driver);
						break;
						case "Name": keypress.keyName(elementName, testdata, driver);
						break;
						case "CssSelector": keypress.keyCssSelector(elementCssSelector, testdata, driver);
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the key to press."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the key to press.";
							exceptionerror=true; 						
					}	**/				
				}
				break;
			}	
			
			case "PRESSKEY":
			{
				//checkLocBlankValue(locatorValue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				//if (!exceptionerror)  //Execute it only if the values are valid
			//	{
					//findElement();					
					Robot robot = new Robot();
					switch (testdata.toUpperCase())
					{
						case "F1": 
						{
							robot.keyPress(KeyEvent.VK_F1);
							robot.keyRelease(KeyEvent.VK_F1);
							Thread.sleep(300);
							break;
						}
						case "F2": 
						{
							robot.keyPress(KeyEvent.VK_F2);
							robot.keyRelease(KeyEvent.VK_F2);
							Thread.sleep(300);
							break;
						}
						case "F5": 
						{
							robot.keyPress(KeyEvent.VK_F5);
							robot.keyRelease(KeyEvent.VK_F5);
							Thread.sleep(300);
							break;
						}
						case "ENTER": 
						{
							robot.keyPress(KeyEvent.VK_ENTER);
							robot.keyRelease(KeyEvent.VK_ENTER);
							Thread.sleep(300);
							break;
						}
						case "ESCAPE": 
						{
							robot.keyPress(KeyEvent.VK_ESCAPE);
							robot.keyRelease(KeyEvent.VK_ESCAPE);
							Thread.sleep(300);
							break;
						}
						case "TAB": 
						{
							robot.keyPress(KeyEvent.VK_TAB);
							robot.keyRelease(KeyEvent.VK_TAB);
							Thread.sleep(300);
							break;
						}
						case "DOWN": 
						{
							robot.keyPress(KeyEvent.VK_DOWN);
							robot.keyRelease(KeyEvent.VK_DOWN);
							Thread.sleep(300);
							break;
						}
						case "DELETE ALL": 
						{
							robot.keyPress(KeyEvent.VK_CONTROL);
							robot.keyPress(KeyEvent.VK_A);
							robot.keyRelease(KeyEvent.VK_CONTROL);
							robot.keyRelease(KeyEvent.VK_A);
							Thread.sleep(300);
							robot.keyPress(KeyEvent.VK_DELETE);
							robot.keyRelease(KeyEvent.VK_DELETE);
							Thread.sleep(300);
							break;
						}
						default:
						{
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the key to press."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No key specified.";
							exceptionerror=true; 	
						}
					}
						
				//}
				
				
			}	
			
		
			
			case "PAUSE EXECUTION (SPECIFY SECONDS)":
			{
				long sleeptimeinsec= Long.parseLong(testdata+"000");  //convert string to long				
				Thread.sleep(sleeptimeinsec);	
				break;
			}
			case "CLOSE BROWSER": 
			{
				driver.close();
				//driver.quit();
				//logger.log(LogStatus.INFO,"Test Case - "+tcid+"("+tc_desc+") executed.");
				break;
			}
			
			
			
			case "VALIDATE OBJECT PRESENCE":
			{
				checkLocBlankValue(locatorValue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					findElement();
					switch (locatortype.toUpperCase())
					{
						case "ID": assertTrue(isElementPresent(By.id(locatorValue)));
					//	 WebElement lnktext = driver.findElement(By.xpath("//ul[@id='courses']/li[@class='sv-list-group-item']/a[contains(text(),'" + value + "')]")); 
						break;
						case "XPATH": assertTrue(isElementPresent(By.xpath(locatorValue)));
						break;
						case "NAME": assertTrue(isElementPresent(By.name(locatorValue)));
						break;
						case "CSSSELECTOR": assertTrue(isElementPresent(By.cssSelector(locatorValue)));
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the key to press."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the object.";
							exceptionerror=true; 						
					}		
				
				}
				break;
			}
			
			
			case "VALIDATE OBJECT ABSENCE":
			{
				checkLocBlankValue(locatorValue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					//findElement(); No need as we are checking the absence
					switch (locatortype.toUpperCase())
					{
						case "ID": assertFalse(isElementPresent(By.id(locatorValue)));
					//	 WebElement lnktext = driver.findElement(By.xpath("//ul[@id='courses']/li[@class='sv-list-group-item']/a[contains(text(),'" + value + "')]")); 
						break;
						case "XPATH": assertFalse(isElementPresent(By.xpath(locatorValue)));
						break;
						case "NAME": assertFalse(isElementPresent(By.name(locatorValue)));
						break;
						case "CSSSELECTOR": assertFalse(isElementPresent(By.cssSelector(locatorValue)));
						break;
						default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the key to press."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							errormessage="Invalid or No Locator type specified for the object.";
							exceptionerror=true; 						
					}		
				
				}
				break;
			}
			
			
			
		//POAL///	
			case "VALIDATE IF OBJECT IS PRESENT IN THE TABLE VIEW":
			{
				checkLocBlankValue(locatorValue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					if (object.equals("Form Filler > Reported Hazard Status Closed*"))
					{
						assertTrue(confirmHazardReportClosedStatus("Closed", testdata));
					}
					else
					{
							findElement();
							assertTrue(isElementPresent(By.linkText(testdata)));  // If Hyperlink
					}
					
				
					//assertTrue(isElementPresent(By.id(locatorValue)));
				//	switch (locatortype)
				//	{
					//	case "ID": assertTrue(isElementPresent(By.id(locatorValue+"[contains(text(),'"+testdata+"')]")));
					//	 WebElement lnktext = driver.findElement(By.xpath("//ul[@id='courses']/li[@class='sv-list-group-item']/a[contains(text(),'" + value + "')]")); 
					//	break;
					//	case "Xpath": {
							
						/**	List<WebElement> tableCells= driver.findElements(By.xpath(locatorValue+"/tr/td"));  //Get All The Cell Values
							for(WebElement cell : tableCells) {
								System.out.println(cell.getText());
								System.out.println("Xpath"+driver.findElement(By.name(cell.getText())).getAttribute("xpath"));
							    if(cell.getText().equals(testdata));
							    {
							    	driver.findElement(By.linkText(testdata)).click();
							    }
							}
							METHOD to get XPATH of childelements
							private String generateXPATH(WebElement childElement, String current) {
							    String childTag = childElement.getTagName();
							    if(childTag.equals("html")) {
							        return "/html[1]"+current;
							    }
							    WebElement parentElement = childElement.findElement(By.xpath("..")); 
							    List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
							    int count = 0;
							    for(int i=0;i<childrenElements.size(); i++) {
							        WebElement childrenElement = childrenElements.get(i);
							        String childrenElementTag = childrenElement.getTagName();
							        if(childTag.equals(childrenElementTag)) {
							            count++;
							        }
							        if(childElement.equals(childrenElement)) {
							            return generateXPATH(parentElement, "/" + childTag + "[" + count + "]"+current);
							        }
							    }
							    return null;
							}
							
							
							*
							**/
							
					//		System.out.println("Table Value"+ driver.findElement(By.xpath(locatorValue)).getText());
							//WebElement t = driver.findElement(By.xpath(locatorValue+"[contains(text(),'"+testdata+"')]"));
							//t.getText();
							//assertTrue(isElementPresent(By.linkText(testdata)));
					//	}
					//	break;
					//	case "Name": assertTrue(isElementPresent(By.name(locatorValue+"[contains(text(),'"+testdata+"')]")));
					//	break;
					//	case "CssSelector": assertTrue(isElementPresent(By.cssSelector(locatorValue+"[contains(text(),'"+testdata+"')]")));
					//	break;
					//	default:
							//logger.log(LogStatus.INFO,"Invalid or No Locator type specified for the key to press."); //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
					//		errormessage="Invalid or No Locator type specified for the key to press.";
					//		exceptionerror=true; 						
				//	}		
				}
				break;
			}
			//POAL///			
						
			/**case "DateFormat_Change (dd/MMM/yyyy)":
			{
				
				break;
			}	**/
			
			
			case "VALIDATE IF CHECKBOX IS SELECTED":
			case "VALIDATE IF RADIOBUTTON IS SELECTED":
			{
				checkLocBlankValue(locatorValue);  //check null or blank values and set the exceptionerror and exceptionmessage text.
				if (!exceptionerror)  //Execute it only if the values are valid
				{
					findElement();
					assertTrue(element.isSelected());
					/**
					switch(locatortype)
					{
						case "ID":	assertTrue(elementId.isSelected());
							//	assertTrue(driver.findElement(By.id(locatorvalue)).isSelected());
						break;
						case "Xpath":	assertTrue(element.isSelected());
						break;
						case "Name":	assertTrue(elementName.isSelected());
						break;
						case "CssSelector":	assertTrue(elementCssSelector.isSelected());
						break;
						default:
							errormessage="Invalid or No Locator type specified for the checkbox/radiobutton to verify the selection."; //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
							exceptionerror=true;   
						
					}**/
				}
				break;
			}	
			
			
			
		/**	case "Execute Above Steps Multiple Times (test data)":
			{
				int maxtimes= Integer.parseInt(testdata);
				int row = 
				for (int i=1;i<=maxtimes;i++)
				{
					for(int j=0;j<rows-2;j++)   //Initializing Array to rows-1. First row is just headings and make sure every column cell has a text
					{					    
						
							main(testcasesdata[j+1][0],testcasesdata[j+1][1],testcasesdata[j+1][2],testcasesdata[j+1][3],testcasesdata[j+1][4],testcasesdata[j+1][5],testcasesdata[j+1][6],testcasesdata[j+1][7])					
											
					}
					main(tcid, tc_desc, stepid, step_desc, command, locatortype, locatorvalue, testdata);					
				}
				break;
			}**/
			
			case "DO NOT EXECUTE THIS STEP":
			{
				/// DO Nothing
				break;
			}	
			case "BROWSER: SAVE ATTRIBUTES (BEFORE NEW WINDOW)":
			{
				winhandlebefore = driver.getWindowHandle(); 
				// get all the window handles before the popup window appears
				beforepopup = driver.getWindowHandles();
				break;
			}
			case "BROWSER: SWITCH TO NEW WINDOW":
			{
				// get all the window handles after the popup window appears
				Set<String> afterPopup = driver.getWindowHandles();

				// remove all the handles from before the popup window appears
				afterPopup.removeAll(beforepopup);

				// there should be only one window handle left
				if(afterPopup.size() == 1) 
				{
				     driver.switchTo().window((String)afterPopup.toArray()[0]);
				}	    
				break;
			}
			case "BROWSER: SWITCH BACK TO OLD WINDOW":
			{
				driver.close();
				driver.switchTo().window(winhandlebefore); 
				break;
			}
			//POAL CRM  //
			case "MOVE TO FRAME": 
			{
				if (testdata.equalsIgnoreCase("Default"))  
				{
					driver.switchTo().defaultContent();		//Moves to the top default frame
				}
				else
				{
					(new WebDriverWait(driver, 15)).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(testdata));
				}
				//WebElement frame = driver.findElement(By.id(testdata));
				//driver.switchTo().frame(testdata);

				//Then to move out of frame use:- driver.switchTo().defaultContent();
				
				//driver.switchTo().frame(testdata);
				//Thread.sleep(5000);
				break;
			}
			
			
			//POAL CRM  //
			default: 
			{
				if (tcid.equals("") && tc_desc.equals("") && stepid.equals("") && stepdescription.equals("") && page.equals("") && object.equals("") && testdata.equals(""))
				{
					allRowBlank=true;
					break;
				}
				if (stepdescription.equalsIgnoreCase("Not Executing")) 
				{ 
					break; 
				} 
					
				errormessage="Invalid or No command specified."; //JOptionPane.showMessageDialog(null,"Invalid Command.");	//No action and show a message box to the user, if required.
				exceptionerror=true;   
				break;
			}
		}
	}
	catch (Exception e)
	{
		System.out.println("Error: "+e.getMessage()); // Comment it later on
		exceptionerror=true;
	    errormessage=e.getMessage();
	    //if(errormessage.contains("Expected condition failed: waiting for visibility of element located by")) //check if error is caused by the visibility or Clickable wait check
	    if(errormessage.contains("Expected condition failed: waiting for element to be clickable")) //check if error is caused by the visibility or Clickable wait check
	    {
	    	counter++; //increase the visibility wait counter by 1
	    	if(counter>=3)	wait = new WebDriverWait(driver, 0); // if 3 consecutive objects have not been found then, 0 seconds explicit wait    
	    }	   
	}
}

@DataProvider(name="TestSteps")  //Parameterizing @Test code for the Excel records
public Object[][] readTestCases() throws Exception   // Load Data Excel  
{	  		    
	Object[][] testcasesdata = new Object[0][0];
	//sheetnumber = sheetnumber; // As user will input the exact serial number and the index starts from 0.
///	String excelpath=propertyconfig.getExcelSheetPath();
  	//ExcelDataConfig excelconfig = new ExcelDataConfig("C:\\Users\\rbhatia\\Google Drive\\Project\\Automation\\ZAuto\\TestCases.xlsx");	  	  	
	//excelreadpreferences = new ExcelDataConfig(testcasepath, sheetName);
	excelreadpreferences.openSheet(testcasepath, sheetName);	
  	int rows=excelreadpreferences.getRowCount(sheetName);  //rows in the first sheet
//	int cols=excelconfig.getColCount(sheetnumber);  //cols in the first sheet
  	int cols = 18;										// Fixing it as we know the Column count
	if (rows>=0 && cols>=0) //atleast one , index 0
	{
		testcasesdata = new Object[rows][cols];	
		for(int i=0;i<rows;i++)   //Initializing Array to rows-1. First row is just headings and make sure every column cell has a text
		{
		    for (int j=0;j<cols;j++)  //Columns value is one more than the index so less than sign
			{
				testcasesdata[i][j]=excelreadpreferences.getData(sheetName, i+1, j);  //Picking data from the 2nd row in excel sheet, so i+1
		    
				/**if (j==6)   //As DoB field is in the 7th col (6th index)
				{
					//Calling the function to change the date format from mm/dd/yy to dd/mm/yyyy//
				//	dobforage[i]= (String) data[i][j];
					String datevalue = (String) data[i][j]; 
					String datechange = excelconfig.changeDateFormat(datevalue);  
					data[i][j]=datechange;
					 	
					// -----------------------------------------------------------------     //		
				}**/
			}					
		}		
	}
	return testcasesdata;
}

@AfterMethod   //executed after every method. Creating to capture the results of Failure.
public void tearD(ITestResult result) throws Exception
{
	 if(ITestResult.FAILURE==result.getStatus() || (exceptionerror))  //Check if Test case has failed
	 {
	 	 //String screenshot_path = ReportScreenshotUtility.captureScreenshot(driver,"/test-output/screenshots/",result.getName());   //Take screenshot if Test Case fails
		 String screenshot_path = ReportScreenshotUtility.captureScreenshot(driver,executionreportpath,result.getName());   //Take screenshot if Test Case fails and at the same location where execution report is saved.
		 String image="";
		 if (screenshot_path.contains("Exception while taking screenshot: ")) 
	 	 {
	 		 errormessage = screenshot_path;  //capture the error in case of taking screenshot, no image
	 	 }
	 	 else
	 	 {
	 		 image=logger.addScreenCapture(screenshot_path);
	 	 }
		 String exceptionmessage= image +  "Error Message: "+ result.getThrowable()+".\n"+errormessage;	 
		 
		 if (stepid !=null && stepdescription!=null && objectName!=null)			//Only log into Test Report if there is a data in the row. Do not log the results if there is a blank row and Null returned from ExcelDataConfig (excel reading). 
		 {
			 logger.log(LogStatus.FAIL, "Step ID: "+stepid+", Step Desc: "+stepdescription+", Object: "+ objectName, exceptionmessage);
		 }
		 
	 	 ///logger.log(LogStatus.INFO,"PageSource",driver.getPageSource());
	 	 //if(ITestResult.FAILURE==result.getStatus())		logger.log(LogStatus.FAIL, "Step ID: "+stepid+", Step Desc: "+stepdescription+": FAILED. Error Message: "+ result.getThrowable());
	 	 //if (exceptionerror)  logger.log(LogStatus.FAIL, "Step ID: "+stepid+", Step Desc: "+stepdescription+": FAILED. Error Message: "+ errormessage);
	 	 // logger.log(LogStatus.FAIL, "HTML Page Source:", driver.findElement(By.tagName("code")).getText()); //get the page source
	 	//logger.log(LogStatus.FAIL, "HTML Page Source", driver.findElement(By.tagName("code")).getText());  //get the page source
	 	
	  	//logger.log(null, pageSource);
	 	/**String javascript = "return arguments[0].innerHTML";
	  	 String pagesource=(String)((JavascriptExecutor)driver).executeScript(javascript, driver.findElement(By.tagName("html")));
	  	 pagesource = "<html>"+pagesource +"</html>";**/ //No need for this code. getPageSource doing the same thing.
	 	
	 }
	 else if (ITestResult.SUCCESS==result.getStatus() && (!exceptionerror))   //Check if Test case has passed
	 {
	 	 if (stepdescription.equalsIgnoreCase("DO NOT EXECUTE THIS STEP"))
	 	 {
	 		logger.log(LogStatus.PASS, "Step ID: "+stepid+", Step Desc: "+stepdescription+", Object: "+ objectName,"SKIPPED");  //to capture the non-executed step in the report.
	 	 }
	 	 else if ((allRowBlank==true) || (stepdescription.equalsIgnoreCase("Not Executing")))
	 	 {
	 		 // Do Not Log anything if row is blank or not executing that step/flag is N 
	 	 }
	 	 else 
	 	 {
	 		logger.log(LogStatus.PASS, "Step ID: "+stepid+", Step Desc: "+stepdescription+", Object: "+ objectName,"PASSED");	
	 	 }
	 	
	 }
	 else if (ITestResult.SKIP==result.getStatus())  //Check if Test case has skipped
	 {
		logger.log(LogStatus.SKIP, "Step ID: "+stepid+", Step Desc: "+stepdescription+", Object: "+ objectName+": SKIPPED.", result.getThrowable());
	 }		
		// ReportScreenshotUtility.report.endTest(logger);
	 	
		 //ReportScreenshotUtility.report.flush(); //This may flush the results after every step
		 
		 //ReportScreenshotUtility.report.
	 
		
}


/**
public void browserSettings(WebDriver driver, String testdata)
{
	driver.get("https://"+testdata);
	//driver.get(testdata);
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

}
**/





public void checkLocParamBlankValues(String locatorValue, String testdata)
{
	if (testdata.equals("") || testdata.equals(null) || locatorValue.equals("") || locatorValue.equals(null)) //validate that parameters value is valid
	{
		errormessage="Invalid Locator or test data.";
		exceptionerror=true;   
	}
	
}

public void checkLocBlankValue(String locatorValue)
{
	if (locatorValue.equals("") || locatorValue.equals(null)) //validate that parameters value is valid
	{
		errormessage="Invalid Locator value.";
		exceptionerror=true;   
	}
	
}


private boolean isElementPresent(By by) 
	{
    try {
      driver.findElement(by);
      return true;
    	} catch (NoSuchElementException e) {
    		return false;
    	}
	}


@Override
public void transform(ITestAnnotation annotation, Class testClass,
		Constructor testConstructor, Method testMethod)   //It gets called from InvokeMaster (implemented in that class in this project) OR through testng.xml file.
	{
		// TODO Auto-generated method stub
		preferencesSheetName = "Control"; //This is called first, so Preferences Sheet name here also
		//ExcelDataConfig excelreadpreferences = new ExcelDataConfig(System.getProperty("user.dir")+"/Preferences.xlsm",preferencesSheetName);  // dir returning base path in the tool so changing to Home
		excelreadpreferences = new ExcelDataConfig(InvokeMaster.sheetDirPathAndName);  //Using this object to get the TestCases data also
		excelreadpreferences.openSheet(InvokeMaster.sheetDirPathAndName, preferencesSheetName);
			//preferencesdata = new Object[5][1];
			/**for(int i=0;i<5;i++)   //Initializing Array to rows-1. First row is just headings and make sure every column cell has a text
			{
				for(int j=0;j<1;j++)  //Columns value is one more than the index so less than sign
				{
					preferencesdata[3][0]=excelreadpreferences.getData(0, 5, 1);  //Picking data from the 2nd row in excel sheet, so i+1
					
				}					
			}**/
			preferencesdata[2][0]=excelreadpreferences.getData(preferencesSheetName, 3, 1); //InvocationCount same data multiple executions  //Picking data from the 2nd column in excel sheet, so i+1
			preferencesdata[3][0]=excelreadpreferences.getData(preferencesSheetName, 4, 1); //InvocationCount different data multiple executions
			//Same data multiple execution
			//if (preferencesdata[4][0]!=""  && preferencesdata[4][0]!="0")		
			
			if (((String) preferencesdata[2][0]).matches("[0-9]+") && ((String) preferencesdata[2][0]).length() >= 1 && !preferencesdata[2][0].equals("0")) // No need for this condition && !preferencesdata[3][0].equals(""))
			{
					invocationcount = Integer.parseInt((String) preferencesdata[2][0]);
			}
			else
			{
					if (!preferencesdata[3][0].equals("0") && ((String) preferencesdata[3][0]).matches("[0-9]+") && ((String) preferencesdata[3][0]).length() >= 1 )	// check for integer values only
					{	
						invocationcount = Integer.parseInt((String) preferencesdata[3][0]); //Different test data each time for multiple executions. It can't work with multiple runs and same test data
					}
					else
					{									//don't need to do anything if nothing mentioned
						invocationcount = 1;					// This will work if nothing is mentioned for multiple test runs
					}	
			}	
				
			if ("main".equals(testMethod.getName())) 
			{
			      annotation.setInvocationCount(invocationcount);		      
			}
			//Different data multiple execution
		
		
	
	}

public String mData(String string, Calendar cal)
{
	//string=new Timestamp(System.currentTimeMillis()).getTime()+""+cal.get(Calendar.DATE)+string;
	string = string + " " + System.currentTimeMillis()+""+cal.get(Calendar.DATE);
	return string;
}

public void isWebElementVisible(WebElement element)
{
	if (!element.isDisplayed()) 					
	{
		counter++;  //to check counter progress
		if(counter>=3)	wait = new WebDriverWait(driver, 0); // 0 seconds explicit wait
	}	
}



public void findElement() {
	
	switch (locatorType.toUpperCase())
	{
		case "ID":
				element = wait.until(ExpectedConditions.elementToBeClickable(By.id(locatorValue)));
				break;						
		case "XPATH":						
				element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorValue)));
			//	(new WebDriverWait(driver, 15)).until(ExpectedConditions.elementToBeClickable(element));  //checks if element is visible and clickable
				break;						
		case "NAME":
				element = wait.until(ExpectedConditions.elementToBeClickable(By.name(locatorValue)));
				break;						
		case "CSSSELECTOR":
				element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(locatorValue)));
				break;
		default:				
			errormessage="Invalid or No Locator type specified for the object."; //Send error through the AfterMethod and into the report and not via info as above
			exceptionerror=true;
	
	}

}

//POAL Specific Block BEGINS //
public void selectDropDownListPOAL(String locatorvalue, String testdata, String listXpath) {
	
	try {
	driver.findElement(By.xpath(locatorvalue)).click();					//Click Arrow.
	Thread.sleep(1500);			//The value wasn't getting picked from the below WebElement selectParentListOrgUnitPath
	//new Click().click(driver, elementXpath);
	WebElement selectParentListOrgUnitPath = driver.findElement(By.xpath(listXpath));
	//selectListValue(selectParentListOrgUnitPath,testdata);
	// protected void selectListValue(WebElement selectParentListOrgUnitPath, String orgunit) {
	    	
	    	//WebElement listParent= driver.findElement(By.xpath("'"+selectParentListOrgUnitPath+"'"));
			List<WebElement> List=selectParentListOrgUnitPath.findElements(By.tagName("li"));
				for (WebElement li : List) {					
					if (li.getText().equals(testdata)) 
					{
							 li.click();
							 break;
					}
				}
		      
	 //     }				
	
	Thread.sleep(1000);;
		}catch(Exception e)
			{ errormessage = e.getMessage(); 
			exceptionerror = true; }
	}


/**
public Boolean selectSubmittedHazardReport(String reportTitle)
{		
	int sno = 2; //starts from 2 in the list...
	String id = "//*[@id='ctl00_ctl00_pagePlaceHolder_pagePlaceholder_customDashboardControl_columnsRepeater_ctl00_dashboardColumn_widgetRepeater_ctl00_widgetContainer_widget_actionDG_actionGrid_ctl0" + sno + "_actionHyperlink']";
	
	while(isElementPresent(By.xpath(id))) {
		if (driver.findElement(By.xpath(id)).getText().equals(reportTitle+" - Review Hazard")) 
		{
			driver.findElement(By.xpath(id)).click();
			return true;
		}
		else 
		{
			sno++;
			if (sno<10)  id = "//*[@id='ctl00_ctl00_pagePlaceHolder_pagePlaceholder_customDashboardControl_columnsRepeater_ctl00_dashboardColumn_widgetRepeater_ctl00_widgetContainer_widget_actionDG_actionGrid_ctl0" + sno + "_actionHyperlink']";
						
			else id = "//*[@id='ctl00_ctl00_pagePlaceHolder_pagePlaceholder_customDashboardControl_columnsRepeater_ctl00_dashboardColumn_widgetRepeater_ctl00_widgetContainer_widget_actionDG_actionGrid_ctl0" + sno + "_actionHyperlink']";
		}			
	}		
	return false;
	
}

public boolean confirmHazardReportCreation(String titleHazardReportExpectedValue)
{		
	String ReportTableID = "//*[@id='ctl00_ctl00_pagePlaceHolder_pagePlaceholder_customDashboardControl_columnsRepeater_ctl01_dashboardColumn_widgetRepeater_ctl01_widgetContainer_widget_auditDG_auditGrid_ctl";
	int sno = 2; //starts from 2 in the list...
	String id = ReportTableID + "0" + sno + "_titleLinkButton']";
	System.out.println(id);
	//Boolean status = getHazardReportFromTable(id, sno, titleHazardReportExpectedValue);			
	//return status;
	while(isElementPresent(By.xpath(id))) {
		if (driver.findElement(By.xpath(id)).getText().equals(titleHazardReportExpectedValue)) 
		{
			return true;				
		}
		else 
		{				
			sno++;
			if (sno<10)  id = ReportTableID + "0" + sno + "_titleLinkButton']";
			else id = ReportTableID + sno + "_titleLinkButton']";
		}			
	}		
	return false;
}
**/
public boolean confirmHazardReportClosedStatus(String titleHazardReportStatus, String reportName)
{		
	int sno = 2; //starts from 2 in the list...
	String ReportTableID = "//*[@id='ctl00_ctl00_pagePlaceHolder_pagePlaceholder_customDashboardControl_columnsRepeater_ctl01_dashboardColumn_widgetRepeater_ctl01_widgetContainer_widget_auditDG_auditGrid_ctl";
	String id = ReportTableID + "0" + sno + "_statusLinkButton']";
	String reportLinkID = ReportTableID + "0" + sno + "_titleLinkButton']";

	while(isElementPresent(By.xpath(id))) {
		if ((driver.findElement(By.xpath(id)).getText().equals(titleHazardReportStatus)) && (driver.findElement(By.xpath(reportLinkID)).getText().equals(reportName)))  
		{
			return true;				
		}
		else 
		{				
			sno++;
			if (sno<10)					 
			{
				id = ReportTableID + "0" + sno + "_statusLinkButton']";
				reportLinkID = ReportTableID + "0" + sno + "_titleLinkButton']";
			}
			else 
			{
				id = ReportTableID + sno + "_statusLinkButton']";
				reportLinkID = ReportTableID + sno + "_titleLinkButton']";
			}
		}			
	}		
	return false;	
}


	public void getIframe(String frameID) 
	{
		
	/**	JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
		//String currentFrame = (String) jsExecutor.executeScript("return window.frameElement");
		String currentFrame = (String) jsExecutor.executeScript("return self.name");
		System.out.println();
		System.out.println(jsExecutor.executeScript("return window.frameElement"));
		if (!currentFrame.equals(frameID))
		{
			driver.switchTo().frame(frameID);
		}**/
		/**final List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
		for (WebElement iframe : iframes) {
			if (iframe.getAttribute("id").equals(frameID)) {
	    // 	TODO your stuff.
	    	}
		}**/
	}


//POAL Specific Block ENDS//

}

/**Object result = JOptionPane.showInputDialog(null, "Enter a blog website");
if (result != null) {
        String word2 = (String) result;
}**/

