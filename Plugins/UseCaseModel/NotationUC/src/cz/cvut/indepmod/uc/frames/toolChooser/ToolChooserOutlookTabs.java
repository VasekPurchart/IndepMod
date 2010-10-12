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
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 17:54:59, 18.1.2010
 *
 * The OutlookTabbedPane implementation for the View component of the ToolChooser dockable frame.
 */
public class ToolChooserOutlookTabs extends OutlookTabbedPane{

    private final String BASICS_LABEL = Resources.getResources().getString("uc.frame.tools.cat.basics");
    private final String OPERATORS_LABEL = Resources.getResources().getString("uc.frame.tools.cat.operators");
    private final String CONN_LABEL = Resources.getResources().getString("uc.frame.tools.cat.flows");

    protected final JToggleButton addAndButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.AND_TOOL_RES, null
    );

    protected final JToggleButton addOrButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.OR_TOOL_RES, null
    );

    protected final JToggleButton addXorButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.XOR_TOOL_RES, null
    );

    protected final JToggleButton addControlFlowLineButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.CONTROL_FLOW_LINE_TOOL_RES, null
    );

    protected final JToggleButton addInfoServicesFlowLineButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.INFO_SERVICES_FLOW_LINE_TOOL_RES, null
    );

    protected final JToggleButton addOrganizationFlowLineButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.ORG_FLOW_LINE_TOOL_RES, null
    );

    protected final JToggleButton addInformationFlowLineButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.INFO_FLOW_LINE_TOOL_RES, null
    );

    protected final JToggleButton addMaterialFlowLineButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.MATERIAL_FLOW_LINE_TOOL_RES, null
    );

    protected final JToggleButton addApplicationSWButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.APP_SW_TOOL_RES, null
    );

    protected final JToggleButton addMessageButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.MESSAGE_TOOL_RES, null
    );

    protected final JToggleButton addGoalButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.GOAL_TOOL_RES, null
    );

    protected final JToggleButton addMachineButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.MACHINE_TOOL_RES, null
    );

    protected final JToggleButton addHWButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.HW_TOOL_RES, null
    );

    protected final JToggleButton addAndOrButton = ModelerSession.getComponentFactoryService().createToggleButton(
             ToolChooserModel.AND_TOOL_RES + " / " +
             ToolChooserModel.OR_TOOL_RES, null
    );

    protected final JToggleButton addAndXorButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.AND_TOOL_RES + " / " +
             ToolChooserModel.XOR_TOOL_RES, null
    );

    protected final JToggleButton addOrAndButton = ModelerSession.getComponentFactoryService().createToggleButton(
             ToolChooserModel.OR_TOOL_RES + " / " +
            ToolChooserModel.AND_TOOL_RES, null
    );

    protected final JToggleButton addOrXorButton = ModelerSession.getComponentFactoryService().createToggleButton(
             ToolChooserModel.OR_TOOL_RES + " / " +
            ToolChooserModel.XOR_TOOL_RES, null
    );

    protected final JToggleButton addXorAndButton = ModelerSession.getComponentFactoryService().createToggleButton(
             ToolChooserModel.XOR_TOOL_RES + " / " +
            ToolChooserModel.AND_TOOL_RES, null
    );

    protected final JToggleButton addXorOrButton = ModelerSession.getComponentFactoryService().createToggleButton(
             ToolChooserModel.XOR_TOOL_RES + " / " +
            ToolChooserModel.OR_TOOL_RES, null
    );

    protected final JToggleButton addActorButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.ACTOR_TOOL_RES, null
    );

    protected final JToggleButton addUseCaseButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.USE_CASE_TOOL_RES, null
    );


    public ToolChooserOutlookTabs(){
        initTabPane();
    }

    private void initTabPane() {
        addTab(BASICS_LABEL, createBasicsPanel());
        addTab(OPERATORS_LABEL, createOperatorsPanel());
        addTab(CONN_LABEL, createConnectionsPanel());
    }


    /**
     * Creates a tab with connections/flows.
     *
     * @return an instance of JPanel holding connections/flows controls
     */
    private JPanel createConnectionsPanel() {
        JPanel panel = ModelerSession.getComponentFactoryService().createPanel();
        panel.setBorder(Borders.createEmptyBorder(ComponentFactoryService.DEFAULT_FORM_BORDER));

        panel.setLayout(new FormLayout(
                "pref:grow",
                "pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref"
        ));
        final CellConstraints cellConstraints = new CellConstraints();
        panel.add(addControlFlowLineButton, cellConstraints.xy(1,1));
        panel.add(addInfoServicesFlowLineButton, cellConstraints.xy(1,3));
        panel.add(addOrganizationFlowLineButton, cellConstraints.xy(1,5));
        panel.add(addInformationFlowLineButton, cellConstraints.xy(1,7));
        panel.add(addMaterialFlowLineButton, cellConstraints.xy(1,9));

        return panel;
    }

    private JPanel createOperatorsPanel() {
        JPanel panel = ModelerSession.getComponentFactoryService().createPanel();
        panel.setBorder(Borders.createEmptyBorder(ComponentFactoryService.DEFAULT_FORM_BORDER));
        
        panel.setLayout(new FormLayout(
                "pref:grow",
                "pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref"
        ));
        final CellConstraints cellConstraints = new CellConstraints();
        panel.add(addAndButton, cellConstraints.xy(1,1));
        panel.add(addOrButton, cellConstraints.xy(1,3));
        panel.add(addXorButton, cellConstraints.xy(1,5));
        panel.add(addAndOrButton, cellConstraints.xy(1,7));
        panel.add(addAndXorButton, cellConstraints.xy(1,9));
        panel.add(addOrAndButton, cellConstraints.xy(1,11));
        panel.add(addOrXorButton, cellConstraints.xy(1,13));
        panel.add(addXorAndButton, cellConstraints.xy(1,15));
        panel.add(addXorOrButton, cellConstraints.xy(1,17));

        return panel;
    }

    private JPanel createBasicsPanel() {
        JPanel panel = ModelerSession.getComponentFactoryService().createPanel();
        panel.setBorder(Borders.createEmptyBorder(ComponentFactoryService.DEFAULT_FORM_BORDER));
        
        panel.setLayout(new FormLayout(
                "pref:grow",
                "pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref"
        ));
        final CellConstraints cellConstraints = new CellConstraints();
        panel.add(addActorButton, cellConstraints.xy(1,1));
        panel.add(addUseCaseButton, cellConstraints.xy(1,3));

        return panel;
    }
    

    public JToggleButton getAddAndButton() {
        return addAndButton;
    }

    public JToggleButton getAddOrButton() {
        return addOrButton;
    }

    public JToggleButton getAddXorButton() {
        return addXorButton;
    }

    public JToggleButton getAddControlFlowLineButton() {
        return addControlFlowLineButton;
    }

    public JToggleButton getAddInfoServicesFlowLineButton() {
        return addInfoServicesFlowLineButton;
    }

    public JToggleButton getAddOrganizationFlowLineButton() {
        return addOrganizationFlowLineButton;
    }

    public JToggleButton getAddInformationFlowLineButton() {
        return addInformationFlowLineButton;
    }

    public JToggleButton getAddMaterialFlowLineButton() {
        return addMaterialFlowLineButton;
    }

    //88
    public JToggleButton getAddApplicationSWButton() {
        return addApplicationSWButton;
    }

    public JToggleButton getAddMessageButton() {
        return addMessageButton;
    }

    public JToggleButton getAddGoalButton() {
        return addGoalButton;
    }

    public JToggleButton getAddMachineButton() {
        return addMachineButton;
    }

    public JToggleButton getAddHWButton() {
        return addHWButton;
    }

    public JToggleButton getAddAndOrButton() {
        return addAndOrButton;
    }

    public JToggleButton getAddAndXorButton() {
        return addAndXorButton;
    }

    public JToggleButton getAddOrAndButton() {
        return addOrAndButton;
    }

    public JToggleButton getAddOrXorButton() {
        return addOrXorButton;
    }

    public JToggleButton getAddXorAndButton() {
        return addXorAndButton;
    }

    public JToggleButton getAddXorOrButton() {
        return addXorOrButton;
    }

    public JToggleButton getAddUseCaseButton() {
        return addUseCaseButton;
    }

    public JToggleButton getAddActorButton() {
        return addActorButton;
    }
}