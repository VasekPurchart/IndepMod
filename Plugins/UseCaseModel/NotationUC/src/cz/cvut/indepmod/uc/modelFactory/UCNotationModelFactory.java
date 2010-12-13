package cz.cvut.indepmod.uc.modelFactory;

import cz.cvut.indepmod.uc.modelFactory.diagramModel.UCDiagramModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphModel.UCGraphModel;
import cz.cvut.indepmod.uc.workspace.cell.UCCellViewFactory;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.factory.DiagramModelFactory;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.model.DiagramModel;
import org.jgraph.graph.GraphLayoutCache;

/**
 * Created by IntelliJ IDEA.
 * User: martin
 * Date: 9.8.2010
 * Time: 13:24:06
 */
public class UCNotationModelFactory implements DiagramModelFactory {
    /**
     * Creates empty model of diagram
     * @return returns model of diagram - empty
     */
    public DiagramModel createEmptyDiagramModel() {
        return new UCDiagramModel(
                new GraphLayoutCache(new UCGraphModel(), new UCCellViewFactory())
        );
    }
}
