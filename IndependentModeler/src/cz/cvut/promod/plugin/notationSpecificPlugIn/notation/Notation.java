package cz.cvut.promod.plugin.notationSpecificPlugIn.notation;

import cz.cvut.promod.plugin.notationSpecificPlugIn.NotationSpecificPlugin;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.factory.DiagramModelFactory;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.localIOController.NotationLocalIOController;

import javax.swing.*;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 20:58:30, 12.10.2009
 */

/**
 * Common interface for all plugin of the type Notation.
 */
public interface Notation extends NotationSpecificPlugin {

    /**
     * Returns the full notation name.
     *
     * @return the full notation name.
     */
    public String getFullName();

    /**
     * Returns the notation name abbreviation.
     *
     * @return the notation name abbreviation.
     */
    public String getAbbreviation();

    /**
     * Returns the notation specific icon that is used for items in the project navigation tree.
     *
     * @return the notation specific icon that is used for items in the project navigation tree
     */
    public Icon getNotationIcon();

    /**
     * Returns the notation specific tool tip that is used for items in the project navigation tree.
     *
     * @return the notation specific tool tip that is used for items in the project navigation tree
     */
    public String getNotationToolTip();

    /**
     * Intervenes the access to the workspace component implementation.
     *
     * @return a workspace component holder implementation
     */
    public NotationWorkspaceData getNotationWorkspaceData();

    /**
     * Returns a image that represents the notation specific preview.
     * The image is supposed to be 400 x 200 pixels.
     *
     * @return preview image
     */
    public ImageIcon getNotationPreviewImage();

    /**
     * Returns a short notation description.
     *
     * @return a short notation description
     */
    public String getNotationPreviewText();

    /**
     * Intervenes the access to the new (empty) notation specific diagrams factory.
     *
     * @return a factory for a empty diagrams creation
     */
    public DiagramModelFactory getDiagramModelFactory();

    /**
     * Intervenes the access to the notation specific file system diagram serialization mechanism.
     *
     * @return a controller for the notation specific file system diagram serialization mechanism 
     */
    public NotationLocalIOController getLocalIOController();

    /**
     * Returns the notation's popup menu. This method is not supposed to be used directly. When one intends  to insert
     * anything to this popup menu, it is highly recommended to use MenuService.
     *
     * @return an instance of notation's popup menu, or null, if the notation doesn't support the popup menu
     */
    public JPopupMenu getPopupMenu();

}
