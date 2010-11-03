package cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels;

import cz.cvut.indepmod.uc.resources.Resources;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: Viktor
 * Date: 2.11.2010
 * Time: 15:00:23
 * To change this template use File | Settings | File Templates.
 */
public class ScenarioModel extends UCEditableVertex {
    private static final String DEFAULT_LABEL = "SCENARIO";// Resources.getResources().getString("uc.vertex.actor");
    private final UUID uuid;

    public ScenarioModel(UUID uuid) {
        this.uuid = uuid;
        this.setName(DEFAULT_LABEL);
    }

    public UUID getUuid() {
        return this.uuid;
    }
}
