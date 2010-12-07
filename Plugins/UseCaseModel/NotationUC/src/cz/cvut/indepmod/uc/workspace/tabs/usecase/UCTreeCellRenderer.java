package cz.cvut.indepmod.uc.workspace.tabs.usecase;

import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.UseCaseModel;
import cz.cvut.indepmod.uc.workspace.UCWorkspace;
import cz.cvut.indepmod.uc.workspace.UCWorkspaceData;
import org.jgraph.graph.DefaultGraphCell;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class UCTreeCellRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer {
    public UCTreeCellRenderer() {
        super();
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        this.setBorder(null);
        this.setIcon(null);
        this.setClosedIcon(null);

        Font font = new Font("SansSerif", Font.PLAIN, 16);
        if (value instanceof UCScenarioNode) {
            if (((UCScenarioNode) value).getMain()) {
                font = new Font("SansSerif", Font.BOLD, 25);
            } else {
                font = new Font("SansSerif", Font.PLAIN, 25);
            }
            FontMetrics metrics = this.getFontMetrics(font);
            this.setPreferredSize(new Dimension(metrics.stringWidth(this.getText()), 34));
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
            }

            if (included != null) {
                this.setText("<html><body><div style=\"padding: 2px;\"><strong>Include </strong><a href=\"\">" + included.getName() + "</a><br />" + this.getText() + "</div></body></html>");
            } else {
                this.setText("<html><body><div style=\"padding: 2px;\">" + this.getText() + "<br /></div></body></html>");
            }

            FontMetrics metrics = this.getFontMetrics(font);
            int w = 400;
            int h = (int) (Math.ceil((double) ((metrics.stringWidth(this.getText()) / w) + 1) * (metrics.getHeight() + 2)));
            this.setPreferredSize(new Dimension(w, h));
            this.setVerticalAlignment(SwingConstants.TOP);

            Border borderBlack = BorderFactory.createLineBorder(Color.BLACK, 2);
            this.setBorder(borderBlack);
        }
        if (value.equals(tree.getModel().getRoot())) {
            
            font = new Font("SansSerif", Font.BOLD, 30);
            FontMetrics metrics = this.getFontMetrics(font);
            this.setPreferredSize(new Dimension(metrics.stringWidth(this.getText()) + 15, 40));
            this.setText("<html><body><div style=\"margin-left: 10px;\">" + this.getText() + "</div></body></html>");
        }

        this.setFont(font);
        return this;
    }
}
