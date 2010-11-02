package cz.cvut.indepmod.uc.modelFactory.ucGraphModel;

import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.EdgeModel;
import cz.cvut.indepmod.uc.resources.Resources;
import org.jgraph.graph.*;

import java.util.List;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 *
 * Represents an implementation of common GraphModel for UC notation diagrams.
 */
public class UCGraphUseCaseModel extends DefaultGraphModel{       

    private final static String ERROR_TITLE_LABEL = Resources.getResources().getString("uc.menu.uc.notation");
    private final static String ERROR_EVENTS_LABEL = Resources.getResources().getString("uc.conn.error.events");
    private final static String ERROR_FUNCTIONS_LABEL = Resources.getResources().getString("uc.conn.error.functions");
    private final static String ERROR_MULTIPLE_CONTROL_FLOW_LABEL =
            Resources.getResources().getString("uc.conn.error.multiple.ctr.flow");
    private final static String ERROR_LOGICS_ILLEGAL_FLOW_LABEL =
            Resources.getResources().getString("uc.conn.error.logics.illegal.flow");
    private final static String ERROR_LOGICS_ILLEGAL_NEIGHBOUR_LABEL =
            Resources.getResources().getString("uc.conn.error.logics.illegal.neig");


    public UCGraphUseCaseModel(){
        super(null, null);
    }

    public UCGraphUseCaseModel(final List roots, final AttributeMap attributes){
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

        // No function can be connected directly with another event.
        /*
        if(sourceCell.getUserObject() instanceof FunctionModel && targetCell.getUserObject() instanceof FunctionModel){
            JOptionPane.showMessageDialog(
                    ModelerSession.getFrame(),
                    ERROR_FUNCTIONS_LABEL, ERROR_TITLE_LABEL, JOptionPane.ERROR_MESSAGE);

            return false;
        }
        */

        return super.acceptsSource(edge, targetPort);
    }

}
