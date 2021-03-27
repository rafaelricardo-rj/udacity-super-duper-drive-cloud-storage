package com.udacity.jwdnd.course1.cloudstorage.fileUpload;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;

public class UploadFileTest {

    protected static WebDriver driver;

    private By fileOpen = By.id("fileUpload");
    private By uploadButton = By.id("uploadButton");
    private By fileDeleteButton = By.className("file-delete");

    public UploadFileTest(WebDriver driver){
        this.driver = driver;
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
        WebDriverWait wait = new WebDriverWait(driver, 200);
        //return wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("newFileUploadedTest"))));
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.className("file-delete"))).isDisplayed();
    }

    public Boolean isFileDeleted(){
        WebDriverWait wait = new WebDriverWait(driver, 200);
        return wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("file-delete"))));
    }

    public void deleteFile(){
        WebDriverWait wait = new WebDriverWait(driver, 500);
        wait.until(ExpectedConditions.presenceOfElementLocated(fileDeleteButton)).isDisplayed();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(fileDeleteButton));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(fileDeleteButton)));
    }
}