package cz.cvut.indepmod.uc.modelFactory.diagramModel;

import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.model.DiagramModel;
import cz.cvut.promod.plugin.notationSpecificPlugIn.notation.model.DiagramModelChangeListener;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import org.jgraph.graph.GraphLayoutCache;
import org.jgraph.graph.GraphUndoManager;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 19:57:33, 5.12.2009
 *
 * Implementation of DiagramModel for the UCNotation plugin.
 */
public class UCDiagramModel implements DiagramModel {

    private final GraphLayoutCache graphLayoutCache;

    private final transient GraphUndoManager undoManager;

    private transient Action undoAction = null;
    private transient Action redoAction = null;

    /** Do not serialize set of listeners */
    private final transient Set<DiagramModelChangeListener> diagramModelChangeListeners;

    public UCDiagramModel(final GraphLayoutCache graphLayoutCache){
        this.graphLayoutCache = graphLayoutCache;

        diagramModelChangeListeners = new HashSet<DiagramModelChangeListener>();

        undoManager = new GraphUndoManager(){
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
                super.undoableEditHappened(e);

                if(undoAction != null){
                    undoAction.setEnabled(undoManager.canUndo());
                    publishChange(e.getEdit());
                }

                if(redoAction != null){
                    redoAction.setEnabled(undoManager.canRedo());
                    publishChange(e.getEdit());
                }
            }
        };

        undoManager.setLimit(10);

        installUndoManager();

    }

    public GraphLayoutCache getGraphLayoutCache() {
        return graphLayoutCache;
    }

    /**
     * Installs the actions to be used for enabling and disabling by the UndoManager.
     *
     * @param undoAction is the UC notation's undo action
     * @param redoAction is the UC notation's redo action
     */
    public void installUndoActions(final ProModAction undoAction, final ProModAction redoAction){
        this.undoAction = undoAction;
        this.redoAction = redoAction;
    }

    /**
     * Installs the UndoManager. 
     */
    public void installUndoManager(){
        graphLayoutCache.getModel().addUndoableEditListener(undoManager);
    }

    /**
     * Un-install UndoManager from listeners.
     */
    public void uninstallUndoManager(){
        graphLayoutCache.getModel().removeUndoableEditListener(undoManager);
    }

    /**
     * Un-install undo & redo actions.
     */
    public void uninstallUndoActions(){
        this.undoAction = null;
        this.redoAction = null;
    }

    /**
     * @return UC notation diagram's UndoManager
     */
    public GraphUndoManager getUndoManager() {
        return undoManager;
    }

    public void addChangeListener(final DiagramModelChangeListener listener) {
        diagramModelChangeListeners.add(listener);   
    }

    /**
     * Publishes changes in an instance of UCDiagramModel.
     *
     * @param change is some detail info about the change
     */
    public void publishChange(final Object change){
        for(final DiagramModelChangeListener listener : diagramModelChangeListeners){
            listener.changePerformed(change);
        }
    }

    /** {@inheritDoc} */
    public void update() {}

    /** {@inheritDoc} */
    public void over() {}
}
