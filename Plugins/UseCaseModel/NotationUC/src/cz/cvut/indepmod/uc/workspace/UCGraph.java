package cz.cvut.indepmod.uc.workspace;

import com.jgoodies.binding.value.ValueModel;
import cz.cvut.indepmod.uc.UCNotationModel;
import cz.cvut.indepmod.uc.frames.toolChooser.ToolChooserModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.UCIdentifiableVertex;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.indepmod.uc.workspace.factory.UCCellFactory;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.Port;
import org.jgraph.graph.PortView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.Map;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 * Date: 14:42:28, 6.12.2009
 *
 * Implementation of JGraph for UC notation. 
 */
public class UCGraph extends JGraph {

    private static final Logger LOG = Logger.getLogger(UCGraph.class);

    private static final int GET_PORTVIEW_TOLERANCE = 2;

    private final ValueModel selectedToolModel;

    private ProModAction removeAction; //never change this action once has been instantiated

    public UCGraph(final ValueModel selectedToolModel,
                    final JPopupMenu popupMenu,
                    Map<String, ProModAction> actions) {

        this.selectedToolModel = selectedToolModel;

        setPortsVisible(false);
        setJumpToDefaultPort(true);

        initActions(actions);
        
        setMarqueeHandler(new UCWorkspaceMarqueeHandler(this, selectedToolModel, popupMenu));
    }

    protected void processKeyEvent(KeyEvent e) {
        if(127 == e.getKeyCode()) {
            if (!isSelectionEmpty()) {
                Object[] selectedCells = getSelectionCells();
                logDeleteInfo(selectedCells);
                getGraphLayoutCache().remove(selectedCells, true, true);
            }
        }
    }

    private void initActions(final Map<String, ProModAction> actions) {
        final ProModAction refreshAction = new ProModAction(Resources.getResources().getString(UCNotationModel.REFRESH_ACTION_KEY), null, null){
            public void actionPerformed(ActionEvent event) {
                refresh();
            }
        };

        actions.put(UCNotationModel.REFRESH_ACTION_KEY, refreshAction);       

        removeAction = new ProModAction(Resources.getResources().getString(UCNotationModel.DELETE_ACTION_KEY), null, null){
                            public void actionPerformed(ActionEvent event) {
                                if (!isSelectionEmpty()) {
					                Object[] selectedCells = getSelectionCells();
                                    logDeleteInfo(selectedCells);
                                    getGraphLayoutCache().remove(selectedCells, true, true);
				                }
                            }
                        };

        actions.put(UCNotationModel.DELETE_ACTION_KEY, removeAction);
    }

    /**
     * Log information about cells tha have been deleted.
     *
     * @param selectedCells that have been deleted
     */
    private void logDeleteInfo(final Object[] selectedCells){
        for(final Object cell : selectedCells){
            if(cell instanceof DefaultEdge){
                final DefaultEdge edge = (DefaultEdge) cell;
                LOG.info("Edge has been deleted.");

            } else if(cell instanceof DefaultGraphCell){
                final DefaultGraphCell defaultGraphCell = (DefaultGraphCell) cell;
                final Object object = defaultGraphCell.getUserObject();

                final String identifier;
                if(object instanceof UCIdentifiableVertex){
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
        try{
            result = getPortViewAt(point.getX(), point.getY(), GET_PORTVIEW_TOLERANCE);
        }
        catch (Exception exception){
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
     * Inserts a new vertex (type depends on currently selected tool) to the graph and currently selected
     * graph layout cache.
     *
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
     *
     * @return new vertex of required type
     */
    private DefaultGraphCell createVertex(final Point2D point) {
        final ToolChooserModel.Tool tool = (ToolChooserModel.Tool) selectedToolModel.getValue();
        final Point2D snappedPoint = snap((Point2D) point.clone());

        return UCCellFactory.createVertex(snappedPoint, tool);
    }

    public ProModAction getRemoveAction() {
        return removeAction;
    }
}
