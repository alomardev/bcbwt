package ce313.team4.project2015;

import java.awt.Color;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;

/**
 * A text field that has the capability of having a place holder.
 *
 * @author Abdulrahman
 */
public class PJTextField extends JTextField implements FocusListener {

    private String placeholder;
    private Color oldColor, placeholderColor;

    /**
     * Constructs an empty text field with the specified number of columns.
     *
     * @param columns "the number of columns to use to calculate the preferred
     * width; if columns is set to zero, the preferred width will be whatever
     * naturally results from the component implementation" - JTextField doc.
     */
    public PJTextField(int columns) {
        super(columns);
        placeholderColor = Color.GRAY;
    }

    /**
     * Sets the place holder to the component.
     *
     * @param text the place holder text
     */
    public void setPlaceholder(String text) {
        placeholder = text;
        focusLost(null);
        if (text.isEmpty()) {
            removeFocusListener(this);
        } else {
            addFocusListener(this);
        }
    }

    /**
     * Change the default color of the place holder text. (default color is
     * gray)
     *
     * @param color the new color of the place holder text
     */
    public void setPlaceholderColor(Color color) {
        placeholderColor = color;
    }

    /**
     * @return place holder text
     */
    public String getPlaceholder() {
        return placeholder;
    }

    /**
     * @return place holder color
     */
    public Color getPlaceholderColor() {
        return placeholderColor;
    }

    /**
     * @return whatever in the text field; it may be the place holder or the
     * typed text.
     */
    public String getActualText() {
        return super.getText();
    }

    /**
     * @return the text in the text field (empty string will be returned if the
     * visible text is the place holder)
     */
    @Override
    public String getText() {
        String actual = super.getText();
        if (actual.equals(placeholder)) {
            return "";
        } else {
            return actual;
        }
    }

    /**
     * Removes the place holder text when the text field becomes focused.
     *
     * @param e
     */
    @Override
    public void focusGained(FocusEvent e) {
        if (getActualText().equals(placeholder)) {
            setText("");
        }
        setForeground(oldColor);
    }

    /**
     * Reset the place holder text if nothing typed after it being focused.
     *
     * @param e
     */
    @Override
    public void focusLost(FocusEvent e) {
        if (getActualText().isEmpty()) {
            setText(placeholder);
            oldColor = getForeground();
            setForeground(Color.GRAY);
        }
    }
}
