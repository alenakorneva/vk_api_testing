package test;

import APIModels.CommentId;
import APIModels.PostId;
import APIModels.Reaction;
import APIModels.SavePhoto;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.FeedPage;
import pages.LoginFormWithPassword;
import pages.LoginFormWithPhoneNumber;
import pages.PostForm;
import requests.APIApplicationRequest;
import steps.StepsGenerator;
import utils.RandomlyGeneratedUtils;

import java.io.File;

public class VKTest extends CommonConditions {

    LoginFormWithPhoneNumber loginFormWithPhoneNumber = new LoginFormWithPhoneNumber();
    LoginFormWithPassword loginFormWithPassword = new LoginFormWithPassword();
    FeedPage feedPage = new FeedPage();
    PostForm postForm;

    @Test
    public void interactionsWithVKWallTest() {

        logger.info("Step 2: perform authorization.");
        StepsGenerator.fulfillLoginForm(loginFormWithPhoneNumber,
                jsonSettings.getValue("/phoneNumber").toString(),
                loginFormWithPassword,
                jsonSettings.getValue("/password").toString());

        logger.info("Step 3: move to 'My profile' page.");
        feedPage.clickMyProfileButton();

        logger.info("Step 4: using API request create post on the wall with randomly generated text and get post's 'id' from root.");
        String randomTextToCreatePost = RandomlyGeneratedUtils.getRandomlyGeneratedSymbols(RandomlyGeneratedUtils.LATIN_CHARS,
                Integer.parseInt(jsonSettings.getValue("/lengthOfRandomlyGeneratedText").toString()));
        PostId postId = APIApplicationRequest.createNewPostOnTheWall(randomTextToCreatePost);

        postForm = new PostForm(jsonSettings.getValue("/ownerId").toString(), postId.getResponse().getPost_id());

        logger.info("Step 5: without page refreshment make sure that there is post with set text from appropriate user.");
        SoftAssert softAssertForPostTextAndUser = new SoftAssert();
        softAssertForPostTextAndUser.assertEquals(randomTextToCreatePost, postForm.getTextFromThePost(postId.getResponse().getPost_id()),
                "Text in the post isn't similar to the text in the POST request.");
        softAssertForPostTextAndUser.assertTrue(postForm.getUserOfPost(postId.getResponse().getPost_id()).contains(jsonSettings.getValue("/ownerId").toString()),
                "User who has sent post isn't correct.");
        softAssertForPostTextAndUser.assertAll("Text and user in the post aren't correct.");

        logger.info("Step 6: edit post on the wall through API request - change the text and add (load) any picture.");
        String randomTextToEditPost = jsonSettings.getValue("/textToEditPost").toString();
        File photoToUpload = new File(System.getProperty("user.dir") + jsonSettings.getValue("/pathToUploadedPhoto").toString());
        SavePhoto savePhoto = StepsGenerator.uploadPictureToPostOnTheWall(photoToUpload);
        APIApplicationRequest.editPost(Integer.toString(postId.getResponse().getPost_id()),
                randomTextToEditPost,
                savePhoto.getResponse().get(0).getOwner_id(),
                savePhoto.getResponse().get(0).getId());

        logger.info("Step 7: without page refreshment make sure that the text has changed and there is a picture similar to required.");
        SoftAssert softAssertForChangedTextAndPicture = new SoftAssert();
        softAssertForChangedTextAndPicture.assertEquals(randomTextToEditPost, postForm.getTextFromThePost(postId.getResponse().getPost_id()),
                "Text in the post isn't similar to the edited text in the POST request.");
        softAssertForChangedTextAndPicture.assertEquals(StepsGenerator.downloadAndComparePhotos(StepsGenerator.getPhotoURL(postForm, postId.getResponse().getPost_id()),
                        System.getProperty("user.dir") + jsonSettings.getValue("/pathToUploadedPhoto").toString(),
                        System.getProperty("user.dir") + jsonSettings.getValue("/pathToUploadedPhoto").toString()),
                ImageComparisonState.MATCH, "Pictures aren't similar.");
        softAssertForChangedTextAndPicture.assertAll("Text and photo in the post aren't similar to the edited in the POST request.");

        logger.info("Step 8: add comment with random text to the post on the wall through API request.");
        String randomlyGeneratedComment = RandomlyGeneratedUtils.getRandomlyGeneratedSymbols(RandomlyGeneratedUtils.LATIN_CHARS,
                Integer.parseInt(jsonSettings.getValue("/lengthOfRandomlyGeneratedText").toString()));
        CommentId commentId = APIApplicationRequest.createCommentOnPost(randomlyGeneratedComment, postId.getResponse().getPost_id());

        logger.info("Step 9: without page refreshment make sure that the comment from certain user is added to the required post.");
        postForm.clickShowNextCommentButton(jsonSettings.getValue("/ownerId").toString(), postId.getResponse().getPost_id());
        Assert.assertEquals(postForm.getCommentOnThePost(jsonSettings.getValue("/ownerId").toString(), commentId.getResponse().getComment_id()), randomlyGeneratedComment,
                "The randomly generated comment from certain user wasn't added to the required post.");

        logger.info("Step 10: using UI put like on the post.");
        postForm.putLikeOnThePost(jsonSettings.getValue("/ownerId").toString(), commentId.getResponse().getComment_id());

        logger.info("Step 11: through API request make sure that the post has got like from required user.");
        Reaction reaction = APIApplicationRequest.checkIfThePostIsLiked(postId.getResponse().getPost_id());
        Assert.assertEquals(reaction.getResponse().getLiked(), 1, "Post isn't liked.");

        logger.info("Step 12: delete the post through API request.");
        APIApplicationRequest.deleteResponse(postId.getResponse().getPost_id());

        logger.info("Step 13: without page refreshment make sure that the post is deleted.");
        Assert.assertTrue(postForm.getDeletedPost(jsonSettings.getValue("/ownerId").toString(), commentId.getResponse().getComment_id())
                .state().waitForDisplayed(), "Post isn't deleted.");
    }
}
