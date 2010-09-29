package cz.cvut.indepmod.usecasemodel.ioController;

import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.localIOController.NotationLocalIOController;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagram;

public class UseCaseModelNotationIOController implements NotationLocalIOController {

    public SaveResult saveProjectDiagram(ProjectDiagram projectDiagram, String parentLocation, boolean makeDirs) {
        return null;
    }

    public ProjectDiagram loadProjectDiagram(String diagramLocation) throws Exception {
        return null;
    }

    public String getNotationFileExtension() {
        return "use"; // TODO: This should be configurable (or part of the model)
    }
}
