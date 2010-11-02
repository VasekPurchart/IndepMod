package cz.cvut.indepmod.uc.workspace.cell;

import cz.cvut.indepmod.uc.workspace.cell.UCPortView;
import cz.cvut.indepmod.uc.workspace.cell.UCVertexView;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.PortView;
import org.jgraph.graph.VertexView;

/**
 * UseCase plugin - SI2/3 school project
 * User: Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 * <p/>
 * Special implementation of DefaultCellViewFactory for the UCNotation plugin.
 */
public class UCCellViewFactory extends DefaultCellViewFactory {

    @Override
    protected VertexView createVertexView(final Object cell) {
        return new UCVertexView(cell);
    }

    @Override
    protected PortView createPortView(final Object cell) {
        return new UCPortView(cell);
    }

}
