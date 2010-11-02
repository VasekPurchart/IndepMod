package cz.cvut.indepmod.uc.workspace.tabs;

import com.jgoodies.binding.value.ValueModel;
import cz.cvut.indepmod.uc.frames.toolChooser.ToolChooserModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.UCIdentifiableVertex;
import cz.cvut.indepmod.uc.modelFactory.ucGraphModel.UCGraphUseCaseModel;
import cz.cvut.indepmod.uc.workspace.UCWorkspaceMarqueeHandlerUseCase;
import cz.cvut.indepmod.uc.workspace.cell.UCCellViewFactoryUseCase;
import cz.cvut.indepmod.uc.workspace.factory.UCCellFactory;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.graph.*;

import java.awt.geom.Point2D;
import java.util.Map;

public class UCUseCaseTab extends JGraph {
    private static final Logger LOG = Logger.getLogger(UCGraph.class);
    private static final int GET_PORTVIEW_TOLERANCE = 2;
    private final ValueModel selectedToolModel;
   
    public UCUseCaseTab() {
        this(null, null);
    }

    public UCUseCaseTab(final ValueModel selectedToolModel, Map<String, ProModAction> actions) {
        UCGraphUseCaseModel model = new UCGraphUseCaseModel();
        GraphLayoutCache view = new GraphLayoutCache(model, new UCCellViewFactoryUseCase());
        this.setModel(model);
        this.setGraphLayoutCache(view);
        this.selectedToolModel = selectedToolModel;
        this.setMarqueeHandler(new UCWorkspaceMarqueeHandlerUseCase(this, selectedToolModel));

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

    public PortView getTargetPortAt(final Point2D point) {
        return getPortViewAt(point.getX(), point.getY()); // default port can be used
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
     * Creates new vertex (type depends on selected tool).
     *
     * @param point is the point where the new vertex is supposed to be inserted
     * @return new vertex of required type
     */
    private DefaultGraphCell createVertex(final Point2D point) {
//        final ToolChooserModel.Tool tool = (ToolChooserModel.Tool) selectedToolModel.getValue();
        final ToolChooserModel.Tool tool = ToolChooserModel.Tool.ADD_USE_CASE;

        final Point2D snappedPoint = snap((Point2D) point.clone());

        return UCCellFactory.createVertex(snappedPoint, tool);
    }
    
}
