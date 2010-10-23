package cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels;

import cz.cvut.indepmod.uc.resources.Resources;
import org.junit.*;

import java.util.Map;
import java.util.UUID;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D;

/**
 * Created by IntelliJ IDEA.
 * User: Vasek
 * Date: 23.10.2010
 * Time: 14:41:52
 * To change this template use File | Settings | File Templates.
 */
public class ActorModelTest {
    protected UUID uuid;
    protected ActorModel actor;
    protected String defaultName;

    @Before
    public void setUp() throws Exception {
        uuid = UUID.randomUUID();

        actor = new ActorModel(uuid);
        actor.setNote("testNote");

        defaultName = Resources.getResources().getString("uc.vertex.actor");
    }

    @After
    public void tearDown() throws Exception {
    }

    ////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

    @Test
    public void testContruct() throws Exception {
        Assert.assertEquals(uuid, actor.getUuid());
        Assert.assertEquals(defaultName, actor.getName());
        Assert.assertEquals("testNote", actor.getNote());
    }

    @Test
    public void testConstructFromActorModel() {
        ActorModel a = new ActorModel(actor, "testName");
        Assert.assertEquals(uuid, a.getUuid());
        Assert.assertEquals("testName", a.getName());
        Assert.assertEquals("testNote", a.getNote());
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals(defaultName, actor.toString());
    }

    @Test
    public void testGetUuid() throws Exception {
        Assert.assertEquals(uuid.getClass(), actor.getUuid().getClass());
        Assert.assertEquals(uuid, actor.getUuid());
    }

    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals(String.class, actor.getName().getClass());
        Assert.assertEquals(defaultName, actor.getName());
    }

    @Test
    public void testSetName() throws Exception {
        actor.setName("newName");
        Assert.assertEquals("newName", actor.getName());
    }

    @Test
    public void testGetNote() throws Exception {
        Assert.assertEquals(String.class, actor.getNote().getClass());
        Assert.assertEquals("testNote", actor.getNote());
    }

    @Test
    public void testSetNote() throws Exception {
        actor.setName("newNote");
        Assert.assertEquals("newNote", actor.getName());
    }

    @Test
    public void testInstallAttributes() throws Exception {
        Assert.fail("Not tested");
    }
}
