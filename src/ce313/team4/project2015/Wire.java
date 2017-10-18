package ce313.team4.project2015;

import ce313.team4.project2015.res.R;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * Wire class provides a convenient way for drawing them in the
 * <code>BasicComputerPanel</code>.
 *
 * @author Team 4
 */
public class Wire {

    final private String resName;
    final private BufferedImage image;
    final private int x, y;

    /**
     * Resource name is the name of wire image
     *
     * @return image resource name
     */
    public String getResName() {
        return resName;
    }

    /**
     * Gets the buffered image from the image resource
     *
     * @return buffered image of the given resource.
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * @return x-coordinate of the wire
     */
    public int getX() {
        return x;
    }

    /**
     * @return y-coordinate of the wire
     */
    public int getY() {
        return y;
    }

    /**
     * Constructs an image with a given x and y coordinates.
     *
     * @param resName wire image resource name
     * @param x the coordinate X
     * @param y the coordinate Y
     * @throws IOException
     */
    public Wire(String resName, int x, int y) throws IOException {
        image = ImageIO.read(R.get(resName));
        this.resName = resName;
        this.x = x;
        this.y = y;
    }

    /**
     * Draws the wire image on the passed graphics object. Wire image will be
     * drawn in the middle of panel by calculating the preferred size and the
     * actual one.
     *
     * @param g the graphics object that will contain the wire
     * @param panel the image observer
     */
    public void draw(Graphics g, JPanel panel) {
        Dimension p = panel.getPreferredSize();
        g.drawImage(image, x + (panel.getWidth() - p.width) / 2,
                y + (panel.getHeight() - p.height) / 2, panel);
    }

    /**
     * Colors the wire image
     *
     * @param color the new color of the image
     */
    public void tint(Color color) {
        int rgb = color.getRGB();
        for (int xx = 0; xx < image.getWidth(); xx++) {
            for (int yy = 0; yy < image.getHeight(); yy++) {
                if (image.getRGB(xx, yy) != 0) {
                    image.setRGB(xx, yy, rgb);
                }
            }
        }
    }
}
