package cz.cvut.indepmod.usecasemodel.modelFactory;

import cz.cvut.indepmod.usecasemodel.modelFactory.diagramModel.UseCaseModelDiagramModel;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.factory.DiagramModelFactory;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.model.DiagramModel;

/**
 * Created by IntelliJ IDEA.
 * User: martin
 * Date: 9.8.2010
 * Time: 13:24:06
 */
public class UseCaseModelNotationModelFactory implements DiagramModelFactory {

    public DiagramModel createEmptyDiagramModel() {
        return new UseCaseModelDiagramModel();
    }
}
