package cz.cvut.indepmod.uc.workspace.tabs;

import cz.cvut.indepmod.uc.UCNotationModel;
import cz.cvut.indepmod.uc.modelFactory.diagramModel.UCDiagramModel;
import cz.cvut.indepmod.uc.workspace.UCWorkspace;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.workspace.UpdatableWorkspaceComponent;
import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagram;
import cz.cvut.promod.services.projectService.treeProjectNode.listener.ProjectDiagramListener;
import cz.cvut.promod.services.projectService.utils.ProjectServiceUtils;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;

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
    protected static final Logger LOG = Logger.getLogger(UCWorkspace.class);
    protected final JGraph graph;

    protected UCDiagramModel actualUCDiagramModel = null;
    protected ProjectDiagram actualProjectDiagram = null;
    protected final GraphModelListener graphModelListener;

    final Map<String, ProModAction> actions;

    public UCTabParent(final JGraph graph, final Map<String, ProModAction> actions) {
        super(graph);
        this.graph = graph;
        this.actions = actions;

        graphModelListener = new GraphModelListener() {
            public void graphChanged(GraphModelEvent e) {
                final Object[] selectedCells = graph.getSelectionModel().getSelectionCells();
                graph.getSelectionModel().clearSelection();
                graph.getSelectionModel().setSelectionCells(selectedCells);
            }
        };
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Un-install the last UC notation diagram's listeners, sets the UNDO & REDO action as disable and makes
     * the actualUCDiagramModel variable null (actual UC notation diagram is none).
     */
    public void over() {
        if (actualUCDiagramModel != null) {
            actualUCDiagramModel.uninstallUndoActions();
        } else {
            LOG.error("over() method of UC notation workspace has been invoked, but there hasn't been set any" +
                    "actual UC notation diagram before.");
        }

        if (actualProjectDiagram != null) {
            actualProjectDiagram.removeChangeListener(this);
        }

        actualUCDiagramModel.getGraphLayoutCache().getModel().removeGraphModelListener(graphModelListener);

        actualUCDiagramModel = null;
        actualProjectDiagram = null;

        actions.get(UCNotationModel.UNDO_ACTION_KEY).setEnabled(false);
        actions.get(UCNotationModel.REDO_ACTION_KEY).setEnabled(false);

        ModelerSession.clearFrameTitleText();

        graph.getSelectionModel().clearSelection();
    }


    /**
     * Used when the context is switched. Installs the undoMa
     */
    public void update() {
        try {
            actualProjectDiagram = ModelerSession.getProjectService().getSelectedDiagram();
            actualUCDiagramModel = (UCDiagramModel) actualProjectDiagram.getDiagramModel();
            actualUCDiagramModel.getGraphLayoutCache().getModel().addGraphModelListener(graphModelListener);

            graph.setGraphLayoutCache(actualUCDiagramModel.getGraphLayoutCache());

            actualUCDiagramModel.installUndoActions(
                    actions.get(UCNotationModel.UNDO_ACTION_KEY), actions.get(UCNotationModel.REDO_ACTION_KEY)
            );

            actions.get(UCNotationModel.UNDO_ACTION_KEY).setEnabled(actualUCDiagramModel.getUndoManager().canUndo());
            actions.get(UCNotationModel.REDO_ACTION_KEY).setEnabled(actualUCDiagramModel.getUndoManager().canRedo());

            final ProjectDiagram projectDiagram = ModelerSession.getProjectService().getSelectedDiagram();
            projectDiagram.addChangeListener(this);
            actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(projectDiagram.isChanged());

            // sets the frame's title
            ModelerSession.setFrameTitleText(ProjectServiceUtils.getFileSystemPathToProjectItem(
                    ModelerSession.getProjectService().getSelectedTreePath()
            ));

            // forces all ports are repainted, even when the graph has just been loaded
            graph.getGraphLayoutCache().update();

        } catch (ClassCastException exception) {
            LOG.error("Unable to cast selected diagram model to UCDiagramModel class.", exception);
        } catch (Exception exception) {
            LOG.error("An error has occurred during context switch.", exception);
        }
    }
}
