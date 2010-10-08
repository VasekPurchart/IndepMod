package cz.cvut.indepmod.uc.workspace;

import cz.cvut.indepmod.uc.UCNotationModel;
import cz.cvut.indepmod.uc.modelFactory.diagramModel.UCDiagramModel;
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

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 20:00:20, 5.12.2009
 *
 * UCWorkspace encapsulate the UCGraph component. 
 */
public class UCWorkspace extends JScrollPane implements UpdatableWorkspaceComponent, ProjectDiagramListener {

    private static final Logger LOG = Logger.getLogger(UCWorkspace.class);

    private final JGraph graph;
    final Map<String, ProModAction> actions;

    /** holds the actual diagram model of a UC notation diagram */
    private UCDiagramModel actualUCDiagramModel = null;

    /** holds the actual project diagram of a UC notation diagram */
    private ProjectDiagram actualProjectDiaram = null;

    private final GraphModelListener graphModelListener;


    public UCWorkspace(final JGraph graph, final Map<String, ProModAction> actions){
        super(graph);

        this.graph = graph;
        this.actions = actions;

        /**
         * Whenever an vertex is updated, this forces VertexInfo frame to update as well.
         */
        graphModelListener = new GraphModelListener(){
            public void graphChanged(GraphModelEvent e) {
                final Object[] selectedCells = graph.getSelectionModel().getSelectionCells();
                graph.getSelectionModel().clearSelection();
                graph.getSelectionModel().setSelectionCells(selectedCells);

            }
        };
    }

    /**
     * Used when the context is switched. Installs the undoMa
     */
    public void update() {
        try{
            actualProjectDiaram = ModelerSession.getProjectService().getSelectedDiagram();
            actualUCDiagramModel = (UCDiagramModel) actualProjectDiaram.getDiagramModel();

            actualUCDiagramModel.getGraphLayoutCache().getModel().addGraphModelListener(graphModelListener);

            graph.setGraphLayoutCache(actualUCDiagramModel.getGraphLayoutCache());

            actualUCDiagramModel.installUndoActions(
                    actions.get(UCNotationModel.UNDO_ACTION_KEY), actions.get(UCNotationModel.REDO_ACTION_KEY)
            );
            
//            actions.get(UCNotationModel.UNDO_ACTION_KEY).setEnabled(actualUCDiagramModel.getUndoManager().canUndo());
     //       actions.get(UCNotationModel.REDO_ACTION_KEY).setEnabled(actualUCDiagramModel.getUndoManager().canRedo());

            final ProjectDiagram projectDiagram = ModelerSession.getProjectService().getSelectedDiagram();
            projectDiagram.addChangeListener(this);
//            actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(projectDiagram.isChanged());

            // sets the frame's title            
            ModelerSession.setFrameTitleText(ProjectServiceUtils.getFileSystemPathToProjectItem(
                    ModelerSession.getProjectService().getSelectedTreePath()
            ));

            // forces all ports are repainted, even when the graph has just been loaded
            graph.getGraphLayoutCache().update();

        } catch (ClassCastException exception){
            LOG.error("Unable to cast selected diagram model to UCDiagramModel class.", exception);
        } catch (Exception exception){
            LOG.error("An error has occurred during context switch.", exception);
        }
    }

    /**
     * {@inheritDoc}
     *
     * Un-install the last UC notation diagram's listeners, sets the UNDO & REDO action as disable and makes
     * the actualUCDiagramModel variable null (actual UC notation diagram is none).
     */
    public void over() {
        if(actualUCDiagramModel != null){
            actualUCDiagramModel.uninstallUndoActions();
        } else {
            LOG.error("over() method of UC notation workspace has been invoked, but there hasn't been set any" +
                    "actual UC notation diagram before.");
        }

        if(actualProjectDiaram != null){
            actualProjectDiaram.removeChangeListener(this);
        }

        actualUCDiagramModel.getGraphLayoutCache().getModel().removeGraphModelListener(graphModelListener);

        actualUCDiagramModel = null;
        actualProjectDiaram = null;

        actions.get(UCNotationModel.UNDO_ACTION_KEY).setEnabled(false);
        actions.get(UCNotationModel.REDO_ACTION_KEY).setEnabled(false);        

        ModelerSession.clearFrameTitleText();

        graph.getSelectionModel().clearSelection();
    }

    public void changePerformed(final ProjectDiagramChange change) {
        if(ProjectDiagramChange.ChangeType.CHANGE_FLAG.equals(change.getChangeType())
            && change.getChangeValue() instanceof Boolean
                && Boolean.FALSE.equals(change.getChangeValue())){

            //actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(false);

            return;
        }

        //actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(true);
    }
}
