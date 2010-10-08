package cz.cvut.indepmod.uc;

import com.jidesoft.status.LabelStatusBarItem;
import cz.cvut.indepmod.uc.ioController.UCNotationIOController;
import cz.cvut.indepmod.uc.modelFactory.UCNotationModelFactory;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.promod.gui.settings.SettingPageData;
import cz.cvut.promod.plugin.notationSpecificPlugIn.DockableFrameData;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.Notation;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.NotationWorkspaceData;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.factory.DiagramModelFactory;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.localIOController.NotationLocalIOController;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.menuService.MenuService;
import cz.cvut.promod.services.menuService.utils.InsertMenuItemResult;
import cz.cvut.promod.services.menuService.utils.MenuItemPosition;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Plugin providing class model notation
 */

public class UCNotation implements Notation {

    private static final Logger LOG = Logger.getLogger(UCNotation.class);

    private final LabelStatusBarItem selectedToolStatusBarItem;
    private final UCNotationModel model;
    private final UCNotationModelFactory modelFactory;
    private final UCNotationIOController ioController;
    private final String NOTATION_DESCRIPTION = Resources.getResources().getString("uc.description");

    public UCNotation(final File propertiesFile) throws InstantiationException  {
        final Properties properties = new Properties();
        LOG.info(propertiesFile);
        try {
            properties.load(new FileReader(propertiesFile));
            Enumeration keys = properties.keys();
while (keys.hasMoreElements()) {
  String key = (String)keys.nextElement();
  String value = (String)properties.get(key);
  LOG.info(key + ": " + value);
}
        } catch (IOException e) {
            LOG.error("Properties for the Use Case Notations couldn't be read.", e);
            throw new InstantiationException("Mandatory properties couldn't be read.");
        }


        selectedToolStatusBarItem = new LabelStatusBarItem();
        model = new UCNotationModel(properties, selectedToolStatusBarItem);
        modelFactory = new UCNotationModelFactory();
        ioController = new UCNotationIOController(model.getExtension(), getIdentifier());
    }

    public String getIdentifier() {
        return "12e456a8-2e30-7412-c6a1-2d5e86a521c3";
    }

    public String getName() {
        return "UMLUseCaseModelNotation";
    }

    public String getFullName() {
        return "UML UseCase Model Notation";
    }

    public String getAbbreviation() {
        return "UMLUseCaseModelNotation";
    }

    public String getDescription() {
        return "This plugin provides UseCase model notation according to the UML standard.";
    }

    public Icon getNotationIcon() {
        return Resources.getIcon(Resources.ICONS + Resources.DIAGRAM);
    }

    public String getNotationToolTip() {
        return "Plugin implementing UML UseCase model notation.";
    }

    public String getNotationPreviewText() {
        return "UML UseCase model notation is used for modeling various data models. It can also be used in application design phase.";
    }

    public ImageIcon getNotationPreviewImage() {
        return Resources.getIcon(Resources.ICONS + Resources.PREVIEW);
    }
    
    public NotationWorkspaceData getNotationWorkspaceData() {
        return model.getWorkspace();
    }

    public DiagramModelFactory getDiagramModelFactory() {
        return modelFactory;
    }

    public NotationLocalIOController getLocalIOController() {
        return ioController;
    }

    /**
     * Class model notation does NOT support popup menu functionality.
     *
     * {@inheritDoc}
     */
    public InsertMenuItemResult addPopupMenuItem(final ProModAction proModAction, final MenuItemPosition menuItemPosition,final MenuService.MenuSeparator menuSeparator, boolean checkable){
        return InsertMenuItemResult.POPUP_NOT_SUPPORTED;
    }

    public Set<DockableFrameData> getDockableFrames() {
        return null;
    }

    public void init() {
    }

    public void finish() {
    }

    public List<SettingPageData> getSettingPages() {
        return null;
    }

}
