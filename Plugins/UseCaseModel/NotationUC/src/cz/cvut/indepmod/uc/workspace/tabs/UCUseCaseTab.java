package cz.cvut.indepmod.uc.workspace.tabs;

import com.jgoodies.binding.value.ValueModel;
import cz.cvut.indepmod.uc.frames.toolChooser.ToolChooserModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphModel.UCGraphUseCaseModel;
import cz.cvut.indepmod.uc.workspace.cell.UCCellViewFactoryUseCase;
import cz.cvut.indepmod.uc.workspace.factory.UCCellFactory;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphLayoutCache;

import java.awt.geom.Point2D;
import java.util.Map;

public class UCUseCaseTab extends JGraph {
    private final ValueModel selectedToolModel;

    public UCUseCaseTab() {
        this(null, null);
    }
    public UCUseCaseTab(final ValueModel selectedToolModel, Map<String, ProModAction> actions) {
        UCGraphUseCaseModel model = new UCGraphUseCaseModel();
        GraphLayoutCache view = new GraphLayoutCache(model, new UCCellViewFactoryUseCase());
        this.setModel(model);
        this.setGraphLayoutCache(view);
        this.selectedToolModel = selectedToolModel;

    }

    /**
     * Inserts a new vertex (type depends on currently selected tool) to the graph and currently selected
     * graph layout cache.
     * <p/>
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
     * Creates new vertex (type depends on selected tool).
     *
     * @param point is the point where the new vertex is supposed to be inserted
     * @return new vertex of required type
     */
    private DefaultGraphCell createVertex(final Point2D point) {
        final ToolChooserModel.Tool tool = (ToolChooserModel.Tool) selectedToolModel.getValue();
        final Point2D snappedPoint = snap((Point2D) point.clone());

        return UCCellFactory.createVertex(snappedPoint, tool);
    }
}
