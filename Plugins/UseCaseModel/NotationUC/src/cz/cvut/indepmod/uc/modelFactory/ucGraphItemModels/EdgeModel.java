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
        ORGANIZATION_FLOW,
        INFORMATION_FLOW,
        INFORMATION_SERVICES_FLOW,
        MATERIAL_FLOW
    }

    private final EdgeType edgeType;

    public EdgeModel(final EdgeType edgeType){
        this.edgeType = edgeType;
    }

    public EdgeModel(final EdgeModel edgeModel, final String newName){
        this.edgeType = edgeModel.getEdgeType();
        setName(newName);
        setNote(edgeModel.getNote());
    }

    public EdgeType getEdgeType() {
        return edgeType;
    }

    @Override
    public String toString() {
        return name;
    }

    public UUID getUuid() {
        return null; // edges do not have uuid
    }
}
