package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.access.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.access.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class CredentialPage {
    @LocalServerPort
    private int port;

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {

        this.driver = new ChromeDriver();

        driver.get("http://localhost:" + this.port + "/signup");
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.trySignUp("Rafael", "Souza", "rafaelricardo", "12345");

        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.tryLogin("rafaelricardo", "12345");
        driver.get("http://localhost:" + this.port + "/home");
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void createCredential() {
        CredentialTest credentialTest = new CredentialTest(driver);
        credentialTest.createCredential();
        Assertions.assertEquals(true, credentialTest.isCredentialCreated());
    }

    @Test
    public void deleteCredential() throws InterruptedException {
        CredentialTest credentialTest = new CredentialTest(driver);
        credentialTest.createCredential();
        credentialTest.deleteCredential();
        // test there should be no note data on homepage:
        assertThrows(NoSuchElementException.class, () -> {
            credentialTest.getNewCredential();
        });
    }

    @Test
    public void editCredential(){
        CredentialTest credentialTest = new CredentialTest(driver);
        credentialTest.editCredential();
    }
}
