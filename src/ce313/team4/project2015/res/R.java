package ce313.team4.project2015.res;

import java.net.URL;

/**
 * The class <code>R</code> is a simple class that returns URL of the images on
 * the same package. URLs useful for retrieving the data of image and use them
 * in many applications.
 *
 * @author Abdulrahman
 */
public class R {

    /**
     * @param resName image file name that located in the package
     * @return URL of the file or null if the file is not found
     */
    final public static URL get(String resName) {
        return R.class.getResource(resName);
    }
}
