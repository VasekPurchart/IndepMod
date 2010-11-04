package cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels;

import com.jgraph.components.labels.CellConstants;
import com.jgraph.components.labels.MultiLineVertexRenderer;
import cz.cvut.indepmod.uc.resources.Resources;
import org.jgraph.graph.GraphConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * User: Lukáš Beran
 * Date: 20.10.2010
 */
public class SystemBorderModel extends UCEditableVertex {
    private static final String DEFAULT_LABEL = Resources.getResources().getString("uc.vertex.border");

    public static final int DEFAULT_INSET = 6;

    private UUID uuid;

    public SystemBorderModel(final UUID uuid){
        this.uuid = uuid;
        setName(DEFAULT_LABEL);
    }

    public SystemBorderModel(final SystemBorderModel systemBorderModel, final String name){
        setName(name);
        uuid = systemBorderModel.getUuid();
        setNote(systemBorderModel.getNote());
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

        map.put(CellConstants.VERTEXSHAPE, MultiLineVertexRenderer.SHAPE_RECTANGLE);

        GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(), point.getY(), 400, 800));
        GraphConstants.setResize(map, false);
        GraphConstants.setBorderColor(map, Color.black);
        GraphConstants.setOpaque(map, false);
        GraphConstants.setVerticalAlignment(map, SwingConstants.TOP);
        GraphConstants.setMoveable(map, true);
        GraphConstants.setEditable(map, true);
        GraphConstants.setConnectable(map, true);
        GraphConstants.setSelectable(map, true);
        GraphConstants.setInset(map, DEFAULT_INSET);
        GraphConstants.setDisconnectable(map, false);
        GraphConstants.setBendable(map, false);

        return map;
    }
    
    public UUID getUuid() {
        return this.uuid;  
    }

}
