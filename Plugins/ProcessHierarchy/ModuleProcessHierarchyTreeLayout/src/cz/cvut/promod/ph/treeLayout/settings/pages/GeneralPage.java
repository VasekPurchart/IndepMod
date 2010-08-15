package cz.cvut.promod.ph.treeLayout.settings.pages;

import com.jidesoft.dialog.AbstractDialogPage;
import com.jidesoft.dialog.ButtonListener;
import com.jidesoft.dialog.ButtonEvent;
import com.jidesoft.dialog.ButtonNames;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.builder.PanelBuilder;
import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.services.componentFactoryService.ComponentFactoryService;
import cz.cvut.promod.ph.treeLayout.settings.TreeLayoutSettings;
import cz.cvut.promod.ph.treeLayout.settings.TreeLayoutSettingsModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 23:46:10, 28.1.2010
 *
 * GeneralPage represents the common settings dialog page defined by ProcessHierarchyTreeLayout plugin. 
 */
public class GeneralPage extends AbstractDialogPage{

    private final JRadioButton topButton = ModelerSession.getComponentFactoryService().createRadioButton("top");
    private final JRadioButton centerButton = ModelerSession.getComponentFactoryService().createRadioButton("center");
    private final JRadioButton bottomButton = ModelerSession.getComponentFactoryService().createRadioButton("bottom");

    public GeneralPage(){
        super("Process Hierarchy Tree Layout");

        initEventHandling();
    }

    private void initEventHandling() {
        final ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(topButton);
        buttonGroup.add(centerButton);
        buttonGroup.add(bottomButton);

        addButtonListener(new ButtonListener(){
            public void buttonEventFired(ButtonEvent e) {
                if(e.getID() == 0){ // the button was clicked
                    if(ButtonNames.OK.equals(e.getButtonName()) || ButtonNames.APPLY.equals(e.getButtonName())){
                        if(topButton.isSelected()){
                            TreeLayoutSettings.getInstance().setVerticalLayout(TreeLayoutSettingsModel.VerticalLayout.TOP);

                        } else if(centerButton.isSelected()) {
                            TreeLayoutSettings.getInstance().setVerticalLayout(TreeLayoutSettingsModel.VerticalLayout.CENTER);

                        } else if(bottomButton.isSelected()){
                            TreeLayoutSettings.getInstance().setVerticalLayout(TreeLayoutSettingsModel.VerticalLayout.BOTTOM);
                        }
                    }
                }
            }
        });

        topButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);     
            }
        });

        centerButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
            }
        });

        bottomButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
            }
        });

        addButtonListener(new ButtonListener(){
            public void buttonEventFired(ButtonEvent e) {
                if(ButtonNames.CANCEL.equals(e.getButtonName())){
                    init();
                }
            }
        });
    }

    private void init() {
        switch (TreeLayoutSettings.getInstance().getVerticalLayout()){
            case TOP:
                topButton.doClick();
                break;
            case BOTTOM:
                bottomButton.doClick();
                break;
            case CENTER:
                centerButton.doClick();
                break;
        }

        fireButtonEvent(ButtonEvent.DISABLE_BUTTON, ButtonNames.APPLY);
    }

    public void lazyInitialize() {
        initLayout();
        init();
    }

    private void initLayout() {
        setBorder(Borders.createEmptyBorder(ComponentFactoryService.DEFAULT_FORM_BORDER));

        final FormLayout layout = new FormLayout(
                "pref",
                "pref, 3dlu, pref, 3dlu, pref, 3dlu, pref"
        );

        final CellConstraints cellConstraints = new CellConstraints();

        final PanelBuilder panelBuilder = new PanelBuilder(layout);

        int row = 1;
        panelBuilder.addSeparator("Process vertical layout", cellConstraints.xy(1, row));

        row += 2;
        panelBuilder.add(topButton, cellConstraints.xy(1,row));

        row += 2;
        panelBuilder.add(centerButton, cellConstraints.xy(1,row));

        row += 2;
        panelBuilder.add(bottomButton, cellConstraints.xy(1,row));

        setLayout(new BorderLayout());
        add(panelBuilder.getPanel(), BorderLayout.CENTER);
    }
}
