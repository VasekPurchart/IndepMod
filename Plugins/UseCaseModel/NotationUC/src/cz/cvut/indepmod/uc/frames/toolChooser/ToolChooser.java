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

        outlookTabbedPane.setChevronVisible(false);

        outlookTabbedPane.setIconAt(0, Resources.getIcon(Resources.ICONS + Resources.ELEMENT));

        controlButton.doClick(); // initial tool is "control"
    }

    private void initButtonGroup() {
        buttonGroup.add(controlButton);

        buttonGroup.add(outlookTabbedPane.getAddActorButton());
        buttonGroup.add(outlookTabbedPane.getAddScenarioButton());
        buttonGroup.add(outlookTabbedPane.getAddSelectMssButton());
        buttonGroup.add(outlookTabbedPane.getAddStepButton());
        buttonGroup.add(outlookTabbedPane.getAddIncludeUcButton());
        buttonGroup.add(outlookTabbedPane.getAddUseCaseButton());
        buttonGroup.add(outlookTabbedPane.getAddSystemBorderButton());
        buttonGroup.add(outlookTabbedPane.getAddControlFlowLineButton());
        buttonGroup.add(outlookTabbedPane.getAddIncludeFlowLineButton());
        
    }

    private void initEventHandling() {
        controlButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent actionEvent) {
                selectedToolModel.setValue(ToolChooserModel.Tool.CONTROL);
                updateStatusBarSelectedItemInfo(ToolChooserModel.CONTROL_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddActorButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_ACTOR);
                updateStatusBarSelectedItemInfo(ToolChooserModel.ACTOR_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddScenarioButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_SCENARIO);
                updateStatusBarSelectedItemInfo(ToolChooserModel.SCENARIO_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddSelectMssButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.SELECT_MSS);
                updateStatusBarSelectedItemInfo(ToolChooserModel.SELECT_MSS_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddStepButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_STEP);
                updateStatusBarSelectedItemInfo(ToolChooserModel.STEP_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddIncludeUcButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.INCLUDE_UC);
                updateStatusBarSelectedItemInfo(ToolChooserModel.INCLUDE_UC_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddSystemBorderButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_SYSTEM_BORDER);
                updateStatusBarSelectedItemInfo(ToolChooserModel.SYSTEM_BORDER_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddUseCaseButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_USE_CASE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.USE_CASE_TOOL_RES);
            }
        });
       
        outlookTabbedPane.getAddControlFlowLineButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                selectedToolModel.setValue(ToolChooserModel.Tool.ADD_CONTROL_FLOW_LINE);
                updateStatusBarSelectedItemInfo(ToolChooserModel.CONTROL_FLOW_LINE_TOOL_RES);
            }
        });

        outlookTabbedPane.getAddIncludeFlowLineButton().addActionListener(new ActionListener(){
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
                        outlookTabbedPane.getAddActorButton().doClick();
                        break;
                    case ADD_SCENARIO:
                        outlookTabbedPane.getAddScenarioButton().doClick();
                        break;
                    case SELECT_MSS:
                        outlookTabbedPane.getAddSelectMssButton().doClick();
                        break;
                    case ADD_STEP:
                        outlookTabbedPane.getAddStepButton().doClick();
                        break;
                    case INCLUDE_UC:
                        outlookTabbedPane.getAddIncludeUcButton().doClick();
                        break;
                    case ADD_USE_CASE:
                        outlookTabbedPane.getAddUseCaseButton().doClick();
                        break;
                    case ADD_SYSTEM_BORDER:
                        outlookTabbedPane.getAddSystemBorderButton().doClick();
                        break;
                    case ADD_CONTROL_FLOW_LINE:
                        outlookTabbedPane.getAddControlFlowLineButton().doClick();
                        break;
                    case ADD_INCLUDE_FLOW_LINE:
                        outlookTabbedPane.getAddIncludeFlowLineButton().doClick();
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