package cz.cvut.indepmod.uc.modelFactory.ucGraphModel;

import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.ActorModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.StepModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.EdgeModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.SystemBorderModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.UseCaseModel;
import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.promod.services.ModelerSession;
import org.jgraph.graph.*;

import javax.swing.*;
import java.util.List;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 *
 * Represents an implementation of common GraphModel for UC notation diagrams.
 */
public class UCGraphModel extends DefaultGraphModel{       

    private final static String ERROR_TITLE_LABEL = Resources.getResources().getString("uc.conn.error.title");
    private final static String ERROR_SYSTEM_BORDER_LABEL = Resources.getResources().getString("uc.conn.error.system_border");
    private final static String ERROR_INCLUDE_FLOW_LABEL = Resources.getResources().getString("uc.conn.error.include.flow");
    private final static String ERROR_CONTROL_FLOW_LABEL = Resources.getResources().getString("uc.conn.error.control.flow");

    public UCGraphModel(){
        super(null, null);
    }

    public UCGraphModel(final List roots, final AttributeMap attributes){
        super(roots, attributes);        
    }

    @Override
    public boolean acceptsTarget(Object edge, Object port) {
        return ((Edge) edge).getSource() != port && accepts(edge, port, false);

    }

    @Override
    public boolean acceptsSource(Object edge, Object port) {
        return ((Edge) edge).getTarget() != port && accepts(edge, port, true);

    }

    /**
     * Determines whether the connection is acceptable or not.
     *
     * @param edge is the connecting edge
     * @param port is the source or target port
     * @param source if true, the port specified is source port, otherwise the port is target port
     * @return true if the connection is acceptable, false otherwise
     */
    private boolean accepts(final Object edge, final Object port, final boolean source) {
        final DefaultEdge defaultEdge = (DefaultEdge) edge;
        final DefaultPort sourcePort;
        final DefaultPort targetPort;

        if(source){
            sourcePort = (DefaultPort) port;
            targetPort = (DefaultPort) defaultEdge.getTarget();
        } else {
            sourcePort = (DefaultPort) defaultEdge.getSource();
            targetPort = (DefaultPort) port;            
        }

        if((sourcePort == null) || (targetPort == null)){
            return false;
        }

        final DefaultGraphCell sourceCell = (DefaultGraphCell) sourcePort.getParent();
        final DefaultGraphCell targetCell = (DefaultGraphCell) targetPort.getParent();
        final EdgeModel edgeModel = (EdgeModel) defaultEdge.getUserObject();
        final Object targetUserObject = targetCell.getUserObject();
        final Object sourceUserObject = sourceCell.getUserObject();

        // System border cant be connected with anything
        if(sourceCell.getUserObject() instanceof SystemBorderModel || targetCell.getUserObject() instanceof SystemBorderModel){
            JOptionPane.showMessageDialog(
                    ModelerSession.getFrame(),
                    ERROR_SYSTEM_BORDER_LABEL, ERROR_TITLE_LABEL, JOptionPane.ERROR_MESSAGE);

            return false;
        }

        // Include flow can be used only between two Use Case instances
        if(cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.EdgeModel.EdgeType.INCLUDE_FLOW.equals(edgeModel.getEdgeType())){
            if( !(sourceCell.getUserObject() instanceof UseCaseModel) || !(targetCell.getUserObject() instanceof UseCaseModel)){
                JOptionPane.showMessageDialog(
                    ModelerSession.getFrame(),
                    ERROR_INCLUDE_FLOW_LABEL, ERROR_TITLE_LABEL, JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }

        // Control flow can be used only between Use Case and Actor
        if(cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.EdgeModel.EdgeType.CONTROL_FLOW.equals(edgeModel.getEdgeType())){
            if( !((sourceCell.getUserObject() instanceof UseCaseModel && targetCell.getUserObject() instanceof ActorModel) ||
                  (targetCell.getUserObject() instanceof UseCaseModel && sourceCell.getUserObject() instanceof ActorModel))){
                    JOptionPane.showMessageDialog(
                    ModelerSession.getFrame(),
                    ERROR_CONTROL_FLOW_LABEL, ERROR_TITLE_LABEL, JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }

        return super.acceptsSource(edge, targetPort);
    }

}
