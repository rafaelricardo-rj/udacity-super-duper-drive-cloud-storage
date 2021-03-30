package com.udacity.jwdnd.course1.cloudstorage.fileUpload;

import com.udacity.jwdnd.course1.cloudstorage.BasePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UploadFilePage extends BasePage {

    @BeforeAll
    static void beforeAll(){
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach(){
        super.beforeEach();
    }

    @AfterEach
    public void afterEach() {
        super.afterEach();
    }

    @Test
    public void uploadFile() {
        UploadFileTest uploadFileTest = new UploadFileTest(driver);
        uploadFileTest.uploadFile();
        Assertions.assertEquals(true, uploadFileTest.isFileUploaded());
    }

    @Test
    public void download() throws InterruptedException {
        UploadFileTest uploadFileTest = new UploadFileTest(driver);
        uploadFileTest.downloadFile();
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
