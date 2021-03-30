package com.udacity.jwdnd.course1.cloudstorage.credential;

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
    private By deleteCredentialButton = By.className("credential-delete");
    private By editCredentialButton = By.className("credential-open");
    private By newCredentialTest = By.className("newCredentialTest");

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

    public void deleteCredential(){
        //createCredential();
        WebDriverWait wait = new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("newCredentialTest"))).isDisplayed();
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(deleteCredentialButton));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(deleteCredentialButton)));
    }

    public WebElement getNewCredential() {
        return driver.findElement(newCredentialTest);
    }

    public void editCredential(){
        createCredential();
        WebDriverWait wait = new WebDriverWait(driver, 200);
        //wait the credential to appear
        wait.until(ExpectedConditions.presenceOfElementLocated(editCredentialButton)).isDisplayed();
        //click on edit button
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(editCredentialButton));
        //wait the url input visibility
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(urlInput)));
        //fetch the original url value
        String urlBeforeEdit = driver.findElement(urlInput).getAttribute("value");
        //fetch the original username value
        String usernameBeforeEdit = driver.findElement(usernameInput).getAttribute("value");
        //fetch the original password value
        String passwordBeforeEdit = driver.findElement(passwordInput).getAttribute("value");
        //set a new url
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "https//myspace.com" + "';", driver.findElement(urlInput));
        //set new username
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "description test Edited" + "';", driver.findElement(usernameInput));
        //set new password
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "description test Edited" + "';", driver.findElement(passwordInput));
        //save the credential
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(submitButton));
        //wait the modal
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("credentialModal"))));
        //open the note again to check if the note has been changed
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(editCredentialButton));
        //fetch the url
        String urlAfterEdit = driver.findElement(urlInput).getAttribute("value");
        //fetch the username
        String usernameAfterEdit = driver.findElement(usernameInput).getAttribute("value");
        //fetch the username
        String passwordAfterEdit = driver.findElement(passwordInput).getAttribute("value");
        assertEquals(false, urlBeforeEdit.equals(urlAfterEdit));
        assertEquals(false, usernameBeforeEdit.equals(usernameAfterEdit));
        assertEquals(false, passwordBeforeEdit.equals(passwordAfterEdit));
    }
}
