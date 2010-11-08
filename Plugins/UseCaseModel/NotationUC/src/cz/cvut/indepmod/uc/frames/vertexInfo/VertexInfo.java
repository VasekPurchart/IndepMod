package cz.cvut.indepmod.uc.frames.vertexInfo;

import com.jidesoft.grid.Property;
import com.jidesoft.grid.PropertyTableModel;
import com.jidesoft.introspector.BeanProperty;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.ActorModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.SystemBorderModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.UseCaseModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.*;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.promod.gui.support.utils.NotationGuiHolder;
import cz.cvut.promod.plugin.notationSpecificPlugIn.DockableFrameData;
import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagram;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.DefaultGraphCell;

import javax.swing.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 0:20:45, 8.12.2009
 *
 * The Info dockable frame.
 */
public class VertexInfo extends VertexInfoView implements DockableFrameData {

    private static final Logger LOG = Logger.getLogger(VertexInfo.class);

    public static final String FRAME_ID = "uc.info";

    private static final String TITLE_LABEL = Resources.getResources().getString("uc.info.title");
    private static final String NOTE_LABEL = Resources.getResources().getString("uc.info.note");
    private static final String CATEGORY_NOTES_LABEL = Resources.getResources().getString("uc.info.cat.notes");
    private static final String APP_SW_LABEL = Resources.getResources().getString("uc.vertex.app.sw");
    private static final String ACTOR_LABEL = Resources.getResources().getString("uc.vertex.actor");
    private static final String UC_LABEL = Resources.getResources().getString("uc.vertex.uc");
    private static final String SYSTEM_BORDER_LABEL = Resources.getResources().getString("uc.vertex.border");
    private static final String CATEGORY_TYPE_LABEL = Resources.getResources().getString("uc.info.cat.type");
    private static final String TYPE_LABEL = Resources.getResources().getString("uc.info.type");
    private static final String NAME_LABEL = Resources.getResources().getString("uc.info.name");
    private static final String UUID_LABEL = Resources.getResources().getString("uc.info.uuid");
    private static final String CATEGORY_GENERALS_LABEL = Resources.getResources().getString("uc.info.cat.general");
    private static final String CTR_FLOW_LABEL = Resources.getResources().getString("uc.frame.tools.line.control.flow");
    private static final String INCLUDE_FLOW_LABEL = Resources.getResources().getString("uc.frame.tools.line.include.flow");

    public String getDockableFrameName() {
        return FRAME_ID;
    }

    public JComponent getDockableFrameComponent() {
        return this;
    }

    public NotationGuiHolder.Position getInitialPosition() {
        return NotationGuiHolder.Position.BOTTOM;
    }

    public boolean isMaximizable() {
        return false;
    }

    public Set<NotationGuiHolder.Position> getAllowedDockableFramePositions() {
        final Set<NotationGuiHolder.Position> allowedPositions = new HashSet<NotationGuiHolder.Position>();
        allowedPositions.add(NotationGuiHolder.Position.BOTTOM);
        allowedPositions.add(NotationGuiHolder.Position.TOP);

        return allowedPositions;
    }

    public InitialState getInitialState() {
        return InitialState.HIDDEN;
    }

    public String getDockableFrameTitle() {
        return TITLE_LABEL;
    }

    public Icon getButtonIcon() {
        return null;
    }

    /**
     * Takes care of updating of vertex information whenever a selection is changed.  
     *
     * @param graph is the graph used by the notation
     */
    public void initCellSelectionListener(final JGraph graph) {
        graph.addGraphSelectionListener(new GraphSelectionListener(){
            public void valueChanged(GraphSelectionEvent e){
                List<Property> listProperties = new ArrayList<Property>();

                final Object newlySelectedVertex = getNewlySelectedVertex(e);
                if(newlySelectedVertex != null){
                    final Object cell = e.getCell();

                    if(cell instanceof DefaultGraphCell){
                       final Object userObject = ((DefaultGraphCell) cell).getUserObject();

                        recognizeItemType(userObject, listProperties);

                        if(userObject instanceof UCEditableVertex){
                            updateInfo(listProperties, userObject);
                        }
                        /*
                        if(userObject instanceof LogicFunctionModel){
                            getLogicOperatorCondition((LogicFunctionModel)userObject, listProperties);
                        }    */

                        getItemNote(userObject, listProperties);

                        table.setModel(new PropertyTableModel<Property>(listProperties));
                        table.expandFirstLevel();

                        return;
                    }
                }

                table.setModel(null);
            }
        });
    }

