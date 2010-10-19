package cz.cvut.indepmod.uc.frames.toolChooser;

import com.jgoodies.binding.beans.Model;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.promod.gui.support.utils.NotationGuiHolder;

import java.util.HashSet;
import java.util.Set;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 12:48:44, 29.11.2009
 *
 * The model component of the ToolChooser dockable frame.   
 */
public class ToolChooserModel extends Model {   

    public static final String FRAME_TITLE_RES = "uc.frame.tools.title";

    public static final String ACTOR_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.actor");
    public static final String USE_CASE_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.use_case");
    public static final String CONTROL_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.control");
    public static final String DELETE_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.delete");
    public static final String CONTROL_FLOW_LINE_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.line.control.flow");
    public static final String INFO_SERVICES_FLOW_LINE_TOOL_RES = 
            Resources.getResources().getString("uc.frame.tools.line.info.services");
    public static final String ORG_FLOW_LINE_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.line.org.flow");
    public static final String INFO_FLOW_LINE_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.line.info.flow");
    public static final String MATERIAL_FLOW_LINE_TOOL_RES = 
            Resources.getResources().getString("uc.frame.tools.line.material");


    public enum Tool {
        CONTROL,
        ADD_ACTOR,
        ADD_USE_CASE,
        ADD_CONTROL_FLOW_LINE, //solid line with narrow 
        ADD_INFORMATION_SERVICE_FLOW_LINE, //dashed line with narrow
        ADD_INFORMATION_FLOW_LINE, //dotted line with narrow
        ADD_MATERIAL_OUTPUT_FLOW_LINE, // dot-and-dash line with narrow        
        ADD_ORGANIZATION_FLOW_LINE, // solid line without narrow
        DELETE
    }

    public static final String PROPERTY_SELECTED_TOOL = "selectedTool";
    private Tool selectedTool;

    private Set<NotationGuiHolder.Position> allowedSides;

    
    public ToolChooserModel(){
        allowedSides = new HashSet<NotationGuiHolder.Position>();
        allowedSides.add(NotationGuiHolder.Position.LEFT);
        allowedSides.add(NotationGuiHolder.Position.RIGHT);
    }

    public Set<NotationGuiHolder.Position> getAllowedSides() {
        return allowedSides;
    }

    public void setSelectedTool(final Tool selectedTool) {
        final Tool oldValue = this.selectedTool;
        this.selectedTool = selectedTool;
        firePropertyChange(PROPERTY_SELECTED_TOOL, oldValue, selectedTool);
    }

    public Tool getSelectedTool() {
        return selectedTool;
    }
}