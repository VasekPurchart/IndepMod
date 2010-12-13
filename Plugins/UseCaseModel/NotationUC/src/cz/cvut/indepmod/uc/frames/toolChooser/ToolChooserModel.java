package cz.cvut.indepmod.uc.frames.toolChooser;

import com.jgoodies.binding.beans.Model;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.promod.gui.support.utils.NotationGuiHolder;

import java.util.HashSet;
import java.util.Set;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 *
 * The model component of the ToolChooser dockable frame.   
 */
public class ToolChooserModel extends Model {   

    public static final String FRAME_TITLE_RES = "uc.frame.tools.title";

    public static final String ACTOR_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.actor");
    public static final String SCENARIO_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.scenario");
    public static final String STEP_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.step");
    public static final String USE_CASE_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.use_case");
    public static final String SYSTEM_BORDER_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.border");
    public static final String CONTROL_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.control");
    public static final String CONTROL_FLOW_LINE_TOOL_RES =
            Resources.getResources().getString("uc.frame.tools.line.control.flow");
    public static final String INCLUDE_FLOW_LINE_TOOL_RES = 
            Resources.getResources().getString("uc.frame.tools.line.include");


    public enum Tool {
        CONTROL,
        ADD_ACTOR,
        ADD_SCENARIO,
        ADD_STEP,
        ADD_USE_CASE,
        ADD_SYSTEM_BORDER,
        ADD_CONTROL_FLOW_LINE, //solid line with narrow
        ADD_INCLUDE_FLOW_LINE, //solid line with narrow 
    }

    public static final String PROPERTY_SELECTED_TOOL = "selectedTool";
    private Tool selectedTool;

    private Set<NotationGuiHolder.Position> allowedSides;

    /**
     * Constructor of ToolChoserModel
     */
    public ToolChooserModel(){
        allowedSides = new HashSet<NotationGuiHolder.Position>();
        allowedSides.add(NotationGuiHolder.Position.LEFT);
        allowedSides.add(NotationGuiHolder.Position.RIGHT);
    }

    /**
     * Getter - alowed sides
     * @return returns allowed sides
     */
    public Set<NotationGuiHolder.Position> getAllowedSides() {
        return allowedSides;
    }

    /**
     * Setter - selected tool
     * @param selectedTool sets selected tool
     */
    public void setSelectedTool(final Tool selectedTool) {
        final Tool oldValue = this.selectedTool;
        this.selectedTool = selectedTool;
        firePropertyChange(PROPERTY_SELECTED_TOOL, oldValue, selectedTool);
    }

    /**
     * Getter - selected tool
     * @return gets selected tool
     */
    public Tool getSelectedTool() {
        return selectedTool;
    }
}