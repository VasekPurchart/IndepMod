package cz.cvut.indepmod.uc.workspace.cell.borders;

import javax.swing.border.AbstractBorder;
import java.awt.*;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 19:06:36, 7.12.2009
 *
 * The special borders for Application SW uc element.
 */
public class ApplicationSWBorder extends AbstractBorder {

    private final int FIRST_VERTICAL_LINE_OFFSET = 7;

    private final Insets INSETS = new Insets(1,9,1,9);

    private static final Stroke STROKE = new BasicStroke(
            1,
            BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_BEVEL,
            0,
            new float[] {3},
            0
    );

    private Color lineColor;

    
    public ApplicationSWBorder(final Color color) {
        lineColor = color;
    }

    /**
     * Paints border
     */
    @Override
    public void paintBorder(final Component c,
                            final Graphics g,
                            final int x,
                            final int y,
                            final int width,
                            final int height) {

        g.setColor(lineColor);

        g.drawRect(x, y, width-1, height-1);

        final Graphics2D g2d = (Graphics2D) g;

        g2d.setStroke(STROKE);

        final int x1 = x + FIRST_VERTICAL_LINE_OFFSET;
        final int x2 = width - (FIRST_VERTICAL_LINE_OFFSET);

        g2d.drawLine(x1, 0, x1, height);
        g2d.drawLine(x2, 0, x2, height);
    }

    /**
     * Getter - border insets
     */
    @Override
    public Insets getBorderInsets(final Component component) {
        return INSETS;
    }

    /**
     * Getter - line color
     * @return returns color of the line
     */
    public Color getLineColor() {
        return lineColor;
    }

    /**
     * Getter - border opaque
     * @return true if border is opaque
     */
    public boolean isBorderOpaque() {
        return false;
    }

}