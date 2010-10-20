package cz.cvut.indepmod.uc.workspace.cell;

import com.jgraph.components.labels.MultiLineVertexRenderer;
import org.apache.log4j.Logger;

import java.awt.*;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 21:52:01, 2.12.2009
 *
 * Special implementation of the MultiLineVertexRenderer for the EPCNotation plugin.
 *
 * It paints the special shapes of EPC items.
 */
public class UCVertexRenderer extends MultiLineVertexRenderer{

    private final static Logger LOG = Logger.getLogger(UCVertexRenderer.class);

    /**
     * In case of any change in constants in any future version of JGraph, this value could be re-considered,
     * if any errors occur.
     */

    public static final int SHAPE_UC_ACTOR = -1;

    @Override
    protected void paintBackground(Graphics g) {
        final Dimension d = getSize();
        final int b = borderWidth;
        final int width = d.width - b;
        final int height = d.height - b;

       if(shape == SHAPE_UC_ACTOR){
            final Color originColor = g.getColor();
            g.setColor(getForeground());

            g.drawLine(0, height, width / 2, height - height / 3);
            g.drawLine(width, height, width / 2, height - height / 3);
            g.drawLine(width / 2, height - height / 3, width / 2, height / 4);
            g.drawLine(0, height / 3, width, height / 3);            

            g.drawArc(width >> 2, 0 , width / 2, height / 4, 0, 360);
            
            g.setColor(originColor);
        } else {
            super.paintBackground(g);
        }
    }


    /**
     *  Calculates distance from horizontal axis.
     *
     * @param xFromLeft is a distance from left side of eclipse
     * @return the distance of  eclipse borders at the given horizontal position
     */
    private int getDistancefromHorizontalCenter(final int xFromLeft) {
        final double x = (getWidth() / 2.0) - xFromLeft;
        final double a = getWidth() / 2.0;
        final double b = getHeight() / 2.0;
        final double b_square = b*b;

        return new Double(Math.sqrt( b_square - ((b_square * x * x) / (a*a)))).intValue();
    }

    @Override
    protected void paintShapeBorder(Graphics g) {

            super.paintShapeBorder(g);
       
    }

}
