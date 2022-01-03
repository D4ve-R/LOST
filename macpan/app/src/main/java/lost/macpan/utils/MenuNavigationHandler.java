package lost.macpan.utils;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public interface MenuNavigationHandler{

    /**
     * sets all the key bindings
     *
     * @author Sebastian
     */
    default void setKeyBindings(ActionMap actionMap, InputMap inputMap) {

        String vkW = "VK_W";
        String vkS = "VK_S";

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), vkW);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), vkS);

        actionMap.put(vkW, new KeyAction(vkW));
        actionMap.put(vkS, new KeyAction(vkS));
    }

    /**
     * Class for handling the Key actions and calling the newKeyAction method of the game object to pass the action allong
     *
     * @author Sebastian
     */
    class KeyAction extends AbstractAction {
        public KeyAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvt) {
            newKeyAction(actionEvt.getActionCommand());
        }
    }

    /**
     * method for key Actions, gets called every time a mapped Key is pressed
     * To add new Keys they first have to be added to the keymap in the setKeyBindings() function
     *
     * @param pKey String with the name of the key event constant (for a pKey would be "VK_A")
     * @author Sebastian
     */
    static void newKeyAction(String pKey) {
        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        switch (pKey) {
            case "VK_W":
                manager.focusPreviousComponent();
                break;
            case "VK_S":
                manager.focusNextComponent();
                break;
        }
    }
}
