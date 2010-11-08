package cz.cvut.indepmod.uc.workspace.tabs;

import cz.cvut.indepmod.uc.UCNotationModel;
import cz.cvut.indepmod.uc.modelFactory.diagramModel.UCDiagramModel;
import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagram;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagramChange;
import cz.cvut.promod.services.projectService.utils.ProjectServiceUtils;
import org.jgraph.JGraph;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;

import java.util.Map;
import java.util.UUID;

public class UCUseCaseTab extends UCTabParent {
    private final GraphModelListener graphModelListener;
    private UUID uuid;

    public UCUseCaseTab(final JGraph graph, final Map<String, ProModAction> actions, UUID uuid) {
        super(graph, actions);
        this.uuid = uuid;

        graphModelListener = new GraphModelListener() {
            public void graphChanged(GraphModelEvent e) {
                final Object[] selectedCells = graph.getSelectionModel().getSelectionCells();
                graph.getSelectionModel().clearSelection();
                graph.getSelectionModel().setSelectionCells(selectedCells);

            }
        };
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

    /**
     * Used when the context is switched. Installs the undoMa
     */
    public void update() {
        try {
            actualProjectDiagram = ModelerSession.getProjectService().getSelectedDiagram();
            actualUCDiagramModel = (UCDiagramModel) actualProjectDiagram.getDiagramModel();
            actualUCDiagramModel.getGraphLayoutCacheUseCase(uuid).getModel().addGraphModelListener(graphModelListener);

            graph.setGraphLayoutCache(actualUCDiagramModel.getGraphLayoutCacheUseCase(uuid));

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
