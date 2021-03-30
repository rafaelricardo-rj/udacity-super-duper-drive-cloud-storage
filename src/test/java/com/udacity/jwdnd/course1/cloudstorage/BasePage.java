package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.access.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.access.SignUpPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BasePage {

    @LocalServerPort
    protected int port;

    protected WebDriver driver;

    protected void beforeEach() {
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.prompt_for_download", false);
        options.setExperimentalOption("prefs", prefs);

        this.driver = new ChromeDriver(options);

        driver.get("http://localhost:" + this.port + "/signup");
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.trySignUp("Rafael", "Souza", "rafaelricardo", "12345");

        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.tryLogin("rafaelricardo", "12345");
        driver.get("http://localhost:" + this.port + "/home");
    }

    protected void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }
}
