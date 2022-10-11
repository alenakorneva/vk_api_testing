package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class PostForm extends Form {

    private final String textInThePost = "//div[contains(@id, '%d')]//div[contains(@class, 'wall_post_text')]";
    private final String postLocatorToCheckUser = "//div[contains(@id, 'post') and contains(@id, '%d')]";
    private final String photoInThePost = "//div[contains(@id, '%d') and contains (@class, 'wall_post_cont')]//a";
    private final ILabel openedPhotoInThePost = AqualityServices.getElementFactory().getLabel(By.xpath("//div[@id='pv_photo']/img"), "openedPhotoInThePost");
    private final ILabel closePhotoButton = AqualityServices.getElementFactory().getLabel(By.xpath("//div[contains(@class, 'pv_close_btn')]"), "closePhotoButton");
    private final String commentOnThePost = "//div[contains(@id, '%s') and contains(@id, '%d')]//div[contains(@class, 'wall_reply_text')]";
    private final String likeIcon = "//div[contains(@class, '%s') and contains(@class, '%d')]//div[@data-reaction-set-id='reactions']//span[contains(@class, 'PostBottomAction__icon')]";
    private final String post = "//div[@id='post%s_%d']";
    private final String showNextCommentButton = "//a[contains(@href, '%s') and contains(@href, '%d')]//span[contains(@class, 'js-replies_next_label')]";

    public PostForm(String owner_id, int post_id) {
        super(By.xpath(String.format("//div[@id='post%s_%d']", owner_id, post_id)), "post");
    }

    public String getTextFromThePost(int postId) {
        return AqualityServices.getElementFactory().getTextBox(By.xpath(String.format(textInThePost, postId)), "textInThePost").getText();
    }

    public String getUserOfPost(int postId) {
        return AqualityServices.getElementFactory().getTextBox(By.xpath(String.format(postLocatorToCheckUser, postId)), "userOfThePost")
                .getAttribute("id");
    }

    public void clickOnPhotoInThePost(int postId) {
        AqualityServices.getElementFactory().getLabel(By.xpath(String.format(photoInThePost, postId)), "photoInThePost").click();
    }

    public String getUrlOfPhoto() {
        return openedPhotoInThePost.getAttribute("src");
    }

    public void closePhoto() {
        closePhotoButton.click();
    }

    public String getCommentOnThePost(String ownerId, int postId) {
        return AqualityServices.getElementFactory().getTextBox(By.xpath(String.format(commentOnThePost, ownerId, postId)), "commentOnThePost").getText();
    }

    public void putLikeOnThePost(String ownerId, int postId) {
        AqualityServices.getElementFactory().getLabel(By.xpath(String.format(likeIcon, ownerId, postId - 1)), "likeIcon").click();
    }

    public void clickShowNextCommentButton(String ownerId, int postId) {
        AqualityServices.getElementFactory().getButton(By.xpath(String.format(showNextCommentButton, ownerId, postId)), "showNextCommentButton").click();
    }

    public ILabel getDeletedPost(String ownerId, int postId) {
        return AqualityServices.getElementFactory().getLabel(By.xpath(String.format(post, ownerId, postId)), "post");
    }

}
