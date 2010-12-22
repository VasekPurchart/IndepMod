package cz.cvut.indepmod.uc.workspace.tabs.usecase;

import com.sun.java.swing.plaf.windows.WindowsTreeUI;
import cz.cvut.indepmod.uc.UCNotationModel;
import cz.cvut.indepmod.uc.frames.toolChooser.ToolChooserModel;
import cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels.UseCaseModel;
import cz.cvut.indepmod.uc.workspace.UCWorkspace;
import cz.cvut.indepmod.uc.workspace.UCWorkspaceData;
import cz.cvut.indepmod.uc.workspace.tabs.UCTabParent;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.projectService.treeProjectNode.ProjectDiagramChange;
import org.jgraph.graph.DefaultGraphCell;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import java.util.UUID;

public class UCUseCaseTab extends UCTabParent {
    private UUID uuid;
    private JTree tree;
    final private UseCaseModel useCase;
    final Map<String, ProModAction> actions;

    private JPopupMenu popupMenuStep = new JPopupMenu();
    private JPopupMenu popupMenuScenario = new JPopupMenu();
    private JPopupMenu popupMenuStepIncluded = new JPopupMenu();

    public UCUseCaseTab(final Map<String, ProModAction> actions, final UUID uuid, String name, UseCaseModel ucModel) {
        super(null, actions);
        this.uuid = uuid;
        this.actions = actions;
        UIManager.put("Tree.expandedIcon", new WindowsTreeUI.ExpandedIcon());
        UIManager.put("Tree.collapsedIcon", new WindowsTreeUI.CollapsedIcon());

        useCase = ucModel;
        if (useCase.getModel().getRoot() == null) {
            useCase.getModel().setRoot(new DefaultMutableTreeNode(name));
        }

        tree = new JTree(useCase.getModel());
        this.initPopupMenus();
        tree.setToggleClickCount(1);

        tree.putClientProperty("JTree.lineStyle", "None");

        tree.setEditable(true);
        tree.setCellRenderer(new UCTreeCellRenderer());


        ((DefaultTreeModel) tree.getModel()).addTreeModelListener(new TreeModelListener() {
            public void treeNodesChanged(TreeModelEvent e) {
                if (e.getSource() instanceof DefaultTreeModel) {
                    DefaultTreeModel model = (DefaultTreeModel) e.getSource();
                    if (model.getRoot().equals(tree.getLastSelectedPathComponent())) {
                        if (getUseCaseByUUID(uuid) != null) {
                            if (((DefaultMutableTreeNode) model.getRoot()).getUserObject() instanceof String) {
                                getUseCaseByUUID(uuid).setName((String) ((DefaultMutableTreeNode) model.getRoot()).getUserObject());
                            }
                        }
                    }
                }
            }

            public void treeNodesInserted(TreeModelEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void treeNodesRemoved(TreeModelEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void treeStructureChanged(TreeModelEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

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
        tree.setToggleClickCount(0);
        tree.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    if (e.getSource() instanceof JTree) {
                        if (tree.getLastSelectedPathComponent() instanceof UCStepNode) {
                            showEditDialog((DefaultMutableTreeNode) tree.getLastSelectedPathComponent());
                        } else {
                            tree.startEditingAtPath(tree.getSelectionPath());
                        }
                        e.consume();
                        return;
                    }
                }
                if (e.isMetaDown()) {
                    TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                    if (path == null) return;
                    tree.setSelectionPath(path);
                    if (tree.getLastSelectedPathComponent() instanceof UCStepNode) {
                        if (((UCStepNode) tree.getLastSelectedPathComponent()).getInclude() != null) {
                            popupMenuStepIncluded.show(tree, e.getX(), e.getY());
                        } else {
                            popupMenuStep.show(tree, e.getX(), e.getY());
                        }
                    }
                    if (tree.getLastSelectedPathComponent() instanceof UCScenarioNode) {
                        popupMenuScenario.show(tree, e.getX(), e.getY());
                    }

                } else {

                    final ToolChooserModel.Tool tool = ((ToolChooserModel.Tool) ((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).getSelectedToolModel().getValue());

                    DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    DefaultMutableTreeNode newNode;

                    switch (tool) {
                        case ADD_SCENARIO:
                            if (!(node instanceof UCScenarioNode)) {
                                newNode = new UCScenarioNode("Scenario");
                                model.insertNodeInto(newNode, node, node.getChildCount());
                                ((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).getSelectedToolModel().setValue(ToolChooserModel.Tool.CONTROL);
                            }
                            tree.expandRow(tree.getLeadSelectionRow());
                            break;
                        case ADD_STEP:
                            if (node instanceof UCScenarioNode) {
                                newNode = new UCStepNode("Step");
                                newNode.setAllowsChildren(false);
                                model.insertNodeInto(newNode, node, node.getChildCount());
                                ((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).getSelectedToolModel().setValue(ToolChooserModel.Tool.CONTROL);
                            }
                            tree.expandRow(tree.getLeadSelectionRow());
                            break;
                        case CONTROL:
                            if (tree.getLastSelectedPathComponent() instanceof UCScenarioNode) {
                                tree.setToggleClickCount(oldToggleClickCount);
                                tree.setEditable(true);
                            } else if (tree.getLastSelectedPathComponent() instanceof UCStepNode) {
                                tree.setEditable(false);
                            } else if (model.getRoot().equals(tree.getLastSelectedPathComponent())) {
                                tree.setEditable(true);
                                tree.setToggleClickCount(0);
                            } else {
                                tree.setToggleClickCount(0);
                                tree.setEditable(false);
                            }
                            break;
                        default:

                    }
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

    private JMenuItem makeDeleteNodePopupItem() {
        JMenuItem item = new JMenuItem("Delete");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tree.getLastSelectedPathComponent() instanceof MutableTreeNode) {
                    ((DefaultTreeModel) tree.getModel()).removeNodeFromParent((MutableTreeNode) tree.getLastSelectedPathComponent());
                }
            }
        });
        return item;
    }

    private JMenuItem makeIncludeUCPopupItem() {
        JMenuItem item = new JMenuItem("Include UC");
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tree.getLastSelectedPathComponent() instanceof UCStepNode) {
                    final UCStepNode node = (UCStepNode) tree.getLastSelectedPathComponent();
                    final JDialog dialog = new JDialog((JFrame) null, "Select included UC");
                    dialog.setLocationRelativeTo(tree);
                    dialog.setSize(new Dimension(400, 200));

                    JPanel frame = new JPanel();
                    JLabel label = new JLabel("Select Use Case:");

                    final JComboBox combo = new JComboBox();
                    combo.addItem("<< none >>");

                    Object[] objects = ((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).getDiagramModel().getGraphLayoutCache().getCells(false, true, false, false);
                    for (Object obj : objects) {
                        if (obj instanceof DefaultGraphCell) {
                            if (((DefaultGraphCell) obj).getUserObject() instanceof UseCaseModel) {
                                UseCaseModel tmpUC = (UseCaseModel) ((DefaultGraphCell) obj).getUserObject();
                                if (getUuid().equals(tmpUC.getUuid())) {
                                    continue;
                                }
                                combo.addItem(tmpUC);
                                if (node.getInclude() != null && tmpUC.getUuid().compareTo(node.getInclude()) == 0) {
                                    combo.setSelectedItem(tmpUC);
                                }
                            }
                        }
                    }

                    JButton include = new JButton("Include");
                    include.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            UCStepNode ucNode = (UCStepNode) tree.getLastSelectedPathComponent();
                            if (combo.getSelectedItem() instanceof UseCaseModel) {
                                ucNode.setInclude(((UseCaseModel) combo.getSelectedItem()).getUuid());
                            } else {
                                ucNode.setInclude(null);
                            }
                            dialog.setVisible(false);
                            dialog.dispose();
                            useCase.getModel().reload((TreeNode) tree.getLastSelectedPathComponent());    
                        }
                    });

                    frame.add(label);
                    frame.add(combo);
                    frame.add(include);

                    dialog.add(frame);
                    dialog.setVisible(true);
                }
            }
        });
        return item;
    }

    private UseCaseModel getUseCaseByUUID(UUID uuid) {
        Object[] objects = ((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).getDiagramModel().getGraphLayoutCache().getCells(false, true, false, false);
        for (Object obj : objects) {
            if (obj instanceof DefaultGraphCell) {
                if (((DefaultGraphCell) obj).getUserObject() instanceof UseCaseModel) {
                    UseCaseModel tmpUC = (UseCaseModel) ((DefaultGraphCell) obj).getUserObject();
                    if (uuid.equals(tmpUC.getUuid())) return tmpUC;
                }
            }
        }
        return null;
    }

    private JMenuItem makeShowIncludedUCPopupItem() {
        JMenuItem showIncluded = new JMenuItem("Show Included UseCase");
        showIncluded.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (tree.getLastSelectedPathComponent() instanceof UCStepNode) {
                    final UCStepNode node = (UCStepNode) tree.getLastSelectedPathComponent();
                    if (node.getInclude() != null && getUseCaseByUUID(node.getInclude()) != null) {
                        ((UCWorkspace) UCWorkspaceData.getWorkspaceComponentSingletonStatic()).openTab(node.getInclude(), getUseCaseByUUID(node.getInclude()).getName(), getUseCaseByUUID(node.getInclude()));
                    }
                }
            }
        });
        return showIncluded;
    }

    private JMenuItem makeSelectMSSPopupItem() {
        JMenuItem selectMSS = new JMenuItem("Select MSS");
        selectMSS.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tree.getLastSelectedPathComponent() instanceof UCScenarioNode) {
                    TreeNode root = (TreeNode) tree.getModel().getRoot();
                    for (int a = 0; a < root.getChildCount(); a++) {
                        if (root.getChildAt(a) instanceof UCScenarioNode) {
                            ((UCScenarioNode) root.getChildAt(a)).setMain(false);
                        }
                    }

                    if (tree.getLastSelectedPathComponent() instanceof UCScenarioNode) {
                        ((UCScenarioNode) tree.getLastSelectedPathComponent()).setMain(true);
                    }

                    useCase.getModel().reload((TreeNode) tree.getLastSelectedPathComponent());
                    tree.repaint();
                }
            }
        });
        return selectMSS;
    }

    private void initPopupMenus() {
        popupMenuScenario.add(this.makeSelectMSSPopupItem());
        popupMenuScenario.add(this.makeDeleteNodePopupItem());

        popupMenuStep.add(this.makeIncludeUCPopupItem());
        popupMenuStep.add(this.makeDeleteNodePopupItem());

        popupMenuStepIncluded.add(this.makeShowIncludedUCPopupItem());
        popupMenuStepIncluded.add(this.makeIncludeUCPopupItem());
        popupMenuStepIncluded.add(this.makeDeleteNodePopupItem());

        this.tree.add(popupMenuStep);
        this.tree.add(popupMenuStepIncluded);
        this.tree.add(popupMenuScenario);
    }

    public void changePerformed(final ProjectDiagramChange change) {
        if (ProjectDiagramChange.ChangeType.CHANGE_FLAG.equals(change.getChangeType())
                && change.getChangeValue() instanceof Boolean
                && Boolean.FALSE.equals(change.getChangeValue())) {

            actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(false);

            return;
        }

        actions.get(UCNotationModel.SAVE_ACTION_KEY).setEnabled(true);
    }

    @Override
    public void over() {
    }

    public void update() {
    }

    public JTree getTree() {
        return this.tree;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void showEditDialog(final DefaultMutableTreeNode node) {

        JPanel frame = new JPanel();
        final JDialog dialog = new JDialog((JFrame) null, "Edit step");
        dialog.setLocationRelativeTo(tree);
        dialog.setSize(new Dimension(520, 550));
        dialog.setModal(true);
        JLabel label = new JLabel("Step:");

        final JEditorPane editor = new JEditorPane();
        editor.setSize(new Dimension(500, 400));
        editor.setPreferredSize(new Dimension(500, 400));
        editor.setText((String) node.getUserObject());

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                node.setUserObject(editor.getText());
                dialog.setVisible(false);
                dialog.dispose();
            }
        });

        JScrollPane editorScrollPane = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        frame.add(label);
        frame.add(editorScrollPane);
        frame.add(save);

        dialog.add(frame);
        dialog.setVisible(true);
    }
}
