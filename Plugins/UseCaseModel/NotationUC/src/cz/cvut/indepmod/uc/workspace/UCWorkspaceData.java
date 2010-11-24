package cz.cvut.indepmod.uc.workspace;

import com.jgoodies.binding.value.ValueModel;
import cz.cvut.indepmod.uc.frames.toolChooser.ToolChooserModel;
import cz.cvut.indepmod.uc.workspace.tabs.UCGraph;
import cz.cvut.indepmod.uc.workspace.tabs.UCTabParent;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.NotationWorkspaceData;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import org.jgraph.JGraph;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 * <p/>
 * UCWorkspaceData represents the NotationWorkspaceData interface implementation for the UCNotation plugin.
 */
public class UCWorkspaceData implements NotationWorkspaceData {

    private static JGraph graph;
    private static UCWorkspace workspace;
    private static HashMap<UUID, UCTabParent> tabs = new HashMap<UUID, UCTabParent>();

    private final ValueModel selectedToolModel;
    private final ValueModel gridModel;
    private final ValueModel lockModel;
    private final ValueModel viewGridModel;
    private final ValueModel cellSizeModel;
    private final ValueModel scaleModel;
    private final ValueModel movableBelowZeroModel;

    public static final String PROPERTY_SELECTED_CELL = "selectedCell";

    public UCWorkspaceData(final ValueModel selectedToolModel,
                           final ValueModel gridModel,
                           final ValueModel lockModel,
                           final ValueModel viewGridModel,
                           final ValueModel cellSizeModel,
                           final ValueModel scaleModel,
                           final ValueModel movableBelowZeroModel,
                           final Map<String, ProModAction> actions,
                           final JPopupMenu popupMenu) {

        this.selectedToolModel = selectedToolModel;

        graph = new UCGraph(selectedToolModel, popupMenu, actions);
        workspace = new UCWorkspace(graph, actions, selectedToolModel, popupMenu);

        this.gridModel = gridModel;
        this.lockModel = lockModel;
        this.viewGridModel = viewGridModel;
        this.cellSizeModel = cellSizeModel;
        this.scaleModel = scaleModel;
        this.movableBelowZeroModel = movableBelowZeroModel;

        initEventHandling();
    }

    private void initEventHandling() {
        lockModel.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                graph.setEditable((Boolean) propertyChangeEvent.getNewValue());

            }
        });

        gridModel.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                graph.setGridEnabled((Boolean) propertyChangeEvent.getNewValue());

            }
        });

        viewGridModel.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                graph.setGridVisible((Boolean) propertyChangeEvent.getNewValue());

            }
        });

        cellSizeModel.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                final Integer gridCellSize = (Integer) propertyChangeEvent.getNewValue();

                graph.setGridSize(gridCellSize.doubleValue());
                graph.refresh();

            }
        });

        selectedToolModel.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                final ToolChooserModel.Tool selectedTool = (ToolChooserModel.Tool) propertyChangeEvent.getNewValue();

                boolean portsVisible = false;

                switch (selectedTool) {
                    case ADD_CONTROL_FLOW_LINE:
                    case ADD_INCLUDE_FLOW_LINE:
                        portsVisible = true;
                }

                graph.setPortsVisible(portsVisible);
            }
        });

        scaleModel.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                final double scaleMultiplicator = (((Integer) evt.getNewValue()).doubleValue()) / 100.0;

                graph.setScale(scaleMultiplicator);

            }
        });

        movableBelowZeroModel.addValueChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                graph.setMoveBelowZero((Boolean) evt.getNewValue());


            }
        });
    }

    public JComponent getWorkspaceComponentSingleton() {
        return workspace;
    }

    public JGraph getGraph() {
        return graph;
    }

    public static HashMap<UUID, UCTabParent> getTabs() {
        return tabs;
    }

    public static JComponent getWorkspaceComponentSingletonStatic() {
        return workspace;
    }

}
