package cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels;

import com.jgraph.components.labels.CellConstants;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.indepmod.uc.workspace.cell.UCVertexRenderer;
import org.jgraph.graph.GraphConstants;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * User: Lukáš Beran
 * Date: 20.10.2010
 */
public class ActorModel extends UCEditableVertex {
    private static final String DEFAULT_LABEL = Resources.getResources().getString("uc.vertex.actor");

    public static final int DEFAULT_INSET = 6;

    private final UUID uuid;

    /**
     * Constructor of Actor model
     * @param uuid
     */
    public ActorModel(final UUID uuid){
        this.uuid = uuid;
        setName(DEFAULT_LABEL);
    }

    /**
     * Constructor of Actor Model - needs model and name
     * @param actorModel model of other actor
     * @param name name of the actor
     */
    public ActorModel(final ActorModel actorModel, final String name){
        setName(name);
        uuid = actorModel.getUuid();
        setNote(actorModel.getNote());
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
        
        map.put(CellConstants.VERTEXSHAPE, UCVertexRenderer.SHAPE_UC_ACTOR);

        GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(), point.getY(), 40, 120));
        //GraphConstants.setResize(map, true);
        GraphConstants.setOpaque(map, true);
        GraphConstants.setInset(map, DEFAULT_INSET);
        GraphConstants.setVerticalAlignment(map, SwingConstants.BOTTOM);
        GraphConstants.setSizeableAxis(map, GraphConstants.X_AXIS);
        //GraphConstants.setAutoSize(map, true);

        return map;
    }

    /**
     * Getter - UUID
     * @return
     */
    public UUID getUuid() {
        return this.uuid;  
    }
}