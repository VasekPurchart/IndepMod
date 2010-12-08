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
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
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

        //outlookTabbedPane.setChevronVisible(false);

        //outlookTabbedPane.setIconAt(0, Resources.getIcon(Resources.ICONS + Resources.ELEMENT));

        controlButton.doClick(); // initial tool is "control"
    }

    private void initButtonGroup() {
        buttonGroup.add(controlButton);

        buttonGroup.add(getAddActorButton());
        buttonGroup.add(getAddScenarioButton());
        buttonGroup.add(getAddStepButton());
        buttonGroup.add(getAddUseCaseButton());
        buttonGroup.add(getAddSystemBorderButton());
        buttonGroup.add(getAddControlFlowLineButton());
        buttonGroup.add(getAddIncludeFlowLineButton());
        
    }

    private void initEventHandling() {
        controlButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent) {
                selectedToolModel.setValue(ToolChooserModel.Tool.CONTROL);
                updateStatusBarSelectedItemInfo(ToolChooserModel.CONTROL_TOOL_RES);
            }
        });

        getAddActorButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_ACTOR);
                updateStatusBarSelectedItemInfo(ToolChooserModel.ACTOR_TOOL_RES);
            }
        });

        getAddScenarioButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_SCENARIO);
                updateStatusBarSelectedItemInfo(ToolChooserModel.SCENARIO_TOOL_RES);
            }
        });

        getAddStepButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_STEP);
                updateStatusBarSelectedItemInfo(ToolChooserModel.STEP_TOOL_RES);
            }
        });

        getAddSystemBorderButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_SYSTEM_BORDER);
                updateStatusBarSelectedItemInfo(ToolChooserModel.SYSTEM_BORDER_TOOL_RES);
            }
        });

        getAddUseCaseButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_USE_CASE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.USE_CASE_TOOL_RES);
            }
        });
       
        getAddControlFlowLineButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_CONTROL_FLOW_LINE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.CONTROL_FLOW_LINE_TOOL_RES);
            }
        });

        getAddIncludeFlowLineButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_INCLUDE_FLOW_LINE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.INCLUDE_FLOW_LINE_TOOL_RES);
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
                        getAddActorButton().doClick();
                        break;
                    case ADD_SCENARIO:
                        getAddScenarioButton().doClick();
                        break;
                    case ADD_STEP:
                        getAddStepButton().doClick();
                        break;
                    case ADD_USE_CASE:
                        getAddUseCaseButton().doClick();
                        break;
                    case ADD_SYSTEM_BORDER:
                        getAddSystemBorderButton().doClick();
                        break;
                    case ADD_CONTROL_FLOW_LINE:
                        getAddControlFlowLineButton().doClick();
                        break;
                    case ADD_INCLUDE_FLOW_LINE:
                        getAddIncludeFlowLineButton().doClick();
                        break;
                    default:
                        LOG.error("No such a tool in UC notation.");
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
        return "UCToolChooser";
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
        return InitialState.OPENED;
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