package com.udacity.jwdnd.course1.cloudstorage.notes;

import com.udacity.jwdnd.course1.cloudstorage.BasePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NotePage extends BasePage {

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        super.beforeEach();
    }

    @AfterEach
    public void afterEach() {
        super.afterEach();
    }

    @Test
    public void aCreateNote() {
        NotesTest notesTest = new NotesTest(driver);
        notesTest.createNote();
        Assertions.assertEquals(true, notesTest.isNoteCreated());
    }

    @Test
    public void cDeleteNote() throws InterruptedException {
        NotesTest notesTest = new NotesTest(driver);
        notesTest.createNote();
        notesTest.deleteNote();
        // test there should be no note data on homepage:
        assertThrows(NoSuchElementException.class, () -> {
            notesTest.getNewNote();
        });
    }

    @Test
    public void dEditNote(){
        NotesTest notesTest = new NotesTest(driver);
        notesTest.editNote();
    }
}
