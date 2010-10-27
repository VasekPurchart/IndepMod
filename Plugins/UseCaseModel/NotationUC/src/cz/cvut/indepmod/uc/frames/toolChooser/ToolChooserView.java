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

    protected final ToolChooserOutlookTabs outlookTabbedPane;

    protected final JToggleButton controlButton = ModelerSession.getComponentFactoryService().createToggleButton(
            ToolChooserModel.CONTROL_TOOL_RES, null
    );

    public ToolChooserView(){
        outlookTabbedPane = new ToolChooserOutlookTabs();

        initLayout();       
    }

    private void initLayout() {
        setBorder(Borders.createEmptyBorder(ComponentFactoryService.DEFAULT_FORM_BORDER));

        final FormLayout layout = new FormLayout(
                /* columns */ "pref:grow",
                /* rows */ "pref, 3dlu, pref, 10dlu"
        );

        final PanelBuilder builder = new PanelBuilder(layout);
        final CellConstraints cellConstraints = new CellConstraints();

        builder.add(controlButton, cellConstraints.xy(1,1));
        
        setLayout(new BorderLayout());

        add(builder.getPanel(), BorderLayout.NORTH);
        add(outlookTabbedPane, BorderLayout.CENTER);
    }
}