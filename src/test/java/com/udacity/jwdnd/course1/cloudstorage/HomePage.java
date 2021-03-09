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

public class HomePage {
    protected static WebDriver driver;

    //<h4>Welcome</h4>
    private By messageBy = By.tagName("h4");

    private By newNote = By.className("newNoteTest");

    private By addNewNoteButton = By.id("addNewNote");

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
        Actions bar = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.className("newNoteTest"))).isDisplayed();
    }

    public void createNote(){
        driver.findElement(By.id("nav-notes-tab")).click();

        driver.findElement(By.id("nav-notes-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.elementToBeClickable(addNewNoteButton)).click();
        WebDriverWait wait_modal = new WebDriverWait(driver, 10);

        //Switch to active element here in our case its model dialogue box.
        driver.switchTo().activeElement();

        wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("note-title")))).sendKeys("sdsds");
        wait_modal.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("note-description")))).sendKeys("description");

        //waitForVisibility(saveNoteButton);
        driver.findElement(By.id("saveNoteButton")).click();
        //((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveNoteButton);
        Assertions.assertEquals(true, isNoteCreated());
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
        this.logoutButton.click();
    }
}
