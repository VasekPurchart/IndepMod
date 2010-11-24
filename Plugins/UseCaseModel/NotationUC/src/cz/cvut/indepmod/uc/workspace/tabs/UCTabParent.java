package cz.cvut.indepmod.uc.workspace.tabs;

import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.workspace.UpdatableWorkspaceComponent;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.projectService.treeProjectNode.listener.ProjectDiagramListener;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Viktor
 * Date: 5.11.2010
 * Time: 15:39:00
 * To change this template use File | Settings | File Templates.
 */
public abstract class UCTabParent extends JScrollPane implements UpdatableWorkspaceComponent, ProjectDiagramListener {
    protected static final Logger LOG = Logger.getLogger(UCTabParent.class);

    public UCTabParent(final JComponent graph, final Map<String, ProModAction> actions) {
        super(graph);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Un-install the last UC notation diagram's listeners, sets the UNDO & REDO action as disable and makes
     * the actualUCDiagramModel variable null (actual UC notation diagram is none).
     */
    public abstract void over();


    /**
     * Used when the context is switched. Installs the undoMa
     */
    public abstract void update();

}
