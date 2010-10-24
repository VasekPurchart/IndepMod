package cz.cvut.indepmod.uc.workspace.cell;

import com.jgraph.components.labels.MultiLineVertexView;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.VertexRenderer;


/**
 * UseCase plugin - SI2/3 school project
 * Alena Varkockova
 * User: Viktor Bohuslav Bohdal
 * Date: 22:41:01, 6.12.2009
 *
 * Special implementation of the MultiLineVertexView for the UCNotation plugin.
 */
public class UCVertexView extends MultiLineVertexView {

    private static final VertexRenderer RENDERER = new UCVertexRenderer();

    /**
     * UCGraphCellEditor class overrides getCellEditorValue() method. For details
     * @see cz.cvut.indepmod.uc.workspace.cell.UCGraphCellEditor
     */
    private static final GraphCellEditor ucCellEditor = new UCGraphCellEditor();


    public UCVertexView(final Object cell){
        super(cell);
    }

    @Override
    public CellViewRenderer getRenderer() {
        return RENDERER;
    }

    @Override
    public GraphCellEditor getEditor() {
        return ucCellEditor;
    }
}
