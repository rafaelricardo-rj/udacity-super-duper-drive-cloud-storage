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

public class HomePage {
    protected static WebDriver driver;

    //<h4>Welcome</h4>
    private By messageBy = By.tagName("h4");

    private By addNewNoteButton = By.id("addNewNote");

    private By deleteNoteButton = By.className("note-delete");
    //private By saveNoteButton = By.id("saveNoteButton");

    @FindBy(id = "logoutButton")
    private WebElement logoutButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "saveNoteButton")
    private WebElement saveNoteButton;

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

    public Boolean isNoteCreated(){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.className("newNoteTest"))).isDisplayed();
    }

    // verify that new note title is created:
    public WebElement getNewNote() {
        return driver.findElement(By.className("newNoteTest"));
    }

    public void createNote(){

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("nav-notes-tab")));
        WebDriverWait wait = new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.elementToBeClickable(addNewNoteButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(addNewNoteButton));

        //Switch to active element here in our case its model dialogue box.
        driver.switchTo().activeElement();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("note-title"))));
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("note-description"))));

        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "Test title" + "';", driver.findElement(By.id("note-title")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value='" + "description test" + "';", driver.findElement(By.id("note-description")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.id("saveNoteButton")));
    }

    public void deleteNote(){
        createNote();
        WebDriverWait wait = new WebDriverWait(driver, 200);
        Boolean isShown = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("note-delete"))).isDisplayed();
        //waitForVisibility(driver.findElement(By.className("note-delete")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(deleteNoteButton));
        driver.navigate().refresh();
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
