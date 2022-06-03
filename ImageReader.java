/**
 * This is the second draft of the ImageReader class.
 * <p>
 * Changes made:
 * <ul>
 *     <li>Added a method to efficiently store the dialogue assets in an array
 * </ul>
 * <p>
 * Version date: 05/27/2022
 * @author Fatma Jadoon
 * @version 1.2.29
 * <p>
 */

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageReader {
    /**
     * Reads an image using ImageIO.read();
     * @param path      The path of the image
     * @return An Image if no error is thrown, otherwise return null
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

    /**
     * Stores the dialogue assets into an array
     * @param dirPath       The path to the dialogue directory
     * @return      An array of the script/dialogue assets
     */
    public static Image[] storeDir(String dirPath) {
        File dir = new File(dirPath);
        String[] paths = dir.list();
        Image[] images = new Image[paths.length];

        for(int i = 0; i < images.length; i++) {
            images[i] = ImageReader.reader(dirPath + paths[i]);
        }

        return images;
    }

    public static ArrayList<Boolean> isToxic(ArrayList<Image> imgs) {
        ArrayList<Boolean> isToxic = new ArrayList<Boolean>();
        for (int i = 0; i < imgs.size(); i++)
          {
            if (i == 1 || i == 2 || i == 4 || i == 5)
              isToxic.add(true);
            else
              isToxic.add(false);           
          }
        return isToxic;
    }
}
