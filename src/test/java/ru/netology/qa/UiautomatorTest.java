package ru.netology.qa;

import io.appium.java_client.AppiumDriver;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import ru.netology.qa.screens.UiautomatorScreen;

import java.net.MalformedURLException;
import java.net.URL;

import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_ACTIVITY;
import static io.appium.java_client.remote.AndroidMobileCapabilityType.APP_PACKAGE;
import static io.appium.java_client.remote.MobileCapabilityType.AUTOMATION_NAME;
import static io.appium.java_client.remote.MobileCapabilityType.DEVICE_NAME;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM_NAME;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UiautomatorTest {
    private AppiumDriver driver;
    String someText = "test";
    String emptyText = "";

    @BeforeAll
    public void createDriver() throws MalformedURLException {
        String platform = System.getProperty("platform");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        if ("android".equals(platform)) {
            desiredCapabilities.setCapability(PLATFORM_NAME, "android");
            desiredCapabilities.setCapability(DEVICE_NAME, "Some name");
            desiredCapabilities.setCapability(APP_PACKAGE, "ru.netology.testing.uiautomator");
            desiredCapabilities.setCapability(APP_ACTIVITY, "ru.netology.testing.uiautomator.MainActivity");
            desiredCapabilities.setCapability(AUTOMATION_NAME, "uiautomator2");
        } else {
            throw new IllegalArgumentException(String.format("Platform %s no supported", platform));
        }
        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
    }

    @Test
    public void testEmptyField() {
        UiautomatorScreen uiautomatorScreen = new UiautomatorScreen(driver);
        uiautomatorScreen.userInput.sendKeys(someText);
        uiautomatorScreen.buttonChange.click();
        var text = uiautomatorScreen.textToBeChanged.getText();
        uiautomatorScreen.userInput.sendKeys(emptyText);
        uiautomatorScreen.buttonChange.click();
        Assertions.assertEquals(someText, uiautomatorScreen.textToBeChanged.getText());
    }

    @Test
    public void testOpenText() {

        UiautomatorScreen uiautomatorScreen = new UiautomatorScreen(driver);
        uiautomatorScreen.userInput.sendKeys(someText);
        uiautomatorScreen.buttonChange.click();
        uiautomatorScreen.buttonActivity.click();
        Assertions.assertEquals(someText, uiautomatorScreen.newPageText.getText());
    }

    @AfterAll
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
