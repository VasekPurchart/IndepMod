package cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels;

import com.jgraph.components.labels.CellConstants;
import com.jgraph.components.labels.MultiLineVertexRenderer;
import org.jgraph.graph.GraphConstants;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Alena Varko?ková
 * Date: 2.11.2010
 * Time: 15:00:23
 * To change this template use File | Settings | File Templates.
 */
public class StepModel extends UCEditableVertex {
    private static final String DEFAULT_LABEL = "STEP";// Resources.getResources().getString("uc.vertex.actor");
    private final UUID uuid;
    public static final int DEFAULT_INSET = 6;

    public StepModel(UUID uuid) {
        this.uuid = uuid;
        this.setName(DEFAULT_LABEL);
    }

     public StepModel(final StepModel stepModel, final String name){
        setName(name);
        uuid = stepModel.getUuid();
        setNote(stepModel.getNote());
    }

     /**
    /**
     * Initialize new vertex attributes.
     *
     * @param point is the point where new vertex is supposed to be inserted
     * @return a proper attribute map for new vertex
     */
    public static Map installAttributes(final Point2D point) {
        final Map map = new Hashtable();

        map.put(CellConstants.VERTEXSHAPE, MultiLineVertexRenderer.SHAPE_CIRCLE);

        GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(), point.getY(), 200, 20)); // velikost 200*20
        // GraphConstants.setResize(map, true); !!! Toto je nutne odebrat, aby se velikost zmenila
        GraphConstants.setBorderColor(map, Color.black);
        GraphConstants.setOpaque(map, true);
        GraphConstants.setInset(map, DEFAULT_INSET);

        return map;
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
