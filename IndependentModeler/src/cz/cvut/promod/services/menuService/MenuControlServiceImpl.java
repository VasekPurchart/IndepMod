package cz.cvut.promod.services.menuService;

import cz.cvut.promod.gui.ModelerModel;
import cz.cvut.promod.services.ModelerSession;
import cz.cvut.promod.services.actionService.actionUtils.ProModAction;
import cz.cvut.promod.services.menuService.utils.*;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * ProMod, master thesis project
 * User: Petr Zverina, petr.zverina@gmail.com
 * Date: 17:49:26, 10.10.2009
 */

/**
 * Implementation of MenuService and MenuControlService.
 */
public class MenuControlServiceImpl implements MenuControlService {

    private final Logger LOG = Logger.getLogger(MenuControlServiceImpl.class);

    private final JPopupMenu projectTreePopupMenu;
    private final JMenuBar menuBar;

    public MenuControlServiceImpl(){
        projectTreePopupMenu = ModelerSession.getComponentFactoryService().createPopupMenu();
        menuBar = ModelerSession.getComponentFactoryService().createMenuBar();
    }

    /** {@inheritDoc} */
    public boolean check() {
        return true;
    }

    /** {@inheritDoc} */
    public JMenuBar getMenuBar(){
        return menuBar;
    }

    /** {@inheritDoc} */
    public JPopupMenu getProjectTreePopupMenu() {
        return projectTreePopupMenu;
    }

    /** {@inheritDoc} */
    public InsertMenuItemResult insertProjectNavigationPopupMenuItem(final ProModAction proModAction,
                                                                      final MenuItemPosition menuItemPosition) {
        
        return insertProjectNavigationPopupMenuItem(
                proModAction,
                menuItemPosition,
                MenuSeparator.NONE
        );
    }

    /** {@inheritDoc} */
    public InsertMenuItemResult insertProjectNavigationPopupMenuItem(final ProModAction proModAction,
                                                                      final MenuItemPosition menuItemPosition,
                                                                      final MenuSeparator menuSeparator) {
        
        return registerPopupMenuItem(
                ModelerModel.MODELER_IDENTIFIER,
                proModAction,
                menuItemPosition,
                menuSeparator
        );
    }

    /** {@inheritDoc} */
    public InsertMenuItemResult insertPopupMenuItem(final String notationIdentifier,
                                                     final ProModAction proModAction,
                                                     final MenuItemPosition menuItemPosition){
        
        return insertPopupMenuItem(
                notationIdentifier,
                proModAction,
                menuItemPosition,
                MenuSeparator.NONE
        );
    }

    /** {@inheritDoc} */
    public InsertMenuItemResult insertPopupMenuItem(final String notationIdentifier,
                                                     final ProModAction proModAction,
                                                     final MenuItemPosition menuItemPosition,
                                                     final MenuSeparator menuSeparator) {

            return registerPopupMenuItem(
                    notationIdentifier,
                    proModAction,
                    menuItemPosition,
                    menuSeparator
            );
        }

    /**
     * Inserts the popup menu item.
     *
     * @param notationIdentifier is the identifier of notation
     * @param proModAction is the action to be added
     * @param menuItemPosition is the menu items required position
     * @param menuSeparator true if the separator should be added
     * @return an value of InsertMenuItemResult enum saying what was the the operation result
     */
    private InsertMenuItemResult registerPopupMenuItem(final String notationIdentifier,
                                                        final ProModAction proModAction,
                                                        final MenuItemPosition menuItemPosition,
                                                        final MenuSeparator menuSeparator) {


        // check whether all notations identifiers in includedInNotations set are valid identifiers of loaded notations

        final JPopupMenu popupMenu = getRequiredPopupMenu(notationIdentifier);

        // if notation doesn't support popup menu
        if(popupMenu == null){
            LOG.error("Notation (" + notationIdentifier +") doesn't support popup menu.");
            return InsertMenuItemResult.POPUP_NOT_SUPPORTED;
        }

        if(menuItemPosition == null || menuItemPosition.getHierarchicalPlacement() == null){
            LOG.error("Nullary MenuItemPosition info.");
            return InsertMenuItemResult.WRONG_PLACEMENT;
        }

        final String hierarchicalPlacement = menuItemPosition.getHierarchicalPlacement();
        final String[] placementParts;

        if(hierarchicalPlacement != null && !hierarchicalPlacement.isEmpty()){
            placementParts = hierarchicalPlacement.split(PLACEMENT_DELIMITER);
        } else {
            placementParts = new String[0];
        }

        // get all menus of popupMenuItems
        final JMenuItem[] popupMenuItems = new JMenuItem[popupMenu.getComponentCount()];
        for(int i = 0; i < popupMenu.getComponentCount(); i++){
            popupMenuItems[i] = (JMenuItem) popupMenu.getComponent(i);
        }

        return insertIntoMenu(
                popupMenuItems,
                placementParts,
                proModAction,
                null,
                null,
                popupMenu,
                menuSeparator,
                menuItemPosition,
                false
        );
    }

