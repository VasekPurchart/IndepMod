package cz.cvut.indepmod.uc.modelFactory.ucItemModels;

import cz.cvut.indepmod.uc.resources.Resources;

import java.awt.geom.Point2D;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * User: Viktor Bohuslav Bohdal, bohdavik@fel.cvut.cz
 * Date: 6.10.2010
 */
public class ActorModel extends UCEditableVertex {
    private static final String DEFAULT_LABEL = Resources.getResources().getString("uc.vertex.actor");

    private final UUID uuid;


    public ActorModel(final UUID uuid){
        this.uuid = uuid;
        setName(DEFAULT_LABEL);
    }

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

        /**
         * Tady bude graf. implementace actora
         *
         * Neco an tento zpusob:
        map.put(CellConstants.VERTEXSHAPE, MultiLineVertexRenderer.SHAPE_CIRCLE);

        GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(), point.getY(), 0, 0));
        GraphConstants.setResize(map, true);
        GraphConstants.setBorderColor(map, Color.black);
        GraphConstants.setOpaque(map, true);
        GraphConstants.setInset(map, DEFAULT_INSET);


         */

        return map;
    }
    
    public UUID getUuid() {
        return null;  
    }
}
