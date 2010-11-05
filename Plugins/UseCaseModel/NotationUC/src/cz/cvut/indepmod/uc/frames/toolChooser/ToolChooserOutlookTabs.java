package cz.cvut.indepmod.uc.frames.toolChooser;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jidesoft.pane.OutlookTabbedPane;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.services.componentFactoryService.ComponentFactoryService;

import javax.swing.*;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 *
 * The OutlookTabbedPane implementation for the View component of the ToolChooser dockable frame.
 */
public class ToolChooserOutlookTabs extends OutlookTabbedPane{

    private final String BASICS_LABEL = Resources.getResources().getString("uc.frame.tools.cat.basics");

    protected final JToggleButton addControlFlowLineButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.CONTROL_FLOW_LINE_TOOL_RES, null
    );

    protected final JToggleButton addIncludeFlowLineButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.INCLUDE_FLOW_LINE_TOOL_RES, null
    );
                                
    protected final JToggleButton addActorButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.ACTOR_TOOL_RES, null
    );

    protected final JToggleButton addUseCaseButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.USE_CASE_TOOL_RES, null
    );

    protected final JToggleButton addSystemBorderButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.SYSTEM_BORDER_TOOL_RES, null
    );

    protected final JToggleButton addScenarioButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.SCENARIO_TOOL_RES, null
    );

    protected final JToggleButton addSelectMssButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.SELECT_MSS_TOOL_RES, null
    );

    protected final JToggleButton addStepButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.STEP_TOOL_RES, null
    );

    protected final JToggleButton addIncludeUcButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.INCLUDE_UC_TOOL_RES, null
    );

    public ToolChooserOutlookTabs(){
        initTabPane();
    }

    private void initTabPane() {
        addTab(BASICS_LABEL, createBasicsPanel());
    }
    
    private JPanel createBasicsPanel() {
        JPanel panel = ModelerSession.getComponentFactoryService().createPanel();
        panel.setBorder(Borders.createEmptyBorder(ComponentFactoryService.DEFAULT_FORM_BORDER));
        
        panel.setLayout(new FormLayout(
                "pref:grow",
                "pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref"
        ));
        final CellConstraints cellConstraints = new CellConstraints();
        panel.add(addActorButton, cellConstraints.xy(1,1));
        panel.add(addUseCaseButton, cellConstraints.xy(1,3));
        panel.add(addSystemBorderButton, cellConstraints.xy(1,5));
        panel.add(addScenarioButton, cellConstraints.xy(1,7));
        panel.add(addSelectMssButton, cellConstraints.xy(1,9));
        panel.add(addStepButton, cellConstraints.xy(1,11));
        panel.add(addIncludeUcButton, cellConstraints.xy(1,13));
        panel.add(new JLabel("____________________________________________"), cellConstraints.xy(1,14));
        panel.add(addControlFlowLineButton, cellConstraints.xy(1,16));
        panel.add(addIncludeFlowLineButton, cellConstraints.xy(1,18));

        return panel;
    }
    
  public JToggleButton getAddControlFlowLineButton() {
        return addControlFlowLineButton;
    }

    public JToggleButton getAddIncludeFlowLineButton() {
        return addIncludeFlowLineButton;
    }

    public JToggleButton getAddUseCaseButton() {
        return addUseCaseButton;
    }

    public JToggleButton getAddActorButton() {
        return addActorButton;
    }

    public JToggleButton getAddScenarioButton() {
        return addScenarioButton;
    }

    public JToggleButton getAddSelectMssButton() {
        return addSelectMssButton;
    }

    public JToggleButton getAddStepButton() {
        return addStepButton;
    }

    public JToggleButton getAddIncludeUcButton() {
        return addIncludeUcButton;
    }

    public JToggleButton getAddSystemBorderButton() {
        return addSystemBorderButton;
    }
}
