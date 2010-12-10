package cz.cvut.indepmod.uc.frames.graphOptions;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.adapter.BoundedRangeAdapter;
import com.jgoodies.binding.adapter.SpinnerAdapterFactory;
import com.jgoodies.binding.value.ValueModel;
import cz.cvut.indepmod.uc.UCNotationModel;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.promod.gui.support.utils.NotationGuiHolder;
import cz.cvut.promod.plugin.notationSpecificPlugIn.DockableFrameData;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 *
 * Dockable frame controlling the options of the graph. 
 */
public class GraphOptions extends GraphOptionsView implements DockableFrameData {

    private static final Logger LOG = Logger.getLogger(GraphOptions.class);

    private final GraphOptionsModel model;

    private final ValueModel gridModel;
    private final ValueModel lockModel;
    private final ValueModel viewGridModel;
    private final ValueModel cellSizeModel;
    private final ValueModel scaleModel;
    private final ValueModel movableBelowZeroModel;

    private boolean lastViewGridValue;


    /**
     * Constructor creates the dockable frame
     */
    public GraphOptions() {
        model = new GraphOptionsModel();
        final PresentationModel<GraphOptionsModel> presentation = new PresentationModel<GraphOptionsModel>(model);

        gridModel = presentation.getModel(GraphOptionsModel.PROPERTY_GRID);
        lockModel = presentation.getModel(GraphOptionsModel.PROPERTY_LOCK);
        viewGridModel = presentation.getModel(GraphOptionsModel.PROPERTY_VIEW_GRID);
        cellSizeModel = presentation.getModel(GraphOptionsModel.PROPERTY_CELL_SIZE);
        scaleModel = presentation.getModel(GraphOptionsModel.PROPERTY_SCALE);
        movableBelowZeroModel = presentation.getModel(GraphOptionsModel.PROPERTY_ANTI_ALIASING);

        initBinding();

        initEventHandling();

        // set viewGridCheckBox and cellSizeSpinned disabled
        gridModel.setValue(false);

        // set initial grid cell size
        cellSizeModel.setValue(GraphOptionsModel.INIT_SELL_SIZE);

        lastViewGridValue = (Boolean) viewGridModel.getValue();

        scaleModel.setValue(GraphOptionsModel.INIT_SCALE);

        movableBelowZeroModel.setValue(false);
    }

