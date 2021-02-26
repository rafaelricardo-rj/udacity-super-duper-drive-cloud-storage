package com.udacity.jwdnd.course1.cloudstorage.access;

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

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void tryLogin(String username, String password) {
        usernameInput.sendKeys(String.valueOf(username));
        passwordInput.sendKeys(String.valueOf(password));
        loginButton.click();
    }
}
