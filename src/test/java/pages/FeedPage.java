package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class FeedPage extends Form {

    private IButton myProfileButton = AqualityServices.getElementFactory().getButton(By.xpath("//li[@id='l_pr']//a"), "newsButton");

    public FeedPage() {
        super(By.xpath("//div[contains(@class, 'stories_feed_cont')]"), "feedPage");
    }

    public void clickMyProfileButton(){
        myProfileButton.click();
    }
}