    /** {@inheritDoc} */
    public InsertMenuItemResult insertMainMenuItem(final ProModAction proModAction,
                                                      final MenuItemPosition menuItemPosition,
                                                      final boolean checkable){

        return insertMainMenuItem(proModAction, menuItemPosition, MenuSeparator.NONE, checkable);
    }

    /** {@inheritDoc} */
    public InsertMenuItemResult insertMainMenuItem(final ProModAction proModAction,
                                                      final MenuItemPosition menuItemPosition,
                                                      final MenuSeparator menuSeparator,
                                                      final boolean checkable){

        if(menuItemPosition == null || menuItemPosition.getHierarchicalPlacement() == null){
            LOG.error("Nullary MenuItemPosition info.");
            return InsertMenuItemResult.WRONG_PLACEMENT;
        }

        final String[] placementParts = menuItemPosition.getHierarchicalPlacement().split(PLACEMENT_DELIMITER);
        if(!areValidPlacementParts(placementParts)){
            LOG.error("Invalid any/all part(s) of MenuItemPosition info.");
            return InsertMenuItemResult.WRONG_PLACEMENT;
        }

        if(placementParts.length < 1){
            LOG.error("Illegal menu item location.");
            return InsertMenuItemResult.WRONG_PLACEMENT;
        }

        // get all menus of menuBar
        final JMenuItem[] menuBarItems = new JMenuItem[menuBar.getMenuCount()];
        for(int i = 0; i < menuBar.getMenuCount(); i++){
            menuBarItems[i] = menuBar.getMenu(i);
        }

        return insertIntoMenu(
                menuBarItems,
                placementParts,
                proModAction,
                null,
                menuBar,
                null,
                menuSeparator,
                menuItemPosition,
                checkable
        );
    }

    /**
     * Checks the menu item placement parts not to be null or an empty strings.
     *
     * @param placementParts is an array representing hierarchical menu item placement
     * @return true if all the placement parts are correct, false otherwise 
     */
    private boolean areValidPlacementParts(final String[] placementParts) {
        for(final String placement : placementParts){
            if(placement == null || placement.isEmpty()){
                return false;
            }
        }

        return true;
    }

    /** {@inheritDoc} */
    public InsertMenuItemResult insertMainMenuItem(final ProModAction proModAction,
                                                    final MenuItemPosition menuItemPosition){

        return insertMainMenuItem(
                proModAction,
                menuItemPosition,
                false);
    }

    /** {@inheritDoc} */
    public InsertMenuItemResult insertMainMenuItem(final ProModAction proModAction,
                                                    final MenuItemPosition menuItemPosition,
                                                    final MenuSeparator menuSeparator){

        return insertMainMenuItem(
                proModAction,
                menuItemPosition,
                menuSeparator,
                false);
    }

    /**
     * @param menu parent
     * @return items in the parent menu
     */
    private JMenuItem[] getMenuItems(final JMenu menu){
        final ArrayList<JMenuItem> array = new ArrayList<JMenuItem>();

        for(int i = 0; i < menu.getItemCount(); i++){
            final Object object = menu.getItem(i);
            if((object != null)){
                array.add(menu.getItem(i));
            }
        }

        return array.toArray(new JMenuItem[array.size()]);
    }

