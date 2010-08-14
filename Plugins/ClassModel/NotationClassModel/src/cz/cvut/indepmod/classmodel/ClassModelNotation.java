package cz.cvut.indepmod.classmodel;

import com.jidesoft.dialog.AbstractDialogPage;
import cz.cvut.indepmod.classmodel.ioController.ClassModelNotationIOController;
import cz.cvut.indepmod.classmodel.modelFactory.ClassModelNotationModelFactory;
import cz.cvut.indepmod.classmodel.resources.Resources;
import cz.cvut.promod.plugin.notationSpecificPlugIn.DockableFrameData;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.Notation;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.NotationWorkspaceData;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.factory.DiagramModelFactory;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.localIOController.NotationLocalIOController;

import javax.swing.*;
import java.util.List;
import java.util.Set;

/**
 * Plugin providing class model notation
 */

public class ClassModelNotation implements Notation {

//    private static final Logger LOG = Logger.getLogger(ClassModelNotation.class);

    private final ClassModelNotationModelFactory modelFactory;
    private final ClassModelNotationIOController ioController;

    public ClassModelNotation() {
        modelFactory = new ClassModelNotationModelFactory();
        ioController = new ClassModelNotationIOController();
    }

    public String getIdentifier() {
        return "8e188b22-cd90-4043-b7d0-43c0a0a56cc4";
    }

    public String getName() {
        return "UMLClassModelNotation";
    }

    public String getFullName() {
        return "UML Class Model Notation";
    }

    public String getAbbreviation() {
        return "UMLClassModelNotation";
    }

    public String getDescription() {
        return "This plugin provides class model notation according to the UML standard.";
    }

    public Icon getNotationIcon() {
        return Resources.getIcon(Resources.ICONS + Resources.DIAGRAM);
    }

    public String getNotationToolTip() {
        return "Plugin implementing UML class model notation.";
    }

    public String getNotationPreviewText() {
        return "UML class model notation is used for modeling various data models. It can also be used in application design phase.";
    }

    public ImageIcon getNotationPreviewImage() {
        return Resources.getIcon(Resources.ICONS + Resources.PREVIEW);
    }
    
    public NotationWorkspaceData getNotationWorkspaceData() {
        return null;
    }

    public DiagramModelFactory getDiagramModelFactory() {
        return modelFactory;
    }

    public NotationLocalIOController getLocalIOController() {
        return ioController;
    }

    public JPopupMenu getPopupMenu() {
        return null;
    }

    public Set<DockableFrameData> getDockableFrames() {
        return null;
    }

    public void init() {
    }

    public void finish() {
    }

    public List<AbstractDialogPage> getSettingPages() {
        return null;
    }

}
