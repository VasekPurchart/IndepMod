package cz.cvut.indepmod.uc.workspace.tabs;

import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;

import javax.swing.*;
import java.util.Map;

public class UCUseCaseTab extends JScrollPane {
    private static final Logger LOG = Logger.getLogger(UCGraph.class);
    private static final int GET_PORTVIEW_TOLERANCE = 2;
    final Map<String, ProModAction> actions;
    private final JGraph graph;

    public UCUseCaseTab(final JGraph graph, final Map<String, ProModAction> actions) {
        super(graph);
        this.graph = graph;
        this.actions = actions;
    }

}
