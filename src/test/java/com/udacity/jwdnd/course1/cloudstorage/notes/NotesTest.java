package com.udacity.jwdnd.course1.cloudstorage.notes;

import com.udacity.jwdnd.course1.cloudstorage.HomePage;
import com.udacity.jwdnd.course1.cloudstorage.access.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesTest {
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
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void createNote() throws InterruptedException {
        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.tryLogin("rafaelricardo", "12345");
        driver.get("http://localhost:" + this.port + "/home");
        HomePage homePage = new HomePage(driver);
        Thread.sleep(2000);
        homePage.createNote();
        //Assertions.assertEquals(true, homePage.isNoteCreated());
    }
}
