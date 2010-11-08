package cz.cvut.indepmod.uc.workspace;

import com.jgoodies.binding.value.ValueModel;
import cz.cvut.indepmod.uc.UCNotationModel;
import cz.cvut.indepmod.uc.modelFactory.diagramModel.UCDiagramModel;
import cz.cvut.indepmod.uc.workspace.icons.CloseTabIcon;
import cz.cvut.indepmod.uc.workspace.tabs.UCDefaultTab;
import cz.cvut.indepmod.uc.workspace.tabs.UCGraphUseCase;
import cz.cvut.indepmod.uc.workspace.tabs.UCTabParent;
import cz.cvut.indepmod.uc.workspace.tabs.UCUseCaseTab;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.workspace.UpdatableWorkspaceComponent;
import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagram;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagramChange;
import cz.cvut.promod.services.projectService.treeProjectNode.listener.ProjectDiagramListener;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.event.GraphModelEvent;
import org.jgraph.event.GraphModelListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 *
 * UCWorkspace encapsulate the UCGraph component. 
 */
public class UCWorkspace extends JTabbedPane implements UpdatableWorkspaceComponent, ProjectDiagramListener, MouseListener {
    private static final Logger LOG = Logger.getLogger(UCWorkspace.class);
    private Map<UUID, JGraph> tabGraphs = new HashMap<UUID, JGraph>();
    protected ProjectDiagram actualProjectDiagram = null;

    private final JGraph graph;
    final Map<String, ProModAction> actions;

    /** holds the actual diagram model of a UC notation diagram */
    private UCDiagramModel actualUCDiagramModel = null;

    /** holds the actual project diagram of a UC notation diagram */
    private ProjectDiagram actualProjectDiaram = null;

    private final GraphModelListener graphModelListener;
    private ValueModel selectedToolModel;
    private JPopupMenu popupMenu;
    private int previousTab = 0;


    public UCWorkspace(final JGraph graph, final Map<String, ProModAction> actions){
        
        this.graph = graph;
        this.actions = actions;

        /**
         * Whenever an vertex is updated, this forces VertexInfo frame to update as well.
         */
        UCTabParent defaultTab = new UCDefaultTab(graph, actions);
        this.add(defaultTab);
        addMouseListener(this);

        graphModelListener = new GraphModelListener() {
            public void graphChanged(GraphModelEvent e) {
                final Object[] selectedCells = graph.getSelectionModel().getSelectionCells();
                graph.getSelectionModel().clearSelection();
                graph.getSelectionModel().setSelectionCells(selectedCells);

            }
        };

        this.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                UCWorkspace pane = (UCWorkspace) e.getSource();
                ((UCTabParent) pane.getComponent(pane.getSelectedIndex())).update();
                ((UCTabParent) pane.getComponent(pane.getPreviousTab())).over();
                pane.setPreviousTab();
            }
        });
    }

    public UCWorkspace(final JGraph graph,
                       final Map<String, ProModAction> actions,
                       final ValueModel selectedToolModel,
                       final JPopupMenu popupMenu) {
        this(graph, actions);
        this.selectedToolModel = selectedToolModel;
        this.popupMenu = popupMenu;
    }

    public void openTab(UUID uuid, String name) {
        if (UCWorkspaceData.getTabs().containsKey(uuid)) {
            int index = this.indexOfComponent(UCWorkspaceData.getTabs().get(uuid));
            this.setSelectedIndex(index);
        } else {
            if (!tabGraphs.containsKey(uuid)) {
                tabGraphs.put(uuid, new UCGraphUseCase(this.selectedToolModel, this.popupMenu, actions));
            }
            JGraph tabGraph = tabGraphs.get(uuid);
            UCTabParent tab = (new UCUseCaseTab(tabGraph, actions, uuid));
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
            actualUCDiagramModel.installUndoActions(
                    actions.get(UCNotationModel.UNDO_ACTION_KEY), actions.get(UCNotationModel.REDO_ACTION_KEY)
            );

            actions.get(UCNotationModel.UNDO_ACTION_KEY).setEnabled(actualUCDiagramModel.getUndoManager().canUndo());
            actions.get(UCNotationModel.REDO_ACTION_KEY).setEnabled(actualUCDiagramModel.getUndoManager().canRedo());

            final ProjectDiagram projectDiagram = ModelerSession.getProjectService().getSelectedDiagram();
            projectDiagram.addChangeListener(this);
            actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(projectDiagram.isChanged());


        } catch (ClassCastException exception) {
            LOG.error("Unable to cast selected diagram model to UCDiagramModel class.", exception);
        } catch (Exception exception) {
            LOG.error("An error has occurred during context switch.", exception);
        }

        this.setTitleAt(0, actualProjectDiagram.getDisplayName());
        ((UCTabParent) this.getComponent(0)).update();
    }

    /**
     * {@inheritDoc}
     *
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
    }

    public void changePerformed(final ProjectDiagramChange change) {
        if(ProjectDiagramChange.ChangeType.CHANGE_FLAG.equals(change.getChangeType())
            && change.getChangeValue() instanceof Boolean
                && Boolean.FALSE.equals(change.getChangeValue())){

            actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(false);

            return;
        }

        actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(true);
    }

    public void mouseClicked(MouseEvent e) {
        int tabNumber = getUI().tabForCoordinate(this, e.getX(), e.getY());
        if (tabNumber < 0) return;
        if (!(getIconAt(tabNumber) instanceof CloseTabIcon)) {
            return;
        }
        Rectangle rect = ((CloseTabIcon) getIconAt(tabNumber)).getBounds();
        if (rect.contains(e.getX(), e.getY())) {
            //the tab is being closed
            this.getComponent(tabNumber);

            Iterator iterator = (UCWorkspaceData.getTabs().keySet()).iterator();
            while (iterator.hasNext()) {
                UUID key = (UUID) iterator.next();
                JScrollPane tab = UCWorkspaceData.getTabs().get(key);
                if (tab.equals(this.getComponent(tabNumber))) {
                    LOG.info("Removing tab: " + key);
                    UCWorkspaceData.getTabs().remove(key);
                }
            }
            this.removeTabAt(tabNumber);
        }
    }

    public void mousePressed(MouseEvent e) {
        return;
    }

    public void mouseReleased(MouseEvent e) {
        return;
    }

    public void mouseEntered(MouseEvent e) {
        return;
    }

    public void mouseExited(MouseEvent e) {
        return;
    }

    public int getPreviousTab() {
        return this.previousTab;
    }

    public void setPreviousTab() {
        this.previousTab = this.getSelectedIndex();
    }

}
