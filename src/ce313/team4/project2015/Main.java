package ce313.team4.project2015;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Basic Computer Wire Tracer is an application that simulate a basic computer
 * organization with a given instruction to be traced. It shows how the wires
 * flow in a basic computer diagram from a control unit and other components
 * according to the instruction.
 *
 * Credit goes to:
 * <ul>
 * <li>Abdulrahman Al'omar - 212513129</li>
 * <li>Khalid Aljughiman - 213114634</li>
 * <li>Ammar Al-Abdulqader - 212509193</li>
 * </ul>
 *
 * @author Team 4
 */
public class Main {

    public static void main(String[] args) {
        // Give the application a system look and feel.
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | InstantiationException |
                ClassNotFoundException | IllegalAccessException ex) {
            System.out.println(ex.getMessage());
        }

        // The launch of the main frame
        CombinedFrame frm = new CombinedFrame();
        frm.setup();
        frm.setVisible(true);
    }
}
