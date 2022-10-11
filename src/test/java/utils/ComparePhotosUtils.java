package utils;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;

import java.awt.image.BufferedImage;

public class ComparePhotosUtils {

    public static ImageComparisonState comparePhotos(String pathToUploadedFile, String pathToDownloadedFile) {

        BufferedImage uploadedImage = ImageComparisonUtil.readImageFromResources(pathToUploadedFile);
        BufferedImage downloadedImage = ImageComparisonUtil.readImageFromResources(pathToDownloadedFile);

        ImageComparison imageComparison = new ImageComparison(uploadedImage, downloadedImage);

        imageComparison.setThreshold(50);

        ImageComparisonResult comparisonResult = imageComparison.compareImages();
        
        return comparisonResult.getImageComparisonState();
    }
}
