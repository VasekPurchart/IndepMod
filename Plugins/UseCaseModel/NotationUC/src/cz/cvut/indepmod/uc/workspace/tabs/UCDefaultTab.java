package cz.cvut.indepmod.uc.workspace.tabs;

import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagramChange;
import org.jgraph.JGraph;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Viktor
 * Date: 2.11.2010
 * Time: 17:02:30
 * To change this template use File | Settings | File Templates.
 */
public class UCDefaultTab extends UCTabParent {
    private final GraphModelListener graphModelListener;

    public UCDefaultTab(final JGraph graph, final Map<String, ProModAction> actions) {
        super(graph, actions);

        graphModelListener = new GraphModelListener() {
            public void graphChanged(GraphModelEvent e) {
                final Object[] selectedCells = graph.getSelectionModel().getSelectionCells();
                graph.getSelectionModel().clearSelection();
                graph.getSelectionModel().setSelectionCells(selectedCells);

            }
        };
    }

    public void changePerformed(final ProjectDiagramChange change) {
        if (ProjectDiagramChange.ChangeType.CHANGE_FLAG.equals(change.getChangeType())
                && change.getChangeValue() instanceof Boolean
                && Boolean.FALSE.equals(change.getChangeValue())) {

            actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(false);

            return;
        }

        actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(true);
    }

}
