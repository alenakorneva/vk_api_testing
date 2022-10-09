package requests;

import APIModels.CommentId;
import APIModels.PostId;
import APIModels.Reaction;
import APIModels.UploadServer;
import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.response.ValidatableResponse;
import utils.VkAPIUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class APIApplicationRequest {

    private static ISettingsFile jsonSettings = new JsonSettingsFile("testData.json");
    private static final String OWNER_ID = "owner_id";
    private static final String MESSAGE = "message";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String V = "v";
    private static final String POST_ID = "post_id";
    private static final String ATTACHMENTS = "attachments";
    private static final String SERVER = "server";
    private static final String PHOTO = "photo";
    private static final String HASH = "hash";
    private static final String USER_ID = "user_id";
    private static final String TYPE = "type";
    private static final String ITEM_ID = "item_id";


    public static PostId createNewPostOnTheWall(String randomlyGeneratedText) {
        HashMap<String, String> queryParams = new HashMap<>() {{
            put(OWNER_ID, jsonSettings.getValue("/ownerId").toString());
            put(MESSAGE, randomlyGeneratedText);
            put(ACCESS_TOKEN, jsonSettings.getValue("/accessToken").toString());
            put(V, jsonSettings.getValue("/VKVersion").toString());
        }};
        return VkAPIUtils.postRequest(jsonSettings.getValue("/basePathToCreateNewPostOnTheWall").toString(), queryParams).extract().as(PostId.class);
    }

    public static void editPost(String postId, String randomlyGeneratedText, int owner_id, int photo_id) {
        HashMap<String, String> queryParams = new HashMap<>() {{
            put(OWNER_ID, jsonSettings.getValue("/ownerId").toString());
            put(POST_ID, postId);
            put(MESSAGE, randomlyGeneratedText);
            put(ATTACHMENTS, "photo" + owner_id + "_" + photo_id);
            put(ACCESS_TOKEN, jsonSettings.getValue("/accessToken").toString());
            put(V, jsonSettings.getValue("/VKVersion").toString());
        }};
        VkAPIUtils.postRequest(jsonSettings.getValue("/basePathToEditPostOnTheWall").toString(), queryParams);
    }

    public static UploadServer getAddressForPhotosUpload() {
        HashMap<String, String> queryParams = new HashMap<>() {{
            put(OWNER_ID, jsonSettings.getValue("/ownerId").toString());
            put(ACCESS_TOKEN, jsonSettings.getValue("/accessToken").toString());
            put(V, jsonSettings.getValue("/VKVersion").toString());
        }};
        return VkAPIUtils.getRequest(jsonSettings.getValue("/basePathToGetAddressForPhotosUpload").toString(), queryParams).extract().as(UploadServer.class);
    }

    public static Map<Object, Object> uploadPhotoOnServer(String uploadUrl, File photoToUpload) {
        return VkAPIUtils.postRequestToUploadFile(uploadUrl, photoToUpload).extract().body().jsonPath().getMap("");
    }

    public static ValidatableResponse savePhotoOnWall(String server, String photo, String hash) {
        HashMap<String, String> queryParams = new HashMap<>() {{
            put(SERVER, server);
            put(PHOTO, photo);
            put(HASH, hash);
            put(ACCESS_TOKEN, jsonSettings.getValue("/accessToken").toString());
            put(V, jsonSettings.getValue("/VKVersion").toString());
        }};

        return VkAPIUtils.postRequest(jsonSettings.getValue("/basePathToSavePhotoOnWall").toString(), queryParams);
    }

    public static CommentId createCommentOnPost(String randomComment, int postId) {
        HashMap<String, String> queryParams = new HashMap<>() {{
            put(OWNER_ID, jsonSettings.getValue("/ownerId").toString());
            put(POST_ID, Integer.toString(postId));
            put(MESSAGE, randomComment);
            put(ACCESS_TOKEN, jsonSettings.getValue("/accessToken").toString());
            put(V, jsonSettings.getValue("/VKVersion").toString());
        }};
        return VkAPIUtils.postRequest(jsonSettings.getValue("/basePathToCreateCommentOnPost").toString(), queryParams).extract().as(CommentId.class);
    }

    public static Reaction checkIfThePostIsLiked(int itemId) {
        HashMap<String, String> queryParams = new HashMap<>() {{
            put(USER_ID, jsonSettings.getValue("/ownerId").toString());
            put(TYPE, jsonSettings.getValue("/typeOfLikedItem").toString());
            put(OWNER_ID, jsonSettings.getValue("/ownerId").toString());
            put(ITEM_ID, Integer.toString(itemId));
            put(ACCESS_TOKEN, jsonSettings.getValue("/accessToken").toString());
            put(V, jsonSettings.getValue("/VKVersion").toString());
        }};
        return VkAPIUtils.getRequest(jsonSettings.getValue("/basePathToCheckIfThePostIsLiked").toString(), queryParams).extract().as(Reaction.class);
    }

    public static void deleteResponse(int postId) {
        HashMap<String, String> queryParams = new HashMap<>() {{
            put(OWNER_ID, jsonSettings.getValue("/ownerId").toString());
            put(POST_ID, Integer.toString(postId));
            put(ACCESS_TOKEN, jsonSettings.getValue("/accessToken").toString());
            put(V, jsonSettings.getValue("/VKVersion").toString());
        }};
        VkAPIUtils.postRequest(jsonSettings.getValue("/basePathToDeletePost").toString(), queryParams);
    }
}
