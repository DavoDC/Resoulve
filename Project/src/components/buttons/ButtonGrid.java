package components.buttons;

import java.util.ArrayList;

import static states.base.InfoScreen.headerX;
import base.Globals;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.ComponentListener;

/**
 * Helps to manipulate to a group of buttons
 *
 * @author David
 */
public class ButtonGrid {

    // Holds buttons
    private ArrayList<Button> buttons;

    /**
     * Create a ButtonGrid Input the values common to most buttons
     *
     * @param common 0: NumberOfButtons, 1: ImageLocationString, 2: startXpos,
     * 3: startYpos, 4: width, 5: height, 6: XSpacing, 7: YSpacing, 8:
     * NumberofColumns, 9: FontString
     *
     * @param labels
     */
    public ButtonGrid(ArrayList<Object> common, ArrayList<String> labels) {

        // Check arguments and notify
        int buttonNo = (int) common.get(0);
        boolean con1 = common.size() != 11;
        boolean con2 = buttonNo != labels.size();
        boolean con3 = labels.isEmpty();
        if (con1 || con2 || con3) {
            String errS = "ButtonGrid init error \nISSUE: ";
            if (con1) {
                errS += "Missing feature in 'common' AL";
            } else if (con2) {
                errS += "Common size != Label size";
            } else if (con3) {
                errS += "No labels";
            }
            errS += "\n" + "Header: ";
            if (!labels.isEmpty()) {
                errS += labels.get(0) + "\n";
            }
            throw new IllegalArgumentException(errS);
        }

        // Extract variables from common
        String imgRes = (String) common.get(1);
        int x = (int) common.get(2);
        int y = (int) common.get(3);
        int w = (int) common.get(4);
        int h = (int) common.get(5);
        boolean isSoundWanted = (boolean) common.get(10);

        // Get image and adjust
        Image img = null;
        try {
            img = new Image(imgRes).getScaledCopy(w, h);
        } catch (SlickException ex) {
            System.err.println("\n ImageError: " + imgRes);
        }

        // Extract font
        TrueTypeFont font = Globals.fontServer.getFont((String) common.get(9));

        // Initialize list and add generic buttons
        buttons = new ArrayList<>();
        for (int i = 0; i < buttonNo; i++) {
            buttons.add(new Button(img, new Rectangle(x, y, w, h),
                    font, isSoundWanted));
        }

        // Adjust buttons
        adjustButtons(common, labels);
    }

    /**
     * Adjust a list of buttons to reflect several parameters
     *
     * @param common
     * @param labels
     */
    public final void adjustButtons(ArrayList<Object> common, ArrayList<String> labels) {

        // Extract required information
        int buttonNo = (int) common.get(0);
        int xpos = (int) common.get(2);
        int ypos = (int) common.get(3);
        int bW = (int) common.get(4);
        int bH = (int) common.get(5);
        int xspacing = (Integer) common.get(6);
        int yspacing = (Integer) common.get(7);
        int columns = (Integer) common.get(8);

        // Local loop variables
        int curxpos = xpos;
        int curypos = ypos;

        // For all buttons
        for (int i = 0; i < buttons.size(); i++) {

            // Get label
            String curLabel = labels.get(i);

            // If label is a header
            if (curLabel.contains("header")) {

                // Replace button with header
                replaceButton(i, getHeader(curLabel));

            } else {
                // Else if we have a regular button

                // Apply current position to current button
                buttons.get(i).setBounds(curxpos, curypos, bW, bH);

                // Apply label
                buttons.get(i).setLabel(curLabel);

                // Shift current X by width + spacing
                curxpos += (bW + xspacing);

                // If current position is a HEADER or a multiple of columnNo
                if (((i % columns) == 0) && (columns != buttonNo)) {

                    // Reset X (go back to left)
                    curxpos = xpos;

                    // Increase Y (move downwards)
                    curypos += (bH + yspacing);
                }
            }
        }
    }

    /**
     * Create a header with a given label
     *
     * @param rawLabel
     * @return
     */
    private Button getHeader(String rawLabel) {

        // Process raw label
        String[] parts = rawLabel.split("_");
        String actualLabel = parts[1];
        String headerFont = parts[2];

        // Make Header rect (startXpos, startYpos, width, height)
        Rectangle rect = new Rectangle(headerX, 100, 450, 60);

        // Get Header font 
        TrueTypeFont font = Globals.fontServer.getFont(headerFont);

        // Get image and adjust
        Image img = null;
        try {
            img = new Image(Globals.getFP("button.png"));
            img = img.getScaledCopy((int) rect.getWidth(), (int) rect.getHeight());
        } catch (SlickException ex) {
            System.err.println("Image error in ButtonGrid");
        }

        // Make header button
        Button header = new Button(img, rect, font, false);

        // Set label
        header.setLabel(actualLabel);

        // Return header
        return header;
    }

    /**
     * Get a button using a label substring
     *
     * @param labelPart
     * @return Button
     */
    public Button getButtonByLabel(String labelPart) {

        // For all buttons
        for (int i = 0; i < buttons.size(); i++) {

            // Retrieve current button
            Button curBut = getButtonByPos(i);

            // If current button contains label part
            if (curBut.getLabel().contains(labelPart)) {

                // Return current button
                return curBut;
            }
        }

        // Print error message
        String errS = "Error: '" + labelPart + "' button label substring not found";
        System.err.println(errS);

        // Return null as nothing was found
        return null;
    }

    /**
     * Draws all buttons fully
     *
     * @param g
     */
    public void drawButtons(Graphics g) {

        // For all buttons
        for (Button curB : buttons) {

            // Draw in full
            curB.drawFull(g);
        }
    }

    /**
     * Draw all buttons fully, with offset
     *
     * @param g
     * @param sX
     * @param sY
     */
    public void drawButtonsShifted(Graphics g, int sX, int sY) {

        // For all buttons
        for (Button curB : buttons) {

            // Draw shifted
            curB.drawShifted(g, sX, sY);
        }
    }

    /**
     * Add an action to all buttons
     *
     * @param compList
     */
    public void applyActions(ComponentListener compList) {

        // For all buttons
        for (Button b : buttons) {

            // Add action
            b.addListener(compList);
        }
    }

    /**
     * Apply a font to all buttons
     *
     * @param fontS
     */
    public void applyFont(String fontS) {

        // For all buttons
        for (Button b : buttons) {

            // Change font
            b.setFont(fontS);
        }
    }

    /**
     * Apply a label to all buttons
     *
     * @param newS
     */
    public void applyLabel(String newS) {

        // For all buttons
        for (Button b : buttons) {

            // Change label
            b.setLabel(newS);
        }
    }

    /**
     * Replace the label of a button
     *
     * @param oldLabel
     * @param newLabel
     */
    public void replaceButtonLabel(String oldLabel, String newLabel) {

        // Retrieve the button using the old label,
        // and replace its label with the one given
        getButtonByLabel(oldLabel).setLabel(newLabel);
    }

    /**
     * Get a button using its position
     *
     * @param pos
     * @return Button
     */
    public Button getButtonByPos(int pos) {
        return (buttons.get(pos));
    }

    /**
     * Replace a button
     *
     * @param pos Old button position
     * @param newB New button
     */
    public final void replaceButton(int pos, Button newB) {

        // Replace the button at 'pos' with 'newB'
        buttons.set(pos, newB);
    }

    /**
     * Get the number of buttons in the grid
     *
     * @return size of button list
     */
    public int getSize() {
        return buttons.size();
    }
}
