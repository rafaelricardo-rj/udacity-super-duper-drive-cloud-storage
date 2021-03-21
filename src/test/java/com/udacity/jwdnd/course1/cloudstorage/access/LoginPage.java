package com.udacity.jwdnd.course1.cloudstorage.access;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    private final WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void tryLogin(String username, String password) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", usernameInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", passwordInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);
    }
}