    /**
     * Event handler initialization for button
     */
    private void initEventHandling() {
        initialSizeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                scaleModel.setValue(100);
            }
        });
    }

    /**
     * Event Handler for actions
     * @param actions  List of actions that should be performed
     */
    public void initEventHandling(final Map<String, ProModAction> actions) {
        if(actions.containsKey(UCNotationModel.REFRESH_ACTION_KEY)){
            refreshButton.setAction(actions.get(UCNotationModel.REFRESH_ACTION_KEY));
            
        } else {
            LOG.error("No " + UCNotationModel.REFRESH_ACTION_KEY + " action available. Refresh button won't have no action set.");
        }

    }

    /**
     * Components binder
     */
    private void initBinding() {
        Bindings.bind(gridCheckBox, gridModel);
        Bindings.bind(viewGridCheckBox, viewGridModel);
        Bindings.bind(movableBelowZeroCheckBox, lockModel);
        Bindings.bind(movableBelowZeroCheckBox, movableBelowZeroModel);

        scaleSlider.setModel(new BoundedRangeAdapter(
                                scaleModel,
                                GraphOptionsModel.EXTENT,
                                scaleSlider.getMinimum(),
                                scaleSlider.getMaximum()
                            )
        );

        final SpinnerNumberModel scaleSpinnerModel =
            SpinnerAdapterFactory.createNumberAdapter(
                    scaleModel,
                    GraphOptionsModel.INIT_SCALE,
                    GraphOptionsModel.MIN_SCALE,
                    GraphOptionsModel.MAX_SCALE,
                    GraphOptionsModel.INIT_SPINNER_STEP);

        scaleSpinner.setModel(scaleSpinnerModel);

        SpinnerNumberModel gridSpinnerModel =
            SpinnerAdapterFactory.createNumberAdapter(
                    cellSizeModel,
                    GraphOptionsModel.INIT_CELL_SIZE,
                    GraphOptionsModel.MIN_CELL_SIZE,
                    GraphOptionsModel.MAX_CELL_SIZE,
                    GraphOptionsModel.INIT_SPINNER_STEP);

        cellSizeSpinner.setModel(gridSpinnerModel);


        // set viewGridCheckBox and cellSizeSpinned enabled or disabled
        gridModel.addValueChangeListener(new PropertyChangeListener(){
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                final boolean enabled = (Boolean) propertyChangeEvent.getNewValue();

                /* if one don't use the grid, show grid view option should be hidden,
                when the grid is used again, tha last value of view grid is restored */
                if(enabled){
                    viewGridModel.setValue(lastViewGridValue);
                } else {
                    lastViewGridValue = (Boolean) viewGridModel.getValue();
                    viewGridModel.setValue(false);
                }

                cellSizeSpinner.setEnabled(enabled);
                viewGridCheckBox.setEnabled(enabled);
            }
        });
    }

    /**
     * Getter - dockable frame
     * @return returns dockableFrame
     */
    public String getDockableFrameName() {
        return "UCJGRaphOptions";
    }

    /**
     * Getter - dockable frame component
     * @return returns dockable frame component
     */
    public JComponent getDockableFrameComponent() {
        return this;
    }

    /**
     * Sets initial position
     * @return returns initial position
     */
    public NotationGuiHolder.Position getInitialPosition() {
        return NotationGuiHolder.Position.RIGHT;
    }

    /**
     * Returns true/false whether is Maxizable
     * @return returns if it is maxizable
     */
    public boolean isMaximizable() {
        return false;
    }

    /**
     * Getter for dockable frame positions
     * @return returns Set of dockable positions
     */
    public Set<NotationGuiHolder.Position> getAllowedDockableFramePositions() {
        final Set<NotationGuiHolder.Position> positions = new HashSet<NotationGuiHolder.Position>();
        positions.add(NotationGuiHolder.Position.LEFT);
        positions.add(NotationGuiHolder.Position.RIGHT);

        return positions;
    }

    /**
     * Getter - initial state
     * @return  returns initial state
     */
    public InitialState getInitialState() {
        return InitialState.HIDDEN;
    }

    /**
     * Getter - title of the dockable frame
     * @return returns dockable frame title String
     */
    public String getDockableFrameTitle() {
        return Resources.getResources().getString(GraphOptionsModel.FRAME_TITLE_RES);
    }

    /**
     * Getter - button icon
     * @return Icon for the button
     */
    public Icon getButtonIcon() {
        return null;
    }

    /**
     * Getter - grid model
     * @return  returns grid model
     */
    public ValueModel getGridModel() {
        return gridModel;
    }

    /**
     * Getter - lock model
     * @return returns ValueModel of lockModel
     */
    public ValueModel getLockModel() {
        return lockModel;
    }

    /**
     * Getter - view grid model
     * @return returns ValueModel of view grid model
     */
    public ValueModel getViewGridModel() {
        return viewGridModel;
    }

    /**
     * Getter - cell size model
     * @return returns ValueModel of cellSizeModel
     */
    public ValueModel getCellSizeModel() {
        return cellSizeModel;
    }

    /**
     * Getter - scale model
     * @return returns ValueModel of scale model
     */
    public ValueModel getScaleModel() {
        return scaleModel;
    }

    /**
     * Getter - movable below zero model
     * @return returns ValueModel of movableBelowZeroModel
     */
    public ValueModel getMovableBelowZeroModel() {
        return movableBelowZeroModel;
    }
}