    /**
     * Inserts the menu action.
     *
     * @param parentMenu is the parent menu where the action is supposed to be added
     * @param parentPopupMenu is the parent popup menu where the action is supposed to be added
     * @param proModAction is the action to be inserted
     * @param menuSeparator the separator if one is supposed to be inserted
     * @param menuItemPosition required menu item position
     * @param checkable true if the menu item is supposed to be checkable
     * @return true if no error occurs, false otherwise
     */
    private InsertMenuItemResult insertAction(final JMenu parentMenu,
                                               final JPopupMenu parentPopupMenu,
                                               final ProModAction proModAction,
                                               final MenuSeparator menuSeparator,
                                               final MenuItemPosition menuItemPosition,
                                               final boolean checkable){
        
        // just one parent can be used
        if(parentMenu != null && parentPopupMenu != null){
            LOG.error("There can be just one parent menu item for new menu item insertion.");
            throw new IllegalArgumentException("Obscure parent menu item.");
        }

        if(proModAction == null){
            LOG.error("Nullary action cannot be inserted into the menu.");
            return InsertMenuItemResult.UNDEFINED_ACTION;
        }

        if(proModAction.getValue(Action.NAME) == null || ((String)proModAction.getValue(Action.NAME)).isEmpty()){
            return InsertMenuItemResult.UNDEFINED_ACTION;
        }

        final JMenuItem menuItem = (JMenuItem) getMenuItem(checkable);
        menuItem.setAction(proModAction);

        // check whether the is no same menu item in the this menu structure level
        final List<ModelerMenuItem> relatives =
                isUniqueMenuItemLabel(parentPopupMenu, parentMenu, (String) proModAction.getValue(Action.NAME));
        if(relatives != null){
            LOG.debug("Inserting a menu item to the menu hierarchy where a menu item with the same name (" +
                    proModAction.getValue(Action.NAME) + ") has already been inserted.");

            if(menuItem instanceof ModelerMenuItem){
                ((ModelerMenuItem) menuItem).setListOfRelatives(relatives);

                // if there are any relatives, always group them
                menuItemPosition.getPositionInfo().setRelativePosition(MenuItemPosition.PlacementStyle.GROUPED);
            }
        }

        if(!isUniqueAction(parentMenu, parentPopupMenu, proModAction)){
            LOG.error("Inserting the same action to the one menu more than once.");
            return InsertMenuItemResult.DUPLICIT_ACTION;
        }

        int insertionPosition = insertItem(menuItemPosition, menuItem, parentMenu, parentPopupMenu);
        if(insertionPosition < 0){
            return InsertMenuItemResult.WRONG_PLACEMENT;
        }

        // if items are grouped, only one separator can be inserted before and one after the group
        // this statement deals with inserting of separators AFTER the group
        if(MenuSeparator.AFTER.equals(menuSeparator)){
            if(relatives != null && relatives.size() > 0){ // grouping active
                insertionPosition += relatives.size() + 1;
            }
        }

        insertSeparator(menuSeparator, parentMenu, parentPopupMenu, insertionPosition);

        return InsertMenuItemResult.SUCCESS;
    }

    /**
     * Checks whether no the same action has been already inserted under the same menu parent.
     *
     * @param parentMenu is to menu parent
     * @param parentPopupMenu is the popup menu parent
     * @param proModAction is the action supposed to be inserted
     * @return true if there is no the same action under the parent, false otherwise
     */
    private boolean isUniqueAction(final JMenu parentMenu,
                                   final JPopupMenu parentPopupMenu,
                                   final ProModAction proModAction) {

        final Component[] menuComponets;
        if(parentMenu != null){
            menuComponets = parentMenu.getMenuComponents();
        } else if(parentPopupMenu != null){
            menuComponets = parentPopupMenu.getComponents();
        } else {
            LOG.error("Parent of menu item cannot be determined.");
            return false;
        }

        for(final Component component : menuComponets){
            if(component instanceof JMenuItem){
                final JMenuItem menuItem = (JMenuItem) component;
                final Action action = menuItem.getAction();

                if(action != null && action == proModAction){
                    return false;
                }
            }
        }


        return true;
    }

