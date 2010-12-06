package cz.cvut.indepmod.uc.workspace.tabs.usecase;

import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.UseCaseModel;
import cz.cvut.indepmod.uc.workspace.UCWorkspace;
import cz.cvut.indepmod.uc.workspace.UCWorkspaceData;
import org.jgraph.graph.DefaultGraphCell;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class UCTreeCellRenderer implements TreeCellRenderer {
    DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();

    public UCTreeCellRenderer() {

    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        renderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

        renderer.setBorder(null);
        renderer.setIcon(null);
        renderer.setClosedIcon(null);

        Font font = new Font("SansSerif", Font.PLAIN, 20);
        if (value instanceof UCScenarioNode) {
            if (((UCScenarioNode) value).getMain()) {
                font = new Font("SansSerif", Font.BOLD, 25);
                renderer.setPreferredSize(new Dimension(300, 30));
            } else {
                renderer.setPreferredSize(new Dimension(200, 25));
            }
        }
        if (value instanceof UCStepNode) {
            UseCaseModel included = null;
            if (((UCStepNode) value).getInclude() != null) {
                Object[] objects = ((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).getDiagramModel().getGraphLayoutCache().getCells(false, true, false, false);
                for (Object obj : objects) {
                    if (obj instanceof DefaultGraphCell) {
                        if (((DefaultGraphCell) obj).getUserObject() instanceof UseCaseModel) {
                            UseCaseModel tmpUC = (UseCaseModel) ((DefaultGraphCell) obj).getUserObject();
                            if (((UCStepNode) value).getInclude().compareTo(tmpUC.getUuid()) == 0) {
                                included = tmpUC;
                                break;
                            }
                        }
                    }
                }
                if (included != null) {
                    renderer.setText("<html><a href=\"\">" + included.getName() + "</a><br />" + renderer.getText() + "</html>");
                }
            }
            //renderer.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            renderer.setPreferredSize(new Dimension(400, 60));


        }
        if (value.equals(tree.getModel().getRoot())) {
            font = new Font("SansSerif", Font.BOLD, 30);
            renderer.setPreferredSize(new Dimension(300, 40));
        }

        renderer.setFont(font);
        return renderer;
    }
}
