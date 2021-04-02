package com.udacity.jwdnd.course1.cloudstorage.credential;

import com.udacity.jwdnd.course1.cloudstorage.BasePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CredentialPage extends BasePage {

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
    public void aCreateCredential() {
        CredentialTest credentialTest = new CredentialTest(driver);
        credentialTest.createCredential();
        Assertions.assertEquals(true, credentialTest.isCredentialCreated());
    }

    @Test
    public void cDeleteCredential() {
        CredentialTest credentialTest = new CredentialTest(driver);
        credentialTest.createCredential();
        credentialTest.deleteCredential();
        // test there should be no note data on homepage:
        assertThrows(NoSuchElementException.class, () -> {
            credentialTest.getNewCredential();
        });
    }

    @Test
    public void bEditCredential(){
        CredentialTest credentialTest = new CredentialTest(driver);
        credentialTest.editCredential();
    }
}
