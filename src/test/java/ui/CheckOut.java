package ui;

import java.time.Duration;
import ui.loginpage;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckOut {
	private WebDriver driver ;
	  private WebDriverWait wait ;
	  private loginpage login = new loginpage (driver);
	public  CheckOut ( WebDriver driver) {
		this.driver=driver;
		 wait = new WebDriverWait(driver, Duration.ofSeconds(100)); // 10 seconds wait
		 
	}
	
	By checkout_button = By.name("checkout");
	By firstname = By.id("first-name");
	By lastname = By.id("last-name");
    By postalcode =  By.id("postal-code");
   By finish_button = By.name("finish");
    By continue_button = By.name("continue");
    By button_menu = By.id("react-burger-menu-btn");
    By logout_link = By.id("logout_sidebar_link");
    By reset_app_link =By.id("reset_sidebar_link");
    By Allitems_link =By.id("inventory_sidebar_link");
    By About_link = By.id("about_sidebar_link");
    By back_to_products = By.name("back-to-products");
    
    void checkout_process ( String first_name ,String last_name, String postal_code ) {
    	//wait for cart 
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(checkout_button));
    	driver.findElement(checkout_button).click();
    	 wait.until(ExpectedConditions.visibilityOfElementLocated(firstname));
    	driver.findElement(firstname).sendKeys(first_name);
    	driver.findElement(lastname).sendKeys(last_name);
    	driver.findElement(postalcode).sendKeys(postal_code);
    	driver.findElement(continue_button).click();
      	 wait.until(ExpectedConditions.visibilityOfElementLocated(finish_button));
    	driver.findElement(finish_button).click();
    	
    

}
    public void scrollIntoView(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    public void clickWithRetry(By locator) {
        int attempts = 0;
        while (attempts < 3) {
            try {
                scrollIntoView(locator);
                wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
                return; // Exit if click is successful
            } catch (ElementClickInterceptedException e) {
                attempts++;
                try {
                    Thread.sleep(1000); // Wait before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
        }
    }
    void back () {
    	
    	
    	scrollIntoView(back_to_products);
    	wait.until(ExpectedConditions.elementToBeClickable(back_to_products)).click();
    }
    
    void logout() {
        clickWithRetry(button_menu);
        clickWithRetry(logout_link);
        wait.until(ExpectedConditions.visibilityOfElementLocated(login.user_name));
    }

    void reset_app_state() {
        clickWithRetry(button_menu);
        clickWithRetry(reset_app_link);
    }

    void about_app() {
        clickWithRetry(button_menu);
        clickWithRetry(About_link);
    }

    void got_to_all_items() {
        clickWithRetry(button_menu);
        clickWithRetry(Allitems_link);
    } 
}
