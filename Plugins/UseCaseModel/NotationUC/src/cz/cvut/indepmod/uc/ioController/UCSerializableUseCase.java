package cz.cvut.indepmod.uc.ioController;

import javax.swing.tree.DefaultTreeModel;
import java.io.Serializable;
import java.util.UUID;

/**
 * Serializable UseCase
 * User: Viktor Bohuslav Bohdal
 * Date: 6.12.2010
 * Time: 0:25:39
 */
public class UCSerializableUseCase implements Serializable {
    private DefaultTreeModel root;
    private UUID uuid;

    /**
     * Constructor of UC Serializable UseCase
     */
    public UCSerializableUseCase() {
        
    }

    /**
     * Constructor of UC Serializable UseCase - uses Tree Model and UUID
     * @param treeModel
     * @param uuid
     */
    public UCSerializableUseCase(DefaultTreeModel treeModel, UUID uuid) {
        this.root = treeModel;
        this.uuid = uuid;
    }

    /**
     * Getter - root
     * @return returns root of the tree
     */
    public DefaultTreeModel getRoot() {
        return root;
    }

    /**
     * Setter - root
     * @param root sets root
     */
    public void setRoot(DefaultTreeModel root) {
        this.root = root;
    }

    /**
     * Getter UUID
     * @return
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Setter UUID
     * @param uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