    /**
     * Adds the note.
     *
     * @param userObject is the owner of the note
     * @param listProperties is the list where is the info about note supposed to be added
     */
    private void getItemNote(final Object userObject, final List<Property> listProperties) {
        if(userObject instanceof UCNoteItem){
            try {
                final BeanProperty conditionBean = new BeanProperty(
                        new PropertyDescriptor(UCNoteItem.NOTE_PROPERTY, UCNoteItem.class)){

                    @Override
                    public void setValue(Object o) {
                        super.setValue(o);

                        // let the project diagram know about the change
                        final ProjectDiagram projectDiagram = ModelerSession.getProjectService().getSelectedDiagram();
                        if(projectDiagram != null){
                            projectDiagram.changePerformed(null);
                        }
                    }
                };

                conditionBean.setInstance(userObject);
                conditionBean.setName(NOTE_LABEL);
                conditionBean.setCategory(CATEGORY_NOTES_LABEL);
                conditionBean.setEditable(true);

                listProperties.add(conditionBean);

            } catch (IntrospectionException e) {
                LOG.error("Couldn't introspect an instance of UCNoteItem", e);
            }
        }
    }


    /**
     * Determinate the type of selected graph item.
     *
     * @param userObject is the user's object of selected cell
     * @param listProperties is the list to that will be added the record about the type of cell
     */
    private void recognizeItemType(final Object userObject, final List<Property> listProperties) {
        final Property typeBean = new Property(){
                public void setValue(Object o) {
                }

                public Object getValue() {
                    if(userObject instanceof ApplicationSoftwareModel){
                        return APP_SW_LABEL;
                    } else if(userObject instanceof ActorModel) {
                        return ACTOR_LABEL;
                    } else if(userObject instanceof SystemBorderModel) {
                        return SYSTEM_BORDER_LABEL;
                    } else if(userObject instanceof UseCaseModel) {
                        return UC_LABEL;
                    } else {
                        LOG.error("An unknown type of UC item.");
                        return "";
                    }
                }
            };

            typeBean.setName(TYPE_LABEL);
            typeBean.setCategory(CATEGORY_TYPE_LABEL);
            typeBean.setEditable(false);
            listProperties.add(typeBean);
    }

    /**
     * Get description for flow (edge).
     *
     * @param edgeModel is an instance of flow
     * @return the description of flow
     */
    private String getFlowType(final EdgeModel edgeModel) {
        StringBuffer type = new StringBuffer();
        type.append("(");

        switch (edgeModel.getEdgeType()){
            case CONTROL_FLOW:
                type.append(CTR_FLOW_LABEL);
                break;
            case INCLUDE_FLOW:
                type.append(INCLUDE_FLOW_LABEL);
                break;

            default:
                type.append("-");
                LOG.error("Unknown type of flow.");
        }

        type.append(")");

        return type.toString();
    }    

    /**
     * Finds newly selected vertex in the graph.
     *
     * @param event represents actual graph selection event
     * @return newly selected vertex; or null if there are no newly selected vertexes or more than one vertex is selected
     */
    private Object getNewlySelectedVertex(final GraphSelectionEvent event) {
        Object newCell = null;
        Object systemBorderCell = null;
        
        for(Object cell : event.getCells()){
            if(cell instanceof org.jgraph.graph.DefaultGraphCell)
            {
                org.jgraph.graph.DefaultGraphCell gCell = (org.jgraph.graph.DefaultGraphCell) cell;
                if(gCell.getUserObject() instanceof SystemBorderModel)
                {
                    systemBorderCell = cell;
                    continue;
                }
            }
            if(event.isAddedCell(cell)){
                if(newCell != null) {
                    return null; // multiple vertex selection
                }

                newCell = cell;
            }
        }

        return newCell;
    }

    private void updateInfo(final List<Property> listProperties, final Object userObject) {
        try{
            final UCEditableVertex editableVertex = (UCEditableVertex) userObject;

            final Property nameBean = new Property(){
                public void setValue(Object object) {
                    // do nothing
                }

                public Object getValue() {
                    return editableVertex.getName();
                }
            };

            nameBean.setName(NAME_LABEL);
            nameBean.setCategory(CATEGORY_GENERALS_LABEL);
            nameBean.setEditable(false);
            listProperties.add(nameBean);

            final Property uuidBean = new Property(){
                public void setValue(Object o) {
                    //do nothing
                }

                public Object getValue() {
                    return editableVertex.getUuid();
                }
            };

            uuidBean.setName(UUID_LABEL);
            uuidBean.setCategory(CATEGORY_GENERALS_LABEL);
            uuidBean.setEditable(false);
            listProperties.add(uuidBean);

        } catch(Exception exception){
            LOG.error("Introspection failed.", exception);
        }
    }

}
