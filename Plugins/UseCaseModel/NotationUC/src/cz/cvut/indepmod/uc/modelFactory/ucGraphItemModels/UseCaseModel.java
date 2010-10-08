package cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels;

import com.jgraph.components.labels.CellConstants;
import com.jgraph.components.labels.MultiLineVertexRenderer;
import cz.cvut.indepmod.uc.resources.Resources;
import org.jgraph.graph.GraphConstants;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * User: Viktor Bohuslav Bohdal, bohdavik@fel.cvut.cz
 * Date: 6.10.2010
 */
public class UseCaseModel extends UCEditableVertex {
    private static final String DEFAULT_LABEL = Resources.getResources().getString("uc.vertex.uc");

    public static final int DEFAULT_INSET = 6;

    private final UUID uuid;


    public UseCaseModel(final UUID uuid){
        this.uuid = uuid;
        setName(DEFAULT_LABEL);
    }

    public UseCaseModel(final UseCaseModel useCaseModel, final String name){
        setName(name);
        uuid = useCaseModel.getUuid();
        setNote(useCaseModel.getNote());

    }

    @Override
    public String toString(){
        return name;
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

        GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(), point.getY(), 0, 0));
        GraphConstants.setResize(map, true);
        GraphConstants.setBorderColor(map, Color.black);
        GraphConstants.setOpaque(map, true);
        GraphConstants.setInset(map, DEFAULT_INSET);

        return map;
    }
    
    public UUID getUuid() {
        return null;  
    }
}
