package cz.cvut.indepmod.uc.workspace.cell;

import org.jgraph.graph.PortRenderer;

import javax.swing.*;
import java.awt.*;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 19:51:23, 7.12.2009
 *
 * Special implementation of the UCPortRenderer for the UCNotation plugin.
 */
public class UCPortRenderer extends PortRenderer {

    private ImageIcon portIcon = null;

    public UCPortRenderer(final ImageIcon portIcon){
        this.portIcon = portIcon;
    }

    public void paint(Graphics g) {
        if(portIcon != null){
            portIcon.paintIcon(this, g, 0, 0);

        } else {
            // if the icon is not available, then use default appearance
            super.paint(g);
        }
    }
    
}
