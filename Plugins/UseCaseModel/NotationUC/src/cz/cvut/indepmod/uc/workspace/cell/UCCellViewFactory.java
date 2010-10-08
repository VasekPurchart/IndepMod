package cz.cvut.indepmod.uc.workspace.cell;

import cz.cvut.indepmod.uc.workspace.cell.UCPortView;
import cz.cvut.indepmod.uc.workspace.cell.UCVertexView;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.PortView;
import org.jgraph.graph.VertexView;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 11:25:37, 17.12.2009
 *
 * Special implementation of DefaultCellViewFactory for the EPCNotation plugin.
 */
public class UCCellViewFactory extends DefaultCellViewFactory{

    @Override
    protected VertexView createVertexView(final Object cell) {
        return new UCVertexView(cell);
    }

    @Override
    protected PortView createPortView(final Object cell) {
        return new UCPortView(cell);
    }

}
