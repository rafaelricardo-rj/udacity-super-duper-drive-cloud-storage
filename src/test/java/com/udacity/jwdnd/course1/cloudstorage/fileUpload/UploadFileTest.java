package com.udacity.jwdnd.course1.cloudstorage.fileUpload;

import com.udacity.jwdnd.course1.cloudstorage.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class UploadFileTest {

    protected static WebDriver driver;

    private By fileOpen = By.id("fileUpload");
    private By uploadButton = By.id("uploadButton");
    private By fileDeleteButton = By.className("file-delete");
    private WebDriverWait wait;

    public UploadFileTest(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 200);
        if (!driver.getTitle().equals("Home")) {
            throw new IllegalStateException("This is not Home Page of logged in user," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public void uploadFile(){

        File file = new File("src/test/resources/fileForUploadTests.ods");
        String pathToTheFile = file.getAbsolutePath();
        driver.findElement(fileOpen).sendKeys(pathToTheFile);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(uploadButton));
    }

    public Boolean isFileUploaded(){
        //return wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("newFileUploadedTest"))));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.className("file-delete"))).isDisplayed();
    }

    public Boolean isFileDeleted(){
        return wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("file-delete"))));
    }

    public void downloadFile() throws InterruptedException {
        //uploadFile();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("file-delete")));
        WebElement link = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[1]/div/table/tbody/tr[1]/td/a"));
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        Thread.sleep(3000);
    }

    public void deleteFile(){
        WebDriverWait wait = new WebDriverWait(driver, 500);
        wait.until(ExpectedConditions.presenceOfElementLocated(fileDeleteButton)).isDisplayed();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(fileDeleteButton));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(fileDeleteButton)));
    }
}