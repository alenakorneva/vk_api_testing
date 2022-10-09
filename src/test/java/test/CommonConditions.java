package test;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.browser.Browser;
import aquality.selenium.core.logging.Logger;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class CommonConditions {

    Browser browser = AqualityServices.getBrowser();
    ISettingsFile jsonSettings = new JsonSettingsFile("testData.json");
    Logger logger = AqualityServices.getLogger();

    @BeforeTest
    public void setUp(){
        browser.maximize();
        logger.info("Step 1: move to " + jsonSettings.getValue("/basePageUrl").toString());
        browser.goTo(jsonSettings.getValue("/basePageUrl").toString());
    }

    @AfterTest
    public void tearDown(){
        browser.quit();
    }
}
