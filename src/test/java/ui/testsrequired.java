package ui;

import java.time.Duration;

import org.openqa.selenium.By;
import ui.ProductsPage;
import ui.CheckOut;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import io.github.bonigarcia.wdm.WebDriverManager;
import ui.loginpage ;

public class testsrequired {
    private ChromeDriver driver;
    private loginpage login;
    private ProductsPage product ;
    private CheckOut CheckOutProcess ;
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
    int  ProductsPagesetup=0 ;
	@BeforeClass
	void setup () {
		WebDriverManager.chromedriver().setup();
		    driver = new ChromeDriver ();
		    login = new loginpage(driver) ;
		    product=new ProductsPage (driver) ;
		    CheckOutProcess = new CheckOut (driver);
		    String url ="https://www.saucedemo.com/";
		    driver.get(url);
		   
	}
	@DataProvider
	public Object[][] loginset (){
	 return new	Object[][]   {
	    
         {"locked_out_user", "secret_sauce"},  // Valid credentials
         {"problem_user", "secret_sauce"}, // Valid credentials
         {"performance_glitch_user", "secret_sauce"},  // Valid credentials
         {"error_user", "secret_sauce"},  // Valid credentials
         {"visual_user", "secret_sauce"},  // Valid credentials
         {"wrong", "secret_sauce"},  // not Valid user
        {"visual_user", "wrong"}, // not Valid password
         {"", "secret_sauce"},  // not empty user
           {"visual_user", ""},// not empty password
        {"", ""},  	 //  not Valid credentials
        {"standard_user", "secret_sauce"},  // Valid credentials			
		};
		
	}
	
	
	
	@Test(dataProvider="loginset",priority=1,description="Test for login pagee")
	
	public void Testlogin (String username , String password ) throws InterruptedException {
		  String url ="https://www.saucedemo.com/";

	  	  login.openpage(url);
		
  	  login.set_user_name(username);
  	  login.set_password(password);
  	  login.click_login_button();
  	// wait.until(ExpectedConditions.visibilityOfElementLocated(product.products_name));
  	 
  	   if (username.trim().isEmpty()) {
  		  login.emptyusertext();
  	  }
  	  else if( password.trim().isEmpty()) {
  		  login.emptypasstext();
  	  }
  	  else if (username.equals("locked_out_user")) {
  		   login.lockedusertext();
  	   }
  	  
  	  else if (!username.equals("standard_user") &&
              !username.equals("problem_user") &&
              !username.equals("performance_glitch_user") &&
              !username.equals("error_user") &&
              !username.equals("visual_user")) {
  		  login.wrong_user_or_passtext();
  	  }
  	  
  
    }
	
	@DataProvider
	public Object [][] productset (){
		return  new Object [][] {
			   {"Sauce Labs Onesie", "$7.99"},
		        {"Sauce Labs Bike Light", "$9.99"},
		        {"Sauce Labs Bolt T-Shirt", "$15.99"},
		        {"Test.allTheThings() T-Shirt (Red)", "$15.99"},
		        { "Sauce Labs Backpack", "$29.99"},
		        {"Sauce Labs Fleece Jacket", "$49.99"},
		};
	}
	
	@Test(dataProvider="productset" , priority=2,description="Test for product pagee")
	public void test_products(String product_name , String price ) throws InterruptedException {
		
		if (ProductsPagesetup==0){  
		String url ="https://www.saucedemo.com/";
		product.openpage(url);
		product.verify_sorting_ascending();
		product.verify_sorting_assending_alpha();
		product.verify_sorting_descinding();
		product.verify_sorting_dessending_alpha();
		
		
		
		}
		product.addtocart(product_name);
		product.removefromcart("Sauce Labs Onesie");
		ProductsPagesetup++;
		product.verifyproductsname(product_name);
		product.verify_price_of_product(product_name, price);
	 
	 

	    
	
	}
	@Test(dependsOnMethods = {"test_products"}, priority=3,description="Test for checkout  pagee")
	public void cart_checkout() throws InterruptedException{
		product.go_to_cart();
		CheckOutProcess.checkout_process("mark", "harry", "145s");
		
		
	}
	//priority=4,description="Test for   paglee"
	@Test (dependsOnMethods = {"cart_checkout"} )
	public void test_menu_button() throws InterruptedException {
		CheckOutProcess.back();
		CheckOutProcess.reset_app_state();
		CheckOutProcess.got_to_all_items();
		CheckOutProcess.logout();
		
		}
    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit(); // Close the browser after all tests
        }
    }

}
