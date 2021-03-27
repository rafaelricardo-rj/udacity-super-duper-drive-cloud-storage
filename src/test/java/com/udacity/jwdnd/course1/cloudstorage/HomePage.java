package com.udacity.jwdnd.course1.cloudstorage;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePage {
    protected static WebDriver driver;

    //<h4>Welcome</h4>
    private By messageBy = By.tagName("h4");

    private By addNewNoteButton = By.id("addNewNote");

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesAba;

    public HomePage(WebDriver driver){
        this.driver = driver;
        if (!driver.getTitle().equals("Home")) {
            throw new IllegalStateException("This is not Home Page of logged in user," +
                    " current page is: " + driver.getCurrentUrl());
        }
    }
    /**
     * Get message (h4 tag)
     *
     * @return String message text
     */
    public String getMessageText() {
        return driver.findElement(messageBy).getText();
    }

    public HomePage manageProfile() {
        // Page encapsulation to manage profile functionality
        return new HomePage(driver);
    }

    private void waitForVisibility(WebElement element) throws Error {
        new WebDriverWait(driver, 40)
                .until(ExpectedConditions.visibilityOf(element));
    }

    public void logout(){
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", logoutButton);
    }
}
