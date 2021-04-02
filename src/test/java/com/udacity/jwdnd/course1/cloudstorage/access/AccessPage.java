package com.udacity.jwdnd.course1.cloudstorage.access;

import com.udacity.jwdnd.course1.cloudstorage.BasePage;
import com.udacity.jwdnd.course1.cloudstorage.User;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.UUID;

public class AccessPage extends BasePage {

    AccessTests accessTests;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    @Override
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        this.driver = new ChromeDriver(options);
        this.accessTests = new AccessTests(driver);
    }

    @AfterEach
    public void afterEach() {
        super.afterEach();
    }

    @Test
    public void accessProtectedPage() {
        driver.get("http://localhost:" + this.port + "/home");
        String title = driver.getTitle();
        if(title.equals("home")){
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("logoutButton")));
            driver.get("http://localhost:" + this.port + "/home");
        }
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void accessSignUpPage() {
        driver.get("http://localhost:" + this.port + "/signup");
        String title = driver.getTitle();
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    public void signUpTest() {
        signUp();
        waitTime().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.alert-dark"))).isDisplayed();
        String message = "";
        if(driver.findElement(By.cssSelector("div.alert-dark")).isDisplayed()){
            message = driver.findElement(By.cssSelector("div.alert-dark")).getText();
        }
        Assertions.assertEquals("You successfully signed up! Please continue to the login page.", message);
    }

    @Test
    public void signUpUsernameExist() {
        String message = "";
        String randUsername = UUID.randomUUID().toString().replace("-", "").substring(0,19);
        signUp(new User("Rafael", "Souza", randUsername, "12345"));
        signUp(new User("Rafa", "Leno", randUsername, "12378"));
        waitTime().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.alert-danger"))).isDisplayed();
        message = driver.findElement(By.cssSelector("div.alert-danger")).getText();
        Assertions.assertEquals("The username already exists.", message);
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void loginTest() {
        User user = new User("rafael", "souza", "rafael", "12345");
        signUp(user);
        login(user);
        waitTime().until(ExpectedConditions.presenceOfElementLocated(By.tagName("h4"))).isDisplayed();
        Assertions.assertEquals("Welcome", accessTests.getMessageAfterLogin());
    }

    @Test
    public void logoutTest() {
        String randUsername = UUID.randomUUID().toString().replace("-", "").substring(0,19);
        User user = new User("Rafael", "Souza", randUsername, "12345");
        signUp(user);
        login(user);
        waitTime().until(ExpectedConditions.presenceOfElementLocated(By.id("logoutButton"))).isDisplayed();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("logoutButton")));
        Assertions.assertEquals("Login", driver.getTitle());
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }
}
