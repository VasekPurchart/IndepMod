package cz.cvut.indepmod.uc.workspace.tabs;

import cz.cvut.indepmod.uc.modelFactory.diagramModel.UCDiagramModel;
import cz.cvut.indepmod.uc.workspace.UCWorkspace;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagram;
import org.apache.log4j.Logger;
import org.jgraph.JGraph;

import javax.swing.*;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Viktor
 * Date: 2.11.2010
 * Time: 17:02:30
 * To change this template use File | Settings | File Templates.
 */
public class UCDefaultTab extends JScrollPane {
    private static final Logger LOG = Logger.getLogger(UCWorkspace.class);
    private final JGraph graph;
    final Map<String, ProModAction> actions;
    private UCDiagramModel actualUCDiagramModel = null;
    private ProjectDiagram actualProjectDiagram = null;

    public UCDefaultTab(final JGraph graph, final Map<String, ProModAction> actions) {
        super(graph);
        this.graph = graph;
        this.actions = actions;
    }

}