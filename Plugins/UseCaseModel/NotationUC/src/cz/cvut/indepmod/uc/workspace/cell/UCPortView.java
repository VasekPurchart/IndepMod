package cz.cvut.indepmod.uc.workspace.cell;

import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.SystemBorderModel;
import cz.cvut.indepmod.uc.resources.Resources;
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
 *
 * Special implementation of the PortView for the UCNotation plugin.
 */
public class UCPortView extends PortView {

    private static PortRenderer RENDERER = null;

    private static ImageIcon portIcon = Resources.getIcon(Resources.PORTS + Resources.PORT_BLUE);
    private boolean isSystemBorder;

    public UCPortView(final Object cell){
        super(cell);

        org.jgraph.graph.DefaultPort model = (org.jgraph.graph.DefaultPort) cell;
        org.jgraph.graph.DefaultGraphCell graphCell = (org.jgraph.graph.DefaultGraphCell) model.getParent();
        if(graphCell.getUserObject() instanceof SystemBorderModel)
            isSystemBorder = true;
        else
            isSystemBorder = false;

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

            if(point != null){
                final Point2D pointClone = (Point2D) point.clone();
                x = pointClone.getX() - width / 2;
                y = pointClone.getY() - height / 2;
            }


            if(isSystemBorder)
            {
                 // out of universe
                  bounds.setFrame(-100, -100, 1, 1);
            } else {
                  bounds.setFrame(x, y, width, height);
            }

            return bounds;
		}
        
		return super.getBounds();
	}

    @Override
    public CellViewRenderer getRenderer() {
        return RENDERER;
    }
}
