package cz.cvut.indepmod.uc.frames.toolChooser;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.ValueModel;
import com.jidesoft.status.LabelStatusBarItem;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.promod.gui.support.utils.NotationGuiHolder;
import cz.cvut.promod.plugin.notationSpecificPlugIn.DockableFrameData;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Set;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 12:48:24, 29.11.2009
 *
 * The tool chooser dockable frame.
 */
public class ToolChooser extends ToolChooserView implements DockableFrameData{

    private static final Logger LOG = Logger.getLogger(ToolChooser.class);

    private static final String SELECTED_TOOL_LABEL =
            Resources.getResources().getString("uc.frame.tools.selected");

    private final ToolChooserModel model;

    private final ButtonGroup buttonGroup;

    private final ValueModel selectedToolModel;

    private final LabelStatusBarItem selectedToolStatusBarItem;

    private static final String LOG_OP_SEPARATOR = "/";


    public ToolChooser(final LabelStatusBarItem selectedToolStatusBarItem){
        model = new ToolChooserModel();
        final PresentationModel<ToolChooserModel> presentation = new PresentationModel<ToolChooserModel>(model);

        selectedToolModel = presentation.getModel(ToolChooserModel.PROPERTY_SELECTED_TOOL);

        this.selectedToolStatusBarItem = selectedToolStatusBarItem;

        buttonGroup = new ButtonGroup();
        initButtonGroup();

        initEventHandling();

        outlookTabbedPane.setChevronVisible(false);

        outlookTabbedPane.setIconAt(0, Resources.getIcon(Resources.ICONS + Resources.ELEMENT));
        outlookTabbedPane.setIconAt(1, Resources.getIcon(Resources.ICONS + Resources.CONNECTOR));
        outlookTabbedPane.setIconAt(2, Resources.getIcon(Resources.PORTS + Resources.PORT_BLUE));

        controlButton.doClick(); // initial tool is "control"
    }

    private void initButtonGroup() {
        buttonGroup.add(controlButton);
        buttonGroup.add(deleteButton);

        buttonGroup.add(outlookTabbedPane.getAddActorButton());
        buttonGroup.add(outlookTabbedPane.getAddUseCaseButton());
        buttonGroup.add(outlookTabbedPane.getAddAndButton());
        buttonGroup.add(outlookTabbedPane.getAddOrButton());
        buttonGroup.add(outlookTabbedPane.getAddXorButton());
        buttonGroup.add(outlookTabbedPane.getAddControlFlowLineButton());
        buttonGroup.add(outlookTabbedPane.getAddInfoServicesFlowLineButton());
        buttonGroup.add(outlookTabbedPane.getAddOrganizationFlowLineButton());
        buttonGroup.add(outlookTabbedPane.getAddInformationFlowLineButton());
        buttonGroup.add(outlookTabbedPane.getAddMaterialFlowLineButton());
        buttonGroup.add(outlookTabbedPane.getAddApplicationSWButton());
        buttonGroup.add(outlookTabbedPane.getAddMessageButton());
        buttonGroup.add(outlookTabbedPane.getAddGoalButton());
        buttonGroup.add(outlookTabbedPane.getAddMachineButton());
        buttonGroup.add(outlookTabbedPane.getAddHWButton());
        buttonGroup.add(outlookTabbedPane.getAddAndOrButton());
        buttonGroup.add(outlookTabbedPane.getAddAndXorButton());
        buttonGroup.add(outlookTabbedPane.getAddOrAndButton());
        buttonGroup.add(outlookTabbedPane.getAddOrXorButton());
        buttonGroup.add(outlookTabbedPane.getAddXorAndButton());
        buttonGroup.add(outlookTabbedPane.getAddXorOrButton());
    }

