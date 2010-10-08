package cz.cvut.indepmod.uc.workspace.cell;

import com.jgraph.components.labels.MultiLineVertexView;
import cz.cvut.promod.epc.workspace.cell.EPCGraphCellEditor;
import cz.cvut.promod.epc.workspace.cell.EPCVertexRenderer;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphCellEditor;
import org.jgraph.graph.VertexRenderer;


/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 22:41:01, 6.12.2009
 *
 * Special implementation of the MultiLineVertexView for the EPCNotation plugin.
 */
public class UCVertexView extends MultiLineVertexView {

    private static final VertexRenderer RENDERER = new EPCVertexRenderer();

    /**
     * EPCGraphCellEditor class overrides getCellEditorValue() method. For details
     * @see cz.cvut.promod.epc.workspace.cell.EPCGraphCellEditor
     */
    private static final GraphCellEditor epcCellEditor = new EPCGraphCellEditor();


    public UCVertexView(final Object cell){
        super(cell);
    }

    @Override
    public CellViewRenderer getRenderer() {
        return RENDERER;
    }

    @Override
    public GraphCellEditor getEditor() {
        return epcCellEditor;
    }
}
