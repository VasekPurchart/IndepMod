package cz.cvut.indepmod.uc.workspace.tabs.usecase;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Viktor
 * Date: 24.11.2010
 * Time: 11:57:06
 * To change this template use File | Settings | File Templates.
 */
public class UCStepNode extends DefaultMutableTreeNode {
    private UUID include;
    
    public UCStepNode(String str) {
        super(str);        
    }

    public void setInclude(UUID uuid) {
        this.include = uuid;
    }
    public UUID getInclude() {
        return this.include;
    }
}
