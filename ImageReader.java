/**
 * This is the third draft of the ImageReader class.
 * <p>
 * Changes made:
 * <ul>
 *     <li>Added a method to store the answer (toxic/not-toxic) of cases in an ArrayList
 * </ul>
 * <p>
 * Version date: 06/03/2022
 *
 * @author Fatma Jadoon, Alexandra Mitnik
 * @version 1.3.63
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
     * @param path      The path of the image.
     * @return An Image if no error is thrown, otherwise return null.
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
     * Stores a directory of assets into an array.
     * @param dirPath       The path to the directory.
     * @return An array of assets.
     */
    public static Image[] storeDir(String dirPath) {
        File dir = new File(dirPath);
        String[] paths = dir.list();
        Image[] images = new Image[paths.length];

        for (int i = 0; i < images.length; i++) {
            images[i] = ImageReader.reader(dirPath + paths[i]);
        }

        return images;
    }

    /**
     * Stores the correct answer for a series of cases in an array.
     * @param imgs       The ArrayList of cases to store the answers for.
     * @return Returns an ArrayList of the answers for the cases.
     */
    public static ArrayList<Boolean> isToxic(ArrayList<Image> imgs) {
        ArrayList<Boolean> isToxic = new ArrayList<Boolean>();
        for (int i = 0; i < imgs.size(); i++) {
            if (i == 1 || i == 2 || i == 4 || i == 5)
                isToxic.add(true);
            else
                isToxic.add(false);
        }
        return isToxic;
    }
}
