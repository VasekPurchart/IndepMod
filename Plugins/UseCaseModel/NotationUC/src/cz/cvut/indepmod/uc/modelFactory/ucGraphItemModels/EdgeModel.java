package cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels;

import java.util.UUID;

/**
 * UseCase plugin - SI2/3 school project
 * User: Lukáš Beran
 * Date: 23:34:37, 20.10.2010
 *
 * Represents the general edge element of the UCNotation plugin.
 */
public class EdgeModel extends UCEditableVertex {

    public static enum EdgeType {
        CONTROL_FLOW,
        INCLUDE_FLOW
    }

    private final EdgeType edgeType;

    /**
     * Constructor - edge model
     * @param edgeType
     */
    public EdgeModel(final EdgeType edgeType){
        this.edgeType = edgeType;
    }

    /**
     * Constructor - edge model
     * @param edgeModel
     * @param newName
     */
    public EdgeModel(final EdgeModel edgeModel, String newName){
        this.edgeType = edgeModel.getEdgeType();

        setName(newName);
        setNote(edgeModel.getNote());
    }

    /**
     * Getter - edge type
     * @return returns edge type
     */
    public EdgeType getEdgeType() {
        return edgeType;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Getter - uuid
     * @return
     */
    public UUID getUuid() {
        return null; // edges do not have uuid
    }
}
