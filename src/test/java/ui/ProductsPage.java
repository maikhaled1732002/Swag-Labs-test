package ui;


import java.time.Duration;
import ui.loginpage;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class ProductsPage {

	private WebDriver driver ;
	  private WebDriverWait wait ;
	  private loginpage login;
	public  ProductsPage ( WebDriver driver) {
		this.driver=driver;
		 wait = new WebDriverWait(driver, Duration.ofSeconds(100)); // 100 seconds wait
	}
	public void openpage (String url) {
		 driver.get(url);
		 driver.manage().window().maximize();
		 login = new loginpage(driver) ;
		 login.set_user_name("standard_user");
	  	 login.set_password("secret_sauce");
	    login.click_login_button();
	    wait.until(ExpectedConditions.visibilityOfElementLocated(products_name));
	 }
	
	By products_name = By.className ("inventory_item_name");
	By addcart = By.className("btn_inventory");
	By price = By.className("inventory_item_price");
	By sort_container = By.className("product_sort_container");
	By cart_icon =By.className("shopping_cart_link");
	
	
	void verifyproductsname (String expected) {
		List<WebElement> products = driver.findElements(products_name);
		boolean found =false;
		for(WebElement product : products) {
			if (product.getText().equals(expected)) {
				found =true;
				break ;
			}
			
		}
		Assert.assertTrue(found, "product name doesnot match"+expected);
	}
	

	
	void verify_price_of_product (String expectedProduct , String expectedPrice){
		boolean price_right = false ;
		List<WebElement> products= driver.findElements(products_name);
		List<WebElement> Prices =driver.findElements(price);
		//fisrt make sure there is a price in this product 
		for(int i=0 ;i<products.size();i++) {
			if(products.get(i).getText().equals(expectedProduct)) {
				if(Prices.get(i).getText().equals(expectedPrice)) {
				price_right = true ;
				break;
				}
				
			}
			
		}
		if (price_right == false ) {
		
		 Assert.fail("Price does not match for product: " + expectedProduct + 
                 ". Expected price: " + expectedPrice);
	}
	}
	
	
	void verify_sorting_ascending (  ) {
		//prices before sort
		List<WebElement>pricesbefore = driver.findElements(price);
		Vector <Double>before_prices =new Vector <Double> () ;
		for(WebElement price:pricesbefore) {//change prices to double
			before_prices.add (Double.valueOf((price.getText().replace("$", ""))));
			
		}
		Collections.sort(before_prices);
		
		Select Sort_order = new Select (driver.findElement(sort_container));
		Sort_order.selectByVisibleText ("Price (low to high)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(price));
		
		List<WebElement>pricesafter = driver.findElements(price);
		Vector <Double>after_prices =new Vector <Double> () ;
		for(WebElement price:pricesafter) {//change prices to double
			after_prices.add (Double.valueOf((price.getText().replace("$", ""))));
			
		}
		
		Assert.assertEquals(after_prices, before_prices,"sorting from low to high is done wrong");
	}
	
	void verify_sorting_descinding(   ) {
		//prices before sort
		List<WebElement>pricesbefore = driver.findElements(price);
		Vector <Double>before_prices =new Vector <Double> () ;
		for(WebElement price:pricesbefore) {//change prices to double
			before_prices.add (Double.valueOf((price.getText().replace("$", ""))));
			
		}
		Collections.sort(before_prices);
		Collections.reverse(before_prices);
		
		Select Sort_order = new Select (driver.findElement(sort_container));
		Sort_order.selectByVisibleText ("Price (high to low)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(price));
		
		List<WebElement>pricesafter = driver.findElements(price);
		Vector <Double>after_prices =new Vector <Double> () ;
		for(WebElement price:pricesafter) {//change prices to double
			after_prices.add (Double.valueOf((price.getText().replace("$", ""))));
			
		}
		
		Assert.assertEquals(after_prices, before_prices,"sorting from high to low is done wrong");
	}
	
	void verify_sorting_assending_alpha(  ) {
		//prices before sort
		List<WebElement>productsbefore = driver.findElements(products_name);
		Vector <String>before_Products =new Vector <String> () ;
		for(WebElement price:productsbefore) {//change prices to double
			before_Products.add (price.getText());
			
		}

		Collections.sort(before_Products);
		
		
		Select Sort_order = new Select (driver.findElement(sort_container));
		Sort_order.selectByVisibleText ("Name (A to Z)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(products_name));
		
		List<WebElement>productsafter = driver.findElements(products_name);
		Vector <String>after_products =new Vector <String> () ;
		for(WebElement price:productsafter) {//change prices to double
			after_products.add (price.getText());
			
		}
		
		Assert.assertEquals(after_products, before_Products,"sorting from A to Z  is done wrong");
	}
	
	
	void verify_sorting_dessending_alpha()  {
		//prices before sort
		List<WebElement>productsbefore = driver.findElements(products_name);
		Vector <String>before_Products =new Vector <String> () ;
		for(WebElement price:productsbefore) {//change prices to double
			before_Products.add (price.getText());
			
		}

		Collections.sort(before_Products);
		Collections.reverse(before_Products);
		
		Select Sort_order = new Select (driver.findElement(sort_container));
		Sort_order.selectByVisibleText ("Name (Z to A)");
		wait.until(ExpectedConditions.visibilityOfElementLocated(products_name));
		
		List<WebElement>productsafter = driver.findElements(products_name);
		Vector <String>after_products =new Vector <String> () ;
		for(WebElement price:productsafter) {//change prices to double
			after_products.add (price.getText());
			
		}
		
		Assert.assertEquals(after_products, before_Products,"sorting from Z to A is done wrong");
	}
	
	void addtocart ( String productwanted) {
		List<WebElement> products = driver.findElements(products_name);
		List<WebElement> cartbuttons = driver.findElements(addcart);
		 wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(products_name));
		boolean found =false;
		for (int i =0 ; i<products.size() ;i++){
			if(products.get(i).getText().trim().equals(productwanted)) {
				//check button is add not remove
				wait.until(ExpectedConditions.elementToBeClickable(cartbuttons.get(i))); // Ensure button is clickable
				if(cartbuttons.get(i).getAttribute("id").startsWith("remove")){
					 System.out.println("The product is already in the cart. Removing...");
				}
				else if (cartbuttons.get(i).getAttribute("id").startsWith("add")){
					 System.out.println("The product can be added");
					 cartbuttons.get(i).click();
				}
				
				 found = true;
				 break;
			}
		}
		   Assert.assertTrue(found, "Product not found: " + productwanted);
	}
	
	void removefromcart ( String producttoremove) {
		List<WebElement> products = driver.findElements(products_name);
		List<WebElement> cartbuttons = driver.findElements(addcart);
		//boolean found =false;
		for (int i =0 ; i<products.size() ;i++){
			if(products.get(i).getText().trim().equals(producttoremove)) {
				//check button is add not remove
				if(cartbuttons.get(i).getAttribute("id").startsWith("remove")){
					 System.out.println("The product can be removed");
					 cartbuttons.get(i).click();
				}
				else if (cartbuttons.get(i).getAttribute("id").startsWith("add")){
					 System.out.println("The product cannot be removed it is not in the cart");
					
				}
				
				// found = true;
				 //break;
			}
		}
		// Assert.assertTrue(found, "Product not found: " + producttoremove);
	}
	
	void go_to_cart() {
		driver.findElement(cart_icon).click();
	}
}
 