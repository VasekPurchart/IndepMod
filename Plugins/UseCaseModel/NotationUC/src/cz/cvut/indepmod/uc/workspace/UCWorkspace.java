package cz.cvut.indepmod.uc.workspace;

import cz.cvut.indepmod.uc.UCNotationModel;
import cz.cvut.indepmod.uc.modelFactory.diagramModel.UCDiagramModel;
import cz.cvut.indepmod.uc.workspace.icons.CloseTabIcon;
import cz.cvut.indepmod.uc.workspace.tabs.UCDefaultTab;
import cz.cvut.indepmod.uc.workspace.tabs.UCUseCaseTab;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.workspace.UpdatableWorkspaceComponent;
import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagram;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagramChange;
import cz.cvut.promod.services.projectService.treeProjectNode.listener.ProjectDiagramListener;
import cz.cvut.promod.services.projectService.utils.ProjectServiceUtils;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;

import javax.swing.*;
import java.util.Map;
import java.util.UUID;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 * <p/>
 * UCWorkspace encapsulate the UCGraph component.
 */
public class UCWorkspace extends JTabbedPane implements UpdatableWorkspaceComponent, ProjectDiagramListener {
    private static final Logger LOG = Logger.getLogger(UCWorkspace.class);
    private UCDiagramModel actualUCDiagramModel = null;
    private ProjectDiagram actualProjectDiagram = null;
    private final JGraph graph;
    final Map<String, ProModAction> actions;
    private final GraphModelListener graphModelListener;

    public UCWorkspace(final JGraph graph, final Map<String, ProModAction> actions) {
        this.graph = graph;
        this.actions = actions;
        this.add(new UCDefaultTab(graph, actions));
        
        graphModelListener = new GraphModelListener() {
            public void graphChanged(GraphModelEvent e) {
                final Object[] selectedCells = graph.getSelectionModel().getSelectionCells();
                graph.getSelectionModel().clearSelection();
                graph.getSelectionModel().setSelectionCells(selectedCells);

            }
        };
    }

    public void openTab(UUID uuid, String name) {
        if (UCWorkspaceData.getTabs().containsKey(uuid)) {
            int index = this.indexOfComponent(UCWorkspaceData.getTabs().get(uuid));
            this.setSelectedIndex(index);
        } else {
            JScrollPane tab = new JScrollPane(new UCUseCaseTab());
            this.addTab(name, new CloseTabIcon(), tab);

            UCWorkspaceData.getTabs().put(uuid, tab);
            int index = this.indexOfComponent(tab);
            this.setSelectedIndex(index);
        }
    }
    /**
     * Used when the context is switched. Installs the undoMa
     */
    public void update() {
        try {
            actualProjectDiagram = ModelerSession.getProjectService().getSelectedDiagram();
            actualUCDiagramModel = (UCDiagramModel) actualProjectDiagram.getDiagramModel();
            actualUCDiagramModel.getGraphLayoutCache().getModel().addGraphModelListener(graphModelListener);
            this.setTitleAt(0, actualProjectDiagram.getDisplayName());

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

    public void changePerformed(final ProjectDiagramChange change) {
        if (ProjectDiagramChange.ChangeType.CHANGE_FLAG.equals(change.getChangeType())
                && change.getChangeValue() instanceof Boolean
                && Boolean.FALSE.equals(change.getChangeValue())) {

            actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(false);

            return;
        }

        actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(true);
    }
}
