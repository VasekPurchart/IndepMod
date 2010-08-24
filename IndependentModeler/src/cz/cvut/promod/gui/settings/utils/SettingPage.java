package cz.cvut.promod.gui.settings.utils;

import com.jidesoft.dialog.AbstractDialogPage;
import cz.cvut.promod.gui.settings.SettingPageData;
import cz.cvut.promod.gui.settings.SettingPagePanel;

import javax.swing.*;

/**
 * Created by IntelliJ IDEA.
 * User: lucky
 * Date: 20.8.2010
 * Time: 18:55:40
 */
public class SettingPage extends AbstractDialogPage {

    private SettingPageData data;

    public SettingPage(SettingPageData data) {
        this.data = data;

        this.setName(this.data.getName());
        this.setIcon(this.data.getIcon());
    }

    public void lazyInitialize() {
        this.add(this.data.getSettingPage());
        this.data.getSettingPage().lazyInitialize();
    }
}
