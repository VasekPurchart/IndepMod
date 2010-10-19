package cz.cvut.indepmod.uc;

import com.jidesoft.status.LabelStatusBarItem;
import cz.cvut.indepmod.uc.frames.graphOptions.GraphOptions;
import cz.cvut.indepmod.uc.frames.toolChooser.ToolChooser;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.indepmod.uc.workspace.UCWorkspaceData;
import cz.cvut.promod.epc.frames.vertexInfo.VertexInfo;
import cz.cvut.promod.plugin.notationSpecificPlugIn.DockableFrameData;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.NotationWorkspaceData;
import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.menuService.MenuService;
import cz.cvut.promod.services.menuService.utils.InsertMenuItemResult;
import cz.cvut.promod.services.menuService.utils.MenuItemPosition;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.event.ActionEvent;
import java.util.*;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 19:43:00, 5.12.2009
 *
 * A model component for the EPCNotation plugin.
 */
public class UCNotationModel {

    // actions
    public static final String SAVE_ACTION_KEY = "uc.action.save";
    public static final String SAVE_ALL_ACTION_KEY = "uc.action.save.all";   
    public static final String UNDO_ACTION_KEY = "uc.action.undo";
    public static final String REDO_ACTION_KEY = "uc.action.redo";
    public static final String DELETE_ACTION_KEY = "uc.action.delete";

    // resources
    public static final String REFRESH_ACTION_KEY = "uc.action.refresh";

    // properties
    public static final String IDENTIFIER = "uc.identifier";
    public static final String PLUGIN_NAME = "uc.name";
    public static final String DESCRIPTION = "uc.description";
    public static final String FULL_NAME = "uc.full.name";
    public static final String ABBREVIATION = "uc.abbreviation";
    public static final String EXTENSION = "uc.file.extension";

    private static final Logger LOG = Logger.getLogger(UCNotationModel.class);

    private final Properties properties;

    private Set<DockableFrameData> dockableFrames;

    private final UCWorkspaceData workspace;

    private final Map<String, ProModAction> actions;

    private final  JPopupMenu popupMenu = ModelerSession.getComponentFactoryService().createPopupMenu();


    /**
     * Constructs a new EPCNotationModel.
     *
     * @param properties are the required properties
     * @param selectedToolStatusBarItem is the status bat item holding the actual select tool
     * @throws InstantiationException when the initialization fail
     */
    public UCNotationModel(final Properties properties,
                            final LabelStatusBarItem selectedToolStatusBarItem) throws InstantiationException{
        this.properties = properties;

        actions = new HashMap<String, ProModAction>();

        initActions();

        checkProperties();

        //init dockable frames
        dockableFrames = new HashSet<DockableFrameData>();

        final ToolChooser toolChooser = new ToolChooser(selectedToolStatusBarItem);
        dockableFrames.add(toolChooser);

        final VertexInfo vertexInfo = new VertexInfo();
        dockableFrames.add(vertexInfo);

        final GraphOptions graphOptions = new GraphOptions();
        dockableFrames.add(graphOptions);

        // init workspace data
        workspace = new UCWorkspaceData(
                toolChooser.getSelectedToolModel(),
                graphOptions.getGridModel(),
                graphOptions.getLockModel(),
                graphOptions.getViewGridModel(),
                graphOptions.getCellSizeModel(),
                graphOptions.getScaleModel(),
                graphOptions.getMovableBelowZeroModel(),
                actions,
                popupMenu
        );

        // frames event handling
        vertexInfo.initCellSelectionListener(workspace.getGraph());
        graphOptions.initEventHandling(actions);
        initPopupMenu();
    }

    private void initActions() {
        actions.put(SAVE_ACTION_KEY,
            new ProModAction(Resources.getResources().getString(SAVE_ACTION_KEY),
                    Resources.getIcon(Resources.ICONS + Resources.SAVE), null){
                public void actionPerformed(ActionEvent event) {
                    final TreePath treePath = ModelerSession.getProjectService().getSelectedDiagramPath();

                    if(treePath != null){
                        ModelerSession.getProjectControlService().synchronize(
                                treePath,
                                true, true, false, false
                        );
                    }
                }
            }
        );
    }

    private void initPopupMenu() {
        final Action deleteAction = getAction(DELETE_ACTION_KEY);
        deleteAction.setEnabled(true);
        popupMenu.add(deleteAction);
    }

