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
    public static final String FUNCTION_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.function");
    public static final String EVENT_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.event");
    public static final String DELIVERABLE_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.deliverable");
    public static final String INFORMATION_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.information");
    public static final String ORG_UNIT_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.org.unit");
    public static final String ORG_ROLE_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.org.role");
    public static final String AND_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.and");
    public static final String OR_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.or");
    public static final String XOR_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools..xor");
    public static final String APP_SW_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.app.sw");
    public static final String DELETE_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.delete");
    public static final String MESSAGE_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.message");
    public static final String GOAL_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.goal");
    public static final String MACHINE_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.machine");
    public static final String HW_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.HW");

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
        ADD_FUNCTION,
        ADD_EVENT,
        ADD_DELIVERABLE,
        ADD_INFORMATION_OBJECT,
        ADD_ORGANIZATION_UNIT,
        ADD_ORGANIZATION_ROLE,
        ADD_AND,
        ADD_OR,
        ADD_XOR,
        ADD_APP_SW,
        ADD_GOAL,
        ADD_MACHINE,
        ADD_HW,
        ADD_MESSAGE,
        ADD_CONTROL_FLOW_LINE, //solid line with narrow 
        ADD_INFORMATION_SERVICE_FLOW_LINE, //dashed line with narrow
        ADD_INFORMATION_FLOW_LINE, //dotted line with narrow
        ADD_MATERIAL_OUTPUT_FLOW_LINE, // dot-and-dash line with narrow        
        ADD_ORGANIZATION_FLOW_LINE, // solid line without narrow
        DELETE,
        ADD_AND_OR,
        ADD_AND_XOR,
        ADD_OR_AND,
        ADD_OR_XOR,
        ADD_XOR_AND,
        ADD_XOR_OR
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