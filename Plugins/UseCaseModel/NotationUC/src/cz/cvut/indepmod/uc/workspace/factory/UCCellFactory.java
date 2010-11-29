package cz.cvut.indepmod.uc.workspace.factory;

import cz.cvut.indepmod.uc.frames.toolChooser.ToolChooserModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.*;
import org.apache.log4j.Logger;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;

import java.awt.geom.Point2D;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * ProMod, master thesis project
 * 
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 *
 * UCCellFactory is responsible for creating a new vertexes in the UCNotation plugin.
 */
public class UCCellFactory {

    private static final Logger LOG = Logger.getLogger(UCCellFactory.class);

    private static final int DASH_LINE_SEGMENT_LENGTH = 10;
    private static final int DASH_SPACE_SEGMENT_LENGTH = 10;

    private static final int DOTTED_LINE_SEGMENT_LENGTH = 2;
    private static final int DOTTED_SPACE_SEGMENT_LENGTH = 10;

    private static final int DOT_AND_DASH_SEGMENT_1 = 10;
    private static final int DOT_AND_DASH_SEGMENT_2 = 4;
    private static final int DOT_AND_DASH_SEGMENT_3 = 2;
    private static final int DOT_AND_DASH_SEGMENT_4 = 4;

    /**
     *  Creates the required vertex.
     *
     * @param point is the point to be the vertex positioned
     * @param tool is the selected tool - vertex type
     * @return required vertex in form of graph cell
     */
    public static DefaultGraphCell createVertex(final Point2D point, final ToolChooserModel.Tool tool) {
        final DefaultGraphCell cell = new DefaultGraphCell();
        
        switch (tool){
            case ADD_USE_CASE:
                cell.setUserObject(new UseCaseModel(UUID.randomUUID()));
                cell.getAttributes().applyMap(UseCaseModel.installAttributes(point));
                break;
            case ADD_ACTOR:
                cell.setUserObject(new ActorModel(UUID.randomUUID()));
                cell.getAttributes().applyMap(ActorModel.installAttributes(point));
                break;
            case ADD_SYSTEM_BORDER:
                cell.setUserObject(new SystemBorderModel(UUID.randomUUID()));
                cell.getAttributes().applyMap(SystemBorderModel.installAttributes(point));
                break;
            default:
                LOG.error("No such a vertex type exists in UC notation.");
                return null;
        }

        final DefaultPort defaultPort = new DefaultPort();
        cell.add(defaultPort);
        
        return cell;
    }

    /**
     * Creates required edge.
     *
     * @param tool is the selected tool - edge type
     * 
     * @return an edge
     */
    public static DefaultEdge createEdge(final ToolChooserModel.Tool tool) {
        final DefaultEdge edge = new DefaultEdge();

        final EdgeModel.EdgeType edgeType;
        switch (tool){
            case ADD_CONTROL_FLOW_LINE:
                edgeType = EdgeModel.EdgeType.CONTROL_FLOW;
                break;
            case ADD_INCLUDE_FLOW_LINE:
                edgeType = EdgeModel.EdgeType.INCLUDE_FLOW;
                break;
            default:
                // should never happened, testing & developing purposes
                LOG.error("No proper tool for any kind of edge.");
                return null;
        }

        EdgeModel edgeModel = new EdgeModel(edgeType);
        if(edgeType == EdgeModel.EdgeType.INCLUDE_FLOW)
            edgeModel.setName("<< include >>");
        edge.setUserObject(edgeModel);

        edge.getAttributes().applyMap(createEdgeAttributes(tool));
        return edge;
    }


    /**
     * Creates attributes for a uc graph edges
     *
     * @param tool selected uc tool
     *
     * @return the map with relevant attributes for the edge
     */
    private static  Map createEdgeAttributes(final ToolChooserModel.Tool tool) {
		final Map map = new Hashtable();

        GraphConstants.setLineEnd(map, GraphConstants.ARROW_SIMPLE);

        
        GraphConstants.setEndFill(map, true);
        GraphConstants.setLineStyle(map, GraphConstants.STYLE_ORTHOGONAL);
		GraphConstants.setLabelAlongEdge(map, false);
        GraphConstants.setEditable(map, true);
        GraphConstants.setMoveable(map, true);
        GraphConstants.setDisconnectable(map, false);

        if(ToolChooserModel.Tool.ADD_INCLUDE_FLOW_LINE.equals(tool)){
            GraphConstants.setEditable(map, false);
            // make dashed line
            //GraphConstants.setDashPattern(map, new float[] {DASH_LINE_SEGMENT_LENGTH, DASH_SPACE_SEGMENT_LENGTH});
        } else {
            GraphConstants.setLineEnd(map, GraphConstants.ARROW_NONE);
        }

		return map;
	}
}
