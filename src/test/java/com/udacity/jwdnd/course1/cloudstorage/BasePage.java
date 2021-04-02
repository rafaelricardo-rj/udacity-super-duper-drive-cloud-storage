package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.access.AccessTests;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
        User user = new User("Rafael", "Souza", "rafael", "12345");
        signUp(user);
        login(user);
    }

    protected void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    protected void signUp(User user){
        driver.get("http://localhost:" + this.port + "/signup");
        AccessTests accessTests = new AccessTests(driver);
        accessTests.trySignUp(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());
    }

    protected void signUp(){
        driver.get("http://localhost:" + this.port + "/signup");
        String randUsername = UUID.randomUUID().toString().replace("-", "").substring(0,19);
        AccessTests accessTests = new AccessTests(driver);
        accessTests.trySignUp("Rafael", "Souza", randUsername, "12345");
    }

    protected void login(User user){
        driver.get("http://localhost:" + this.port + "/login");
        AccessTests accessTests = new AccessTests(driver);
        accessTests.tryLogin(user.getUsername(), user.getPassword());
        driver.get("http://localhost:" + this.port + "/home");
    }

    protected WebDriverWait waitTime(){
        return new WebDriverWait(driver, 200);
    }
}
