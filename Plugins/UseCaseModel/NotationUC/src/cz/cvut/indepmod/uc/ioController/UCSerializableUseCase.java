package cz.cvut.indepmod.uc.ioController;

import javax.swing.tree.DefaultTreeModel;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Viktor
 * Date: 6.12.2010
 * Time: 0:25:39
 * To change this template use File | Settings | File Templates.
 */
public class UCSerializableUseCase implements Serializable {
    private DefaultTreeModel root;
    private UUID uuid;

    public UCSerializableUseCase() {
        
    }

    public UCSerializableUseCase(DefaultTreeModel treeModel, UUID uuid) {
        this.root = treeModel;
        this.uuid = uuid;
    }

    public DefaultTreeModel getRoot() {
        return root;
    }

    public void setRoot(DefaultTreeModel root) {
        this.root = root;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
