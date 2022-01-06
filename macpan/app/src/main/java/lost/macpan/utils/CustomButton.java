package lost.macpan.utils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

public class CustomButton extends JButton implements FocusListener,ResourceHandler {

    private ImageIcon imgIndicator;
    private ImageIcon imgPlaceholder;

    public CustomButton(String text){
        super(text + "  ");

        try {
            Image img = ImageIO.read(getFileResourcesAsStream("images/Cursor.png"));
            Image imgSpacer = ImageIO.read(getFileResourcesAsStream("images/CursorPlatzhalter.png"));
            imgIndicator = new ImageIcon(img);
            imgPlaceholder = new ImageIcon(imgSpacer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setIcon(imgPlaceholder);
        this.setIconTextGap(5);
        this.addFocusListener(this);

        InputMap inputMap = this.getInputMap(JComponent.WHEN_FOCUSED);

        // get both key strokes for space key, but pressed and released
        KeyStroke spaceKeyPressed = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false);
        KeyStroke spaceKeyReleased = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true);

        // key stroke for desired key code
        KeyStroke desiredKeyPressed = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
        KeyStroke desiredKeyReleased = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true);

        // share the Action with desired KeyStroke
        inputMap.put(desiredKeyPressed, inputMap.get(spaceKeyPressed));
        inputMap.put(desiredKeyReleased, inputMap.get(spaceKeyReleased));

    }

    public void focusGained(FocusEvent e) {
        this.setIcon(imgIndicator);
    }

    public void focusLost(FocusEvent e) {
        this.setIcon(imgPlaceholder);
    }

}
