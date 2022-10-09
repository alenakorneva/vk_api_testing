package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

public class DownloadUtils {

    public static void downloadPhoto(String url) {
        try {
            URLConnection openConnection = new URL(url).openConnection();
            BufferedImage image1 = ImageIO.read(openConnection.getInputStream());

            File file = new File("download_pic.jpg");
            ImageIO.write(image1, "jpg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
