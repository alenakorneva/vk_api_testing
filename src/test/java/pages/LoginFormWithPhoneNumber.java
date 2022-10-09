package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LoginFormWithPhoneNumber extends Form {

    private ITextBox phoneNumberInputField = AqualityServices.getElementFactory().getTextBox(By.xpath("//input[@id='index_email']"), "phoneNumberInputField");
    private IButton submitButton = AqualityServices.getElementFactory().getButton(By.xpath("//button[contains(@type, 'submit')]"), "submitButton");

    public LoginFormWithPhoneNumber() {
        super(By.xpath("//div[@id='index_login']"), "loginForm");
    }

    public void insertPhoneNumber(String phoneNumber){
        phoneNumberInputField.type(phoneNumber);
    }

    public void clickSubmitButton() {
        submitButton.click();
    }
}
