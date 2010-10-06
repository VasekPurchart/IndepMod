package cz.cvut.indepmod.uc.modelFactory.ucItemModels;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Viktor Bohuslav Bohdal, bohdavik@fel.cvut.cz
 * Date: 6.10.2010
 * Time: 13:10:22
 */
public interface UCIdentifiableVertex {

    public static final String PROPERTY_UUID = "uuid";

    /**
     * All objects of classes implementing this interface are identifiable.
     *
     * @return uuid
     */
    public UUID getUuid();
}
