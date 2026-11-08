
/**
 * Represents an item found in the Haunted Mansion.
 * Each item has a name, description, and weight.
 *
 * @author Colten Aguilar
 * @version 2026.11.07
 */
public class Item
{
    private String name;
    private String description;
    private int weight;

    /**
     * Create a new item.
     *
     * @param name The item's name.
     * @param description The item's description.
     * @param weight The item's weight.
     */
    public Item(String name, String description, int weight)
    {
        this.name = name;
        this.description = description;
        this.weight = weight;
    }

    /**
     * Return the item's name.
     *
     * @return The name of the item.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return the item's description.
     *
     * @return The description of the item.
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Return the item's weight.
     *
     * @return The weight of the item.
     */
    public int getWeight()
    {
        return weight;
    }
}
