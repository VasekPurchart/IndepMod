package cz.cvut.indepmod.uc.workspace.cell;

import cz.cvut.indepmod.uc.resources.Resources;
import cz.cvut.indepmod.uc.workspace.cell.UCPortRenderer;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.PortRenderer;
import org.jgraph.graph.PortView;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 * <p/>
 * Special implementation of the PortView for the UCNotation plugin.
 */
public class UCPortView extends PortView {

    private static PortRenderer RENDERER = null;

    private static ImageIcon portIcon = Resources.getIcon(Resources.PORTS + Resources.PORT_BLUE);


    public UCPortView(final Object cell) {
        super(cell);

        RENDERER = new UCPortRenderer(portIcon);
    }

    @Override
    public Rectangle2D getBounds() {
        if (portIcon != null) {
            final Point2D point = getLocation();
            final Rectangle2D bounds = new Rectangle2D.Double();
            int width = portIcon.getIconWidth();
            int height = portIcon.getIconHeight();
            double x = 0;
            double y = 0;

            if (point != null) {
                final Point2D pointClone = (Point2D) point.clone();
                x = pointClone.getX() - width / 2;
                y = pointClone.getY() - height / 2;
            }

            bounds.setFrame(x, y, width, height);

            return bounds;
        }

        return super.getBounds();
    }

    @Override
    public CellViewRenderer getRenderer() {
        return RENDERER;
    }
}
