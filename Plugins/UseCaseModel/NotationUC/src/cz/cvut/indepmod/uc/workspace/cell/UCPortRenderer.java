package cz.cvut.indepmod.uc.workspace.cell;

import org.jgraph.graph.PortRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 * <p/>
 * Special implementation of the UCPortRenderer for the UCNotation plugin.
 */
public class UCPortRenderer extends PortRenderer {

    private ImageIcon portIcon = null;

    public UCPortRenderer(final ImageIcon portIcon) {
        this.portIcon = portIcon;
    }

    public void paint(Graphics g) {
        if (portIcon != null) {
            portIcon.paintIcon(this, g, 0, 0);

        } else {
            // if the icon is not available, then use default appearance
            super.paint(g);
        }
    }

}
