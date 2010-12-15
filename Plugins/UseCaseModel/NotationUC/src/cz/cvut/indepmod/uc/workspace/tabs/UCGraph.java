package cz.cvut.indepmod.uc.workspace.tabs;

import com.jgoodies.binding.value.ValueModel;
import cz.cvut.indepmod.uc.UCNotationModel;
import cz.cvut.indepmod.uc.frames.toolChooser.ToolChooserModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.SystemBorderModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.UCEditableVertex;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.UCIdentifiableVertex;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.UseCaseModel;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.indepmod.uc.workspace.UCWorkspace;
import cz.cvut.indepmod.uc.workspace.UCWorkspaceData;
import cz.cvut.indepmod.uc.workspace.UCWorkspaceMarqueeHandler;
import cz.cvut.indepmod.uc.workspace.factory.UCCellFactory;
import cz.cvut.indepmod.uc.workspace.tabs.usecase.UCUseCaseTab;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.Port;
import org.jgraph.graph.PortView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.UUID;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 * Date: 14:42:28, 6.12.2009
 * <p/>
 * Implementation of JGraph for UC notation.
 */
public class UCGraph extends JGraph {
    private static final Logger LOG = Logger.getLogger(UCGraph.class);
    private static final int GET_PORTVIEW_TOLERANCE = 2;
    private final ValueModel selectedToolModel;
    private ProModAction removeAction; //never change this action once has been instantiated

    /**
     * Constructor of UC Graph
     */
    public UCGraph(final ValueModel selectedToolModel,
                   final JPopupMenu popupMenuActor,
                   final JPopupMenu popupMenuUC,
                   Map<String, ProModAction> actions) {

        this.selectedToolModel = selectedToolModel;

        setPortsVisible(false);
        setJumpToDefaultPort(true);

        initActions(actions);

        setMarqueeHandler(new UCWorkspaceMarqueeHandler(this, selectedToolModel, popupMenuActor, popupMenuUC));
    }

    /**
     * Event handler - key has been pressed
     *
     * @param e event object
     */
    protected void processKeyEvent(KeyEvent e) {
        if (127 == e.getKeyCode()) {
            if (!isSelectionEmpty()) {
                deleteCells(getSelectionCells());
            }
        }
    }

    public void deleteCells(Object[] cells) {
        for (Object selectedCell : cells) {
            if (!(selectedCell instanceof DefaultGraphCell)) continue;
            if (((DefaultGraphCell) selectedCell).getUserObject() instanceof UseCaseModel) {
                for (Component comp : UCWorkspaceData.getWorkspaceComponentSingletonStatic().getComponents()) {
                    if (comp instanceof UCUseCaseTab) {
                        if (((UCUseCaseTab) comp).getUuid().equals(((UseCaseModel) ((DefaultGraphCell) selectedCell).getUserObject()).getUuid())) {
                            UCWorkspaceData.getWorkspaceComponentSingletonStatic().remove(comp);
                        }
                    }
                }
                UCWorkspaceData.getTabs().remove(((UseCaseModel) ((DefaultGraphCell) selectedCell).getUserObject()).getUuid());
            }

        }
        logDeleteInfo(cells);
        getGraphLayoutCache().remove(cells, true, true);

    }

