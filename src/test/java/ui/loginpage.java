package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class loginpage {
	 private  WebDriver driver;
	public
	loginpage ( WebDriver driver){
		this.driver =driver ;
		
		
	}
	 public void openpage (String url) {
		 driver.get(url);
		 driver.manage().window().maximize();
	 }
	  By user_name = By.id("user-name") ;
	  By password = By.id("password");
	  By login  = By.name("login-button");
	  By emptyuser = By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3") ;
	  By emptypass = By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3");
	  By lockeduser = By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3");
	  By wronguserorpass =By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3");
	  public  void set_user_name (String name) {
		  driver.findElement(user_name).sendKeys(name);
	  }
	
	  
	  public  void set_password (String passwordd) {
		  driver.findElement(password).sendKeys(passwordd);
		  
	  }
	  
	  
	  public  void click_login_button ( ) {
		  driver.findElement(login).click();;
	  }
	  
	  public void login (String name ,String passwordd) {
		  driver.findElement(user_name).sendKeys(name);
		  driver.findElement(password).sendKeys(passwordd);
	  }
	  
	  public void emptyusertext () {
		  String  UsernameExpected_Div="Epic sadface: Username is required";
		   String actualerror=   driver.findElement(emptyuser).getText();
		   Assert.assertEquals(actualerror, UsernameExpected_Div, "The error message for empty username does not match the expected value");
	  }
	  
	  public void emptypasstext () {
		  String  passwordExpected_Div="Epic sadface: Password is required";
		   String actualerror=   driver.findElement(emptypass).getText();
		   Assert.assertEquals(actualerror, passwordExpected_Div, "The error message for empty password does not match the expected value");
	  }
	  
	  
	  public void lockedusertext () {
		  String  lockeduser_Div="Epic sadface: Sorry, this user has been locked out.";
		   String actualerror=   driver.findElement(lockeduser).getText();
		   Assert.assertEquals(actualerror, lockeduser_Div, "The error message for locked user does not match the expected value");
	  }
	  
	  public void wrong_user_or_passtext () {
		  String  lockeduser_Div="Epic sadface: Username and password do not match any user in this service";
		   String actualerror=   driver.findElement(wronguserorpass).getText();
		   Assert.assertEquals(actualerror, lockeduser_Div, "The error message for  wrong user or pass does not match the expected value");
	  }
	  
	  public void  close() {
		  driver.close();
	  }
}
