package cz.cvut.promod.epcImageExport.settings;

import com.jidesoft.dialog.*;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.binding.adapter.SpinnerAdapterFactory;
import com.jgoodies.binding.PresentationModel;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;

import cz.cvut.promod.services.componentFactoryService.ComponentFactoryService;
import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.epcImageExport.resources.Resources;
import cz.cvut.promod.epcImageExport.frames.imageExport.ImageExportModel;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 22:21:09, 24.1.2010
 *
 * The common dialog setting page of the EPCImageExportModule plugin. 
 */
public class ImageExportSettings extends AbstractDialogPage {

    private static final String INSET_LABEL = Resources.getResources().getString("epc.imageexport.settings.inset");

    private final JLabel titleLabel = ModelerSession.getComponentFactoryService().createLabel(INSET_LABEL);

    protected JSpinner insetSpinner = ModelerSession.getComponentFactoryService().createSpinner();

    private final int H_GAP = 0;
    private final int V_GAP = 20;

    private final SpinnerNumberModel insetSpinnerModel;

    private final PresentationModel<ImageExportModel> presentation;

    private final ApplyAction applyAction = new ApplyAction();
    private final CancelAction cancelAction = new CancelAction();


    public ImageExportSettings(final PresentationModel<ImageExportModel> presentation) {
        super(INSET_LABEL);

        this.presentation = presentation;

        insetSpinnerModel =
            SpinnerAdapterFactory.createNumberAdapter(
                    presentation.getBufferedModel(ImageExportModel.PROPERTY_INSET),
                    ImageExportModel.INIT_INSET,
                    ImageExportModel.MIN_INSET,
                    ImageExportModel.MAX_INSET,
                    ImageExportModel.INIT_SPINNER_STEP);
    }


    public void lazyInitialize() {
        initLayout();

        insetSpinner.setModel(insetSpinnerModel);

        initEventHandling();        
    }

    private void initEventHandling() {
        addButtonListener(new ButtonListener() {
            public void buttonEventFired(ButtonEvent e) {
                if (e.getID() == 0) { // the button was clicked
                    if (ButtonNames.OK.equals(e.getButtonName()) || ButtonNames.APPLY.equals(e.getButtonName())) {
                        applyAction.actionPerformed(null);
                    }
                }
            }
        });

        addButtonListener(new ButtonListener() {
            public void buttonEventFired(ButtonEvent e) {
                if(ButtonNames.CANCEL.equals(e.getButtonName())) {
                    cancelAction.actionPerformed(null);
                }
            }
        });

        insetSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                fireButtonEvent(ButtonEvent.ENABLE_BUTTON, ButtonNames.APPLY);
            }
        });
    }

    private void initLayout() {
        setBorder(Borders.createEmptyBorder(ComponentFactoryService.DEFAULT_FORM_BORDER));

        setLayout(new  FlowLayout(FlowLayout.LEFT, H_GAP, V_GAP));

        titleLabel.setBorder(Borders.createEmptyBorder(ComponentFactoryService.DEFAULT_FORM_BORDER));
        
        add(titleLabel);
        add(insetSpinner);
    }

    /**
     * Commits the Trigger used to buffer the editor contents.
     */
    private final class ApplyAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            presentation.triggerCommit();
        }

    }


    /**
     * Flushed the Trigger used to buffer the editor contents.
     */
    private final class CancelAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
            presentation.triggerFlush();
        }

    }

}