    /**
     * Inserts a menu separator to the required position. Never inserts two separators next to each other.
     *
     * @param menuSeparator hold information whether insert separator or not and the relative position
     * @param parentMenu is the parent menu item
     * @param parentPopupMenu is the parent popup menu item
     * @param insertionPosition is the actual position of the menu item that has been just added and to which is the
     * relative position of the separator specified
     */
    private void insertSeparator(final MenuSeparator menuSeparator,
                                 final JMenu parentMenu,
                                 final JPopupMenu parentPopupMenu, 
                                 final int insertionPosition) {

        // do not insert two separator next to each other
        final Component[] components;
        if(parentMenu != null){
            components = parentMenu.getMenuComponents();
        } else if(parentPopupMenu != null){
            components = parentPopupMenu.getComponents();
        } else {
            LOG.error("Obscurity in menu parents.");
            return;
        }

        if(!isAloneSeparator(components, insertionPosition, menuSeparator)){
            LOG.info("Skipping insertion of menu separator.");
            return;
        }

        // insert line separator before, if obtained
        if(MenuSeparator.BEFORE.equals(menuSeparator)){
            if(parentMenu != null) {
                parentMenu.insertSeparator(insertionPosition);
            } else{
                parentPopupMenu.insert(new JPopupMenu.Separator(), insertionPosition);
            }
        }

        // insert line separator after, if obtained
        if(MenuSeparator.AFTER.equals(menuSeparator)){
            if(parentMenu != null) {
                parentMenu.insertSeparator(insertionPosition + 1);
            } else {
                parentPopupMenu.insert(new JPopupMenu.Separator(), insertionPosition + 1);
            }
        }
    }

    /**
     *
     * @param menuComponents menu items
     * @param insertionPosition required menu item position
     * @param menuSeparator menu separator info if one is supposed to be insert
     * @return true if there could be the separator added
     */
    private boolean isAloneSeparator(final Component[] menuComponents,
                                     final int insertionPosition,
                                     final MenuSeparator menuSeparator) {
        if(menuSeparator != null){
            switch(menuSeparator){
                case AFTER:
                case BEFORE:
                    if(isValidMenuPosition(insertionPosition, menuComponents.length)){
                        if(menuComponents[insertionPosition] instanceof JPopupMenu.Separator){
                            return false;
                        }
                    }
            }

            switch(menuSeparator){
                case AFTER:
                    if(isValidMenuPosition(insertionPosition + 1, menuComponents.length)){
                        if(menuComponents[insertionPosition + 1] instanceof JPopupMenu.Separator){
                            return false;
                        }
                    }
                    break;
                case BEFORE:
                    if(isValidMenuPosition(insertionPosition - 1, menuComponents.length)){
                        if(menuComponents[insertionPosition - 1] instanceof JPopupMenu.Separator){
                            return false;
                        }
                    }
                    break;
            }
        }

        return true;
    }

    /**
     * @param position menu position
     * @param menuItems menu items
     * @return true if the given position is valid
     */
    private boolean isValidMenuPosition(final int position, final int menuItems){
        return !(position < 0 || position > (menuItems - 1));

    }

    /**
     * Performs the actual insertion of menu item.
     *
     * @param menuItemPosition is the specification of item's position
     * @param menuItem is the item to be inserted
     * @param parentMenu is the parent menu
     * @param parentPopupMenu is the parent popupMenu
     * @return the position to that has been the new menu item inserted
     */
    private int insertItem(final MenuItemPosition menuItemPosition,
                            final JMenuItem menuItem,
                            final JMenu parentMenu,
                            final JPopupMenu parentPopupMenu) {

        int insertionPosition = -1;

        //insert menuItem to the position specified (exact position, FIRST, LAST possibilities)
        if(MenuItemPosition.PlacementStyle.GROUPED.equals(menuItemPosition.getPlacementStyle())){
            final Component[] parentMenuItems;
            if(parentMenu != null && parentPopupMenu == null){
                parentMenuItems = parentMenu.getMenuComponents();
            }
            else if(parentMenu == null && parentPopupMenu != null){
                parentMenuItems = parentPopupMenu.getComponents();
            }
            else {
                LOG.error("Obscurity in menu item's parent.");
                return -1;
            }

            final int position = groupMenuItem(menuItem, parentMenuItems);
            if(parentMenu != null){
                parentMenu.insert(menuItem, position);
            } else {
                parentPopupMenu.insert(menuItem, position);
            }

            insertionPosition = position;
        }
        else if(menuItemPosition.getPosition() >= 0){
            if(parentMenu != null) {
                final int position = Math.min(menuItemPosition.getPosition(), parentMenu.getItemCount());
                parentMenu.insert(menuItem, position);
                insertionPosition = position;
            }
            else if(parentPopupMenu != null) {
                final int position = Math.min(menuItemPosition.getPosition(), parentPopupMenu.getComponentCount());
                parentPopupMenu.insert(menuItem, position);
                insertionPosition = position;
            }
        } else {
            if(MenuItemPosition.PlacementStyle.FIRST.equals(menuItemPosition.getPlacementStyle())){
                if(parentMenu != null) { parentMenu.insert(menuItem, 0); }
                else if(parentPopupMenu != null) { parentPopupMenu.insert(menuItem, 0); }

                insertionPosition = 0;

            } else if(MenuItemPosition.PlacementStyle.LAST.equals(menuItemPosition.getPlacementStyle())){
                if(parentMenu != null) {
                    parentMenu.add(menuItem);
                    insertionPosition = parentMenu.getMenuComponentCount() -1;
                }
                else if(parentPopupMenu != null) {
                    parentPopupMenu.add(menuItem);
                    insertionPosition = parentPopupMenu.getComponentCount() - 1;
                }
            }
        }

        return insertionPosition;
    }

