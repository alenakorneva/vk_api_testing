package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.IButton;
import aquality.selenium.elements.interfaces.ITextBox;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class LoginFormWithPassword extends Form {

    private ITextBox passwordInputField = AqualityServices.getElementFactory().getTextBox(By.xpath("//input[@name='password']"), "passwordInputField");
    private IButton submitButton = AqualityServices.getElementFactory().getButton(By.xpath("//button[contains(@type, 'submit')]"), "submitButton");

    public LoginFormWithPassword() {
        super(By.xpath("//form[contains(@class, 'vkc__EnterPasswordNoUserInfo__content')]"), "loginFormWithPassword");
    }

    public void insertPassword(String password) {
        passwordInputField.type(password);
    }

    public void clickSubmitButton() {
        submitButton.click();
    }
}
