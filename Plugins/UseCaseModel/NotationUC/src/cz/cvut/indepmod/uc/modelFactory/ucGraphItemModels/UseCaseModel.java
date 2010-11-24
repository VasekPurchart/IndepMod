package cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels;

import com.jgraph.components.labels.CellConstants;
import com.jgraph.components.labels.MultiLineVertexRenderer;
import cz.cvut.indepmod.uc.resources.Resources;
import org.jgraph.graph.GraphConstants;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * User: Viktor Bohuslav Bohdal, bohdavik@fel.cvut.cz
 * Date: 6.10.2010
 */
public class UseCaseModel extends UCEditableVertex {
    private static final String DEFAULT_LABEL = Resources.getResources().getString("uc.vertex.uc");
    private Map<Integer, ScenarioModel> scenarios = new HashMap<Integer, ScenarioModel>();

    public static final int DEFAULT_INSET = 6;

    public static final int USE_CASE_WIDTH = 200;
    public static final int USE_CASE_HEIGHT = 80;

    private UUID uuid;

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

        GraphConstants.setBounds(map, new Rectangle2D.Double(point.getX(), point.getY(), USE_CASE_WIDTH, USE_CASE_HEIGHT)); // velikost 200*20
        // GraphConstants.setResize(map, true); !!! Toto je nutne odebrat, aby se velikost zmenila
        GraphConstants.setBorderColor(map, Color.red);
        GraphConstants.setOpaque(map, true);
        GraphConstants.setInset(map, DEFAULT_INSET);

        return map;
    }
    
    public UUID getUuid() {
        return this.uuid;  
    }

    public Map<Integer, ScenarioModel> getScenarios() {
        return this.scenarios;
    }
    public ScenarioModel getScenario(Integer id) {
        return this.scenarios.get(id);
    }
    public void addScenario(Integer id, ScenarioModel scenario) {
        this.scenarios.put(id, scenario);
    }

}
