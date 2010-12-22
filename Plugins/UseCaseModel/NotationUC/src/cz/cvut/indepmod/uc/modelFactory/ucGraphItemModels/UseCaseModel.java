package cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels;

import com.jgraph.components.labels.CellConstants;
import com.jgraph.components.labels.MultiLineVertexRenderer;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.indepmod.uc.workspace.UCWorkspace;
import cz.cvut.indepmod.uc.workspace.UCWorkspaceData;
import org.jgraph.graph.GraphConstants;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
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
    transient private DefaultTreeModel model = new DefaultTreeModel(null);

    public static final int DEFAULT_INSET = 6;

    public static final int USE_CASE_WIDTH = 200;
    public static final int USE_CASE_HEIGHT = 80;

    private UUID uuid;

    public UseCaseModel(final UUID uuid) {
        this.uuid = uuid;
        setName(DEFAULT_LABEL);
    }

    /**
     * Constructor of Use Case Model - needs model and name
     * @param useCaseModel
     * @param name
     */
    public UseCaseModel(final UseCaseModel useCaseModel, final String name) {
        setName(name);
        uuid = useCaseModel.getUuid();
        setNote(useCaseModel.getNote());
        model = useCaseModel.getModel();
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * /**
     * Initialize new vertex attributes.
     *
     * @param point is the point where new vertex is supposed to be inserted
     * @return a proper attribute map for new vertex
     */
    public static Map installAttributes(final Point2D point) {
        final Map map = new Hashtable();

        map.put(CellConstants.VERTEXSHAPE, MultiLineVertexRenderer.SHAPE_CIRCLE);

        GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(), point.getY(), USE_CASE_WIDTH, USE_CASE_HEIGHT)); // velikost 200*20
        // GraphConstants.setResize(map, true); !!! Toto je nutne odebrat, aby se velikost zmenila
        GraphConstants.setBorderColor(map, Color.BLACK);
        GraphConstants.setOpaque(map, true);
        GraphConstants.setInset(map, DEFAULT_INSET);

        return map;
    }

    /**
     * Getter - UUID
     * @return
     */
    public UUID getUuid() {
        return this.uuid;
    }

    /**
     * Setter - name
     * @param name
     */
    public void setName(final String name) {
        if (this.model.getRoot() != null) {
            ((DefaultMutableTreeNode )this.model.getRoot()).setUserObject(name);
        }
        this.name = name;
    }

    /**
     * Getter - model
     * @return
     */
    public DefaultTreeModel getModel() {
        return model;
    }

    /**
     * Setter - model
     * @param model
     */
    public void setModel(DefaultTreeModel model) {
        this.model = model;
    }
}
