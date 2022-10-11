package steps;

import APIModels.SavePhoto;
import APIModels.UploadServer;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import pages.LoginFormWithPassword;
import pages.LoginFormWithPhoneNumber;
import pages.PostForm;
import requests.APIApplicationRequest;
import utils.ComparePhotosUtils;
import utils.DownloadUtils;

import java.io.File;
import java.util.HashMap;

public class StepsGenerator {

    private static final String SERVER = "server";
    private static final String PHOTO = "photo";
    private static final String HASH = "hash";

    public static void fulfillLoginForm(LoginFormWithPhoneNumber loginFormWithPhoneNumber, String phoneNumber, LoginFormWithPassword loginFormWithPassword, String password) {
        loginFormWithPhoneNumber.insertPhoneNumber(phoneNumber);
        loginFormWithPhoneNumber.clickSubmitButton();
        loginFormWithPassword.insertPassword(password);
        loginFormWithPassword.clickSubmitButton();
    }

    public static String getPhotoURL(PostForm postForm, int postId) {
        postForm.clickOnPhotoInThePost(postId);
        String photoUrl = postForm.getUrlOfPhoto();
        postForm.closePhoto();
        return photoUrl;
    }

    public static SavePhoto uploadPictureToPostOnTheWall(File photoToUpload) {
        UploadServer uploadServer = APIApplicationRequest.getAddressForPhotosUpload();
        HashMap<Object, Object> photoInfoFromServer = new HashMap<>(APIApplicationRequest.uploadPhotoOnServer(uploadServer.getResponse().getUpload_url(), photoToUpload));
        return APIApplicationRequest.savePhotoOnWall(photoInfoFromServer.get(SERVER).toString(),
                photoInfoFromServer.get(PHOTO).toString(), photoInfoFromServer.get(HASH).toString()).extract().as(SavePhoto.class);
    }

    public static ImageComparisonState downloadAndComparePhotos(String url, String pathToUploadedFile, String pathToDownloadedFile) {
        DownloadUtils.downloadPhoto(url);
        return ComparePhotosUtils.comparePhotos(pathToUploadedFile, pathToDownloadedFile);
    }
}
