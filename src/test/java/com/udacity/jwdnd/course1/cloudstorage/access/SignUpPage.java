package com.udacity.jwdnd.course1.cloudstorage.access;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    @FindBy(name = "firstName")
    private WebElement firstNameInput;

    @FindBy(name = "lastName")
    private WebElement lastNameInput;

    @FindBy(name = "username")
    private WebElement usernameInput;

    @FindBy(name = "password")
    private WebElement passwordInput;

    @FindBy(id = "signUpButton")
    private WebElement signUpButton;

    @FindBy(id = "responseSignUp")
    private WebElement divResponseSignUp;

    private final WebDriver driver;

    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void trySignUp(String firstName, String lastName, String username, String password) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + firstName + "';", firstNameInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + lastName + "';", lastNameInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + username + "';", usernameInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + password + "';", passwordInput);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", signUpButton);
    }
}
