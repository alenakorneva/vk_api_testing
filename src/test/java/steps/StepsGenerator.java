package steps;

import APIModels.SavePhoto;
import APIModels.UploadServer;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import pages.LoginFormWithPassword;
import pages.LoginFormWithPhoneNumber;
import requests.APIApplicationRequest;
import utils.DownloadUtils;

import java.awt.image.BufferedImage;
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

    public static SavePhoto uploadPictureToPostOnTheWall(File photoToUpload) {
        UploadServer uploadServer = APIApplicationRequest.getAddressForPhotosUpload();
        HashMap<Object, Object> photoInfoFromServer = new HashMap<>(APIApplicationRequest.uploadPhotoOnServer(uploadServer.getResponse().getUpload_url(), photoToUpload));
        return APIApplicationRequest.savePhotoOnWall(photoInfoFromServer.get(SERVER).toString(),
                photoInfoFromServer.get(PHOTO).toString(), photoInfoFromServer.get(HASH).toString()).extract().as(SavePhoto.class);
    }

    public static ImageComparisonState comparePhotos(String url, String pathToUploadedFile, String pathToDownloadedFile, String pathForResultFile) {
        DownloadUtils.downloadPhoto(url);

        BufferedImage uploadedImage = ImageComparisonUtil.readImageFromResources(pathToUploadedFile);
        BufferedImage downloadedImage = ImageComparisonUtil.readImageFromResources(pathToDownloadedFile);

        ImageComparison imageComparison = new ImageComparison(uploadedImage, downloadedImage);

        imageComparison.setThreshold(50);

        ImageComparisonResult comparisonResult = imageComparison.compareImages();

        ImageComparisonState comparisonState = comparisonResult.getImageComparisonState();

        return comparisonState;
    }
}
