
import java.util.ArrayList;

/**
 * Represents the player exploring the Haunted Mansion.
 * The player has a current room and an inventory of items.
 *
 * @author Colten Aguilar
 * @version 2026.11.07
 */
public class Player
{
    private Room currentRoom;
    private ArrayList<Item> inventory;

    /**
     * Create a player in the specified starting room.
     *
     * @param startingRoom The room where the player begins.
     */
    public Player(Room startingRoom)
    {
        currentRoom = startingRoom;
        inventory = new ArrayList<>();
    }

    /**
     * Get the player's current room.
     *
     * @return The room the player is currently in.
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Move the player into another room.
     *
     * @param room The room to move into.
     */
    public void setCurrentRoom(Room room)
    {
        currentRoom = room;
    }

    /**
     * Get the player's inventory.
     *
     * @return The list of items carried by the player.
     */
    public ArrayList<Item> getInventory()
    {
        return inventory;
    }
    
    /**
 * Add an item to the player's inventory.
 *
 * @param item The item to pick up.
 */
public void addItem(Item item)
{
    inventory.add(item);
}
}
