package cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels;

import com.jgraph.components.labels.CellConstants;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.indepmod.uc.workspace.cell.UCVertexRenderer;
import cz.cvut.indepmod.uc.workspace.cell.borders.ApplicationSWBorder;
import org.jgraph.graph.GraphConstants;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 23:17:10, 18.1.2010
 *
 * Represents the Application Software element of the UCNotation plugin.
 */
public class ApplicationSoftwareModel extends UCEditableVertex{

    private static final String DEFAULT_LABEL = Resources.getResources().getString("uc.vertex.app.sw");

    public static final int DEFAULT_INSET = 5;

    private final UUID uuid;

    /**
     * Constructor of Application Software Model - needs UUID
     * @param uuid
     */
    public ApplicationSoftwareModel(final UUID uuid){
        this.uuid = uuid;
        setName(DEFAULT_LABEL);
    }

    /**
     * Constructor of Application Software Model - needs model and name
     * @param applicationSoftwareModel
     * @param name
     */
    public ApplicationSoftwareModel(final ApplicationSoftwareModel applicationSoftwareModel, final String name){
        uuid = applicationSoftwareModel.getUuid();
        setName(name);
        setNote(applicationSoftwareModel.getNote());
    }

    @Override
    public String toString(){
        return name;
    }

    /**
     * Initialize new vertex attributes.
     *
     * @param point is the point where new vertex is supposed to be inserted
     * @return a proper attribute map for new vertex
     */
    public static Map installAttributes(final Point2D point) {
        final Map map = new Hashtable();

        map.put(CellConstants.VERTEXSHAPE, UCVertexRenderer.SHAPE_RECTANGLE);

        GraphConstants.setBounds(map, new Rectangle.Double(point.getX(), point.getY(), 0, 0));
        GraphConstants.setResize(map, true);
        GraphConstants.setOpaque(map, true);
        GraphConstants.setBorder(map, new ApplicationSWBorder(Color.BLACK));
        GraphConstants.setSizeable(map, true);
        GraphConstants.setInset(map, DEFAULT_INSET);

        return map;
    }

    public UUID getUuid() {
        return uuid;
    }

}
