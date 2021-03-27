package com.udacity.jwdnd.course1.cloudstorage.fileUpload;

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
public class UploadFilePage {
    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach(){

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
    public void uploadFile() {
        UploadFileTest uploadFileTest = new UploadFileTest(driver);
        uploadFileTest.uploadFile();
        Assertions.assertEquals(true, uploadFileTest.isFileUploaded());
    }

    @Test
    public void deleteFile() {
        UploadFileTest uploadFileTest = new UploadFileTest(driver);
        uploadFileTest.deleteFile();
        assertThrows(NoSuchElementException.class, () -> {
            uploadFileTest.isFileDeleted();
        });
    }
}