    /**
     * Finds the menu item(s) having the same text under the same parent menu item and returns the position
     * (first possible) whether the new menu item is supposed to be inserted.
     *
     * @param menuItem is the menu item that is supposed to be inserted
     * @param parentMenuItems are all current menu items
     * @return the position where the new menu item is supposed to be inserted
     */
    private int groupMenuItem(final JMenuItem menuItem, final Component[] parentMenuItems) {
        for(int i = 0; i < parentMenuItems.length; i++){
            final Component component = parentMenuItems[i];

            if(component instanceof JMenuItem){
                final JMenuItem item = (JMenuItem) component;

                if(item instanceof ModelerMenuItem && item.getText().equalsIgnoreCase(menuItem.getText())){
                return i;
                }
            }
        }

        return parentMenuItems.length;
    }

    private ModelerMenuItem getMenuItem(final boolean checkable) {
        if(checkable){
            return new CheckableMenuItem();
        } else {
            return new DefaultMenuItem();
        }
    }

    /**
     * Finds the duplicity in menu item names and prepare the 'relatives' mechanism.
     * If there are more than one item having the same text on it, then the set if relatives is set to an empty
     * set. The last item having the same text on it have this set filled by references on the previous items
     * having the same text (relatives). All menu items that do not have any duplicity in text have this set set
     * to null.
     *
     * @param parentPopupMenu is the parent popup menu item
     * @param parentMenu is the parent menu item
     * @param displayName is the text that is supposed to be displayed on the menu item
     * @return the set holding references to the relatives, null if there is no text duplicity
     * @throws IllegalArgumentException when an obscurity in parent occurs
     */
    private List<ModelerMenuItem> isUniqueMenuItemLabel(
            final JPopupMenu parentPopupMenu, final JMenu parentMenu, final String displayName) throws IllegalArgumentException{

        if(parentMenu != null && parentPopupMenu != null){
            LOG.error("There can be just one parent menu item for new menu item insertion.");
            throw new IllegalArgumentException("Obscure parent menu item.");
        }

        final Component[] menuComponents;
        if(parentPopupMenu != null){
            menuComponents = parentPopupMenu.getComponents();
        } else {
            assert parentMenu != null;
            menuComponents = parentMenu.getMenuComponents();
        }

        List<ModelerMenuItem> relatives = null;

        for(final Component component : menuComponents){
            if(component instanceof JMenuItem){
                final JMenuItem menuItem = (JMenuItem) component;

                if(displayName.equals(menuItem.getText())){
                    if(menuItem instanceof ModelerMenuItem){
                        final ModelerMenuItem modelerMenuItem = (ModelerMenuItem) menuItem;

                        relatives = modelerMenuItem.getListOfRelatives();
                        modelerMenuItem.setListOfRelatives(new LinkedList<ModelerMenuItem>());

                        if(relatives == null){
                            relatives = new LinkedList<ModelerMenuItem>();
                        }

                        relatives.add(modelerMenuItem);
                    }
                }
            }
        }

        return relatives;
    }

