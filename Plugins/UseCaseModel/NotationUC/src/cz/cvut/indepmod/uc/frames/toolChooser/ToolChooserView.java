package cz.cvut.indepmod.uc.frames.toolChooser;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.services.componentFactoryService.ComponentFactoryService;

import javax.swing.*;
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

    protected final JToggleButton addStepButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.STEP_TOOL_RES, null
    );

    public ToolChooserView(){
        //outlookTabbedPane = new ToolChooserOutlookTabs();

        initLayout();       
    }

    private PanelBuilder builderUC;
    private PanelBuilder builderDetail;

    /**
     * Initializes layout
     */
    private void initLayout() {
        setBorder(Borders.createEmptyBorder(ComponentFactoryService.DEFAULT_FORM_BORDER));

        final FormLayout layout = new FormLayout(
                /* columns */ "pref:grow",
                /* rows */ "pref, 3dlu, pref, 10dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref"
        );

        final CellConstraints cellConstraints = new CellConstraints();
        setLayout(new BorderLayout());

        builderUC = new PanelBuilder(layout);

        builderUC.add(controlButton, new CellConstraints().xy(1,3));

        builderUC.add(new JSeparator(), cellConstraints.xy(1,5));

        builderUC.add(addActorButton, new CellConstraints().xy(1,7));
        builderUC.add(addUseCaseButton, cellConstraints.xy(1,9));
        builderUC.add(addSystemBorderButton, cellConstraints.xy(1,11));

        builderUC.add(new JSeparator(), cellConstraints.xy(1,13));

        builderUC.add(addControlFlowLineButton, cellConstraints.xy(1,15));
        builderUC.add(addIncludeFlowLineButton, cellConstraints.xy(1,17));

        add(builderUC.getPanel(), BorderLayout.NORTH);

        builderDetail = new PanelBuilder(layout);
        builderDetail.add(addScenarioButton, cellConstraints.xy(1,1));
        builderDetail.add(addStepButton, cellConstraints.xy(1,3));
    }

    /**
     * Changes the panel
     * @param panelName name of the panel
     */
    public void ChangePanel(String panelName)
    {
        removeAll();
        setLayout(new BorderLayout());
        if(panelName.compareTo("UC") == 0)
        {
            add(builderUC.getPanel(), BorderLayout.NORTH);
        }
        else
        {
            add(builderDetail.getPanel(), BorderLayout.NORTH);
        }
        //this.requestFocus();
        updateUI();
        //repaint();
    }

    /**
     * Getter - add flow line button
     * @return returns add flow line button
     */
    public JToggleButton getAddControlFlowLineButton() {
        return addControlFlowLineButton;
    }

    /**
     * Getter - add include flow line button
     * @return returns add include flow line button
     */
    public JToggleButton getAddIncludeFlowLineButton() {
        return addIncludeFlowLineButton;
    }

    /**
     * Getter - add use case button
     * @return returns add use case button
     */
    public JToggleButton getAddUseCaseButton() {
        return addUseCaseButton;
    }

    /**
     * Getter - add actor button
     * @return returns add actor button
     */
    public JToggleButton getAddActorButton() {
        return addActorButton;
    }

    /**
     * Getter - add scenario button
     * @return returns add scenario button
     */
    public JToggleButton getAddScenarioButton() {
        return addScenarioButton;
    }

    /**
     * Getter - add step button
     * @return returns add step button
     */
    public JToggleButton getAddStepButton() {
        return addStepButton;
    }

    /**
     * Getter - add system border button
     * @return returns add system border button
     */
    public JToggleButton getAddSystemBorderButton() {
        return addSystemBorderButton;
    }
}