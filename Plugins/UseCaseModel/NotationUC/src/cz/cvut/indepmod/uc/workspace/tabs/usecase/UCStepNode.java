package cz.cvut.indepmod.uc.workspace.tabs.usecase;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Created by IntelliJ IDEA.
 * User: Viktor
 * Date: 24.11.2010
 * Time: 11:57:06
 * To change this template use File | Settings | File Templates.
 */
public class UCStepNode extends DefaultMutableTreeNode {
    
    public UCStepNode(String str) {
        super("<html><div style=\"padding: 5px; border: 2px solid #000000; margin: 10px 0 10px 0;\"><a href=\"nejakyodkaz.html\">Include</a><br /><strong>" + str + "</div>");
        
    }
}
