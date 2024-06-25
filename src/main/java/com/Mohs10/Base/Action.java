package com.Mohs10.Base;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.MediaEntityBuilder;

public class Action extends StartBrowser {

	// WebDriver instance to interact with the web application
	public static WebDriver driver;

	// Constructor to initialize the WebDriver
	public Action() {
		driver = StartBrowser.driver;
	}

	/**2
	 * Navigates to the specified URL.
	 *
	 * @param url The URL to navigate to
	 */
	public void navigateToApplication(String url) {
		try {
			driver.get(url);
			StartBrowser.childTest.pass("Successfully Navigated to: " + url);
		} catch (Exception e) {
			StartBrowser.childTest.fail("Unable to Navigate to: " + url);
		}
	}

	/**3
	 * Gets the current URL of the web page.
	 *
	 * @param driver The WebDriver instance
	 * @return The current URL
	 */
	public String getCurrentURL(WebDriver driver) {
		boolean flag = false;

		String text = driver.getCurrentUrl();
		if (flag) {
			System.out.println("Current URL is: \"" + text + "\"");
		}
		return text;
	}

	/**4
	 * Gets the title of the web page.
	 *
	 * @param driver The WebDriver instance
	 * @return The title of the page
	 */
	public String getTitle(WebDriver driver) {
		boolean flag = false;

		String text = driver.getTitle();
		if (flag) {
			System.out.println("Title of the page is: \"" + text + "\"");
		}
		return text;
	}

	/**5
	 * Types the specified text into the given WebElement.
	 *
	 * @param ele  The WebElement to type into
	 * @param text The text to type
	 * @return True if successful, false otherwise
	 */
	public static boolean type(WebElement ele, String text) {
		boolean flag = false;
		try {
			flag = ele.isDisplayed();
			ele.clear();
			ele.sendKeys(text);
			// logger.info("Entered text :"+text);
			flag = true;
		} catch (Exception e) {
			System.out.println("Location Not found");
			flag = false;
		} finally {
			if (flag) {
				System.out.println("Successfully entered value");
			} else {
				System.out.println("Unable to enter value");
			}

		}
		return flag;
	}

	/**6
	 * Types the specified text into the WebElement identified by the given locator.
	 *
	 * @param locator  The locator of the WebElement
	 * @param testData The text to type
	 * @param eleName  Name of the element
	 * @return The WebElement after typing
	 * @throws Exception If unable to perform the action
	 */
	public WebElement type(By locator, String testData, String eleName) throws Exception {
	    WebElement element = null;

	    try {
	        // Find the WebElement using the provided locator
	        element = driver.findElement(locator);

	        // Type the specified test data into the identified WebElement
	        element.sendKeys(testData);

	        // Log a pass message if typing is successful
	        StartBrowser.childTest
	            .pass("Successfully performed type action on :" + eleName + " With test data :" + testData);

	    } catch (Exception e) {
	        // Log a fail message with a screenshot and exception information if typing fails
	        StartBrowser.childTest.fail("Unable to perform type action on :" + eleName + " With test data :" + testData,
	            MediaEntityBuilder.createScreenCaptureFromBase64String(captureScreenshot(eleName)).build());
	        StartBrowser.childTest.info(e);

	        // Throw the exception to be handled by the calling code
	        throw e;
	    }

	    // Return the WebElement for potential further interactions
	    return element;
	}

	/**7
	 * Used to perform click on links, buttons, radio buttons and checkboxes
	 *
	 * @param locator -- Get it from Object Repository
	 * @param eleName -- Name of the element
	 * @throws Exception If unable to perform the action
	 */
	// Method to perform click on links, buttons, radio buttons, and checkboxes
	public void click(By locator, String eleName) throws Exception {
		try {
			driver.findElement(locator).click();
			StartBrowser.childTest.pass("Performed click action on: " + eleName);
		} catch (Exception e) {
			handleException(eleName, e);
			throw e;
		}
	}

	/**8
	 * Handle exceptions by logging the error message and taking a screenshot.
	 *
	 * @param eleName Name of the element
	 * @param e       Exception object
	 */
	public static void handleException(String eleName, Exception e) throws IOException {
		String errorMessage = "Exception occurred while performing action on element: " + eleName;
		System.out.println(errorMessage);
		e.printStackTrace();

		// Capture screenshot
		String screenshotPath = captureScreenshot(eleName);

		// Log screenshot path or perform other actions as needed
		System.out.println("Screenshot captured: " + screenshotPath);
	}

