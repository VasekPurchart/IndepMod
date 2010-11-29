package cz.cvut.indepmod.uc.workspace.tabs.usecase;

import cz.cvut.indepmod.uc.frames.toolChooser.ToolChooserModel;
import cz.cvut.indepmod.uc.workspace.UCWorkspace;
import cz.cvut.indepmod.uc.workspace.UCWorkspaceData;
import cz.cvut.indepmod.uc.workspace.tabs.UCTabParent;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagramChange;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;
import java.util.UUID;

public class UCUseCaseTab extends UCTabParent {
    private UUID uuid;

    public UCUseCaseTab(final Map<String, ProModAction> actions, UUID uuid) {
        super(null, actions);
        this.uuid = uuid;

        final JTree tree = new JTree(((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).getDiagramModel().getJTree(uuid));

        tree.putClientProperty("JTree.lineStyle", "None");

        tree.setCellRenderer(new UCTreeCellRenderer());
        tree.addKeyListener(new KeyListener() {

            public void keyTyped(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void keyPressed(KeyEvent e) {
                if (127 == e.getKeyCode()) {
                    DefaultMutableTreeNode node;
                    DefaultTreeModel model = (DefaultTreeModel) (tree.getModel());
                    TreePath[] paths = tree.getSelectionPaths();
                    for (int i = 0; i < paths.length; i++) {
                        node = (DefaultMutableTreeNode) (paths[i].getLastPathComponent());
                        model.removeNodeFromParent(node);
                    }
                }
            }

            public void keyReleased(KeyEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        final int oldToggleClickCount = tree.getToggleClickCount();
       // tree.setEditable(true);
        tree.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {

                final ToolChooserModel.Tool tool = ((ToolChooserModel.Tool) ((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).getSelectedToolModel().getValue());

                DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                MutableTreeNode newNode;

                switch (tool) {
                    case ADD_SCENARIO:
                        if (!(node instanceof UCScenarioNode)) {
                            newNode = new UCScenarioNode("Scenario");
                            model.insertNodeInto(newNode, node, node.getChildCount());
                        }
                        tree.expandRow(tree.getLeadSelectionRow());
                        ((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).getSelectedToolModel().setValue(ToolChooserModel.Tool.CONTROL);
                        break;
                    case ADD_STEP:
                        if (node instanceof UCScenarioNode) {
                            newNode = new UCStepNode("Step");
                            model.insertNodeInto(newNode, node, node.getChildCount());
                        }
                        tree.expandRow(tree.getLeadSelectionRow());
                        ((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).getSelectedToolModel().setValue(ToolChooserModel.Tool.CONTROL);
                        break;
                    case SELECT_MSS:
                        tree.setToggleClickCount(0);
                        if (!(tree.getLastSelectedPathComponent() instanceof UCScenarioNode)) {
                            break;
                        }
                        TreeNode root = (TreeNode) tree.getModel().getRoot();
                        for (int a = 0; a < root.getChildCount(); a++) {
                            if (root.getChildAt(a) instanceof UCScenarioNode) {
                                ((UCScenarioNode) root.getChildAt(a)).setMain(false);
                            }
                        }

                        if (tree.getLastSelectedPathComponent() instanceof UCScenarioNode) {
                            ((UCScenarioNode) tree.getLastSelectedPathComponent()).setMain(true);
                        }

                        tree.repaint();
                        //((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).getSelectedToolModel().setValue(ToolChooserModel.Tool.CONTROL);
                        break;
                    case CONTROL:
                        if (tree.getLastSelectedPathComponent() instanceof UCScenarioNode || tree.getLastSelectedPathComponent() instanceof UCStepNode) {
                            tree.setToggleClickCount(oldToggleClickCount);
                            tree.setEditable(true);
                        } else {
                            tree.setToggleClickCount(0);
                            tree.setEditable(false);
                        }
                        break;
                    default:
                        tree.setToggleClickCount(oldToggleClickCount);
                }
            }

            public void mousePressed(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void mouseReleased(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void mouseEntered(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void mouseExited(MouseEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        this.getViewport().add(tree);
    }

    public void changePerformed(final ProjectDiagramChange change) {
        if (ProjectDiagramChange.ChangeType.CHANGE_FLAG.equals(change.getChangeType())
                && change.getChangeValue() instanceof Boolean
                && Boolean.FALSE.equals(change.getChangeValue())) {

            //actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(false);

            return;
        }

        //actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(true);
    }

    @Override
    public void over() {
    }

    public void update() {
    }
}
