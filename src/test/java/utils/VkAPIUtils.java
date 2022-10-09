package utils;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class VkAPIUtils {

    private static ISettingsFile jsonSettings = new JsonSettingsFile("testData.json");

    public static ValidatableResponse postRequest(String basePath, HashMap<String, ?> mapWithQueryParams) {
        RestAssured.baseURI = jsonSettings.getValue("/apiBaseURL").toString();
        return given()
                .contentType(ContentType.JSON)
                .queryParams(mapWithQueryParams)
                .when()
                .post(basePath)
                .then()
                .log().all();
    }

    public static ValidatableResponse getRequest(String basePath, HashMap<String, ?> mapWithQueryParams){
        RestAssured.baseURI = jsonSettings.getValue("/apiBaseURL").toString();
        return given()
                .queryParams(mapWithQueryParams)
                .when()
                .get(basePath)
                .then();
    }

    public static ValidatableResponse postRequestToUploadFile(String url, File photoToUpload){
        return given()
                .multiPart(photoToUpload)
                .accept(ContentType.MULTIPART)
                .when()
                .post(url)
                .then()
                .log().all();
    }
}
