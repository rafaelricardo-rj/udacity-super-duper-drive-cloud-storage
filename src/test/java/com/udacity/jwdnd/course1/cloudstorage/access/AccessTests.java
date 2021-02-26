package com.udacity.jwdnd.course1.cloudstorage.access;

import com.udacity.jwdnd.course1.cloudstorage.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccessTests {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    private LoginPage loginPage;
    private SignUpPage signUpPage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void tryToAccessProtectedPage() {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void tryToAccessSignUpPage() {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    public void tryLogIn() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/login");
        loginPage = new LoginPage(driver);
        loginPage.tryLogin("rafaelricardo", "12345");
        HomePage homePage = new HomePage(driver);
        Thread.sleep(1000);
        Assertions.assertEquals("Welcome", homePage.getMessageText());
    }

    @Test
    public void tryToSignUpUsernameExist() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/signup");
        signUpPage = new SignUpPage(driver);
        signUpPage.trySignUp("Raul", "Souza","raul", "12345");
        Thread.sleep(1000);
        //System.out.println(driver.findElement(By.cssSelector("div.alert-danger")).getText());
        String message = "";
        if(driver.findElement(By.cssSelector("div.alert-danger")).isDisplayed()){
            message = driver.findElement(By.cssSelector("div.alert-danger")).getText();
        }
        Assertions.assertEquals("The username already exists.", message);
    }

    @Test
    public void tryToSignUp() throws InterruptedException {
        String randUsername = UUID.randomUUID().toString().replace("-", "").substring(0,19);
        driver.get("http://localhost:" + this.port + "/signup");
        signUpPage = new SignUpPage(driver);
        signUpPage.trySignUp("Ricardo", "Souza",randUsername, "12345");
        Thread.sleep(1000);
        String message = "";
        if(driver.findElement(By.cssSelector("div.alert-dark")).isDisplayed()){
            message = driver.findElement(By.cssSelector("div.alert-dark")).getText();
        }
        Assertions.assertEquals("You successfully signed up! Please continue to the login page.", message);
    }

    @Test
    public void tryToLogOut() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/login");
        loginPage = new LoginPage(driver);
        loginPage.tryLogin("rafaelricardo", "12345");
        driver.get("http://localhost:" + this.port + "/home");
        Thread.sleep(1000);
        WebElement logoutButton = driver.findElement(By.id("logoutButton"));
        logoutButton.click();
        Assertions.assertEquals("Login", driver.getTitle());
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }
}
