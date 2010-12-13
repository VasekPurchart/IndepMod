package cz.cvut.indepmod.uc;

import com.jidesoft.status.LabelStatusBarItem;
import com.jidesoft.swing.JideBoxLayout;
import cz.cvut.indepmod.uc.ioController.UCNotationIOController;
import cz.cvut.indepmod.uc.modelFactory.UCNotationModelFactory;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.promod.gui.Modeler;
import cz.cvut.promod.gui.settings.SettingPageData;
import cz.cvut.promod.plugin.notationSpecificPlugIn.DockableFrameData;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.Notation;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.NotationWorkspaceData;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.factory.DiagramModelFactory;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.localIOController.NotationLocalIOController;
import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.menuService.MenuService;
import cz.cvut.promod.services.menuService.utils.InsertMenuItemResult;
import cz.cvut.promod.services.menuService.utils.MenuItemPosition;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Plugin providing class model notation
 */

public class UCNotation implements Notation {

    private static final Logger LOG = Logger.getLogger(UCNotation.class);

    public static final String UC_LABEL = Resources.getResources().getString("uc.menu.uc");
    private final LabelStatusBarItem selectedToolStatusBarItem;
    private final UCNotationModel model;
    private final UCNotationModelFactory modelFactory;
    private final UCNotationIOController ioController;
    private final String NOTATION_DESCRIPTION = Resources.getResources().getString("uc.description");

    /**
     * Constructor of UC Notation - needs properties file
     * @param propertiesFile
     * @throws InstantiationException
     */
    public UCNotation(final File propertiesFile) throws InstantiationException  {
        final Properties properties = new Properties();

        try {
            properties.load(new FileReader(propertiesFile));

           } catch (IOException e) {
            LOG.error("Properties for the Use Case Notations couldn't be read.", e);
            throw new InstantiationException("Mandatory properties couldn't be read.");
        }


        selectedToolStatusBarItem = new LabelStatusBarItem();
        model = new UCNotationModel(properties, selectedToolStatusBarItem);
        modelFactory = new UCNotationModelFactory();
        ioController = new UCNotationIOController(model.getExtension(), getIdentifier());
    }

    /**
     * Getter - identifier
     * @return
     */
    public String getIdentifier() {
        return "12e456a8-2e30-7412-c6a1-2d5e86a521c3";
    }

    /**
     * Getter - name
     * @return
     */
    public String getName() {
        return "UMLUseCaseModelNotation";
    }

    /**
     * Getter - full name
     * @return
     */
    public String getFullName() {
        return "UseCase Diagram";
    }

    /**
     * Getter abbreviation
     * @return
     */
    public String getAbbreviation() {
        return "UMLUseCaseModelNotation";
    }

    /**
     * Getter - description
     * @return
     */
    public String getDescription() {
        return "This plugin provides UseCase model notation according to the UML standard.";
    }

    /**
     * Getter - notation icon
     * @return
     */
    public Icon getNotationIcon() {
        return Resources.getIcon(Resources.ICONS + Resources.DIAGRAM);
    }

    /**
     * Getter - notation tooltip
     * @return
     */
    public String getNotationToolTip() {
        return "Plugin implementing UML UseCase model notation.";
    }

    /**
     * Getter - notation preview text
     * @return
     */
    public String getNotationPreviewText() {
        return "UML UseCase model notation is used for modeling various data models. It can also be used in application design phase.";
    }

    /**
     * Getter - notation preview image
     * @return
     */
    public ImageIcon getNotationPreviewImage() {
        return Resources.getIcon(Resources.ICONS + Resources.PREVIEW);
    }

    /**
     * Getter - notation workspace data
     * @return
     */
    public NotationWorkspaceData getNotationWorkspaceData() {
        return model.getWorkspace();
    }

    /**
     * Getter - diagram model factory
     * @return
     */
    public DiagramModelFactory getDiagramModelFactory() {
        return modelFactory;
    }

    /**
     * Getter - Local IO Controller
     * @return
     */
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

    /**
     * Getter - dockable framr data
     * @return
     */
    public Set<DockableFrameData> getDockableFrames() {
        return model.getDockableFrames();
    }

    /**
     * Initialisation - menu, actions, toolbar
     */
    public void init() {
        initActions();

        initMainMenu();

        initToolBar();

        initStatusBar();
    }

    /**
     * Initialisation of status bar
     */
    private void initStatusBar() {
        ModelerSession.getStatusBarService().addStatusBarItem(
                getIdentifier(), selectedToolStatusBarItem, JideBoxLayout.FIX
        );
    }

    /**
     * Initialisation of toolbar
     */
    private void initToolBar() {
        ModelerSession.getToolBarService().addAction(
                getIdentifier(),
                model.getAction(UCNotationModel.UNDO_ACTION_KEY)
        );

        ModelerSession.getToolBarService().addAction(
                getIdentifier(),
                model.getAction(UCNotationModel.REDO_ACTION_KEY)
        );

        ModelerSession.getToolBarService().addAction(
                getIdentifier(),
                model.getAction(UCNotationModel.SAVE_ACTION_KEY)
        );

        ModelerSession.getToolBarService().addAction(
                getIdentifier(),
                model.getAction(UCNotationModel.SAVE_ALL_ACTION_KEY)
        );
    }

    /**
     * Initialisation of actions
     */
    private void initActions() {
        ModelerSession.getActionService().registerAction(
                getIdentifier(),
                model.getActionIdentifier(UCNotationModel.REFRESH_ACTION_KEY),
                model.getAction(UCNotationModel.REFRESH_ACTION_KEY)
        );

        ModelerSession.getActionService().registerAction(
                getIdentifier(),
                model.getActionIdentifier(UCNotationModel.DELETE_ACTION_KEY),
                model.getAction(UCNotationModel.DELETE_ACTION_KEY)
        );

        ModelerSession.getActionService().registerAction(
                getIdentifier(),
                model.getActionIdentifier(UCNotationModel.UNDO_ACTION_KEY),
                model.getAction(UCNotationModel.UNDO_ACTION_KEY)
        );

        ModelerSession.getActionService().registerAction(
                getIdentifier(),
                model.getActionIdentifier(UCNotationModel.REDO_ACTION_KEY),
                model.getAction(UCNotationModel.REDO_ACTION_KEY)
        );

        ModelerSession.getActionService().registerAction(
                getIdentifier(),
                model.getActionIdentifier(UCNotationModel.SAVE_ACTION_KEY),
                model.getAction(UCNotationModel.SAVE_ACTION_KEY)
        );

        ModelerSession.getActionService().registerAction(
                getIdentifier(),
                model.getActionIdentifier(UCNotationModel.SAVE_ALL_ACTION_KEY),
                model.getAction(UCNotationModel.SAVE_ALL_ACTION_KEY)
        );
    }
    public void finish() {
    }

    /**
     * Getter - settings pages
     * @return
     */
    public List<SettingPageData> getSettingPages() {
        return null;
    }

    /**
     * Initialisation of main menu
     */
    private void initMainMenu() {
        ModelerSession.getMenuService().insertMainMenuItem(
            model.getAction(UCNotationModel.SAVE_ACTION_KEY + "xx"),
            new MenuItemPosition(Modeler.FILE_LABEL, MenuItemPosition.PlacementStyle.FIRST)
        );


        ModelerSession.getMenuService().insertMainMenuItem(
            model.getAction(UCNotationModel.REFRESH_ACTION_KEY),
            new MenuItemPosition(UC_LABEL)
        );
    }
}
