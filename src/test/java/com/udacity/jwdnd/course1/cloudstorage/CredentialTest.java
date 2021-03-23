package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CredentialTest {

    protected static WebDriver driver;

    private By addNewCredentialButton = By.id("addNewCredential");
    private By urlInput = By.id("credential-url");
    private By usernameInput = By.id("credential-username");
    private By passwordInput = By.id("credential-password");
    private By submitButton = By.id("saveCredentialButton");

    public CredentialTest(WebDriver driver){
        this.driver = driver;
        if (!driver.getTitle().equals("Home")) {
            throw new IllegalStateException("This is not Home Page of logged in user," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }

    public void createCredential(){

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("nav-credentials-tab")));
        WebDriverWait wait = new WebDriverWait(driver, 500);
        wait.until(ExpectedConditions.elementToBeClickable(addNewCredentialButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(addNewCredentialButton));

        //Switch to active element here in our case its model dialogue box.
        driver.switchTo().activeElement();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(urlInput)));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(usernameInput)));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(passwordInput)));

        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "www.face.com/login" + "';", driver.findElement(urlInput));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "rafaelricardo" + "';", driver.findElement(usernameInput));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "12345" + "';", driver.findElement(passwordInput));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(submitButton));
    }

    public Boolean isCredentialCreated(){
        WebDriverWait wait = new WebDriverWait(driver, 100);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.className("newCredentialTest"))).isDisplayed();
    }
}