    private void initEventHandling() {
        controlButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent) {
                selectedToolModel.setValue(ToolChooserModel.Tool.CONTROL);
                updateStatusBarSelectedItemInfo(ToolChooserModel.CONTROL_TOOL_RES);
            }
        });

        deleteButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.DELETE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.DELETE_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddActorButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_ACTOR);
                updateStatusBarSelectedItemInfo(ToolChooserModel.ACTOR_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddUseCaseButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_USE_CASE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.USE_CASE_TOOL_RES);
            }
        });

      

        outlookTabbedPane.getAddAndButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_AND);
                updateStatusBarSelectedItemInfo(ToolChooserModel.AND_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddOrButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_OR);
                updateStatusBarSelectedItemInfo(ToolChooserModel.OR_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddXorButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_XOR);
                updateStatusBarSelectedItemInfo(ToolChooserModel.XOR_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddControlFlowLineButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_CONTROL_FLOW_LINE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.CONTROL_FLOW_LINE_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddInfoServicesFlowLineButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_INFORMATION_SERVICE_FLOW_LINE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.INFO_SERVICES_FLOW_LINE_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddOrganizationFlowLineButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_ORGANIZATION_FLOW_LINE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.ORG_FLOW_LINE_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddInformationFlowLineButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_INFORMATION_FLOW_LINE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.INFO_FLOW_LINE_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddMaterialFlowLineButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_MATERIAL_OUTPUT_FLOW_LINE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.MATERIAL_FLOW_LINE_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddApplicationSWButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_APP_SW);
                updateStatusBarSelectedItemInfo(ToolChooserModel.APP_SW_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddMessageButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_MESSAGE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.MESSAGE_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddGoalButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_GOAL);
                updateStatusBarSelectedItemInfo(ToolChooserModel.GOAL_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddMachineButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_MACHINE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.MACHINE_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddHWButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_HW);
                updateStatusBarSelectedItemInfo(ToolChooserModel.HW_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddAndOrButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_AND_OR);
                updateStatusBarSelectedItemInfo(ToolChooserModel.AND_TOOL_RES + LOG_OP_SEPARATOR + ToolChooserModel.OR_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddAndXorButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_AND_XOR);
                updateStatusBarSelectedItemInfo(ToolChooserModel.AND_TOOL_RES + LOG_OP_SEPARATOR + ToolChooserModel.XOR_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddOrAndButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_OR_AND);
                updateStatusBarSelectedItemInfo(ToolChooserModel.OR_TOOL_RES + LOG_OP_SEPARATOR + ToolChooserModel.AND_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddOrXorButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_OR_XOR);
                updateStatusBarSelectedItemInfo(ToolChooserModel.OR_TOOL_RES + LOG_OP_SEPARATOR + ToolChooserModel.XOR_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddXorAndButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_XOR_AND);
                updateStatusBarSelectedItemInfo(ToolChooserModel.XOR_TOOL_RES + LOG_OP_SEPARATOR + ToolChooserModel.AND_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddXorOrButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_XOR_OR);
                updateStatusBarSelectedItemInfo(ToolChooserModel.XOR_TOOL_RES + LOG_OP_SEPARATOR + ToolChooserModel.OR_TOOL_RES);
            }
        });

        selectedToolModel.addValueChangeListener(new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent evt) {
                final ToolChooserModel.Tool tool = (ToolChooserModel.Tool) evt.getNewValue();

                switch (tool){
                    case CONTROL:
                        controlButton.doClick();
                        break;
                    case ADD_ACTOR:
                        outlookTabbedPane.getAddActorButton().doClick();
                        break;
                    case ADD_USE_CASE:
                        outlookTabbedPane.getAddUseCaseButton().doClick();
                        break;
                    
                    case DELETE:
                        deleteButton.doClick();
                        break;
                    case ADD_CONTROL_FLOW_LINE:
                        outlookTabbedPane.getAddControlFlowLineButton().doClick();
                        break;
                    case ADD_INFORMATION_SERVICE_FLOW_LINE:
                        outlookTabbedPane.getAddInfoServicesFlowLineButton().doClick();
                        break;
                    case ADD_MATERIAL_OUTPUT_FLOW_LINE:
                        outlookTabbedPane.getAddMaterialFlowLineButton().doClick();
                        break;
                    case ADD_INFORMATION_FLOW_LINE:
                        outlookTabbedPane.getAddInformationFlowLineButton().doClick();
                        break;
                    case ADD_ORGANIZATION_FLOW_LINE:
                        outlookTabbedPane.getAddOrganizationFlowLineButton().doClick();
                        break;                     
                    case ADD_HW:
                        outlookTabbedPane.getAddHWButton().doClick();
                        break;
                    case ADD_MACHINE:
                        outlookTabbedPane.getAddMachineButton().doClick();
                        break;
                    case ADD_APP_SW:
                        outlookTabbedPane.getAddApplicationSWButton().doClick();
                        break;
                    case ADD_MESSAGE:
                        outlookTabbedPane.getAddMessageButton().doClick();
                        break;
                    case ADD_GOAL:
                        outlookTabbedPane.getAddGoalButton().doClick();
                        break;
                   
                    case ADD_AND:
                        outlookTabbedPane.getAddAndButton().doClick();
                        break;
                    case ADD_OR:
                        outlookTabbedPane.getAddOrButton().doClick();
                        break;
                    case ADD_XOR:
                        outlookTabbedPane.getAddXorButton().doClick();
                        break;
                    case ADD_AND_OR:
                        outlookTabbedPane.getAddAndOrButton().doClick();
                        break;
                    case ADD_AND_XOR:
                        outlookTabbedPane.getAddAndXorButton().doClick();
                        break;
                    case ADD_OR_AND:
                        outlookTabbedPane.getAddOrAndButton().doClick();
                        break;
                    case ADD_OR_XOR:
                        outlookTabbedPane.getAddOrXorButton().doClick();
                        break;
                    case ADD_XOR_AND:
                        outlookTabbedPane.getAddXorAndButton().doClick();
                        break;
                    case ADD_XOR_OR:
                        outlookTabbedPane.getAddXorOrButton().doClick();
                        break;
                    default:
                        LOG.error("No such a tool in EPC notation.");
                }
            }
        });
    }

    /**
     * Updates info in status bar about actually selected control.
     *
     * @param controlToolRes is the name of currently selected control
     */
    private void updateStatusBarSelectedItemInfo(final String controlToolRes) {
        selectedToolStatusBarItem.setText(SELECTED_TOOL_LABEL + " " + controlToolRes);        
    }


    public String getDockableFrameName() {
        return "EPCToolChooser";
    }

    public JComponent getDockableFrameComponent() {
        return this;
    }

    public NotationGuiHolder.Position getInitialPosition() {
        return NotationGuiHolder.Position.RIGHT;
    }

    public boolean isMaximizable() {
        return false;
    }

    public Set<NotationGuiHolder.Position> getAllowedDockableFramePositions() {
        return model.getAllowedSides();
    }

    public InitialState getInitialState() {
        return InitialState.HIDDEN;
    }

    public String getDockableFrameTitle() {
        return Resources.getResources().getString(ToolChooserModel.FRAME_TITLE_RES);
    }

    public Icon getButtonIcon() {
        return null;
    }

    public ValueModel getSelectedToolModel() {
        return selectedToolModel;
    }
}