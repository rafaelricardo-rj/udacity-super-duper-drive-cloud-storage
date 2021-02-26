package com.udacity.jwdnd.course1.cloudstorage.access;

import org.openqa.selenium.By;
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

    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void trySignUp(String firstName, String lastName, String username, String password) {
        firstNameInput.sendKeys(String.valueOf(firstName));
        lastNameInput.sendKeys(String.valueOf(lastName));
        usernameInput.sendKeys(String.valueOf(username));
        passwordInput.sendKeys(String.valueOf(password));
        signUpButton.click();
    }
}
