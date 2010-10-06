package cz.cvut.indepmod.uc.resources;

import cz.cvut.promod.resources.AbstractResources;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.util.*;

public class Resources extends AbstractResources {
    private static final Logger LOG = Logger.getLogger(Resources.class);

    public static final String ICONS = "picUC/";
    public static final String DIAGRAM = "diagram.png";
    public static final String PREVIEW = "preview.png";

    public static final String RESOURCES_FILE = "uc";
    
    private final static Map<String, ImageIcon> icons = new HashMap<String, ImageIcon>();
    private static ResourceBundle resources = null;

    /**
     * Returns resources for this module.
     *
     * @return the resource bundle
     */
    public static ResourceBundle getResources(){
        if(resources == null){
            try{
                resources = ResourceBundle.getBundle(RESOURCES_FILE);
            } catch (MissingResourceException e){
                LOG.error("Couldn't load the resource bundle file.");
            }
        }

        return resources;
    }
}
