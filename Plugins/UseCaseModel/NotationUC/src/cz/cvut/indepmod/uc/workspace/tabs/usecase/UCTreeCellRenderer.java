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

        Font font = new Font(getFont().getName(), Font.PLAIN, 16);
        if (value instanceof UCScenarioNode) {
            if (((UCScenarioNode) value).getMain()) {
                font = new Font("SansSerif", Font.BOLD, 25);
            } else {
                font = new Font("SansSerif", Font.PLAIN, 25);
            }
            FontMetrics metrics = this.getFontMetrics(font);
            this.setPreferredSize(new Dimension(metrics.stringWidth((String) ((UCScenarioNode) value).getUserObject()), 34))   ;
        }
        if (value instanceof UCStepNode) {

            UseCaseModel included = null;
            if (((UCStepNode) value).getInclude() != null) {
                Object[] objects = ((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).getDiagramModel().getGraphLayoutCache().getCells(false, true, false, false);
                for (Object obj : objects) {
                    if (obj instanceof DefaultGraphCell && ((DefaultGraphCell) obj).getUserObject() instanceof UseCaseModel) {
                        UseCaseModel tmpUC = (UseCaseModel) ((DefaultGraphCell) obj).getUserObject();
                        if (((UCStepNode) value).getInclude().compareTo(tmpUC.getUuid()) == 0) {
                            included = tmpUC;
                            break;
                        }
                    }
                }
            }

            JComponent frame = new JPanel();
            JComponent frameIn = new JPanel();
            frame.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            frame.setBackground(null);
            frameIn.setBackground(null);
            final JEditorPane node = new JEditorPane();
            node.setContentType("text/html");
            node.setEditable(false);

            FontMetrics metrics = this.getFontMetrics(font);
            int w = 400;
            int h = (int) (Math.ceil((double) ((metrics.stringWidth((String) ((UCStepNode) value).getUserObject()) / w) + 1) * (metrics.getHeight() + 3))) + 15;

            if (included != null) {
                node.setText("<html><body><div style=\"padding: 5px;\"><strong>Include </strong><a href=\"" + included.getUuid() + "\">" + included.getName() + "</a><br />" + this.getText() + "</div></body></html>");
                h += 40;
            } else {
                node.setText("<html><body><div style=\"padding: 5px;\">" + this.getText() + "<br /></div></body></html>");
            }
            node.setFont(font);
            node.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);

            node.setPreferredSize(new Dimension(w, h));
            node.setAlignmentY(TOP_ALIGNMENT);
            node.setMargin(new Insets(5, 5, 5, 5));

            Border borderBlack = BorderFactory.createLineBorder(Color.BLACK, 2);
            frameIn.setBorder(borderBlack);

            frameIn.add(node);
            frame.add(frameIn);
            return frame;
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
