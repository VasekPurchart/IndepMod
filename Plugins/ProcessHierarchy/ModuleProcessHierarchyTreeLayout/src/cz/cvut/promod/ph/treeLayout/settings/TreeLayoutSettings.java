package cz.cvut.promod.ph.treeLayout.settings;

import com.jidesoft.dialog.AbstractDialogPage;

import java.util.List;
import java.util.LinkedList;

import cz.cvut.promod.ph.treeLayout.settings.pages.GeneralPage;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 23:47:22, 28.1.2010
 *
 * TreeLayoutSettings represents a setting page fpr the common Settings Dialog.
 */
public class TreeLayoutSettings {

    private final List<AbstractDialogPage> settingPages;

    private static TreeLayoutSettings instance;

    private final TreeLayoutSettingsModel model;

    public TreeLayoutSettings(){
        settingPages = new LinkedList<AbstractDialogPage>();

        model = new TreeLayoutSettingsModel();

        initPages();
    }

    private void initPages() {
        settingPages.add(new GeneralPage());
    }

    public List<AbstractDialogPage> getSettingPages() {
        return settingPages;
    }

    public TreeLayoutSettingsModel.VerticalLayout getVerticalLayout(){
        return model.getVerticalLayout();
    }

    public void setVerticalLayout(final TreeLayoutSettingsModel.VerticalLayout verticalLayout){
        model.setVerticalLayout(verticalLayout);
    }

    public static TreeLayoutSettings getInstance() {
        return instance;
    }

    public static void setInstance(final TreeLayoutSettings instance) {
        TreeLayoutSettings.instance = instance;
    }

}