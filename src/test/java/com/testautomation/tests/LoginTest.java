package com.testautomation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

/**
 * Test class for login functionality
 * Contains smoke and regression test cases
 */
public class LoginTest {
    
    private WebDriver driver;
    private WebDriverWait wait;
    
    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    /**
     * Smoke test: Verify successful login with valid credentials
     */
    @Test(groups = {"smoke", "login"})
    public void testSuccessfulLogin() {
        // Navigate to a demo login page
        driver.get("https://the-internet.herokuapp.com/login");
        
        // Enter valid credentials
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        
        usernameField.sendKeys("tomsmith");
        passwordField.sendKeys("SuperSecretPassword!");
        loginButton.click();
        
        // Verify successful login
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flash.success")));
        WebElement successMessage = driver.findElement(By.cssSelector(".flash.success"));
        
        Assert.assertTrue(successMessage.getText().contains("You logged into a secure area!"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/secure"));
    }
    
    /**
     * Regression test: Verify login fails with invalid credentials
     */
    @Test(groups = {"regression", "login"})
    public void testFailedLoginWithInvalidCredentials() {
        // Navigate to a demo login page
        driver.get("https://the-internet.herokuapp.com/login");
        
        // Enter invalid credentials
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        
        usernameField.sendKeys("invaliduser");
        passwordField.sendKeys("invalidpassword");
        loginButton.click();
        
        // Verify login failure
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".flash.error")));
        WebElement errorMessage = driver.findElement(By.cssSelector(".flash.error"));
        
        Assert.assertTrue(errorMessage.getText().contains("Your username is invalid!"));
        Assert.assertFalse(driver.getCurrentUrl().contains("/secure"));
    }
    
    /**
     * Regression test: Verify login form validation for empty fields
     */
    @Test(groups = {"regression", "login"})
    public void testLoginFormValidation() {
        // Navigate to a demo login page
        driver.get("https://the-internet.herokuapp.com/login");
        
        // Try to login with empty fields
        WebElement loginButton = driver.findElement(By.cssSelector("button[type='submit']"));
        loginButton.click();
        
        // Verify form validation (the demo site doesn't have client-side validation, 
        // so we check that we're still on the login page)
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
        
        // Verify login form is still present
        WebElement usernameField = driver.findElement(By.id("username"));
        WebElement passwordField = driver.findElement(By.id("password"));
        
        Assert.assertTrue(usernameField.isDisplayed());
        Assert.assertTrue(passwordField.isDisplayed());
    }
} 