    /**
     * Inserts the menu item.
     *
     * @param menuLevelItems is the parent menu item
     * @param placementParts is the hierarchical position
     * @param proModAction is the action to be inserted
     * @param parentMenu menu parent
     * @param parentMenuBar menu bar
     * @param parentPopupMenu parent popup menu
     * @param menuSeparator menu separator info
     * @param menuItemPosition menu item position info
     * @param checkable true if the action is supposed to be checkable
     * @return true id no error occurred, false otherwise
     */
    private InsertMenuItemResult insertIntoMenu(
                                final JMenuItem[] menuLevelItems,
                                final String[] placementParts,
                                final ProModAction proModAction,
                                final JMenu parentMenu,
                                final JMenuBar parentMenuBar,
                                final JPopupMenu parentPopupMenu,
                                final MenuSeparator menuSeparator,
                                final MenuItemPosition menuItemPosition,
                                final boolean checkable) {
        // parent has to be clear
        if((parentMenu != null && parentMenuBar != null)
                || (parentMenu != null && parentPopupMenu != null)
                || (parentMenuBar != null && parentPopupMenu != null)){
            LOG.error("Obscurity in parent of menu item");
            throw new IllegalArgumentException("Obscure parent menu item.");
        }

        if(proModAction == null){
            LOG.error("Nullary action to be inserted.");
            return InsertMenuItemResult.UNDEFINED_ACTION;
        }

        if(!ModelerSession.getActionControlService().isRegisteredAction(proModAction)){
            LOG.error("Unregistered action, action identifier: " + proModAction.getActionIdentifier());
            return InsertMenuItemResult.NOT_REGISTERED_ACTION;
        }

        if(placementParts.length == 0){
            if(parentMenu != null){
                return insertAction(parentMenu, null, proModAction, menuSeparator, menuItemPosition, checkable);
            } else if (parentPopupMenu != null){
                return insertAction(null, parentPopupMenu, proModAction, menuSeparator, menuItemPosition, checkable);
            } else {
                LOG.error("No parent set for the menu item.");
                return InsertMenuItemResult.WRONG_PLACEMENT;
            }
        } else {
            for (final JMenuItem menuItem : menuLevelItems) {
                if (menuItem.getText().equals(placementParts[0])) {
                    if (menuItem instanceof JMenu) {
                        final JMenu menu = (JMenu) menuItem;

                        final JMenuItem[] nextMenuItems = getMenuItems(menu);

                        return insertIntoMenu(
                                nextMenuItems,
                                Arrays.copyOfRange(placementParts, 1, placementParts.length),
                                proModAction,
                                menu,
                                null,
                                null,
                                menuSeparator,
                                menuItemPosition,
                                checkable
                        );
                    }
                }
            }

            JMenu parent = parentMenu;
            for (final String placementPart : placementParts) {
                final JMenu menu = ModelerSession.getComponentFactoryService().createMenu(placementPart);

                if (parent != null) {
                    parent.add(menu);
                } else if (parentMenuBar != null) {
                    parentMenuBar.add(menu);
                } else if (parentPopupMenu != null) {
                    parentPopupMenu.add(menu);
                } else {
                    LOG.error("There is no parent set to build the menu structure.");
                    throw new IllegalArgumentException("Obscure parent menu item.");
                }

                parent = menu;
            }

            return insertAction(parent, null, proModAction, menuSeparator, menuItemPosition, checkable);
        }
    }

    /**
     * Returns required JPopupMenu instance.
     *
     * @param notationIdentifier of a notation
     * @return notation's popup menu, or null if there is no such a notation with notation identifier,
     * or the notation doesn't support popup menu
     */
    private JPopupMenu getRequiredPopupMenu(final String notationIdentifier){
        if(ModelerModel.MODELER_IDENTIFIER.equals(notationIdentifier)){
            return projectTreePopupMenu;
        }

        if(ModelerSession.getNotationService().existNotation(notationIdentifier)){
            return ModelerSession.getNotationService().getNotation(notationIdentifier).getPopupMenu();

        } else {
            LOG.error("No existing notation cannot provide it's popup menu.");
            return null;
        }
    }

}
