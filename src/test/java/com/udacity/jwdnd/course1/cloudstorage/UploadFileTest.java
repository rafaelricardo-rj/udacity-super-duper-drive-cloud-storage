package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class UploadFileTest {

    protected static WebDriver driver;

    private By fileOpen = By.id("fileUpload");
    private By uploadButton = By.id("uploadButton");
    private By fileDeleteButton = By.id("file-delete");

    public UploadFileTest(WebDriver driver){
        this.driver = driver;
        if (!driver.getTitle().equals("Home")) {
            throw new IllegalStateException("This is not Home Page of logged in user," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public void uploadFile(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(fileOpen));
    }
}