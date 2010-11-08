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
public class SystemBorderModelTest {
    protected UUID uuid;
    protected SystemBorderModel border;
    protected String defaultName;

    @Before
    public void setUp() throws Exception {
        uuid = UUID.randomUUID();

        border = new SystemBorderModel(uuid);
        border.setNote("testNote");

        defaultName = Resources.getResources().getString("uc.vertex.border");
    }

    @After
    public void tearDown() throws Exception {
    }

    ////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////

    @Test
    public void testContruct() throws Exception {
        Assert.assertEquals(uuid, border.getUuid());
        Assert.assertEquals(defaultName, border.getName());
        Assert.assertEquals("testNote", border.getNote());
    }

    @Test
    public void testConstructFromSystemBorderModel() {
        SystemBorderModel b = new SystemBorderModel(border, "testName");
        Assert.assertEquals(uuid, b.getUuid());
        Assert.assertEquals("testName", b.getName());
        Assert.assertEquals("testNote", b.getNote());
    }

    @Test
    public void testToString() throws Exception {
        Assert.assertEquals(defaultName, border.toString());
    }

    @Test
    public void testGetUuid() throws Exception {
        Assert.assertEquals(uuid.getClass(), border.getUuid().getClass());
        Assert.assertEquals(uuid, border.getUuid());
    }

    @Test
    public void testGetName() throws Exception {
        Assert.assertEquals(String.class, border.getName().getClass());
        Assert.assertEquals(defaultName, border.getName());
    }

    @Test
    public void testSetName() throws Exception {
        border.setName("newName");
        Assert.assertEquals("newName", border.getName());
    }

    @Test
    public void testGetNote() throws Exception {
        Assert.assertEquals(String.class, border.getNote().getClass());
        Assert.assertEquals("testNote", border.getNote());
    }

    @Test
    public void testSetNote() throws Exception {
        border.setName("newNote");
        Assert.assertEquals("newNote", border.getName());
    }

    @Test
    public void testInstallAttributes() throws Exception {
        Assert.fail("Not tested");
    }
}
