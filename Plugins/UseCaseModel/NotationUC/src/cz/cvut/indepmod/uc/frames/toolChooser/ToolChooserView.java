package cz.cvut.indepmod.uc.frames.toolChooser;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;

import javax.swing.*;

import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.services.componentFactoryService.ComponentFactoryService;

import java.awt.*;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 *
 * The View component of the ToolChooser dockable frame.
 */
public class ToolChooserView extends JPanel {

    protected final JToggleButton addControlFlowLineButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.CONTROL_FLOW_LINE_TOOL_RES, null
    );

    protected final JToggleButton controlButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.CONTROL_TOOL_RES, null
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

    public ToolChooserView(){
        //outlookTabbedPane = new ToolChooserOutlookTabs();

        initLayout();       
    }

    private void initLayout() {
        setBorder(Borders.createEmptyBorder(ComponentFactoryService.DEFAULT_FORM_BORDER));

        final FormLayout layout = new FormLayout(
                /* columns */ "pref:grow",
                /* rows */ "pref, 3dlu, pref, 10dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref"
        );

        final PanelBuilder builder = new PanelBuilder(layout);
        final CellConstraints cellConstraints = new CellConstraints();

        builder.add(controlButton, cellConstraints.xy(1,1));
        builder.add(addActorButton, cellConstraints.xy(1,5));
        builder.add(addUseCaseButton, cellConstraints.xy(1,7));
        builder.add(addSystemBorderButton, cellConstraints.xy(1,9));
        builder.add(addScenarioButton, cellConstraints.xy(1,11));
        builder.add(addSelectMssButton, cellConstraints.xy(1,13));
        builder.add(addStepButton, cellConstraints.xy(1,15));
        builder.add(addIncludeUcButton, cellConstraints.xy(1,17));
        builder.add(new JLabel("   "), cellConstraints.xy(1,18));
        builder.add(addControlFlowLineButton, cellConstraints.xy(1,20));
        builder.add(addIncludeFlowLineButton, cellConstraints.xy(1,22));
        
        setLayout(new BorderLayout());

        add(builder.getPanel(), BorderLayout.NORTH);
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