    /**
     * Initialisation of actions
     *
     * @param actions Map of actions we want to initialize
     */
    private void initActions(final Map<String, ProModAction> actions) {
        final ProModAction refreshAction = new ProModAction(Resources.getResources().getString(UCNotationModel.REFRESH_ACTION_KEY), null, null) {
            public void actionPerformed(ActionEvent event) {
                refresh();
            }
        };

        actions.put(UCNotationModel.REFRESH_ACTION_KEY, refreshAction);

        removeAction = new ProModAction(Resources.getResources().getString(UCNotationModel.DELETE_ACTION_KEY), null, null) {
            public void actionPerformed(ActionEvent event) {
                if (!isSelectionEmpty()) {
                    deleteCells(getSelectionCells());
                }
            }
        };

        actions.put(UCNotationModel.DELETE_ACTION_KEY, removeAction);


        final ProModAction detailAction = new ProModAction(Resources.getResources().getString(UCNotationModel.DETAIL_ACTION_KEY), null, null) {
            public void actionPerformed(ActionEvent event) {
                if (!isSelectionEmpty()) {
                    Object selectedCells = getSelectionCell();
                    if (selectedCells instanceof DefaultGraphCell) {
                        final DefaultGraphCell defaultGraphCell = (DefaultGraphCell) selectedCells;
                        final Object object = defaultGraphCell.getUserObject();

                        final String name;
                        final UUID uuid;
                        if (object instanceof UCEditableVertex) {
                            name = ((UCEditableVertex) object).getName();
                        } else {
                            name = null;
                        }
                        if (object instanceof UCIdentifiableVertex) {
                            uuid = ((UCIdentifiableVertex) object).getUuid();
                        } else {
                            uuid = null;
                        }
                        if (name != null && uuid != null) {
                            UCWorkspace workspace = (UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic();
                            if (object instanceof UseCaseModel) {
                                workspace.openTab(uuid, name, (UseCaseModel) object);
                            }
                        }
                    }
                }
            }
        };

        actions.put(UCNotationModel.DETAIL_ACTION_KEY, detailAction);
    }

    /**
     * Log information about cells tha have been deleted.
     *
     * @param selectedCells that have been deleted
     */
    private void logDeleteInfo(final Object[] selectedCells) {
        for (final Object cell : selectedCells) {
            if (cell instanceof DefaultEdge) {
                final DefaultEdge edge = (DefaultEdge) cell;
                LOG.info("Edge has been deleted.");

            } else if (cell instanceof DefaultGraphCell) {
                final DefaultGraphCell defaultGraphCell = (DefaultGraphCell) cell;
                final Object object = defaultGraphCell.getUserObject();

                final String identifier;
                if (object instanceof UCIdentifiableVertex) {
                    identifier = ((UCIdentifiableVertex) object).getUuid().toString();
                } else {
                    identifier = "Not identifiable item";
                }

                LOG.info("Vertex has been deleted, detail info: " + cell + ", uuid: " + identifier + ".");

            } else {
                LOG.info("Unknown item has been deleted, object: " + cell + ".");
            }
        }
    }

    /**
     * Getter - port view at given point
     */
    public PortView getSourcePortAt(final Point2D point) {
        setJumpToDefaultPort(false); // do not use default currentPort if no another currentPort is on the point

        PortView result;
        try {
            result = getPortViewAt(point.getX(), point.getY(), GET_PORTVIEW_TOLERANCE);
        }
        catch (Exception exception) {
            LOG.error("Couldn't locate the portview.");
            result = null;
        }

        setJumpToDefaultPort(true);

        return result;
    }

    /**
     * Getter - target port view at given point
     */
    public PortView getTargetPortAt(final Point2D point) {

        PortView result = getPortViewAt(point.getX(), point.getY());

        // System Border cant be connected
        if (result != null) {
            org.jgraph.graph.DefaultPort model = (org.jgraph.graph.DefaultPort) result.getCell();
            org.jgraph.graph.DefaultGraphCell graphCell = (org.jgraph.graph.DefaultGraphCell) model.getParent();
            if (graphCell.getUserObject() instanceof SystemBorderModel)
                result = null;

            /*  This works but view part doesnt work - Lukas
            if(graphCell.getUserObject() instanceof ActorModel &&           
               (UCWorkspaceMarqueeHandler.getStartingPortComponentType() == UCGraphModel.TYPE_ACTOR ||
                UCWorkspaceMarqueeHandler.getEdgeType() == EdgeModel.EdgeType.INCLUDE_FLOW))
                result = null; */
        }

        return result;
    }

    /**
     * Inserts a new vertex (type depends on currently selected tool) to the graph and currently selected
     * graph layout cache.
     * <p/>
     * Finally switch selected tool to 'control' tool.
     *
     * @param point where the new vertex is supposed to be inserted
     */
    public void insert(final Point2D point) {
        DefaultGraphCell vertex = createVertex(point);
        getGraphLayoutCache().insert(vertex);

        selectedToolModel.setValue(ToolChooserModel.Tool.CONTROL);
    }

    /**
     * Realizes the actual connection between 2 vertexes. It means it creates the edge (type is defined by
     * currently selected tool) and adds this new edge to graphLayoutCache.
     *
     * @param source is the source port for new edge
     * @param target is the target port for new edge
     */
    public void connectVertexes(final Port source, final Port target) {
        final ToolChooserModel.Tool tool = (ToolChooserModel.Tool) selectedToolModel.getValue();

        final DefaultEdge edge = UCCellFactory.createEdge(tool);
        edge.setSource(source);
        edge.setTarget(target);

        if ((getModel().acceptsSource(edge, source)) && (getModel().acceptsTarget(edge, target))) {
            getGraphLayoutCache().insertEdge(edge, source, target);
        }
    }

    /**
     * Creates new vertex (type depends on selected tool).
     *
     * @param point is the point where the new vertex is supposed to be inserted
     * @return new vertex of required type
     */
    private DefaultGraphCell createVertex(final Point2D point) {
        final ToolChooserModel.Tool tool = (ToolChooserModel.Tool) selectedToolModel.getValue();
        final Point2D snappedPoint = snap((Point2D) point.clone());

        return UCCellFactory.createVertex(snappedPoint, tool);
    }

    /**
     * Getter - remove action
     */
    public ProModAction getRemoveAction() {
        return removeAction;
    }

    /**
     * Getter - selected tool model
     */
    public ValueModel getSelectedToolModel() {
        return this.selectedToolModel;
    }
}
