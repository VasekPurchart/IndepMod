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
public class UseCaseModelTest {
    protected UUID uuid;
    protected UseCaseModel uc;
    protected String defaultName;

    @Before
    public void setUp() throws Exception {
        uuid = UUID.randomUUID();

        uc = new UseCaseModel(uuid);
        uc.setNote("testNote");

        defaultName = Resources.getResources().getString("uc.vertex.uc");
    }

    @After
    public void tearDown() throws Exception {
    }

    ////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

    @Test
    public void testContruct() throws Exception {
        Assert.assertEquals(uuid, uc.getUuid());
        Assert.assertEquals(defaultName, uc.getName());
        Assert.assertEquals("testNote", uc.getNote());
    }

    @Test
    public void testConstructFromSystemBorderModel() {
        UseCaseModel u = new UseCaseModel(uc, "testName");
        Assert.assertEquals(uuid, u.getUuid());
        Assert.assertEquals("testName", u.getName());
        Assert.assertEquals("testNote", u.getNote());
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals(defaultName, uc.toString());
    }

    @Test
    public void testGetUuid() throws Exception {
        Assert.assertEquals(uuid.getClass(), uc.getUuid().getClass());
        Assert.assertEquals(uuid, uc.getUuid());
    }

    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals(String.class, uc.getName().getClass());
        Assert.assertEquals(defaultName, uc.getName());
    }

    @Test
    public void testSetName() throws Exception {
        uc.setName("newName");
        Assert.assertEquals("newName", uc.getName());
    }

    @Test
    public void testGetNote() throws Exception {
        Assert.assertEquals(String.class, uc.getNote().getClass());
        Assert.assertEquals("testNote", uc.getNote());
    }

    @Test
    public void testSetNote() throws Exception {
        uc.setName("newNote");
        Assert.assertEquals("newNote", uc.getName());
    }

}
