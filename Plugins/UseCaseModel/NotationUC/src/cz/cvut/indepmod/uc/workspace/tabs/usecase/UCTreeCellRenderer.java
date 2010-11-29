package cz.cvut.indepmod.uc.workspace.tabs.usecase;

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
                font = new Font("SansSerif", Font.BOLD, 20);
            }
            renderer.setPreferredSize(new Dimension(200, 25));
        }
        if (value instanceof UCStepNode) {
            renderer.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
            renderer.setPreferredSize(new Dimension(200, 40));
            
        }
        if (value.equals(tree.getModel().getRoot())) {
            font = new Font("SansSerif", Font.BOLD, 30);
            renderer.setPreferredSize(new Dimension(300, 40));
        }

        renderer.setFont(font);
        return renderer;
    }
}
