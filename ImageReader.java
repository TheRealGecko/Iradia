/**
 * This is the first draft of the Image reader class.
 * It reads images.
 * <p>
 * Version date: 05/20/2022
 * @author Alexandra Mitnik, Fatma Jadoon
 * @version 1.0.0
 * <p>
 * External Code Sources:
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ImageReader {
  /**
     * Reads an image using ImageIO.read();
     * @param path      The path of the image
     */
    public static Image reader(String path) {
        Image img;
        try {
            img = ImageIO.read(new File(path));
            return img;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
