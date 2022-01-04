package lost.macpan.utils;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class CustomButton extends JButton implements FocusListener,ResourceHandler {

    private ImageIcon imageForOne;
    private ImageIcon imgPlacaholder;

    public CustomButton(String text){
        super(text + "  ");

        try {
            Image img = ImageIO.read(getFileResourcesAsStream("images/Cursor.png"));
            Image imgSpacer = ImageIO.read(getFileResourcesAsStream("images/CursorPlatzhalter.png"));
            imageForOne = new ImageIcon(img);
            imgPlacaholder = new ImageIcon(imgSpacer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.setIcon(imgPlacaholder);
        this.setIconTextGap(5);
        this.addFocusListener(this);
    }

    public void focusGained(FocusEvent e) {
        this.setIcon(imageForOne);
    }

    public void focusLost(FocusEvent e) {
        this.setIcon(imgPlacaholder);
    }

}
