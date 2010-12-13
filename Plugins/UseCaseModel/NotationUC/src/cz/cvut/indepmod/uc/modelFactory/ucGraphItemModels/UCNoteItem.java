package cz.cvut.indepmod.uc.modelFactory.ucGraphItemModels;

/**
 * Created by IntelliJ IDEA.
 * User: Viktor Bohuslav Bohdal, bohdavik@fel.cvut.cz
 * Date: 6.10.2010
 * Time: 13:00:24
 *
 * Base class for all items.
 */
public abstract class UCNoteItem {

    public static final String NOTE_PROPERTY = "note";
    private String note;

    /**
     * Getter - note
     * @return
     */
    public String getNote() {
        return note;
    }

    /**
     * Setter - note
     * @param note
     */
    public void setNote(final String note) {
        this.note = note;
    }
}
