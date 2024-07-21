package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;


public class LoginTests {
    WebDriver edgeDriver = new EdgeDriver();

    @BeforeClass
    public void setUp(){

        edgeDriver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        String title = edgeDriver.getTitle();
        if(!title.equals("OrangeHRM")){
            Assert.assertEquals(title, "OrangeHRM");
        }
    }

    @Test
    public void correctLogin(){

        //We can also search by class or by id etc.
        WebElement usernameField = edgeDriver.findElement(By.name("username"));
        WebElement passwordField = edgeDriver.findElement(By.name("password"));
        WebElement loginButton = edgeDriver.findElement(By.className("oxd-button oxd-button—medium oxd-button—main orangehrm-login-button"));

        // Enter valid credentials
        usernameField.sendKeys("Admin");
        passwordField.sendKeys("admin123");
        loginButton.click();

        // Wait for the dashboard page to load
        try {
            Thread.sleep(5000); // Explicit wait strategy
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert that the dashboard page is loaded
        Assert.assertEquals(edgeDriver.getCurrentUrl(),"https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");

    }

    @Test
    public void invalidLogin(){
        // Locate username and password fields and login button
        WebElement usernameField = edgeDriver.findElement(By.name("username"));
        WebElement passwordField = edgeDriver.findElement(By.name("password"));
        WebElement loginButton = edgeDriver.findElement(By.className("oxd-button oxd-button—medium oxd-button—main orangehrm-login-button"));

        // Enter valid username and invalid password
        usernameField.sendKeys("Admin");
        passwordField.sendKeys("wrongpassword");
        loginButton.click();

        // Wait for the error message to appear
        try {
            Thread.sleep(5000); // Explicit wait strategy used
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Assert that the error message is displayed
        WebElement errorMessage = edgeDriver.findElement(By.cssSelector("p.oxd-text.oxd-text--p.oxd-alert-content-text"));
        Assert.assertTrue(errorMessage.isDisplayed(), "Invalid password");
    }

    @AfterClass
    public void tearDown() {
        if (edgeDriver != null) {
            edgeDriver.quit();
        }
    }

}