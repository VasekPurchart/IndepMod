package cz.cvut.promod.gui.settings;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: lucky
 * Date: 20.8.2010
 * Time: 18:49:04
 */
public abstract class SettingPagePanel extends JPanel {
    private List<ActionListener> applyActionEnabledListeners = new LinkedList<ActionListener>();

    public abstract void lazyInitialize();

    public abstract AbstractAction getApplyAction();

    public abstract AbstractAction getCancelAction();

    public abstract AbstractAction getOkAction();

    public void addApplyActionEnabledListener(ActionListener lsnr) {
        if (! this.applyActionEnabledListeners.contains(lsnr)) {
            this.applyActionEnabledListeners.add(lsnr);
        }
    }

    public void fireApplyActionEnable() {
        for (ActionListener action : this.applyActionEnabledListeners) {
            action.actionPerformed(null);
        }
    }
}
