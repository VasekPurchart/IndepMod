package cz.cvut.indepmod.uc.workspace.icons;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by IntelliJ IDEA.
 * User: Viktor
 * Date: 2.11.2010
 * Time: 11:35:14
 * To change this template use File | Settings | File Templates.
 */
public class CloseTabIcon implements Icon{
    private static final Logger LOG = Logger.getLogger(CloseTabIcon.class);

    private int x_pos;
    private int y_pos;
    private int width;
    private int height;
    private Icon fileIcon;

    /**
     * Constructor of close tab icon
     */
    public CloseTabIcon() {
        width = 16;
        height = 16;
    }

    /**
     * Paints icon
     * @param c given component
     * @param g given graphics of the icon
     * @param x X position
     * @param y Y position
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.x_pos = x;
        this.y_pos = y;

        Color col = g.getColor();

        g.setColor(Color.black);
        int y_p = y + 2;
        g.drawLine(x + 1, y_p, x + 12, y_p);
        g.drawLine(x + 1, y_p + 13, x + 12, y_p + 13);
        g.drawLine(x, y_p + 1, x, y_p + 12);
        g.drawLine(x + 13, y_p + 1, x + 13, y_p + 12);
        g.drawLine(x + 3, y_p + 3, x + 10, y_p + 10);
        g.drawLine(x + 3, y_p + 4, x + 9, y_p + 10);
        g.drawLine(x + 4, y_p + 3, x + 10, y_p + 9);
        g.drawLine(x + 10, y_p + 3, x + 3, y_p + 10);
        g.drawLine(x + 10, y_p + 4, x + 4, y_p + 10);
        g.drawLine(x + 9, y_p + 3, x + 3, y_p + 9);
        g.setColor(col);
        if (fileIcon != null) {
            fileIcon.paintIcon(c, g, x + width, y_p);
        }
    }

    /**
     * Getter - icon width
     */
    public int getIconWidth() {
        return width + (fileIcon != null ? fileIcon.getIconWidth() : 0);
    }

    /**
     * Getter - icon height
     */
    public int getIconHeight() {
        return height;
    }

    /**
     * Getter - icon bounds
     */
    public Rectangle getBounds() {
        return new Rectangle(x_pos, y_pos, width, height);
    }
}