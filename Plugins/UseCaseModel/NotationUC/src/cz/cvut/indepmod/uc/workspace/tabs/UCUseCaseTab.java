package cz.cvut.indepmod.uc.workspace.tabs;

import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagramChange;
import org.jgraph.JGraph;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.UUID;

public class UCUseCaseTab extends UCTabParent {
    private UUID uuid;

    public UCUseCaseTab(final JGraph graph, final Map<String, ProModAction> actions, UUID uuid) {
        super(null, actions);
        this.uuid = uuid;

        JTree tree = new JTree();

        tree.add(new JTree());

        this.getViewport().add(tree);
    }

    public void changePerformed(final ProjectDiagramChange change) {
        if (ProjectDiagramChange.ChangeType.CHANGE_FLAG.equals(change.getChangeType())
                && change.getChangeValue() instanceof Boolean
                && Boolean.FALSE.equals(change.getChangeValue())) {

            //actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(false);

            return;
        }

        //actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(true);
    }

    public JTextField makeScenario() {
        JTextField field = new JTextField("Scenario");
        field.setPreferredSize(new Dimension(200, 200));
        return field;
    }

    @Override
    public void over() {
    }

    public void update() {
    }
}
