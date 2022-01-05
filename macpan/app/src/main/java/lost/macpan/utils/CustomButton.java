package lost.macpan.utils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
    }

    public void focusGained(FocusEvent e) {
        this.setIcon(imgIndicator);
    }

    public void focusLost(FocusEvent e) {
        this.setIcon(imgPlaceholder);
    }

}