	/**9
	 * Slide the window by dragging the specified element to the given x and y
	 * offsets.
	 *
	 * @param driver WebDriver instance
	 * @param ele    WebElement to be dragged
	 * @param x      X-axis offset for dragging
	 * @param y      Y-axis offset for dragging
	 * @return True if sliding is successful, false otherwise
	 */
	public boolean slider(WebDriver driver, WebElement ele, int x, int y) {
	    boolean flag = false;
	    try {
	        // Use Actions class to perform a slider action by dragging and dropping the WebElement
	        new Actions(driver).dragAndDropBy(ele, x, y).build().perform(); // Adjust the x and y values as needed
	        Thread.sleep(5000);

	        // Set the flag to indicate a successful slider action
	        flag = true;

	        // Return true to signify success
	        return true;
	    } catch (Exception e) {
	        // Return false if an exception occurs during the slider action
	        return false;
	    } finally {
	        // Provide console feedback based on the success or failure of the slider action
	        if (flag) {
	            System.out.println("Slider Action is performed");
	        } else {
	            System.out.println("Slider Action is not performed");
	        }
	    }
	}

	// Method to handle scrolling by the visibility of an element
	/**10
	 * Scroll the window to make the specified element visible.
	 *
	 * @param driver WebDriver instance
	 * @param ele    WebElement to be made visible
	 */
	public void scrollByVisibilityOfElement(WebDriver driver, WebElement ele) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", ele);
	}

	/**11
	 * Click on the specified element using the Actions class.
	 *
	 * @param driver WebDriver instance
	 * @param ele    WebElement to be clicked
	 */
	public static void click(WebDriver driver, WebElement ele) {
		Actions act = new Actions(driver);
		act.moveToElement(ele).click().build().perform();
	}

	/**12
	 * Drag the specified element to the given x and y offsets using the Actions
	 * class.
	 *
	 * @param driver WebDriver instance
	 * @param source WebElement to be dragged
	 * @param x      X-axis offset for dragging
	 * @param y      Y-axis offset for dragging
	 * @return True if dragging is successful, false otherwise
	 */
	public boolean draggable(WebDriver driver, WebElement source, int x, int y) {
	    boolean flag = false;
	    try {
	        // Use Actions class to perform a draggable action by dragging and dropping the source WebElement
	        new Actions(driver).dragAndDropBy(source, x, y).build().perform(); // Adjust the x and y values as needed
	        Thread.sleep(5000);

	        // Set the flag to indicate a successful draggable action
	        flag = true;

	        // Return true to signify success
	        return true;

	    } catch (Exception e) {
	        // Return false if an exception occurs during the draggable action
	        return false;

	    } finally {
	        // Provide console feedback based on the success or failure of the draggable action
	        if (flag) {
	            System.out.println("Draggable Action is performed on \"" + source + "\"");
	        } else {
	            System.out.println("Draggable action is not performed on \"" + source + "\"");
	        }
	    }
	}


	/**13
	 * Drag and drop the source WebElement onto the target WebElement using the
	 * Actions class.
	 *
	 * @param driver WebDriver instance
	 * @param source WebElement to be dragged
	 * @param target WebElement to drop onto
	 * @return True if drag-and-drop is successful, false otherwise
	 */
	public boolean dragAndDrop(WebDriver driver, WebElement source, WebElement target) {
	    boolean flag = false;
	    try {
	        // Use Actions class to perform a drag-and-drop action by dragging the source WebElement to the target WebElement
	        new Actions(driver).dragAndDrop(source, target).perform();

	        // Set the flag to indicate a successful drag-and-drop action
	        flag = true;

	        // Return true to signify success
	        return true;
	    } catch (Exception e) {
	        // Return false if an exception occurs during the drag-and-drop action
	        return false;
	    } finally {
	        // Provide console feedback based on the success or failure of the drag-and-drop action
	        if (flag) {
	            System.out.println("DragAndDrop Action is performed");
	        } else {
	            System.out.println("DragAndDrop Action is not performed");
	        }
	    }
	}

	/**14
	 * Right-click on the specified element using the Actions class.
	 *
	 * @param driver WebDriver instance
	 * @param ele    WebElement to be right-clicked
	 * @return True if right-click is successful, false otherwise
	 */
	public boolean rightClick(WebDriver driver, WebElement ele) {
	    boolean flag = false;
	    try {
	        // Use Actions class to perform a right-click action on the specified WebElement
	        Actions clicker = new Actions(driver);
	        clicker.contextClick(ele).perform();

	        // Set the flag to indicate a successful right-click action
	        flag = true;

	        // Return true to signify success
	        return true;
	    } catch (Exception e) {
	        // Return false if an exception occurs during the right-click action
	        return false;
	    } finally {
	        // Provide console feedback based on the success or failure of the right-click action
	        if (flag) {
	            System.out.println("RightClick Action is performed");
	        } else {
	            System.out.println("RightClick Action is not performed");
	        }
	    }
	}

	/**15
	 * Check if the specified WebElement is displayed.
	 *
	 * @param driver WebDriver instance
	 * @param ele    WebElement to be checked
	 * @return True if the element is displayed, false otherwise
	 */
	public static boolean findElement(WebDriver driver, WebElement ele) {
	    boolean flag = false;
	    try {
	        // Check if the WebElement is displayed
	        ele.isDisplayed();

	        // Set the flag to indicate that the WebElement is found and displayed
	        flag = true;
	    } catch (Exception e) {
	        // Set the flag to indicate that the WebElement is not found or not displayed
	        flag = false;
	    } finally {
	        // Log the status of the WebElement (found or not found)
	        logElementStatus(flag);
	    }
	    // Return the flag indicating whether the WebElement is found and displayed
	    return flag;
	}

	/**16
	 * Check if the specified WebElement is displayed.
	 *
	 * @param driver WebDriver instance
	 * @param ele    WebElement to be checked
	 * @return True if the element is displayed, false otherwise
	 */
	public static boolean isDisplayed(WebDriver driver, WebElement ele) {
	    boolean flag = false;
	    
	    // Check if the WebElement is found and displayed
	    flag = findElement(driver, ele);
	    
	    // If the WebElement is found, check if it is displayed
	    if (flag) {
	        flag = ele.isDisplayed();
	    }
	    
	    // Log the status of the WebElement (found and displayed or not found/not displayed)
	    logElementStatus(flag);
	    
	    // Return the flag indicating whether the WebElement is found and displayed
	    return flag;
	}

	/**17
	 * Check if the specified WebElement is selected.
	 *
	 * @param driver WebDriver instance
	 * @param ele    WebElement to be checked
	 * @return True if the element is selected, false otherwise
	 */
	public static boolean isSelected(WebDriver driver, WebElement ele) {
		boolean flag = false;
		flag = findElement(driver, ele);
		if (flag) {
			flag = ele.isSelected();
		}
		logElementStatus(flag);
		return flag;
	}

	/**18
	 * Check if the specified WebElement is enabled.
	 *
	 * @param driver WebDriver instance
	 * @param ele    WebElement to be checked
	 * @return True if the element is enabled, false otherwise
	 */
	public static boolean isEnabled(WebDriver driver, WebElement ele) {
		boolean flag = false;
		flag = findElement(driver, ele);
		if (flag) {
			flag = ele.isEnabled();
		}
		logElementStatus(flag);
		return flag;
	}

	/**19
	 * Select the specified value from the DropDown using sendKeys method.
	 *
	 * @param value The value to be selected
	 * @param ele   WebElement representing the DropDown
	 * @return True if the selection is successful, false otherwise
	 */
	public boolean selectBySendkeys(String value, WebElement ele) {
		boolean flag = false;
		try {
			ele.sendKeys(value);
			flag = true;
			return true;
		} catch (Exception e) {

			return false;
		} finally {
			if (flag) {
				System.out.println("Select value from the DropDown");
			} else {
				System.out.println("Not Selected value from the DropDown");
				// throw new ElementNotFoundException("", "", "")
			}
		}
	}

	/**20 
	 * Select the option from the DropDown by its index using the SelectByIndex
	 * method.
	 *
	 * @param element WebElement representing the DropDown
	 * @param index   Index of the option to be selected
	 * @return True if the selection is successful, false otherwise
	 */
	public static boolean selectByIndex(WebElement element, int index) {
		boolean flag = false;
		try {
			Select s = new Select(element);
			s.selectByIndex(index);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			logOptionSelectionStatus(flag);
		}
		return flag;
	}

	/**21
	 * Select the option from the DropDown by its value using the SelectByValue
	 * method.
	 *
	 * @param element WebElement representing the DropDown
	 * @param value   Value of the option to be selected
	 * @return True if the selection is successful, false otherwise
	 */
	public static boolean selectByValue(WebElement element, String value) {
		boolean flag = false;
		try {
			Select s = new Select(element);
			s.selectByValue(value);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			logOptionSelectionStatus(flag);
		}
		return flag;
	}

	/**22
	 * Select the option from the DropDown by its visible text using the
	 * SelectByVisibleText method.
	 *
	 * @param element     WebElement representing the DropDown
	 * @param visibleText Visible text of the option to be selected
	 * @param eleName     Name of the WebElement for logging purposes
	 * @return True if the selection is successful, false otherwise
	 */
	public static boolean selectFromDropDownByVisibleText(WebElement element, String visibleText, String eleName) {
		boolean flag = false;
		try {
			Select select = new Select(element);
			select.selectByVisibleText(visibleText);
			logActionStatus("Selected option '" + visibleText + "' from the drop-down: " + eleName);
			flag = true;
		} catch (Exception e) {
			logActionStatus("Unable to select option '" + visibleText + "' from the drop-down: " + eleName);
		}
		return flag;
	}

	/**23
	 * Perform mouse hover on the specified WebElement using JavaScript.
	 *
	 * @param ele WebElement to be hovered over
	 * @return True if mouse hover is successful, false otherwise
	 */
	public static boolean mouseHoverByJavaScript(WebElement ele) {
		boolean flag = false;
		try {
			WebElement mo = ele;
			String javaScript = "var evObj = document.createEvent('MouseEvents');"
					+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
					+ "arguments[0].dispatchEvent(evObj);";
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(javaScript, mo);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			logActionStatus(flag ? "MouseOver Action is performed" : "MouseOver Action is not performed");
		}
		return flag;
	}

	/**24
	 * Perform mouse hover on the specified WebElement using Actions class.
	 *
	 * @param driver WebDriver instance
	 * @param ele    WebElement to be hovered over
	 * @return True if mouse hover is successful, false otherwise
	 */
	public boolean mouseover(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			new Actions(driver).moveToElement(ele).build().perform();
			flag = true;
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			/*
			 * if (flag) {
			 * SuccessReport("MouseOver ","MouserOver Action is performed on \""+locatorName
			 * +"\""); } else {
			 * failureReport("MouseOver","MouseOver action is not performed on \""
			 * +locatorName+"\""); }
			 */
		}
	}

	/**25
	 * Perform a mouse over action on the specified WebElement using the Actions
	 * class.
	 *
	 * @param driver  WebDriver instance
	 * @param element WebElement to be hovered over
	 */
	public void mouseOverElement(WebDriver driver, WebElement element) {
		boolean flag = false;
		try {
			new Actions(driver).moveToElement(element).build().perform();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (flag) {
				System.out.println(" MouserOver Action is performed on ");
			} else {
				System.out.println("MouseOver action is not performed on");
			}
		}
	}

	/**26
	 * Move to the specified WebElement using JavaScriptExecutor to scroll into view
	 * and Actions class to perform the move action.
	 *
	 * @param driver WebDriver instance
	 * @param ele    WebElement to be moved to
	 * @return True if the move action is successful, false otherwise
	 */
	public boolean moveToElement(WebDriver driver, WebElement ele) {
		boolean flag = false;
		try {
			// WebElement element = driver.findElement(locator);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView(true);", ele);
			Actions actions = new Actions(driver);
			// actions.moveToElement(driver.findElement(locator)).build().perform();
			actions.moveToElement(ele).build().perform();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**27
	 * Perform a click on the specified WebElement using JavaScript.
	 *
	 * @param driver WebDriver instance
	 * @param ele    WebElement to be clicked
	 * @return True if the click is successful, false otherwise
	 * @throws Exception if an exception occurs during execution
	 */
	public static boolean JSClick(WebDriver driver, WebElement ele) throws Exception {
		boolean flag = false;
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", ele);
			flag = true;
		} catch (Exception e) {
			throw e;
		} finally {
			logActionStatus(flag ? "Click Action is performed" : "Click Action is not performed");
		}
		return flag;
	}

	/**28
	 * Switches the WebDriver focus back to the default content.
	 *
	 * @param driver WebDriver instance
	 * @return True if the switch to the default frame is successful, false
	 *         otherwise
	 */
	public boolean switchToDefaultFrame(WebDriver driver) {
		boolean flag = false;
		try {
			driver.switchTo().defaultContent();
			flag = true;
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (flag) {
				// SuccessReport("SelectFrame ","Frame with Name is selected");
			} else if (!flag) {
				// failureReport("SelectFrame ","The Frame is not selected");
			}
		}
	}

	/**29
	 * Switches the WebDriver focus to a frame using the frame index.
	 *
	 * @param driver WebDriver instance
	 * @param index  Index of the frame to switch to
	 * @return True if the switch to the frame by index is successful, false
	 *         otherwise
	 */
	public static boolean switchToFrameByIndex(WebDriver driver, int index) {
		boolean flag = false;
		try {
			new WebDriverWait(driver, Duration.ofSeconds(15))
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe")));
			driver.switchTo().frame(index);
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			logFrameSwitchStatus(index, flag);
		}
		return flag;
	}

	/**30
	 * Switches the WebDriver focus to a frame using the provided WebElement.
	 *
	 * @param driver       WebDriver instance
	 * @param frameElement WebElement representing the frame
	 * @return True if the switch to the frame by WebElement is successful, false
	 *         otherwise
	 */
	public static boolean switchToFrameByWebElement(WebDriver driver, WebElement frameElement) {
		boolean flag = false;
		try {
			new WebDriverWait(driver, Duration.ofSeconds(15))
					.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
			flag = true;
		} catch (Exception e) {
			flag = false;
		} finally {
			logFrameSwitchStatus(frameElement, flag);
		}
		return flag;
	}

	/**31
	 * Switches the WebDriver focus to a frame using the frame name or id.
	 *
	 * @param driver   WebDriver instance
	 * @param nameOrId Name or id of the frame
	 * @return True if the switch to the frame by name or id is successful, false
	 *         otherwise
	 */
	@SuppressWarnings("finally")
	public static boolean switchToFrameByNameOrId(WebDriver driver, String nameOrId) {
		boolean flag = false;
		try {
			new WebDriverWait(driver, Duration.ofSeconds(15))
					.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(nameOrId));
			flag = true;
			return true;
		} catch (Exception e) {
			flag = false;
			return false;
		} finally {
			if (flag) {
				System.out.println("Frame with Name \"" + nameOrId + "\" is selected");
				logFrameSwitchStatus(nameOrId, flag);
			} else if (!flag) {
				System.out.println("Frame with Name \"" + nameOrId + "\" is not selected");
			}
			return flag;
		}
	}

	/**32
	 * Switches the WebDriver focus to a new window.
	 *
	 * @param driver WebDriver instance
	 * @return True if the switch to the new window is successful, false otherwise
	 */
	public boolean switchToNewWindow(WebDriver driver) {
		boolean flag = false;
		try {

			Set<String> s = driver.getWindowHandles();
			Object popup[] = s.toArray();
			driver.switchTo().window(popup[1].toString());
			flag = true;
			return flag;
		} catch (Exception e) {
			flag = false;
			return flag;
		} finally {
			if (flag) {
				System.out.println("Window is Navigated with title");
			} else {
				System.out.println("The Window with title: is not Selected");
			}
		}
	}

	/**33
	 * Switches the WebDriver focus back to the old window.
	 *
	 * @param driver WebDriver instance
	 * @return True if the switch to the old window is successful, false otherwise
	 */
	public boolean switchToOldWindow(WebDriver driver) {
		boolean flag = false;
		try {

			Set<String> s = driver.getWindowHandles();
			Object popup[] = s.toArray();
			driver.switchTo().window(popup[0].toString());
			flag = true;
			return flag;
		} catch (Exception e) {
			flag = false;
			return flag;
		} finally {
			if (flag) {
				System.out.println("Focus navigated to the window with title");
			} else {
				System.out.println("The Window with title: is not Selected");
			}
		}
	}

	/**34
	 * Switches the WebDriver focus to a window with a specific title using the
	 * provided index.
	 *
	 * @param driver      WebDriver instance
	 * @param windowTitle Title of the window to switch to
	 * @param count       Index of the window to switch to (1-based)
	 * @return True if the switch to the window by title and index is successful,
	 *         false otherwise
	 */
	public boolean switchWindowByTitle(WebDriver driver, String windowTitle, int count) {
		boolean flag = false;
		try {
			// Get all window handles
			Set<String> windowList = driver.getWindowHandles();

			// Convert the set to an array
			String[] array = windowList.toArray(new String[0]);

			// Switch to the window by index
			driver.switchTo().window(array[count - 1]);

			// Check if the window title contains the specified title
			if (driver.getTitle().contains(windowTitle)) {
				flag = true;
			} else {
				flag = false;
			}
			return flag;
		} catch (Exception e) {
			// Return false if an exception occurs
			return false;
		} finally {
			// Log whether the switch to the window with title is successful or not
			if (flag) {
				System.out.println("Navigated to the window with title");
			} else {
				System.out.println("The Window with title is not Selected");
			}
		}
	}

	/**35
	 * Switches the WebDriver focus back to the default content.
	 *
	 * @param driver WebDriver instance
	 */
	public void switchToDefaultContent(WebDriver driver) {
		driver.switchTo().defaultContent();
	}

	/**36
	 * Handles alerts by either accepting or dismissing them.
	 *
	 * @param driver WebDriver instance
	 * @param action String specifying the action to be performed on the alert
	 *               ("accept" or "dismiss")
	 * @return True if the alert action is successful, false otherwise
	 */
	public static boolean handleAlert(WebDriver driver, String action) {
		boolean flag = false;
		try {
			Alert alert = driver.switchTo().alert();
			if (action.equalsIgnoreCase("accept")) {
				alert.accept();
				flag = true;
			} else if (action.equalsIgnoreCase("dismiss")) {
				alert.dismiss();
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
		} finally {
			logAlertActionStatus(action, flag);
		}
		return flag;
	}

	/**37
	 * Waits for an element to be clickable and returns the WebElement.
	 *
	 * @param driver           WebDriver instance
	 * @param locator          By object representing the locator strategy
	 * @param timeoutInSeconds Maximum time to wait for the element to be clickable
	 * @return WebElement once it is clickable
	 */
	public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		return wait.until(ExpectedConditions.elementToBeClickable(locator));
	}

	/**38
	 * Waits for an element to be present and returns the WebElement.
	 *
	 * @param driver           WebDriver instance
	 * @param locator          By object representing the locator strategy
	 * @param timeoutInSeconds Maximum time to wait for the element to be present
	 * @return WebElement once it is present
	 */
	public static WebElement waitForElementToBePresent(WebDriver driver, By locator, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	/**39
	 * Waits for an element to be visible and returns the WebElement.
	 *
	 * @param driver           WebDriver instance
	 * @param locator          By object representing the locator strategy
	 * @param timeoutInSeconds Maximum time to wait for the element to be visible
	 * @return WebElement once it is visible
	 */
	public static WebElement waitForElementToBeVisible(WebDriver driver, By locator, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**40
	 * Waits for an element to be invisible.
	 *
	 * @param driver           WebDriver instance
	 * @param locator          By object representing the locator strategy
	 * @param timeoutInSeconds Maximum time to wait for the element to be invisible
	 * @return True if the element becomes invisible within the specified time,
	 *         false otherwise
	 */
	public static boolean waitForElementToBeInvisible(WebDriver driver, By locator, int timeoutInSeconds) {
		boolean flag = false;
		try {
			// Wait for the element to be invisible
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
			flag = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Exception e) {
			// Set flag to false if an exception occurs
			flag = false;
		} finally {
			// Log the status of element invisibility
			logElementInvisibilityStatus(locator, flag);
		}
		return flag;
	}

	/**41
	 * Performs a FluentWait for an element to be located.
	 *
	 * @param driver  WebDriver instance
	 * @param locator By object representing the locator strategy
	 * @return WebElement once it is located
	 */
	public static WebElement fluentWait(WebDriver driver, final By locator) {
		Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoAlertPresentException.class);
		return wait.until(driver1 -> driver1.findElement(locator));
	}

	/**42
	 * Capture a screenshot and save it to the specified location.
	 *
	 * @param screenshotName Name for the screenshot
	 * @return Path to the saved screenshot
	 * @throws IOException If an I/O error occurs while saving the screenshot
	 */
	public static String captureScreenshot(String screenshotName) {
		try {
			TakesScreenshot ts = (TakesScreenshot) driver;
			File src = ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(src, new File("./Screenshots/" + screenshotName + ".png"));
			System.out.println("Screenshot captured and saved successfully.");
		} catch (Exception e) {
			System.out.println("Exception occurred while taking screenshot: " + e.getMessage());
		}
		return screenshotName;
	}
	
	/**43
	 * Logs the status of an element (displayed or not displayed).
	 *
	 * @param flag True if the element is displayed, false otherwise
	 */
	public static void logElementStatus(boolean flag) {
		logActionStatus(flag ? "Element is displayed" : "Element is not displayed");
	}

	/**44
	 * Logs the status of option selection in a dropdown.
	 *
	 * @param flag True if the option is selected, false otherwise
	 */
	public static void logOptionSelectionStatus(boolean flag) {
		logActionStatus(flag ? "Option is selected from the drop-down" : "Option is not selected from the drop-down");
	}

	/**45
	 * Logs the status of a generic action.
	 *
	 * @param status String describing the status of the action
	 */
	public static void logActionStatus(String status) {
		StartBrowser.childTest.info(status);
	}

	/**46
	 * Logs the status of switching to a frame by index.
	 *
	 * @param index Index of the frame
	 * @param flag  True if the switch to the frame is successful, false otherwise
	 */
	public static void logFrameSwitchStatus(int index, boolean flag) {
		logFrameSwitchStatus("Frame with index " + index, flag);
	}

	/**47
	 * Logs the status of switching to a frame by WebElement.
	 *
	 * @param frameElement WebElement representing the frame
	 * @param flag         True if the switch to the frame is successful, false
	 *                     otherwise
	 */
	public static void logFrameSwitchStatus(WebElement frameElement, boolean flag) {
		logFrameSwitchStatus("Frame with WebElement " + frameElement.toString(), flag);
	}

	/**48
	 * Logs the status of switching to a frame by name or id.
	 *
	 * @param nameOrId Name or id of the frame
	 * @param flag     True if the switch to the frame is successful, false
	 *                 otherwise
	 */
	public static void logFrameSwitchStatus(String nameOrId, boolean flag) {
		logActionStatus(flag ? "Switched to frame: " + nameOrId : "Unable to switch to frame: " + nameOrId);
	}

	/**49
	 * Logs the status of an alert action.
	 *
	 * @param action String specifying the alert action ("accept" or "dismiss")
	 * @param flag   True if the alert action is successful, false otherwise
	 */
	public static void logAlertActionStatus(String action, boolean flag) {
		logActionStatus(flag ? "Alert " + action + "ed" : "Unable to " + action + " alert");
	}

	/**50
	 * Logs the status of element invisibility.
	 *
	 * @param locator By object representing the locator strategy
	 * @param flag    True if the element becomes invisible, false otherwise
	 */
	public static void logElementInvisibilityStatus(By locator, boolean flag) {
		logActionStatus(flag ? "Element located by " + locator + " is invisible"
				: "Element located by " + locator + " is still visible");
	}

	/**51
	 * Sets the implicit wait for the WebDriver.
	 *
	 * @param driver  WebDriver instance
	 * @param timeOut Time to wait implicitly in seconds
	 */
	public static void implicitWait(WebDriver driver, int timeOut) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	/**52
	 * Sets an explicit wait for the visibility of the given element.
	 *
	 * @param driver  WebDriver instance
	 * @param element WebElement to wait for visibility
	 * @param timeOut Maximum time to wait for the element to be visible
	 */
	public void explicitWait(WebDriver driver, WebElement element, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));// selenium 4.8
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	/**53
	 * Performs a FluentWait for the visibility of the given element and clicks on
	 * it.
	 *
	 * @param driver  WebDriver instance
	 * @param element WebElement to wait for visibility
	 * @param timeOut Maximum time to wait for the element to be visible
	 */
	public void fluentWait(WebDriver driver, WebElement element, int timeOut) {
		Wait<WebDriver> wait = null;
		try {
			// Set up FluentWait
			wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(20)).pollingEvery(Duration.ofSeconds(2))
					.ignoring(Exception.class);
			wait.until(ExpectedConditions.visibilityOf(element));
			element.click();
		} catch (Exception e) {
			// Log exceptions if any
		}
	}

	/**54
	 * Sets the page load timeout for the WebDriver.
	 *
	 * @param driver  WebDriver instance
	 * @param timeOut Time to wait for page load implicitly in seconds
	 */
	public static void pageLoadTimeOut(WebDriver driver, int timeOut) {
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
	}
}
