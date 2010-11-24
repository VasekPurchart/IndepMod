package cz.cvut.indepmod.uc.workspace.tabs.usecase;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by IntelliJ IDEA.
 * User: Viktor
 * Date: 23.11.2010
 * Time: 20:47:10
 * To change this template use File | Settings | File Templates.
 */
public class UCTreeNode extends DefaultMutableTreeNode {
    private boolean main = false;
    public UCTreeNode(String str) {
        super(str);       
    }
    public boolean getMain() {
        return main;
    }
    public void setMain(boolean main) {
        this.main = main;
    }
}