    public InsertMenuItemResult addPopupMenuAction(final ProModAction proModAction, final MenuItemPosition menuItemPosition,final MenuService.MenuSeparator menuSeparator, final boolean checkable)  {
        return ModelerSession.getMenuService().insertAction(null, popupMenu, proModAction, menuSeparator, menuItemPosition, checkable);
    }

    private void checkProperties() throws InstantiationException{
        if(!properties.containsKey(UCNotationModel.FULL_NAME)) {
            LOG.error("Missing property " + UCNotationModel.FULL_NAME);
            throw new InstantiationException("Missing property " + UCNotationModel.FULL_NAME);
        }
        if(!properties.containsKey(UCNotationModel.IDENTIFIER)){
            LOG.error("Missing property " + UCNotationModel.IDENTIFIER);
            throw new InstantiationException("Missing property " + UCNotationModel.IDENTIFIER);
        }
        if(!properties.containsKey(UCNotationModel.ABBREVIATION)){
            LOG.error("Missing property " + UCNotationModel.ABBREVIATION);
            throw new InstantiationException("Missing property " + UCNotationModel.ABBREVIATION);
        }
        if(!properties.containsKey(UCNotationModel.EXTENSION)){
            LOG.error("Missing property " + UCNotationModel.EXTENSION);
            throw new InstantiationException("Missing property " + UCNotationModel.EXTENSION);
        }
        if(!properties.containsKey(REFRESH_ACTION_KEY)){
            LOG.error("Missing property " + UCNotationModel.REFRESH_ACTION_KEY);
            throw new InstantiationException("Missing property " + UCNotationModel.REFRESH_ACTION_KEY);
        }
        if(!properties.containsKey(DELETE_ACTION_KEY)){
            LOG.error("Missing property " + UCNotationModel.DELETE_ACTION_KEY);
            throw new InstantiationException("Missing property " + UCNotationModel.DELETE_ACTION_KEY);
        }
        if(!properties.containsKey(UNDO_ACTION_KEY)){
            LOG.error("Missing property " + UCNotationModel.UNDO_ACTION_KEY);
            throw new InstantiationException("Missing property " + UCNotationModel.UNDO_ACTION_KEY);
        }
        if(!properties.containsKey(REDO_ACTION_KEY)){
            LOG.error("Missing property " + UCNotationModel.REDO_ACTION_KEY);
            throw new InstantiationException("Missing property " + UCNotationModel.REDO_ACTION_KEY);
        }
        if(!properties.containsKey(SAVE_ACTION_KEY)){
            LOG.error("Missing property " + UCNotationModel.SAVE_ACTION_KEY);
            throw new InstantiationException("Missing property " + UCNotationModel.SAVE_ACTION_KEY);
        }
        if(!properties.containsKey(SAVE_ALL_ACTION_KEY)){
            LOG.error(UCNotationModel.SAVE_ALL_ACTION_KEY);
            throw new InstantiationException("Missing property " + UCNotationModel.SAVE_ALL_ACTION_KEY);
        }
    }

    /**
     * @return plugin's identifier
     */
    public String getIdentifier() {
        return properties.getProperty(UCNotationModel.IDENTIFIER);
    }

    /**
     * @return notation's name.
     */
    public String getFullName() {
        return properties.getProperty(UCNotationModel.FULL_NAME);
    }

    /**
     * @return notation's name abbreviation
     */
    public String getAbbreviation() {
        return properties.getProperty(UCNotationModel.ABBREVIATION);
    }

    /*
     * @return notation's file extension
     */
    public String getExtension() {
        return properties.getProperty(UCNotationModel.EXTENSION);
    }

    /**
     * @return dockable frames
     */
    public Set<DockableFrameData> getDockableFrames() {
        return dockableFrames;
    }

    /**
     * @return notation's workspace component
     */
    public NotationWorkspaceData getWorkspace() {
        return workspace;
    }

    /**
     * Returns required plugin's action identifier.
     *
     * @param key is the key of the action
     * @return the required action identifier, null if there is no such an action
     */
    public String getActionIdentifier(final String key){
        return properties.getProperty(key);        
    }


    /**
     * Returns required plugin's action.
     *
     * @param key is the key of the action
     * @return the required action, null if there is no such an action
     */
    public ProModAction getAction(final String key){
        if(actions.containsKey(key)){
            return actions.get(key);
        }

        return null;
    }


    /**
     * @return the plugin's name
     */
    public String getName() {
        return properties.getProperty(UCNotationModel.PLUGIN_NAME);
    }

    /**
     * @return the plugin's description
     */
    public String getDescription() {
        return properties.getProperty(UCNotationModel.DESCRIPTION);
    }
}
