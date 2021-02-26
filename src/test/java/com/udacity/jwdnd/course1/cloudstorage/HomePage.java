package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;

public class HomePage {
    protected static WebDriver driver;

    //<h4>Welcome</h4>
    private By messageBy = By.tagName("h4");

    private By newNote = By.cssSelector("table.tbody.tr.newNote");

    private By addNewNoteButton = By.id("addNewNote");

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
        return driver.findElement(newNote).isDisplayed();
    }

    public void createNote(){
        driver.findElement(By.id("nav-notes-tab")).click();

        driver.findElement(By.id("nav-notes-tab")).click();
        WebDriverWait wait = new WebDriverWait(driver, 200);
        wait.until(ExpectedConditions.elementToBeClickable(addNewNoteButton)).click();
        //wait.until(webDriver -> webDriver.findElement(By.id("note-title"))).sendKeys("Test seleniun title");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("note-title"))).sendKeys("fdfdfd");
        noteTitleInput.sendKeys("test caralho");
        //marker.sendKeys("Test seleniun title");
        //wait.until(webDriver -> webDriver.findElement(By.id("note-title")));
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionInput)).sendKeys("Seleniun test");
        //wait.until(ExpectedConditions.elementToBeClickable(By.id("notesClick"))).click();




        //driver.findElement(addNewNoteButton).click();

        saveNoteButton.click();
    }

    public HomePage manageProfile() {
        // Page encapsulation to manage profile functionality
        return new HomePage(driver);
    }

    public void logout(){
        this.logoutButton.click();
    }
}
