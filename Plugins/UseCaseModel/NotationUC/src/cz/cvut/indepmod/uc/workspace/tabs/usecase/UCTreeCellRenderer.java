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
        Font font = new Font("SansSerif", Font.PLAIN, 20);
        if (value instanceof UCTreeNode) {
            if (((UCTreeNode) value).getMain()) {
                font = new Font("SansSerif", Font.BOLD, 20);
            }
        }

        renderer.setFont(font);
        return renderer;
    }
}
