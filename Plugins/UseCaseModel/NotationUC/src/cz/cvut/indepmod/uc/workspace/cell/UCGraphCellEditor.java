package cz.cvut.indepmod.uc.workspace.cell;

import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.*;
import org.apache.log4j.Logger;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphCellEditor;

/**
 * ProMod, master thesis project
 * 
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 *
 * Special implementation of the UCGraphCellEditor for the UCNotation plugin.
 */
public class UCGraphCellEditor extends DefaultGraphCellEditor {

    private static final Logger LOG = Logger.getLogger(UCGraphCellEditor.class);

    private static String errorString = "ERROR";

    /**
     * When one edits the graph vertex, then an instance of DefaultGraphCellEditor is used. When editing is finished,
     * one of methods of CellEditorListener interface is invoked. Finally completeEditing(boolean, boolean, boolean)
     * method in the BasicGraphUI class is used and this class sets new UserObject which gets by getCellEditorValue() 
     * method invocation.
     *
     * So, this method overrides super class implementation of getCellEditorValue() method and return appropriate
     * object of appropriate class. 
     *
     * @return the cell editor value
     */
    @Override
    public Object getCellEditorValue() {
        if(lastCell instanceof DefaultGraphCell){
            final DefaultGraphCell defaultGraphCell = (DefaultGraphCell) lastCell;
            final Object oldUserObject = defaultGraphCell.getUserObject();

            final Object newUserObject;
            final String newName = (String) realEditor.getCellEditorValue();

            if(oldUserObject instanceof EdgeModel){
                newUserObject = new EdgeModel((EdgeModel)oldUserObject, newName);

            } else if(oldUserObject instanceof UseCaseModel){
                newUserObject = new UseCaseModel((UseCaseModel)oldUserObject, newName);
            } else if(oldUserObject instanceof ActorModel) {
                newUserObject = new ActorModel((ActorModel)oldUserObject, newName);
            } else if(oldUserObject instanceof SystemBorderModel) {
                newUserObject = new SystemBorderModel((SystemBorderModel)oldUserObject, newName);
            } else if(oldUserObject instanceof StepModel) {
                newUserObject = new StepModel((StepModel)oldUserObject, newName);
            } else if(oldUserObject instanceof ScenarioModel) {
                newUserObject = new ScenarioModel((ScenarioModel)oldUserObject, newName);
            } else {
                // should never happened, testing & debugging purposes
                LOG.error("Unknown UC vertex model");
                newUserObject = errorString;
            }

            return newUserObject;

        } else {
            // should never happened, testing & debugging purposes
            LOG.error("UC vertexes is not an instances of DefaultGraphCell class.");
        }

        return errorString;
    }

}
