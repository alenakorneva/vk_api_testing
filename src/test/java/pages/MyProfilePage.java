package pages;

import aquality.selenium.browser.AqualityServices;
import aquality.selenium.elements.interfaces.ILabel;
import aquality.selenium.forms.Form;
import org.openqa.selenium.By;

public class MyProfilePage extends Form {

    private String textInThePost = "//div[contains(@id, '%d')]//div[contains(@class, 'wall_post_text')]";
    private String postLocatorToCheckUser = "//div[contains(@id, 'post') and contains(@id, '%d')]";
    private String photoInThePost = "//div[contains(@id, '%d') and contains (@class, 'wall_post_cont')]//a";
    private ILabel openedPhotoInThePost = AqualityServices.getElementFactory().getLabel(By.xpath("//div[@id='pv_photo']/img"), "openedPhotoInThePost");
    private ILabel closePhotoButton = AqualityServices.getElementFactory().getLabel(By.xpath("//div[contains(@class, 'pv_close_btn')]"), "closePhotoButton");
    private String commentOnThePost = "//div[contains(@id, '%s') and contains(@id, '%d')]//div[contains(@class, 'wall_reply_text')]";
    private String likeIcon = "//div[contains(@class, '%s') and contains(@class, '%d')]//div[@data-reaction-set-id='reactions']//span[contains(@class, 'PostBottomAction__icon')]";
    private String post = "//div[@id='post%s_%d']";
    private String showNextCommentButton = "//a[contains(@href, '%s') and contains(@href, '%d')]//span[contains(@class, 'js-replies_next_label')]";

    public MyProfilePage() {
        super(By.xpath("//div[@id='page_info_wrap']"), "pageInfo");
    }

    public String getTextFromThePost(int postId) {
        return AqualityServices.getElementFactory().getTextBox(By.xpath(String.format(textInThePost, postId)), "textInThePost").getText();
    }

    public String getUserOfPost(int postId) {
        return AqualityServices.getElementFactory().getTextBox(By.xpath(String.format(postLocatorToCheckUser, postId)), "userOfThePost")
                .getAttribute("id");
    }

    public String getPhotoURL(int postId) {
        AqualityServices.getElementFactory().getLabel(By.xpath(String.format(photoInThePost, postId)), "photoInThePost").click();
        String photoUrl = openedPhotoInThePost.getAttribute("src");
        closePhotoButton.click();
        return photoUrl;
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
