package cz.cvut.indepmod.usecasemodel;

import cz.cvut.indepmod.usecasemodel.ioController.UseCaseModelNotationIOController;
import cz.cvut.indepmod.usecasemodel.modelFactory.UseCaseModelNotationModelFactory;
import cz.cvut.indepmod.usecasemodel.resources.Resources;
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

import javax.swing.*;
import java.util.List;
import java.util.Set;

/**
 * Plugin providing class model notation
 */

public class UseCaseModelNotation implements Notation {

//    private static final Logger LOG = Logger.getLogger(ClassModelNotation.class);

    private final UseCaseModelNotationModelFactory modelFactory;
    private final UseCaseModelNotationIOController ioController;

    public UseCaseModelNotation() {
        modelFactory = new UseCaseModelNotationModelFactory();
        ioController = new UseCaseModelNotationIOController();
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
        return null;
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
