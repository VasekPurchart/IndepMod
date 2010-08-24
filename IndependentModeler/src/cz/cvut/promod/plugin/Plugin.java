package cz.cvut.promod.plugin;

import cz.cvut.promod.gui.settings.SettingPageData;

import java.util.List;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 20:57:14, 12.10.2009
 */

/**
 * Plugin is the base class for all plugins.
 *
 * @see cz.cvut.promod.plugin.notationSpecificPlugIn.notation.Notation
 * @see cz.cvut.promod.plugin.notationSpecificPlugIn.module.Module
 */
public interface Plugin {


    /**
     * Returns it's identifier. All notations have to have unique identifier during one ProMod run and all
     * modules related to the same Notation have to have unique identifier. It means that for example Module M1 which is
     * related to a Notation N1 and a Module M2 which is related to a Notation N2 can have the same identifier.
     *
     * @return it's identifier
     */
    public String getIdentifier();

    /**
     * Returns descriptive name of the plugin.
     *
     * @return descriptive name of the plugin
     */
    public String getName();

    /**
     * Returns detail plugin description.
     *
     * @return detail plugin description
     */
    public String getDescription();    

    /**
     * When the init() method of any plugin is invoked, it means that this plugin has
     * been accepted by the ProMod application.
     *
     * All plugins are supposed to use 'dependent' services only in this method or later (init() method of all plugins
     * are invoked by the ProMod only once). No 'dependent' services are available before. SO NO PLUGIN SHOULD
     * USE 'DEPENDENT' SERVICES IN IT'S CONSTRUCTING TIME.
     *
     * @see cz.cvut.promod.IndependentModelerMain and it's JavaDocs for more info about 'dependent' & 'independent' types of services.
     *
     */
    public void init();


    /**
     * Is invoked when the ProMod is shutting down (not on application fall). This method is invoked by the
     * modelerExit() method
     * @see cz.cvut.promod.gui.Modeler
     */
    public void finish();

    /**
     * Returns a list of pages that are supposed to be inserted into the Settings Dialog of ProMod. Because the Option
     * Dialog works in a tree style all pages are supposed to have a parent.
     *
     * The parent of the first item in the list will be set automatically. Parents of all other items are supposed to
     * be defined by the developer. If not, Modeler will set the same parent as the first page to all pages that do
     * not have a parent. If the tree structure is defined by the developer, it's his/her responsibility to defined
     * the correct tree structure whether the root has event no parent of the parent is the first page of the list.
     *
     * Important note: If a page has a parent, then the parent is not supposed to hold any actual view. This parent
     * page serves only as a container a when this parent is selected in the Settings dialog navigation, the view of
     * it's first child is shown. 
     *
     *
     * @return list of all pages that are supposed to be inserted into the Modeler's Settings dialog
     */
    public SettingPageData getSettingPages();

}
