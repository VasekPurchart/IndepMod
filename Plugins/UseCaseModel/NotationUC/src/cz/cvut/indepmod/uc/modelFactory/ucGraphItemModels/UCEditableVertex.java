package cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels;

/**
 * User: Viktor Bohuslav Bohdal, bohdavik@fel.cvut.cz
 * Date: 6.10.2010
 * Time: 13:09:49
 */
public abstract class UCEditableVertex  extends UCNoteItem implements UCIdentifiableVertex{

    public static final String PROPERTY_NAME = "name";
    protected String name;

    /**
     * Getter - name
     * @return
     */
    public String getName(){
        return name;
    }

    /**
     * Setter - name
     * @param name
     */
    public void setName(final String name){
        this.name = name;
    }
}
